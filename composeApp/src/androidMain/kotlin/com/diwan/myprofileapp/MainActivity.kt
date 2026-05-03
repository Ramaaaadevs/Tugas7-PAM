package com.diwan.myprofileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.diwan.myprofileapp.navigation.AppNavigation
import com.diwan.myprofileapp.shared.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val factory = ViewModelFactory(this)
        setContent {
            AppNavigation(factory = factory)
        }
    }
}