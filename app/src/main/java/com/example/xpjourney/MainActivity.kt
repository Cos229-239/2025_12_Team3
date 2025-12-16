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
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.xpjourney.ui.theme.Poppins


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XPJourneyTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "dashboard") {
                    composable("dashboard") { DashboardScreen( navController) }
                    composable("entry") { JournalEntryScreen() }
                    composable("badges") { BadgesScreen() }
                    composable("journey") { JourneyScreen() }
                }
            }
        }
    }
}

@Composable
fun DashboardScreen(navController: NavController) {
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Logo image
            Image(
                painter = painterResource(id = R.drawable.xpj_logo),
                contentDescription = "XPJ Logo",
                modifier = Modifier.size(96.dp)
            )
            // Tagline text
            Text(
                text = "Your story, your progress, your XPJourney.",
                style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(top = 2.dp)
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

        }
        Column(                     // XP tracker in top-right corner
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 48.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(80.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                    strokeWidth = 6.dp
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
            containerColor = MaterialTheme.colorScheme.primary,
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

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    XPJourneyTheme {
        val navController = rememberNavController()
        DashboardScreen(navController)
    }
}

