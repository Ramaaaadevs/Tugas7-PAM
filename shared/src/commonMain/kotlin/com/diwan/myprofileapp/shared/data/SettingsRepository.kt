package com.diwan.myprofileapp.shared.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getStringOrNullFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val settings: Settings) {
    companion object {
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_SORT_ORDER = "sort_order"
        private const val KEY_NAME = "profile_name"
        private const val KEY_BIO = "profile_bio"
        private const val KEY_EMAIL = "profile_email"
        private const val KEY_PHONE = "profile_phone"
    }

    var isDarkMode: Boolean
        get() = settings.getBoolean(KEY_DARK_MODE, false)
        set(value) = settings.putBoolean(KEY_DARK_MODE, value)

    var sortOrder: String
        get() = settings.getString(KEY_SORT_ORDER, "newest")
        set(value) = settings.putString(KEY_SORT_ORDER, value)

    var profileName: String
        get() = settings.getString(KEY_NAME, "Diwan Ramadhani Dwi Putra")
        set(value) = settings.putString(KEY_NAME, value)

    var profileBio: String
        get() = settings.getString(KEY_BIO, "Android Developer")
        set(value) = settings.putString(KEY_BIO, value)

    var profileEmail: String
        get() = settings.getString(KEY_EMAIL, "diwan.123140116@student.itera.ac.id")
        set(value) = settings.putString(KEY_EMAIL, value)

    var profilePhone: String
        get() = settings.getString(KEY_PHONE, "+6281278437207")
        set(value) = settings.putString(KEY_PHONE, value)
}
