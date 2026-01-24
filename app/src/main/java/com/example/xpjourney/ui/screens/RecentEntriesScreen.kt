package com.example.xpjourney.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import com.example.xpjourney.ui.theme.XPJLightBlue
import com.example.xpjourney.data.GameEntry
import com.example.xpjourney.ui.theme.XPJBlue
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Button

val sampleEntries = listOf(
    GameEntry(
        title = "Building Day",
        gameName = "The Sims",
        entryText = "Had a  productive day working on building my house.",
        date = "Jan 12, 2026",
        time = "4:15 PM"
    ),
    GameEntry(
        title = "Barn Upgrade",
        gameName = "Stardew Valley",
        entryText = "Upgraded my barn and unlocked goats!",
        date = "Feb 21, 2026",
        time = "7:40 PM"
    ),
    GameEntry(
        title = "Flawless Victory",
        gameName = "Hades",
        entryText = "Beat Meg without taking any damage.",
        date = "Mar 30, 2026",
        time = "9:22 PM"
    )
)

@Composable
fun RecentEntriesScreen(
    navController: NavController? = null,
    entries: List<GameEntry> = sampleEntries
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(XPJLightBlue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Recent Entries",
                style = MaterialTheme.typography.headlineLarge,
                color = XPJBlue,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 50.dp)
            ) {
                items(entries) { entry ->
                    EntryCard(
                        entry = entry,
                        onEditClick = {
                            navController?.navigate("editEntry/${entry.id}")
                        }
                    )
                }
            }
        }
        Button(
            onClick = { navController?.navigate("dashboard") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = XPJBlue
            ),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp)
        ) {
            Text("Back")
        }

        FloatingActionButton(
            onClick = { navController?.navigate("add_entry") },
            containerColor = XPJBlue,
            contentColor = Color.White,
            modifier = Modifier
                .size(94.dp)
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Entry",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}


@Composable
fun EntryCard(entry: GameEntry,
              onEditClick: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1F1F1F),
            Color(0xFF2A2A2A)
        )
    )
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
        )
        Column(modifier = Modifier.padding(16.dp)) {
            //Title + Game name + Edit button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = entry.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = XPJBlue
                    )
                    Text(
                        text = entry.gameName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = XPJBlue,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Entry",
                        tint = XPJBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Date + time row
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = entry.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = entry.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Entry text
            Text(
                text = entry.entryText,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            // XP badge (static for now)
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .background(Color(0xFFFF8FA3), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "+15 XP",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecentEntriesScreenPreview() {
    RecentEntriesScreen(entries = sampleEntries)
}