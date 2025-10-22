package com.guilherme.delecrode.clonedebanco.data.mapper

import com.guilherme.delecrode.clonedebanco.data.model.LoginResponseDTO
import com.guilherme.delecrode.clonedebanco.domain.model.User
fun LoginResponseDTO.toUserDomain(): User {
    return User(
        name = customerName,
        accountNumber = accountNumber,
        branchNumber = branchNumber,
        checkingAccountBalance = checkingAccountBalance,
        id = id
    )
}
