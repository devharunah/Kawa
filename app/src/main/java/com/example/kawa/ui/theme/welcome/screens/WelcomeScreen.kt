package com.example.kawa.ui.theme.welcome.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kawa.R
import kotlinx.coroutines.launch
import androidx.compose.ui.tooling.preview.Preview
import com.example.kawa.ui.theme.KawaTheme

@Composable
fun WelcomeScreen(
    onNavigateToTerms: () -> Unit
) {
    val pages = listOf(
        OnboardingPage(
            title = "Discover Insights",
            description = "Welcome to Kawa. Explore the coffee marketplace and see what's new. Read posts directly from farmers and gain market insights.",
            imageRes = R.drawable.black_coffee
        ),
        OnboardingPage(
            title = "Connect with Farmers",
            description = "Find professional coffee farmers. Connect, trade, and build your network within the coffee community effortlessly.",
            // REPLACE with your actual coffee image resource ID
            imageRes = R.drawable.first_screen_bg_imgres
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    // Root container to layer Background -> Overlay -> Content
    Box(modifier = Modifier.fillMaxSize()) {

        // 1. BACKGROUND IMAGE
        // This image changes based on the current page
        Image(
            painter = painterResource(id = pages[pagerState.currentPage].imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.85f), // Darker at bottom
                            Color.Black.copy(alpha = 0.2f)   // Lighter at top
                        )
                    )
                )
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Pager Section (Text Content)
            // Weight is adjusted to push text slightly lower or center it
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(0.8f)
            ) { position ->
                OnboardingPageContent(page = pages[position])
            }

            // Bottom Section: Indicators and Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Skip Text Button
                TextButton(onClick = onNavigateToTerms) {
                    Text(
                        text = "SKIP",
                        color = Color.White.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Black
                    )
                }

                // Dots Indicator
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pages.size) { iteration ->
                        val color = if (pagerState.currentPage == iteration)
                            MaterialTheme.colorScheme.primary // Use primary color or White
                        else
                            Color.White.copy(alpha = 0.4f)

                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(if (pagerState.currentPage == iteration) 10.dp else 8.dp)
                        )
                    }
                }

                // Next / Finish Button
                Button(
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage < pages.size - 1) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            } else {
                                onNavigateToTerms()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(text = if (pagerState.currentPage == pages.size - 1) "GET STARTED" else "NEXT")
                }
            }
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // NOTE: Since the background covers the whole screen, we usually remove the small logo
        // image here, OR we make it smaller/white tint.
        // If you want just text over the big background, comment out the Image below.

        /*
        Image(
            painter = painterResource(id = R.drawable.coffee_beans),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp).padding(bottom = 32.dp),
            contentScale = ContentScale.Fit
        )
        */

        Spacer(modifier = Modifier.height(100.dp)) // Push text down a bit

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White, // White text to contrast with dark overlay
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.85f), // White text with slight transparency
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    KawaTheme {
        WelcomeScreen(
            onNavigateToTerms = {}
        )
    }
}
data class OnboardingPage(
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int // Added this to hold the background ID
)
