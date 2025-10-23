package com.guilherme.delecrode.clonedebanco.data.mapper

import com.guilherme.delecrode.clonedebanco.data.model.UserResponseDTO
import com.guilherme.delecrode.clonedebanco.domain.model.User
fun UserResponseDTO.toUserDomain(): User {
    return User(
        name = customerName,
        accountNumber = accountNumber,
        branchNumber = branchNumber,
        checkingAccountBalance = checkingAccountBalance,
        id = id
    )
}
