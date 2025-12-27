package com.example.kawa.ui.theme.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kawa.auth.components.KawaTextField
import com.example.kawa.ui.theme.gray
import com.example.kawa.ui.theme.green
import com.example.kawa.ui.theme.profile.screens.ExperienceItem
import com.example.kawa.ui.theme.white

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
