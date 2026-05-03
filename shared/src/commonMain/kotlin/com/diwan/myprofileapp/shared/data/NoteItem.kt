package com.diwan.myprofileapp.shared.data

data class NoteItem(
    val id: Long,
    val title: String,
    val content: String,
    val isFavorite: Long,
    val createdAt: Long
)
