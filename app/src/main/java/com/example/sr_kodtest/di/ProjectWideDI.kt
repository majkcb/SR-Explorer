package com.example.sr_kodtest.di

import com.example.sr_kodtest.data.ProgramDatasource
import com.example.sr_kodtest.data.ProgramDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProjectWideDI {
    @Binds
    abstract fun bindProgramDatasource(programDatasourceImpl: ProgramDatasourceImpl): ProgramDatasource
}