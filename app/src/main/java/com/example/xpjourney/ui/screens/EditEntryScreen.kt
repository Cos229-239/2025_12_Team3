package com.example.xpjourney.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.xpjourney.JournalDao
import com.example.xpjourney.viewmodel.JournalViewModel
import com.example.xpjourney.viewmodel.JournalViewModelFactory
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import com.example.xpjourney.JournalEntry
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.xpjourney.ui.theme.XPJBlue
import com.example.xpjourney.ui.theme.XPJLightBlue
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.Button





@Composable
fun EditEntryScreen(
    entryId: Int,
    navController: NavController,
    dao: JournalDao
) {
    // Viewmodel
    val viewModel: JournalViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = JournalViewModelFactory(dao)
    )
    //Loaded entry
    val entryState = remember { mutableStateOf<JournalEntry?>(null)
    }
    LaunchedEffect(entryId) {
        entryState.value = viewModel.getEntryById(entryId)
    }

    // Editable text fields
    val titleState = remember { mutableStateOf("") }
    val bodyState = remember { mutableStateOf("") }

    // Populate fields when entry loads
    LaunchedEffect(entryState.value) {
        entryState.value?.let { loaded ->
            titleState.value = loaded.title
            bodyState.value = loaded.body
        }
    }
    // UI
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
                    text = "Edit Entry",
                    style = MaterialTheme.typography.headlineSmall,
                    color = XPJBlue
                )

                OutlinedTextField(
                    value = titleState.value,
                    onValueChange = { titleState.value = it },
                    label = { Text("Entry Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )

                OutlinedTextField(
                    value = bodyState.value,
                    onValueChange = { bodyState.value = it },
                    label = { Text("What happened?") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .heightIn(min = 120.dp),
                    maxLines = 6
                )

                Button(
                    onClick = {
                        viewModel.updateEntry(
                            id = entryId,
                            newTitle = titleState.value,
                            newBody = bodyState.value
                        )
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = XPJBlue
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Save Changes",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = XPJBlue
                    ),
                    border = BorderStroke(1.dp, XPJBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
