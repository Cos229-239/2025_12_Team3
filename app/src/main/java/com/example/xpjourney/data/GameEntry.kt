package com.example.xpjourney.data

import java.util.UUID

data class GameEntry(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val gameName: String,
    val entryText: String,
    val date: String,
    val time: String
)