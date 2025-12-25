package com.example.kawa.auth

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kawa.auth.classes.AuthManager
import com.example.kawa.ui.theme.*
import com.example.kawa.auth.components.InputLabel
import com.example.kawa.auth.components.KawaTextField
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    authManager: AuthManager = remember { AuthManager() },
    onSignUpClicked: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var secondName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(green)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = "Join Kawa",
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = PoppinsFamily, // Using your custom font
                fontWeight = FontWeight.Bold,
                color = white
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Sign up to start trading coffee securely.",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = InterFontFamily,
                color = white.copy(alpha = 0.9f)
            )
        }

        // 2. Form Content Section
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = white
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                InputLabel(text = "First Name")
                KawaTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    placeholder = "Enter first name"
                )

                Spacer(modifier = Modifier.height(16.dp))

                InputLabel(text = "Second Name")
                KawaTextField(
                    value = secondName,
                    onValueChange = { secondName = it },
                    placeholder = "Enter second name"
                )

                Spacer(modifier = Modifier.height(16.dp))

                InputLabel(text = "Email")
                KawaTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "example@gmail.com"
                )

                Spacer(modifier = Modifier.height(16.dp))

                InputLabel(text = "Password")
                KawaTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "*********",
                    isPasswordField = true
                )

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(32.dp))

                if (isLoading){
                    CircularProgressIndicator()
                }
                else{
                    Button(
                        onClick = {
                            val cleanEmail = email.trim()
                            val cleanPassword = password.trim()
                            if (cleanEmail.isNotBlank() && cleanPassword.isNotBlank()) {
                                isLoading = true
                                scope.launch {
                                    val result = authManager.signUp(email, password)
                                    isLoading = false
                                    result.onSuccess {
                                        onSignUpClicked()
                                    }.onFailure { error ->
                                        Toast.makeText(
                                            context,
                                            "Sign up failed: ${error.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            } else {
                                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = green, // Brand Green
                            contentColor = white
                        )
                    ) {
                        Text(
                            text = "Sign Up",
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


// Simple Data Class for Country
data class CountryCode(val flag: String, val code: String)

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    KawaTheme {
        SignUpScreen(onSignUpClicked = {})
    }
}
