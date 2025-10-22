package com.guilherme.delecrode.clonedebanco.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guilherme.delecrode.clonedebanco.ui.theme.CloneDeBancoTheme
import com.guilherme.delecrode.clonedebanco.ui.theme.PrimaryFieldColor
import com.guilherme.delecrode.clonedebanco.ui.theme.PrimaryFieldTextColor


@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Email",
    isError: Boolean = false,
    errorMessage: String = "",
    imeAction: ImeAction = ImeAction.Next
) {
    val focusManager = LocalFocusManager.current

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
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = PrimaryFieldTextColor
                )
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { focusManager.clearFocus() }
            ),
            singleLine = true,
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
fun EmailTextFieldPreview() {
    CloneDeBancoTheme {
        EmailTextField(
            value = "user@example.com",
            onValueChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
