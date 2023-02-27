package uz.gita.waterappgoogleplaygb.domain.impl

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.*
import uz.gita.waterappgoogleplaygb.data.local.entity.WaterEntity
import uz.gita.waterappgoogleplaygb.domain.WaterTrackerUseCase
import uz.gita.waterappgoogleplaygb.repository.WaterRepository
import javax.inject.Inject

class WaterTrackerUseCaseImpl @Inject constructor(
    private val waterRepository: WaterRepository
) : WaterTrackerUseCase {

    override suspend fun insertWaterEntity(waterEntity: WaterEntity) =
        waterRepository.insertWaterEntity(waterEntity)

    override suspend fun updateWaterEntity(waterEntity: WaterEntity) =
        waterRepository.updateWaterEntity(waterEntity)

    override suspend fun deleteWaterEntity(waterEntity: WaterEntity) =
        waterRepository.deleteWaterEntity(waterEntity)

    override fun goalTrack(): Int =
        waterRepository.goalTrack()

    override fun getWaterTracks(date: String): Flow<List<WaterEntity>> =
        waterRepository.getWaterTracks(date)

    override fun getWaterTracksByDate(start: String, end: String): Flow<List<Int>> =
        waterRepository.getWaterTracksByDate(start, end)

    override fun getWaterTrackDaily(date: String): LiveData<Int> = waterRepository.getWaterTrackDaily(date)
    override fun getAllWaterDrinks(): Flow<Int?> = waterRepository.getAllWaterDrinks()

    override suspend fun getWaterTrackByDate(date: String): Int? = waterRepository.getWaterTrackByDate(date)
}