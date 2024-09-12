package com.example.sr_kodtest.di

import com.example.sr_kodtest.domain.ProgramRepository
import com.example.sr_kodtest.domain.ProgramRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelDI {
    @Binds
    abstract fun bindProgramRepository(programRepositoryImpl: ProgramRepositoryImpl): ProgramRepository
}