package com.example.xpjourney

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.xpjourney.ui.theme.XPJBlue
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.xpjourney.ui.theme.XPJLightBlue
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.graphics.Color


@Entity(tableName = "journal_entries")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val body: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Composable
fun AddEntryScreen(
    navController: NavController? = null
) {
    var gameName by remember { mutableStateOf("") }
    var entryText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(XPJLightBlue)
            .padding(20.dp)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "New Entry",
            style = MaterialTheme.typography.headlineSmall,
            color = XPJBlue
        )
        OutlinedTextField(
            value = gameName,
            onValueChange = { gameName = it },
            label = { Text("Game Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
        OutlinedTextField(
            value = entryText,
            onValueChange = { entryText = it },
            label = { Text("What happened?") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            maxLines = 6,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddEntryScreenPreview() {
    AddEntryScreen()
}