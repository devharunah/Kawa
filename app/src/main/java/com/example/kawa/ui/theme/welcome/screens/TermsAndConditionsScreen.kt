package com.example.kawa.ui.theme.welcome.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kawa.ui.theme.green
import com.example.kawa.ui.theme.white
import androidx.compose.ui.tooling.preview.Preview
import com.example.kawa.ui.theme.KawaTheme
import com.example.kawa.ui.theme.black
import com.example.kawa.ui.theme.gray

@Composable
fun TermsAndConditionsScreen(
    onDecline: () -> Unit,
    onAgree: () -> Unit
) {

    val brandColor = green
    val backgroundColor = white

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brandColor)
        ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = "Hello",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Before you proceed, please read carefully and accept our terms and conditions.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
        // 2. Content Card
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = backgroundColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Title inside card
                Text(
                    text = "Terms and conditions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Last updated 30 Oct 2025",
                    style = MaterialTheme.typography.labelMedium,
                    color = gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Scrollable Text
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("These Terms of Use, together with any and all other documents referred to herein, set out the terms of use under which you may use Our Application. ")
                            append("\n\nYou must read, agree, and accept these Terms of Use set out in this agreement which includes the terms and conditions set out below. ")
                            append("\n\nBy using any of the Kawa services, you become a User of the Kawa platform and you agree to be bound by the terms and conditions of this agreement with respect to the provision of such services.")
                            append("\n\nDefinitions and Interpretation:\n")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("User: ")
                            }
                            append("Means any individual who accesses Kawa.\n")
                            // Add more dummy text to demonstrate scrolling
                            append("\nIf you do not agree to be bound by the terms of this agreement, you must stop using Our Application immediately. We may amend this agreement without notice at any time by posting the amended terms on the Kawa application.")
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = black,
                        lineHeight = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 3. Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Decline Button (Outlined)
                    OutlinedButton(
                        onClick = onDecline,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(24.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, brandColor),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = brandColor
                        )
                    ) {
                        Text(text = "Decline", fontWeight = FontWeight.SemiBold)
                    }

                    // Agree Button (Filled)
                    Button(
                        onClick = onAgree,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = brandColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Agree", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TermaAndConditionScreen() {
    KawaTheme {
        TermsAndConditionsScreen(onDecline = {}, onAgree = {})
    }
}