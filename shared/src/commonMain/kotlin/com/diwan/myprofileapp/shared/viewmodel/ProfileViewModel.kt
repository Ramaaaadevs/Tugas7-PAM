package com.diwan.myprofileapp.shared.viewmodel

import androidx.lifecycle.ViewModel
import com.diwan.myprofileapp.shared.data.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProfileUiState(
    val nama: String = "Diwan Ramadhani Dwi Putra",
    val bio: String = "Android Developer",
    val email: String = "diwan.123140116@student.itera.ac.id",
    val phone: String = "+6281278437207",
    val location: String = "Lampung, Indonesia",
    val isDarkMode: Boolean = false,
    val isFollowing: Boolean = false
)

class ProfileViewModel(private val settings: SettingsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            nama = settings.profileName,
            bio = settings.profileBio,
            email = settings.profileEmail,
            phone = settings.profilePhone,
            isDarkMode = settings.isDarkMode
        )
    )
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun toggleDarkMode() {
        val new = !_uiState.value.isDarkMode
        _uiState.value = _uiState.value.copy(isDarkMode = new)
        settings.isDarkMode = new
    }

    fun toggleFollow() {
        _uiState.value = _uiState.value.copy(isFollowing = !_uiState.value.isFollowing)
    }

    fun saveProfile(nama: String, bio: String) {
        _uiState.value = _uiState.value.copy(nama = nama, bio = bio)
        settings.profileName = nama
        settings.profileBio = bio
    }
}