package com.amazingTLR.opensample.di

import androidx.lifecycle.SavedStateHandle
import com.amazingTLR.opensample.USER_LOGIN_ARG
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ScreenArgModule {
        @Provides
        @UserLogin
        @ViewModelScoped
        fun providePersonName(
            savedStatedHandle: SavedStateHandle,
        ): String? {
            return savedStatedHandle[USER_LOGIN_ARG]
        }

        annotation class UserLogin
}