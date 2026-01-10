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


@Preview(showBackground = true)
@Composable
fun RecentEntriesScreen(
    navController: NavController? = null,
    entries: List<String> = listOf(
        "Had a productive day working on XPJourney!",
        "Went for a walk and cleared my head.",
        "Learned more about Jetpack Compose."
    )
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Recent Entries",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(entries) { entry ->
                EntryCard(entry)
            }
        }
    }
}

@Composable
fun EntryCard(text: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            color = Color.DarkGray
        )
    }
}