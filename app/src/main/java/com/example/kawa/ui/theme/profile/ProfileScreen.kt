package com.example.kawa.ui.theme.profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kawa.auth.components.InputLabel
import com.example.kawa.auth.components.KawaTextField
import com.example.kawa.ui.theme.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import java.util.Locale

// Data class to hold Experience items
data class ExperienceItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    var company: String = "",
    var role: String = "",
    var fromDate: String = "",
    var toDate: String = "",
    var skills: String = ""
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit
) {
    // --- State Management ---
    var isEditing by remember { mutableStateOf(false) }

    // User Data States
    var firstName by remember { mutableStateOf("John") } // Replace with AuthManager data
    var secondName by remember { mutableStateOf("Doe") }
    var association by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("Kampala, Uganda") }

    // Experience Data
    val experienceList = remember { mutableStateListOf<ExperienceItem>() }

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Location Permission State
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (isEditing) "Edit Profile" else "My Profile",
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (isEditing) isEditing = false else onNavigateBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = black)
                    }
                },
                actions = {
                    if (!isEditing) {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(Icons.Outlined.Edit, contentDescription = "Edit", tint = green)
                        }
                    } else {
                        IconButton(onClick = {
                            // TODO: Save data to Firebase/Supabase here
                            isEditing = false
                            Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Default.Check, contentDescription = "Save", tint = green)
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = white)
            )
        },
        containerColor = Color(0xFFF9F9F9) // Very light gray background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- Profile Picture Section ---
            Box(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .size(120.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(alpha = 0.3f)),
                    tint = gray
                )

                // Edit Camera Icon Overlay
                if (isEditing) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(green)
                            .border(2.dp, white, CircleShape)
                            .clickable { /* TODO: Open Image Picker */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Change Photo",
                            tint = white,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            if (!isEditing) {
                // --- READ ONLY VIEW ---
                Text(
                    text = "$firstName $secondName",
                    style = MaterialTheme.typography.headlineMedium,
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Bold,
                    color = black
                )

                if (association.isNotEmpty()) {
                    Text(
                        text = association,
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = InterFontFamily,
                        color = gray
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Info Cards
                ProfileInfoCard(icon = Icons.Outlined.LocationOn, label = "Location", value = location)
                if (company.isNotEmpty()) ProfileInfoCard(icon = Icons.Outlined.Business, label = "Company", value = company)

                Spacer(modifier = Modifier.height(16.dp))

                // Experience Section Display
                if (experienceList.isNotEmpty()) {
                    SectionHeader(title = "Experience")
                    experienceList.forEach { exp ->
                        ExperienceReadOnlyItem(exp)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Logout Button
                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth(0.9f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                    border = BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f))
                ) {
                    Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Log Out", fontFamily = PoppinsFamily)
                }
                Spacer(modifier = Modifier.height(24.dp))

            } else {
                // --- EDIT MODE VIEW ---
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    InputLabel("First Name")
                    KawaTextField(value = firstName, onValueChange = { firstName = it }, placeholder = "John")
                    Spacer(modifier = Modifier.height(16.dp))

                    InputLabel("Second Name")
                    KawaTextField(value = secondName, onValueChange = { secondName = it }, placeholder = "Doe")
                    Spacer(modifier = Modifier.height(16.dp))

                    InputLabel("Association / Recognition (Optional)")
                    KawaTextField(
                        value = association,
                        onValueChange = { association = it },
                        placeholder = "e.g. Certified Q Grader"
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    InputLabel("Company / Cooperative (Optional)")
                    KawaTextField(value = company, onValueChange = { company = it }, placeholder = "e.g. Kawa Coffee Co.")
                    Spacer(modifier = Modifier.height(16.dp))

                    // Location with Auto-Detect
                    InputLabel("Location")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.weight(1f)) {
                            KawaTextField(value = location, onValueChange = { location = it }, placeholder = "City, Country")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = {
                                if (locationPermissionsState.allPermissionsGranted) {
                                    getCurrentLocation(context) { address ->
                                        location = address
                                    }
                                } else {
                                    locationPermissionsState.launchMultiplePermissionRequest()
                                }
                            },
                            modifier = Modifier
                                .background(green.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                                .border(1.dp, green, RoundedCornerShape(12.dp))
                        ) {
                            Icon(Icons.Default.MyLocation, contentDescription = "Get Location", tint = green)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Experience Editor Section
                    SectionHeader(title = "Experience History")

                    experienceList.forEachIndexed { index, item ->
                        ExperienceEditItem(
                            item = item,
                            onUpdate = { updated -> experienceList[index] = updated },
                            onRemove = { experienceList.removeAt(index) }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Add Experience Button
                    OutlinedButton(
                        onClick = { experienceList.add(ExperienceItem()) },
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(1.dp, green),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = green)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Text("Add Experience", fontFamily = PoppinsFamily)
                    }

                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }
}

// --- Helper Composables ---

@Composable
fun ProfileInfoCard(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 6.dp)
            .background(white, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = green, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = gray, fontFamily = InterFontFamily)
            Text(text = value, style = MaterialTheme.typography.bodyLarge, color = black, fontFamily = PoppinsFamily, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Bold,
        color = black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    )
}

@Composable
fun ExperienceReadOnlyItem(item: ExperienceItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = white),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(item.role.ifEmpty { "Role" }, fontWeight = FontWeight.Bold, fontFamily = PoppinsFamily, fontSize = 16.sp)
            Text(item.company.ifEmpty { "Company" }, color = green, fontFamily = InterFontFamily, fontSize = 14.sp)
            Text("${item.fromDate} - ${item.toDate}", color = gray, style = MaterialTheme.typography.bodySmall)
            if (item.skills.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Skills: ${item.skills}", style = MaterialTheme.typography.bodySmall, color = black.copy(alpha = 0.7f))
            }
        }
    }
}

@Composable
fun ExperienceEditItem(item: ExperienceItem, onUpdate: (ExperienceItem) -> Unit, onRemove: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = white),
        border = BorderStroke(1.dp, gray.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Job Details", style = MaterialTheme.typography.labelLarge, color = green)
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remove",
                    tint = Color.Red.copy(alpha = 0.6f),
                    modifier = Modifier.clickable { onRemove() }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            KawaTextField(value = item.role, onValueChange = { onUpdate(item.copy(role = it)) }, placeholder = "Role (e.g. Barista)")
            Spacer(modifier = Modifier.height(8.dp))

            KawaTextField(value = item.company, onValueChange = { onUpdate(item.copy(company = it)) }, placeholder = "Company Name")
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f)) {
                    KawaTextField(value = item.fromDate, onValueChange = { onUpdate(item.copy(fromDate = it)) }, placeholder = "From (Year)")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.weight(1f)) {
                    KawaTextField(value = item.toDate, onValueChange = { onUpdate(item.copy(toDate = it)) }, placeholder = "To (Year)")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            KawaTextField(value = item.skills, onValueChange = { onUpdate(item.copy(skills = it)) }, placeholder = "Skills / Tools used")
        }
    }
}

// --- Logic: Get Current Location ---
@SuppressLint("MissingPermission")
private fun getCurrentLocation(context: Context, onLocationFound: (String) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                // Get address from lat/long
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    val city = address.locality ?: address.subAdminArea ?: ""
                    val country = address.countryName ?: ""

                    if (city.isNotEmpty() && country.isNotEmpty()) {
                        onLocationFound("$city, $country")
                    } else {
                        onLocationFound(country)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Could not fetch address name", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Location not found. Try opening Maps first.", Toast.LENGTH_LONG).show()
        }
    }
}
