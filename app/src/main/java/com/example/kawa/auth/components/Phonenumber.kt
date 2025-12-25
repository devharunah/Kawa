package com.example.kawa.auth.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.kawa.ui.theme.*
import com.example.kawa.auth.CountryCode

@Composable
fun PhoneNumberInput(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    selectedCountry: CountryCode,
    onCountrySelected: (CountryCode) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }

    // Hardcoded list of countries
    val countries = listOf(
        CountryCode("🇺🇬", "+256"), // Uganda
        CountryCode("🇰🇪", "+254"), // Kenya
        CountryCode("🇹🇿", "+255"), // Tanzania
        CountryCode("🇷🇼", "+250"), // Rwanda
        CountryCode("🇺🇸", "+1"),   // USA
        CountryCode("🇬🇧", "+44")   // UK
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top // Align Top so error text doesn't break layout
        ) {
            // Country Code Selector
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .border(1.dp, gray, RoundedCornerShape(12.dp))
                    .clickable { expanded = true }
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${selectedCountry.flag} ${selectedCountry.code}",
                        fontFamily = InterFontFamily,
                        color = black,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select Country",
                        tint = gray
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(white)
                ) {
                    countries.forEach { country ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "${country.flag}  ${country.code}",
                                    fontFamily = InterFontFamily,
                                    color = black
                                )
                            },
                            onClick = {
                                onCountrySelected(country)
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Phone Input Field
            // We reuse KawaTextField logic here manually or wrap it
            // Since KawaTextField is a column, we can't easily put it in a Row perfectly
            // without width adjustments. Here is the direct implementation for best alignment:

            KawaTextField(
                value = phoneNumber,
                onValueChange = {
                    // Only allow numeric input
                    if (it.all { char -> char.isDigit() }) {
                        onPhoneNumberChange(it)
                    }
                },
                placeholder = "712 345 678",
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),

            )
        }
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
