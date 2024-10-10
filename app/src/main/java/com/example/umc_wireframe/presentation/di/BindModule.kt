package com.example.umc_wireframe.presentation.di

import com.example.umc_wireframe.data.repository.ShortTermForecastRepositoryImpl
import com.example.umc_wireframe.domain.repository.ShortTermForecastRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class BindModule {
    @Binds
    abstract fun bindShortTermForecastRepository(
        repository: ShortTermForecastRepositoryImpl
    ): ShortTermForecastRepository
}