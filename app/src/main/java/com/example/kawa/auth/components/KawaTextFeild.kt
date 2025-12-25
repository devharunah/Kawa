package com.example.kawa.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kawa.ui.theme.*

@Composable
fun KawaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false,
    errorMessage: String? = null,
    isPasswordField: Boolean = false
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    color = gray,
                    fontFamily = InterFontFamily
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = InterFontFamily,
                color = black
            ),
            singleLine = true,
            isError = isError,
            keyboardOptions = keyboardOptions,
            visualTransformation = if (isPasswordField && !isPasswordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = if (isPasswordField) {
                {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password",

                            )
                    }
                }
            } else null,

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = green,
                unfocusedBorderColor = gray,
                errorBorderColor = Color.Red,
                cursorColor = green,
                focusedContainerColor = white,
                unfocusedContainerColor = white,
                errorContainerColor = white
            )
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.labelSmall,
                fontFamily = InterFontFamily,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }
    }
}

@Composable
fun InputLabel(text: String) {
    Text(
        text = text,
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        color = black,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}
@Preview(showBackground = true)
@Composable
fun KawaTextFieldPreview() {
    KawaTheme {
        KawaTextField(
            value = "",
            onValueChange = {},
            placeholder = "Enter your email",
            isError = false
        )
    }
}
