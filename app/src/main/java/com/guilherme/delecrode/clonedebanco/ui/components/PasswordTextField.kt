package com.guilherme.delecrode.clonedebanco.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guilherme.delecrode.clonedebanco.R
import com.guilherme.delecrode.clonedebanco.ui.theme.CloneDeBancoTheme
import com.guilherme.delecrode.clonedebanco.ui.theme.PrimaryFieldColor
import com.guilherme.delecrode.clonedebanco.ui.theme.PrimaryFieldTextColor


@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Senha",
    isError: Boolean = false,
    errorMessage: String = "",
    imeAction: ImeAction = ImeAction.Done,
    isPasswordVisible: Boolean = false,
    onVisibilityChange: (() -> Unit)? = null
) {
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(isPasswordVisible) }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryFieldColor, RoundedCornerShape(8.dp)),
            placeholder = { Text(label) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = PrimaryFieldTextColor
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (onVisibilityChange != null) {
                        onVisibilityChange()
                    } else {
                        passwordVisible = !passwordVisible
                    }
                }) {
                    Icon(
                        painter = if (onVisibilityChange != null) {
                            if (isPasswordVisible) painterResource(R.drawable.ic_visibility_24) else painterResource(R.drawable.ic_visibility_24)
                        } else {
                            if (passwordVisible)painterResource( R.drawable.ic_visibility_24) else painterResource(R.drawable.ic_visibility_24)
                        },
                        contentDescription = if (onVisibilityChange != null) {
                            if (isPasswordVisible) "Hide password" else "Show password"
                        } else {
                            if (passwordVisible) "Hide password" else "Show password"
                        },
                        tint = PrimaryFieldTextColor
                    )
                }
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { focusManager.clearFocus() }
            ),
            singleLine = true,
            visualTransformation = if (onVisibilityChange != null) {
                if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else {
                if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = PrimaryFieldColor,
                unfocusedContainerColor = PrimaryFieldColor,
                disabledContainerColor = PrimaryFieldColor,
                focusedIndicatorColor = PrimaryFieldTextColor,
                unfocusedIndicatorColor = PrimaryFieldColor,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(8.dp)
        )

        if (isError && errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordTextFieldPreview() {
    CloneDeBancoTheme {
        PasswordTextField(
            value = "password123",
            onValueChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
