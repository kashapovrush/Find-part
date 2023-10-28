package com.kashapovrush.di

import android.app.Application
import com.kashapovrush.presentation.FindPartApp
import com.kashapovrush.presentation.MainActivity
import com.kashapovrush.presentation.PartFragment
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(app: FindPartApp)

    fun inject(mainActivity: MainActivity)

    fun inject(fragment: PartFragment)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}