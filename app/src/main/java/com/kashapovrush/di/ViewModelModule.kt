package com.kashapovrush.di

import androidx.lifecycle.ViewModel
import com.kashapovrush.presentation.MainViewModel
import com.kashapovrush.presentation.PartViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @Binds
    @ViewModuleKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel


    @IntoMap
    @Binds
    @ViewModuleKey(PartViewModel::class)
    fun bindPartViewModel(viewModel: PartViewModel): ViewModel
}