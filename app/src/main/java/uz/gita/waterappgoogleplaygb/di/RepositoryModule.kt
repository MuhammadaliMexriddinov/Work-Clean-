package uz.gita.waterappgoogleplaygb.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.waterappgoogleplaygb.repository.ReminderRepository
import uz.gita.waterappgoogleplaygb.repository.WaterRepository
import uz.gita.waterappgoogleplaygb.repository.impl.ReminderRepositoryImpl
import uz.gita.waterappgoogleplaygb.repository.impl.WaterRepositoryImpl


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindWaterRepository(waterRepositoryImpl: WaterRepositoryImpl): WaterRepository

    @Binds
    fun bindReminderRepository(reminderRepository: ReminderRepositoryImpl): ReminderRepository
}