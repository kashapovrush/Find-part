package com.kashapovrush.presentation

import android.app.Application
import com.kashapovrush.di.DaggerApplicationComponent

class FindPartApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}