package com.example.xpjourney.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.Shadow as TextStyleShadow

@Composable
fun BadgesScreen(navController: NavController) {
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

        Text(
            text = "${BadgeManager.getUnlockedBadgesCount()} of ${BadgeManager.badges.size} badges unlocked",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 24.dp)
        )

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
                colors = ButtonDefaults.buttonColors(containerColor = ButtonPink),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Back to Dashboard", color = Color.White)
            }
            Button(
                onClick = { /* Handle Share Album */ },
                colors = ButtonDefaults.buttonColors(containerColor = ButtonPink),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Share Album", color = Color.White)
            }
        }
    }
}

@Composable
fun OpenBook(modifier: Modifier = Modifier) {
    val badgeItems = BadgeManager.badges

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .border(2.dp, Color.Black.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                drawLine(
                    color = Color.Black.copy(alpha = 0.3f),
                    start = Offset(width / 2, 0f),
                    end = Offset(width / 2, height),
                    strokeWidth = 2.dp.toPx()
                )

                val path = Path().apply {
                    moveTo(width / 2 - 20.dp.toPx(), height)
                    quadraticTo(
                        width / 2, height + 20.dp.toPx(),
                        width / 2 + 20.dp.toPx(), height
                    )
                }
                drawPath(path, color = Color.Black.copy(alpha = 0.5f), style = Stroke(width = 2.dp.toPx()))
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                    repeat(3) { row ->
                        Row(modifier = Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.SpaceAround) {
                            repeat(2) { col ->
                                val index = row * 2 + col
                                val badge = badgeItems.getOrNull(index)
                                BadgeSlot(
                                    icon = badge?.icon,
                                    isUnlocked = badge?.isUnlocked ?: false,
                                    modifier = Modifier.weight(1f).aspectRatio(1f).padding(4.dp)
                                )
                            }
                        }
                    }
                    Text("1", fontSize = 12.sp, modifier = Modifier.align(Alignment.Start).padding(top = 8.dp))
                }

                Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    repeat(3) { row ->
                        Row(modifier = Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.SpaceAround) {
                            repeat(2) { col ->
                                val index = 6 + row * 2 + col
                                val badge = badgeItems.getOrNull(index)
                                BadgeSlot(
                                    icon = badge?.icon,
                                    isUnlocked = badge?.isUnlocked ?: false,
                                    modifier = Modifier.weight(1f).aspectRatio(1f).padding(4.dp)
                                )
                            }
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("2", fontSize = 12.sp)
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Page", tint = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun BadgeSlot(icon: ImageVector?, isUnlocked: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isUnlocked && icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = "Badge",
                tint = PrimaryBlue,
                modifier = Modifier.size(40.dp)
            )
        } else {
            Canvas(modifier = Modifier.matchParentSize()) {
                drawRect(
                    color = Color.Black.copy(alpha = 0.2f),
                    style = Stroke(width = 1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BadgesScreenPreview() {
    XPJourneyTheme {
        BadgesScreen(navController = rememberNavController())
    }
}
