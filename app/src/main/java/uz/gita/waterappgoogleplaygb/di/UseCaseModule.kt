package uz.gita.waterappgoogleplaygb.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.waterappgoogleplaygb.domain.WaterTrackerUseCase
import uz.gita.waterappgoogleplaygb.domain.impl.WaterTrackerUseCaseImpl


@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindWaterUseCase(waterUseCaseModuleImpl: WaterTrackerUseCaseImpl): WaterTrackerUseCase

}