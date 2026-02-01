package com.example.xpjourney.ui.theme

import android.content.Intent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Shadow as TextStyleShadow

@Composable
fun BadgesScreen(navController: NavController) {
    val context = LocalContext.current

    // 1. Define the categories - ensured they match the BadgeManager categories
    val tabs = listOf("Achievements", "Reflections", "Social", "Challenges")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // 2. Filter badges - using a more robust matching logic
    val filteredBadges = remember(selectedTabIndex) {
        val currentCategory = tabs[selectedTabIndex]
        BadgeManager.badges.filter { it.category.equals(currentCategory, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBlue)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Badge Album",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlueText,
            style = TextStyle(
                shadow = TextStyleShadow(
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = Offset(2f, 2f),
                    blurRadius = 3f
                )
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // 3. Dynamic instruction for the next locked badge
        val nextIncategory = filteredBadges.firstOrNull { !it.isUnlocked }
        val instructionText = if (nextIncategory != null) {
            "${nextIncategory.unlockRequirement} to unlock ${nextIncategory.name}"
        } else {
            "All ${tabs[selectedTabIndex]} badges unlocked!"
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

        // Navigation Button to detailed Challenges Screen
        OutlinedButton(
            onClick = { navController.navigate("challenges") },
            modifier = Modifier.padding(bottom = 16.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkBlueText),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkBlueText)
        ) {
            Icon(Icons.Default.List, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("View All Challenges")
        }

        // 4. Category Tabs
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
                        color = if (selectedTabIndex == index) DarkBlueText else Color.Black.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            selectedTabIndex = index
                        }
                    )
                    if (selectedTabIndex == index) {
                        Spacer(modifier = Modifier.height(4.dp))
                        HorizontalDivider(
                            modifier = Modifier.width(40.dp),
                            thickness = 3.dp,
                            color = DarkBlueText
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 5. The Book View
        OpenBook(
            badgeItems = filteredBadges,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
        )

        // Bottom Navigation Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = ButtonPink),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Back to Dashboard", color = Color.White)
            }

            // 6. IMPLEMENTED SHARE ALBUM
            Button(
                onClick = {
                    val unlockedCount = BadgeManager.getUnlockedBadgesCount()
                    val totalCount = BadgeManager.badges.size
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "I've unlocked $unlockedCount of $totalCount badges on my XPJourney! Check out my progress!")
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share your Badge Album"))
                },
                colors = ButtonDefaults.buttonColors(containerColor = ButtonPink),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Share Album", color = Color.White)
            }
        }
    }
}

@Composable
fun OpenBook(badgeItems: List<Badge>, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .border(2.dp, Color.Black.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawLine(
                    color = Color.Black.copy(alpha = 0.2f),
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }

            Row(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                // Left Page
                Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                    for (row in 0..2) {
                        Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                            for (col in 0..1) {
                                val index = row * 2 + col
                                BadgeSlot(badge = badgeItems.getOrNull(index), modifier = Modifier.weight(1f).padding(4.dp))
                            }
                        }
                    }
                }
                // Right Page
                Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    for (row in 0..2) {
                        Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                            for (col in 0..1) {
                                val index = 6 + row * 2 + col
                                BadgeSlot(badge = badgeItems.getOrNull(index), modifier = Modifier.weight(1f).padding(4.dp))
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
            .border(1.dp, Color.Black.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (badge != null && badge.isUnlocked) {
            Icon(
                imageVector = badge.icon,
                contentDescription = badge.name,
                tint = Color(0xFF1A237E),
                modifier = Modifier.size(32.dp)
            )
        } else {
            Canvas(modifier = Modifier.fillMaxSize()) {
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