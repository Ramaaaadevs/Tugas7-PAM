package com.diwan.myprofileapp.shared.viewmodel

import androidx.lifecycle.ViewModel
import com.diwan.myprofileapp.shared.data.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProfileUiState(
    val nama: String = "John Doe",
    val bio: String = "Android Developer",
    val email: String = "john@example.com",
    val phone: String = "+62 812 3456 7890",
    val location: String = "Jakarta, Indonesia",
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