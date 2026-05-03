package com.diwan.myprofileapp.shared.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diwan.myprofileapp.shared.data.DatabaseDriverFactory
import com.diwan.myprofileapp.shared.data.NoteRepository
import com.diwan.myprofileapp.shared.data.SettingsRepository
import com.russhwolf.settings.SharedPreferencesSettings

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    private val noteRepository by lazy {
        NoteRepository(DatabaseDriverFactory(context))
    }
    private val settingsRepository by lazy {
        val prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        SettingsRepository(SharedPreferencesSettings(prefs))
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NoteViewModel::class.java) ->
                NoteViewModel(noteRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(settingsRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
        }
    }
}