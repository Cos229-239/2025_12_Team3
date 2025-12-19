package com.example.xpjourney.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import com.example.xpjourney.data.ProgressRepository

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed

interface ProgressViewModelContract {
    val progressState: StateFlow<ProgressRepository.Progress>
}

class UserProgressViewModel(private val repo: ProgressRepository) : ViewModel(),
ProgressViewModelContract {
    override val progressState = repo.progressFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        ProgressRepository.Progress(LocalDate.now(), 0, 0)
    )
    // Called when user logs in / opens app
    fun onLogin() {
        viewModelScope.launch {
            val current = progressState.value
            val today = LocalDate.now()
            val updated = updateStreakAndXp(current, today)
            repo.saveProgress(
                lastLoginDate = today,
                currentStreak = updated.currentStreak,
                xp = updated.xp
            )
        }
    }

    private fun updateStreakAndXp(current: ProgressRepository.Progress, today: LocalDate): ProgressRepository.Progress {
        val last = current.lastLoginDate
        val newStreak = when {
            last == null -> 1
            last == today -> current.currentStreak // Already credited today
            last == today.minusDays(1) -> current.currentStreak + 1
            else -> 1
        }
        val alreadyCreditedToday = (last == today)
        val xpReward = if (alreadyCreditedToday) 0 else 5 + (newStreak * 2)

        return current.copy(
            lastLoginDate = today,
            currentStreak = newStreak,
            xp = current.xp + xpReward
        )
    }
}
