package com.example.kawa

import android.app.Application
import com.google.firebase.FirebaseApp

class KawaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
