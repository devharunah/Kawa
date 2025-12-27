package com.example.kawa.ui.theme.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.kawa.ui.theme.* // Import your green, white, fonts
import com.example.kawa.auth.components.InputLabel
import com.example.kawa.auth.components.KawaTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostProductScreen(
    onNavigateBack: () -> Unit,
    onPostSuccess: () -> Unit
) {
    // UI State
    var coffeeType by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    // Dropdown States
    var roastLevelExpanded by remember { mutableStateOf(false) }
    var selectedRoastLevel by remember { mutableStateOf("") }
    val roastOptions = listOf("Light", "Medium", "Medium-Dark", "Dark")

    var processMethodExpanded by remember { mutableStateOf(false) }
    var selectedProcessMethod by remember { mutableStateOf("") }
    val processOptions = listOf("Washed", "Natural", "Honey", "Anaerobic")

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    // Image Picker Launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Post Product",
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = white
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = white)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = green)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(20.dp)
            ) {
                // 1. Image Upload Section
                Text(
                    text = "Product Image",
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyLarge,
                    color = black
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(white)
                        .border(1.dp, gray.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .clickable { launcher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImageUri),
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = "Add Photo",
                                tint = green,
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                "Tap to upload image",
                                color = gray,
                                fontFamily = InterFontFamily
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 2. Coffee Type Field
                InputLabel(text = "Coffee Type (e.g. Arabica)")
                KawaTextField(
                    value = coffeeType,
                    onValueChange = { coffeeType = it },
                    placeholder = "Enter coffee type"
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 3. Roast Level Dropdown
                InputLabel(text = "Roast Level")
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = selectedRoastLevel,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Select Roast Level", color = gray) },
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, "dropdown", tint = green)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { roastLevelExpanded = true },
                        shape = RoundedCornerShape(12.dp),
                        enabled = false, // Disable typing, handled by Box click
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledContainerColor = white,
                            disabledBorderColor = gray.copy(alpha = 0.5f),
                            disabledTextColor = black,
                            disabledPlaceholderColor = gray
                        )
                    )
                    // Invisible overlay to catch clicks
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { roastLevelExpanded = true }
                    )

                    DropdownMenu(
                        expanded = roastLevelExpanded,
                        onDismissRequest = { roastLevelExpanded = false },
                        modifier = Modifier.background(white)
                    ) {
                        roastOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, fontFamily = InterFontFamily) },
                                onClick = {
                                    selectedRoastLevel = option
                                    roastLevelExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 4. Process Method Dropdown
                InputLabel(text = "Process Method")
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = selectedProcessMethod,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Select Process Method", color = gray) },
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, "dropdown", tint = green)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        enabled = false,
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledContainerColor = white,
                            disabledBorderColor = gray.copy(alpha = 0.5f),
                            disabledTextColor = black,
                            disabledPlaceholderColor = gray
                        )
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { processMethodExpanded = true }
                    )
                    DropdownMenu(
                        expanded = processMethodExpanded,
                        onDismissRequest = { processMethodExpanded = false },
                        modifier = Modifier.background(white)
                    ) {
                        processOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, fontFamily = InterFontFamily) },
                                onClick = {
                                    selectedProcessMethod = option
                                    processMethodExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 5. Description Field
                InputLabel(text = "Description")
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Describe the flavor profile...", color = gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = white,
                        unfocusedContainerColor = white,
                        focusedBorderColor = green,
                        unfocusedBorderColor = gray.copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                // 6. Submit Button
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = green)
                    }
                } else {
                    Button(
                        onClick = {
                            if (coffeeType.isBlank() || selectedRoastLevel.isBlank() ||
                                selectedProcessMethod.isBlank() || description.isBlank() ||
                                selectedImageUri == null) {
                                Toast.makeText(context, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
                            } else {
                                isLoading = true
                                scope.launch {
                                    // TODO: CALL YOUR API FUNCTION HERE
                                    kotlinx.coroutines.delay(2000)
                                    isLoading = false
                                    onPostSuccess()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = green,
                            contentColor = white
                        )
                    ) {
                        Text(
                            text = "Post Product",
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                // Extra spacer for bottom padding
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview
@Composable
fun PostProductScreenPreview() {
    KawaTheme {
        PostProductScreen(onNavigateBack = {}, onPostSuccess = {})
    }
}
