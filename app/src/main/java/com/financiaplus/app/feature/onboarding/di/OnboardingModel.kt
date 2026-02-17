package com.financiaplus.app.feature.onboarding.di

import com.financiaplus.app.core.network.api.AmlApiService
import com.financiaplus.app.core.network.api.BankApiService
import com.financiaplus.app.core.network.api.GeoLocationApiService
import com.financiaplus.app.feature.onboarding.data.repository.AmlRepositoryImpl
import com.financiaplus.app.feature.onboarding.data.repository.BankRepositoryImpl
import com.financiaplus.app.feature.onboarding.data.repository.GeoRepositoryImpl
import com.financiaplus.app.feature.onboarding.domain.repository.AmlRepository
import com.financiaplus.app.feature.onboarding.domain.repository.BankRepository
import com.financiaplus.app.feature.onboarding.domain.repository.GeoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnboardingModule {

    @Provides
    @Singleton
    fun provideAmlRepository(
        apiService: AmlApiService
    ): AmlRepository = AmlRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideBankRepository(
        apiService: BankApiService
    ): BankRepository = BankRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideGeoRepository(
        apiService: GeoLocationApiService
    ): GeoRepository = GeoRepositoryImpl(apiService)
}