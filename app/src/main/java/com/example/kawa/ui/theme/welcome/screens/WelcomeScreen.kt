package com.example.kawa.ui.theme.welcome.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kawa.R
import com.example.kawa.ui.theme.KawaTheme
import com.example.kawa.ui.theme.green
import com.example.kawa.ui.theme.white
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(
    onNavigateToTerms: () -> Unit
) {

    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = white,
        bottomBar = {
            // Bottom Section: Button and Indicators
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .navigationBarsPadding(), // Handle gesture bar overlap
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Action Button
                Button(
                    onClick = {
                        scope.launch {
                            onNavigateToTerms()
                        }
                    },
                    modifier = Modifier

                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = green,
                        contentColor = white
                    ),
                    shape = RoundedCornerShape(28.dp)

                ) {
                    Text(
                        text =  "Get Started",
                        color = white,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

            }
        }
    ) { paddingValues ->
        // Main Content Area
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.coffee_beans),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Fit,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Learn, Trade, Coffee",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = green,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    KawaTheme {
        WelcomeScreen(onNavigateToTerms = {})
    }
}