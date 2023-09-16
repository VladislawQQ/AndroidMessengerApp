package com.example.messengerApp.di

import com.example.messengerApp.domain.repository.ContactServiceImpl
import com.example.messengerApp.domain.repository.interfaces.ContactService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindService(
        service : ContactServiceImpl
    ) : ContactService

}