package com.example.xpjourney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.xpjourney.ui.theme.XPJourneyTheme
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Star
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.xpjourney.data.ProgressRepository
import com.example.xpjourney.ui.theme.Poppins
import com.example.xpjourney.ui.theme.XPJAccentPink
import com.example.xpjourney.viewmodel.ProgressViewModelContract
import com.example.xpjourney.viewmodel.UserProgressViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import kotlinx.coroutines.flow.StateFlow

val android.content.Context.dataStore by preferencesDataStore(name = "user_progress")


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repo = ProgressRepository(dataStore)
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(UserProgressViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return UserProgressViewModel(repo) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
        setContent {
            XPJourneyTheme {
                val navController = rememberNavController()
                val progressViewModel: UserProgressViewModel = viewModel(factory = factory)
                LaunchedEffect(Unit) {
                    progressViewModel.onLogin()
                }
                NavHost(navController = navController, startDestination = "dashboard") {
                    composable("dashboard") { DashboardScreen( navController, progressViewModel) }
                    composable("entry") { JournalEntryScreen() }
                    composable("badges") { BadgesScreen() }
                    composable("journey") { JourneyScreen() }
                    composable("profile") { ProfileScreen() }
                }
            }
        }
    }
}

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: ProgressViewModelContract
) {
    val progressState = viewModel.progressState.collectAsState()

    Column {
        Text("🔥Login Streak: ${progressState.value.currentStreak} days")
        Text("XP: ${progressState.value.xp}")
    }

    var xp by remember { mutableIntStateOf(120) }
    val xpGoal = 200
    val progress = xp.toFloat() / xpGoal.toFloat()
    val xpjBlue = Color(0xFFABC4FF)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(xpjBlue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Logo image
            Image(
                painter = painterResource(id = R.drawable.xpj_logo),
                contentDescription = "XPJ Logo",
                modifier = Modifier.size(125.dp)
            )
            // Tagline text
            Text(
                text = "Your story, your progress, your XP journey.",
                style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(top = 0.dp)
            )
            XPJButton("Add Entry", Icons.Filled.Add, onClick = {
                xp += 10
                navController.navigate("entry")
            })
            XPJButton("View Badges", Icons.Filled.Star, onClick = {
                navController.navigate("badges")
            })
            XPJButton("Start Journey", Icons.Filled.Directions, onClick = {
                navController.navigate("journey")
            })
            XPJButton("View Profile", Icons.Filled.Person, onClick = {
                navController.navigate("profile")
        })

        }
        Column(                     // XP tracker in top-right corner
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 25.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = progress,
                    modifier = Modifier.size(95.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                    strokeWidth = 9.dp
                )
            }
            Text("XP: $xp/$xpGoal", fontSize = 12.sp)
        }
    }
}
@Composable
fun JournalEntryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Journal Entry Screen")
    }
}

@Composable
fun BadgesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Badges Screen")
    }
}

@Composable
fun JourneyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Journey Screen")
    }
}

@Composable
fun XPJButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = XPJAccentPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 5.dp,
            pressedElevation = 8.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(icon, contentDescription = text)
        Spacer(Modifier.width(8.dp))
        Text(text, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("View Profile")
    }
}


@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    val fakeNavController = rememberNavController()
    val fakeViewModel = FakeProgressViewModel()

XPJourneyTheme {
    DashboardScreen (navController = fakeNavController, viewModel = fakeViewModel)
    }
}

class FakeProgressViewModel : ViewModel(), ProgressViewModelContract {
    override val progressState = MutableStateFlow(
        ProgressRepository.Progress(
            lastLoginDate = LocalDate.now(),
            currentStreak = 5,
            xp = 42
        )
    )
}
