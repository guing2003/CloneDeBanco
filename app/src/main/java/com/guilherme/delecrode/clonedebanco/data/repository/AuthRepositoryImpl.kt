package com.guilherme.delecrode.clonedebanco.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.guilherme.delecrode.clonedebanco.data.mapper.toUserDomain
import com.guilherme.delecrode.clonedebanco.data.remote.service.AuthApiService
import com.guilherme.delecrode.clonedebanco.domain.model.User
import com.guilherme.delecrode.clonedebanco.domain.repository.AuthRepository
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("auth_prefs")
class AuthRepositoryImpl(
    private val apiService: AuthApiService,
    private val context: Context
) : AuthRepository {

    companion object {
        private val NAME_KEY = stringPreferencesKey("user_name")
        private val ACCOUNT_KEY = stringPreferencesKey("account_number")
        private val BRANCH_KEY = stringPreferencesKey("branch_number")
        private val BALANCE_KEY = stringPreferencesKey("balance_user")
        private val ID_KEY = stringPreferencesKey("user_id")
    }

    override suspend fun login(): Result<User> {
        return try {
            val response = apiService.login()
            if (response.isSuccessful) {
                val body = response.body()
                if (!body.isNullOrEmpty()) {
                    val user = body.first().toUserDomain()
                    saveUser(user)
                    Result.success(user)
                } else {
                    Result.failure(Exception("Resposta vazia do servidor"))
                }
            } else {
                Result.failure(Exception("Erro da API: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Falha ao fazer login: ${e.message}", e))
        }
    }

    private suspend fun saveUser(user: User) {
        context.dataStore.edit { prefs ->
            prefs[NAME_KEY] = user.name
            prefs[ACCOUNT_KEY] = user.accountNumber
            prefs[BRANCH_KEY] = user.branchNumber
            prefs[BALANCE_KEY] = user.checkingAccountBalance
            prefs[ID_KEY] = user.id
        }
    }

    override fun getUser() = context.dataStore.data.map { prefs ->
        val name = prefs[NAME_KEY] ?: return@map null
        val account = prefs[ACCOUNT_KEY] ?: return@map null
        val branch = prefs[BRANCH_KEY] ?: return@map null
        val balance = prefs[BALANCE_KEY] ?: return@map null
        val id = prefs[ID_KEY] ?: return@map null

        User(
            name = name,
            accountNumber = account,
            branchNumber = branch,
            checkingAccountBalance = balance,
            id = id
        )
    }

}