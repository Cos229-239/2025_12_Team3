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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.ui.Alignment
import com.example.xpjourney.ui.theme.XPJLightBlue
import com.example.xpjourney.data.GameEntry
import com.example.xpjourney.ui.theme.XPJBlue
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

val sampleEntries = listOf(
    GameEntry("The Sims", "Had a productive day working on my house.", "Jan 12, 2026", "4:15 PM"),
    GameEntry("Stardew Valley", "Upgraded my barn and unlocked goats!", "Feb 11, 2026", "7:40 PM"),
    GameEntry("Hades", "Beat Meg without taking damage.", "Mar 10, 2026", "9:22 PM")
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
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(entries) { entry ->
                    EntryCard(entry)
                }
            }
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

@Preview(showBackground = true)
@Composable
fun RecentEntriesScreenPreview() {
    RecentEntriesScreen(entries = sampleEntries)
}


@Composable
fun EntryCard(entry: GameEntry) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Game name
            Text(
                text = entry.gameName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Date + time row
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = entry.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = entry.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Entry text
            Text(
                text = entry.entryText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
