package com.example.xpjourney

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.xpjourney.ui.theme.XPJourneyTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.Star
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
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
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import com.example.xpjourney.ui.theme.XPJBlue
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import com.example.xpjourney.ui.screens.RecentEntriesScreen
import com.example.xpjourney.ui.screens.sampleEntries
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import com.example.xpjourney.ui.theme.Badge
import com.example.xpjourney.ui.theme.BadgeManager
import com.example.xpjourney.ui.theme.ChallengesScreen
import com.example.xpjourney.ui.theme.DarkBlueText


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
                var isLoggedIn by remember { mutableStateOf(false) }


                NavHost(
                    navController = navController,
                    startDestination = if (isLoggedIn) "dashboard" else "login"
                ) {
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                isLoggedIn = true
                                navController.navigate("dashboard") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("dashboard") { DashboardScreen( navController, progressViewModel) }
                    composable("entry") { JournalEntryScreen() }
                    composable("badges") { BadgesScreen(navController) }
                    composable("journey") { JourneyScreen() }
                    composable("profile") { ProfileScreen() }
                    composable("challenges") { ChallengesScreen(navController) }
                    composable("recent_entries") {
                        RecentEntriesScreen(
                            navController = navController,
                            entries = sampleEntries
                        ) }
                    composable("add_entry") {
                        AddEntryScreen(navController = navController)
                    }
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
            // Streak
            Text(
                text = "🔥Current Streak: ${progressState.value.currentStreak} days",
                style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(top = 4.dp)
            )
             // Buttons
            XPJButton("View Recent Entries", Icons.Filled.History, onClick = {
                navController.navigate("recent_entries")
            })
            XPJButton("View Badges", Icons.Filled.Star, onClick = {
                navController.navigate("badges")
            })
            XPJButton("Search Games", Icons.Filled.Search, onClick = {
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
        FloatingActionButton(
            onClick = { navController.navigate("add_entry") },
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
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
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


@Composable
fun BadgesScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(com.example.xpjourney.ui.theme.PrimaryBlue)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Badge Album",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlueText,
            style = TextStyle(
                /*shadow = TextStyleShadow(
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = Offset(2f, 2f),
                    blurRadius = 3f
                )*/
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val nextBadge = BadgeManager.getNextBadgeToUnlock()
        val instructionText = if (nextBadge != null) {
            "${nextBadge.unlockRequirement} and unlock ${nextBadge.name} badge"
        } else {
            "All badges unlocked! You are a Champion!"
        }

        Text(
            text = instructionText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp)
        )

        // Added Button to View Challenges
        OutlinedButton(
            onClick = { navController.navigate("challenges") },
            modifier = Modifier.padding(bottom = 16.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkBlueText),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkBlueText)
        ) {
            Icon(Icons.AutoMirrored.Filled.List, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("View All Challenges")
        }

        val tabs = listOf("Achievements", "Reflections", "Social", "Challenges")
        var selectedTabIndex by remember { mutableIntStateOf(0) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            tabs.forEachIndexed { index, title ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = title,
                        color = if (selectedTabIndex == index) DarkBlueText else Color.Black.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { selectedTabIndex = index }
                    )
                    if (selectedTabIndex == index) {
                        Spacer(modifier = Modifier.height(4.dp))
                        HorizontalDivider(
                            modifier = Modifier.width(title.length.dp * 8),
                            thickness = 3.dp,
                            color = DarkBlueText
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OpenBook(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = com.example.xpjourney.ui.theme.ButtonPink),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Back to Dashboard", color = Color.White)
            }
            Button(
                onClick = { /* Handle Share Album */ },
                colors = ButtonDefaults.buttonColors(containerColor = com.example.xpjourney.ui.theme.ButtonPink),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Share Album", color = Color.White)
            }
        }
    }
}
@Composable
fun OpenBook(modifier: Modifier = Modifier) {
    // This pulls the list of badges from your BadgeManager
    val badgeItems = BadgeManager.badges

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .border(2.dp, Color.Black.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
        ) {
            // Draws the thin line down the middle of the book
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawLine(
                    color = Color.Black.copy(alpha = 0.2f),
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }

            Row(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                // LEFT PAGE: Displays badges 0 to 5
                Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                    for (row in 0..2) {
                        Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                            for (col in 0..1) {
                                val index = row * 2 + col
                                // Passing the whole badge object to the slot
                                BadgeSlot(
                                    badge = badgeItems.getOrNull(index),
                                    modifier = Modifier.weight(1f).padding(4.dp)
                                )
                            }
                        }
                    }
                }

                // RIGHT PAGE: Displays badges 6 to 11
                Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    for (row in 0..2) {
                        Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                            for (col in 0..1) {
                                val index = 6 + row * 2 + col
                                // Passing the whole badge object to the slot
                                BadgeSlot(
                                    badge = badgeItems.getOrNull(index),
                                    modifier = Modifier.weight(1f).padding(4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BadgeSlot(badge: Badge?, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .border(
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        // If the badge exists and is unlocked, show the icon
        if (badge != null && badge.isUnlocked) {
            Icon(
                imageVector = badge.icon,
                contentDescription = badge.name,
                tint = Color(0xFF1A237E), // Solid dark blue for visibility
                modifier = Modifier.size(32.dp)
            )
        } else {
            // If locked or empty, show a dashed placeholder
            Canvas(modifier = Modifier.matchParentSize()) {
                drawRect(
                    color = Color.Gray.copy(alpha = 0.3f),
                    style = Stroke(
                        width = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                )
            }
        }
    }
}
