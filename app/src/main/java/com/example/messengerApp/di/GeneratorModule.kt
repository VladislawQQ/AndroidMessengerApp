package com.example.messengerApp.di

import com.example.messengerApp.domain.repository.ContactGeneratorImpl
import com.example.messengerApp.domain.repository.interfaces.ContactGenerator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GeneratorModule {

    @Binds
    @Singleton
    abstract fun bindGenerator(
        generator : ContactGeneratorImpl
    ) : ContactGenerator

}