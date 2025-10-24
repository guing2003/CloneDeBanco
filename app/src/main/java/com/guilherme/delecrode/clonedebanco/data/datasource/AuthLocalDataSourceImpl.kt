package com.guilherme.delecrode.clonedebanco.data.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.guilherme.delecrode.clonedebanco.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("auth_prefs")

class AuthLocalDataSourceImpl(private val context: Context) : AuthLocalDataSource {

    private object PreferencesKeys {
        val NAME_KEY = stringPreferencesKey("user_name")
        val ACCOUNT_KEY = stringPreferencesKey("account_number")
        val BRANCH_KEY = stringPreferencesKey("branch_number")
        val BALANCE_KEY = stringPreferencesKey("balance_user")
        val ID_KEY = stringPreferencesKey("user_id")
    }

    override fun getUser(): Flow<User?> = context.dataStore.data.map { prefs ->
        val name = prefs[PreferencesKeys.NAME_KEY]
        val account = prefs[PreferencesKeys.ACCOUNT_KEY]
        val branch = prefs[PreferencesKeys.BRANCH_KEY]
        val balance = prefs[PreferencesKeys.BALANCE_KEY]
        val id = prefs[PreferencesKeys.ID_KEY]

        if (name != null && account != null && branch != null && balance != null && id != null) {
            User(
                name = name,
                accountNumber = account,
                branchNumber = branch,
                checkingAccountBalance = balance,
                id = id
            )
        } else {
            null
        }
    }

    override suspend fun saveUser(user: User) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.NAME_KEY] = user.name
            prefs[PreferencesKeys.ACCOUNT_KEY] = user.accountNumber
            prefs[PreferencesKeys.BRANCH_KEY] = user.branchNumber
            prefs[PreferencesKeys.BALANCE_KEY] = user.checkingAccountBalance
            prefs[PreferencesKeys.ID_KEY] = user.id
        }
    }
}
