package com.example.kawa.ui.theme.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kawa.ui.theme.InterFontFamily
import com.example.kawa.ui.theme.black
import com.example.kawa.ui.theme.gray
import com.example.kawa.ui.theme.green
import com.example.kawa.ui.theme.white

@Composable
fun KawaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
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
        keyboardOptions = keyboardOptions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = green, // Active state
            unfocusedBorderColor = gray, // Inactive state
            cursorColor = green,
            focusedContainerColor = white,
            unfocusedContainerColor = white
        )
    )
}

@Composable
fun InputLabel(text: String) {
    Text(
        text = text,
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        color = black,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

