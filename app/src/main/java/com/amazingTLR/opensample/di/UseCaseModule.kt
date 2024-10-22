package com.amazingTLR.opensample.di

import com.amazingtlr.api.user.UserRepository
import com.amazingtlr.usecase.repo.RepoListUseCase
import com.amazingtlr.usecase.user.UserListUseCase
import com.amazingtlr.usecase.user.UserSingleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideUserListUseCase(userRepository: UserRepository): UserListUseCase {
        return UserListUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideUserSingleUseCase(userRepository: UserRepository): UserSingleUseCase {
        return UserSingleUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideRepoListUseCase(userRepository: UserRepository): RepoListUseCase {
        return RepoListUseCase(userRepository)
    }
}