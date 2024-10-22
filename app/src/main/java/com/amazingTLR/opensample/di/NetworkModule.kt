package com.amazingTLR.opensample.di

import com.amazingTLR.opensample.BuildConfig
import com.amazingtlr.api.user.UserRepository
import com.amazingtlr.ktor.KtorJsonPlaceHolderClient
import com.amazingtlr.ktor.user.KtorUserFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        return KtorJsonPlaceHolderClient(BuildConfig.GITHUB_API_KEY).ktorJsonPlaceHolderClient
    }

    @Provides
    @Singleton
    fun provideUserRepository(httpClient: HttpClient): UserRepository {
        return KtorUserFactory.createUserRepository(httpClient)
    }
}