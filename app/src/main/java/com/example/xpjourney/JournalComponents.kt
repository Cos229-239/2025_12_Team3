package com.example.xpjourney

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun JournalTextField(currentText: String, onTextChange: (String) -> Unit, label: String, modifier: Modifier = Modifier) {
    TextField(
        label = { Text(label) },
        value = currentText,
        onValueChange = onTextChange,
        modifier = modifier
    )
}

@Composable
fun JournalButton(onClick: () -> Unit, buttonLabel: String) {
    Button(onClick = {onClick()}) {
        Text(buttonLabel)
    }
}

@Composable
fun JournalButton(modifier: Modifier, onClick: () -> Unit, buttonLabel: String) {
    Button(onClick = {onClick()}) {
        Text(buttonLabel)
    }
}

/*class JournalRepository(private val dao: JournalDao) {
    suspend fun insert(entry: JournalEntry) = dao.insertEntry(entry)
    suspend fun getAll() = dao.getAllEntries()
}

class JournalViewModel(private val repo: JournalRepository) : ViewModel() {
    fun saveEntry(title: String, content: String) {
        val entry = JournalEntry(title = title, body = content)

        viewModelScope.launch {
            repo.insert(entry)
        }
    }
}

class JournalViewModelFactory(private val repo: JournalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return JournalViewModel(repo) as T
    }
}*/

@Composable
fun TempJournalScreen(navController: NavController) {
    val context = LocalContext.current

    val db = remember { JournalDatabase.getDatabase(context) }
    val repo = remember { JournalRepository(db.JournalDao()) }
    /*val viewModel: JournalViewModel = viewModel(
        factory = JournalViewModelFactory(repo)
    )*/

    var gameName by remember {mutableStateOf("")}
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    Box (
        modifier = Modifier.fillMaxSize().background(color = Color(red = 226, green = 234, blue = 252))
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

            Row(modifier = Modifier.fillMaxWidth().padding(10.dp).height(75.dp)
                .background(color = Color(red = 112, green = 153, blue = 255)),
                Arrangement.Center, Alignment.CenterVertically) {
                JournalTextField(
                    currentText = gameName,
                    onTextChange = {gameName = it},
                    label = "Enter Game Name"
                )
                Spacer(modifier = Modifier.width(10.dp))
                JournalButton(onClick = {}, buttonLabel = "\uD83D\uDD0D")
            }

            Box(modifier = Modifier.fillMaxWidth().padding(10.dp).background(color = Color(red = 112, green = 153, blue = 255))) {
                JournalTextField(
                    currentText = title,
                    onTextChange = {title = it},
                    label = "Entry Name",
                    modifier = Modifier.padding(20.dp)
                )
            }
            Box(modifier = Modifier.fillMaxWidth().padding(10.dp).background(color = Color(red = 112, green = 153, blue = 255))) {
                JournalTextField(
                    currentText = content,
                    onTextChange = {content = it},
                    label = "Write Entry Here",
                    modifier = Modifier
                        .padding(20.dp).fillMaxWidth().defaultMinSize(minHeight = 500.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(), Arrangement.Center
            ) {
                JournalButton(onClick = {navController.navigate("dashboard")}, buttonLabel = "Save and Exit")
                Spacer(modifier = Modifier.width(10.dp))
                JournalButton(onClick = {navController.navigate("dashboard")}, buttonLabel = "Discard Entry")
            }
        }
    }

}