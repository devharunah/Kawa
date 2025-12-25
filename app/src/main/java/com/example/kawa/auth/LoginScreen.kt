package com.example.kawa.auth


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kawa.ui.theme.*
import com.example.kawa.auth.components.InputLabel
import com.example.kawa.auth.components.KawaTextField
import com.example.kawa.R
import com.example.kawa.auth.classes.AuthManager
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    authManager: AuthManager = remember { AuthManager() },
    onNavigateToSignup: () -> Unit,
    onLoginClicked: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(green) // Brand Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = PoppinsFamily, // Using your custom font
                fontWeight = FontWeight.Bold,
                color = white
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Login to start trading coffee securely.",
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
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                InputLabel(text = "Email")
                KawaTextField(
                    value = email.trim(),
                    onValueChange = { email = it },
                    placeholder = "example@gmail.com"
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputLabel(text = "Password")
                KawaTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "******",
                    isPasswordField = true
                )
                Spacer(modifier = Modifier.height(32.dp))
                if (isLoading){
                    CircularProgressIndicator()
                } else{
                    Button(
                        onClick = {
                            val cleanPassword = password.trim()
                            if (email.isNotBlank() && cleanPassword.isNotBlank()) {
                                isLoading = true
                                scope.launch {
                                    val result = authManager.login(email, password)
                                    isLoading = false
                                    result.onSuccess {
                                        onLoginClicked()
                                    }.onFailure { error ->
                                        Toast.makeText(
                                            context,
                                            "Login failed: ${error.message}",
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
                            text = "Login",
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Or",
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Medium,
                        color = black, // Use black or a dark gray for text
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                OutlinedButton(
                    onClick = { /* TODO: Handle Google Sign In */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    border = BorderStroke(1.dp, gray) // Add a border
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google_icon), // Your logo file
                            contentDescription = "Google Logo",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Sign in with Google",
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.Medium,
                            color = black, // Use black or a dark gray for text
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )

                {
                    Text(
                        text = "Don't have an account? ",
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.bodyMedium,
                        color = gray
                    )
                    Text(
                        text = "Sign Up",
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        color = green,

                        modifier = Modifier
                            .clickable {
                                onNavigateToSignup()
                            }
                            .padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))


            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    KawaTheme {
        LoginScreen(
            onLoginClicked = {

            },
            onNavigateToSignup = {}
        )
    }
}