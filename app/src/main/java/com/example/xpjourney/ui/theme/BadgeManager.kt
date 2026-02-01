package com.example.xpjourney.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector


data class Badge(
    val id: Int,
    val name: String,
    val icon: ImageVector,
    val isUnlocked: Boolean = false,
    val unlockRequirement: String,
    val category: String,
    val description: String
)

object BadgeManager {
    val badges = listOf(
        Badge(1, "First Entry", Icons.Default.Edit, true, "Write your first entry", "Achievements", "Start your journey by writing your very first journal entry."),
        Badge(2, "Week Streak", Icons.Default.DateRange, true, "Maintain a 7-day streak", "Achievements", "Consistency is key! Log in and write an entry for 7 consecutive days."),
        Badge(3, "Social Butterfly", Icons.Default.Share, false, "Share your progress", "Social", "Don't journey alone! Share your badge album or an entry with a friend."),
        Badge(4, "Challenge Master", Icons.Default.Star, false, "Complete 5 challenges", "Challenges", "Take on the world! Complete 5 specific daily or weekly challenges."),
        Badge(5, "Deep Thinker", Icons.Default.Lightbulb, false, "Write a reflection longer than 200 words", "Reflections", "Go beyond the surface. Write a detailed reflection about your day."),
        Badge(6, "Goal Getter", Icons.Default.CheckCircle, false, "Set and achieve your first goal", "Challenges", "Aim high. Define a personal goal and mark it as completed."),
        Badge(7, "Explorer", Icons.Default.Map, false, "Visit 3 different journey locations", "Challenges", "Discover new horizons by exploring different sections of the app."),
        Badge(8, "Storyteller", Icons.Default.Book, false, "Complete your first chapter", "Reflections", "Every journey is a story. Complete all entries in your first chapter."),
        Badge(9, "Early Bird", Icons.Default.WbSunny, false, "Write an entry before 7 AM", "Challenges", "The early bird gets the XP! Log your thoughts as soon as you wake up."),
        Badge(10, "Night Owl", Icons.Default.NightsStay, false, "Write an entry after 10 PM", "Challenges", "Reflect on your day under the stars. Write an entry late at night."),
        Badge(11, "Persistent", Icons.Default.Refresh, false, "Come back after a week away", "Challenges", "It's never too late to start again. Return to your journey after a short break."),
        Badge(12, "Champion", Icons.Default.EmojiEvents, false, "Reach level 10", "Achievements", "Become a master of your journey by reaching experience level 10.")
    )

    fun getUnlockedBadgesCount(): Int {
        return badges.count { it.isUnlocked }
    }

    fun getNextBadgeToUnlock(): Badge? {
        return badges.firstOrNull { !it.isUnlocked }
    }
}
