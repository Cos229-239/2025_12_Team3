package com.example.xpjourney.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class Badge(
    val id: Int,
    val name: String,
    val icon: ImageVector,
    val isUnlocked: Boolean = false
)

object BadgeManager {
    val badges = listOf(
        Badge(1, "First Entry", Icons.Default.Edit, true),
        Badge(2, "Week Streak", Icons.Default.DateRange, true),
        Badge(3, "Social Butterfly", Icons.Default.Share, false),
        Badge(4, "Challenge Master", Icons.Default.Star, false),
        Badge(5, "Deep Thinker", Icons.Default.Lightbulb, false),
        Badge(6, "Goal Getter", Icons.Default.CheckCircle, false),
        Badge(7, "Explorer", Icons.Default.Map, false),
        Badge(8, "Storyteller", Icons.Default.Book, false),
        Badge(9, "Early Bird", Icons.Default.WbSunny, false),
        Badge(10, "Night Owl", Icons.Default.NightsStay, false),
        Badge(11, "Persistent", Icons.Default.Refresh, false),
        Badge(12, "Champion", Icons.Default.EmojiEvents, false)
    )

    fun getUnlockedBadgesCount(): Int {
        return badges.count { it.isUnlocked }
    }
}