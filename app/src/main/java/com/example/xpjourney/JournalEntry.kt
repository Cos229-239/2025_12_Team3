package com.example.xpjourney

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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Card
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch


@Entity(tableName = "journal_entries")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val body: String,
    val timestamp: Long = System.currentTimeMillis()
)

class JournalViewModel(private val repo: JournalRepository) : ViewModel() {
    fun saveEntry(title: String, content: String) {
        val entry = JournalEntry(title = title, body = content)

        viewModelScope.launch {
            repo.addEntry(entry)
        }
    }

    fun fetchSteamData(appId: String) {
        viewModelScope.launch {
            SteamApi.fetchGameDetails(appId)
        }
    }
}

class JournalViewModelFactory(private val repo: JournalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return JournalViewModel(repo) as T
    }
}

@Composable
fun AddEntryScreen(
    navController: NavController? = null
) {
    var entryTitle by remember { mutableStateOf("") }
    var gameName by remember { mutableStateOf("") }
    var entryText by remember { mutableStateOf("") }

    val context = LocalContext.current

    val db = remember { JournalDatabase.getDatabase(context) }
    val repo = remember { JournalRepository(db.JournalDao()) }
    val viewModel: JournalViewModel = viewModel(
        factory = JournalViewModelFactory(repo)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(XPJLightBlue)
            .padding(20.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .heightIn(min = 340.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "New Entry",
                    style = MaterialTheme.typography.headlineSmall,
                    color = XPJBlue
                )
                OutlinedTextField(
                    value = entryTitle,
                    onValueChange = { entryTitle = it },
                    label = { Text("Entry Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                )
                OutlinedTextField(
                    value = gameName,
                    onValueChange = { gameName = it },
                    label = { Text("Game Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                    )
                )
                OutlinedTextField(
                    value = entryText,
                    onValueChange = { entryText = it },
                    label = { Text("What happened?") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .heightIn(min = 120.dp),
                    maxLines = 6,
                    colors = OutlinedTextFieldDefaults.colors(
                    )
                )
                Button(
                    onClick = { viewModel.saveEntry(entryTitle, entryText) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = XPJBlue
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Save & Exit",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                OutlinedButton(
                    onClick = {
                        navController?.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = XPJBlue
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, XPJBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Discard Entry",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

    @Preview(showBackground = true)
    @Composable
    fun AddEntryScreenPreview() {
        AddEntryScreen()
    }
