package com.example.kawa.ui.theme.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kawa.ui.theme.InterFontFamily
import com.example.kawa.ui.theme.PoppinsFamily
import com.example.kawa.ui.theme.black
import com.example.kawa.ui.theme.gray
import com.example.kawa.ui.theme.green
import com.example.kawa.ui.theme.profile.screens.ExperienceItem
import com.example.kawa.ui.theme.white
import kotlin.text.ifEmpty

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