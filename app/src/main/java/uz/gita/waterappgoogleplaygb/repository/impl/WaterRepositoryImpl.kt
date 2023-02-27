package uz.gita.waterappgoogleplaygb.repository.impl

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import uz.gita.waterappgoogleplaygb.data.local.dao.WaterTrackerDao
import uz.gita.waterappgoogleplaygb.data.local.entity.WaterEntity
import uz.gita.waterappgoogleplaygb.data.prefs.MySharedPref
import uz.gita.waterappgoogleplaygb.repository.WaterRepository
import javax.inject.Inject

class WaterRepositoryImpl @Inject constructor(
    private val dao: WaterTrackerDao,
    private val sharedPref: MySharedPref
) : WaterRepository {

    override suspend fun insertWaterEntity(waterEntity: WaterEntity) =
        dao.insertWater(waterEntity)

    override suspend fun updateWaterEntity(waterEntity: WaterEntity) =
        dao.updateWater(waterEntity)

    override suspend fun deleteWaterEntity(waterEntity: WaterEntity) =
        dao.deleteWater(waterEntity)

    override fun goalTrack(): Int = sharedPref.dailyWater

    override fun getWaterTracks(date: String): Flow<List<WaterEntity>> =
        dao.getWaterTracks(date)

    override fun getWaterTracksByDate(start: String, end: String): Flow<List<Int>> =
        dao.getWaterTrackByDate(start, end)

    override suspend fun getWaterTrackByDate(date: String): Int? = dao.getWaterDrinksByDate(date)

    override fun getWaterTrackDaily(date: String): LiveData<Int> = dao.getWaterDrinks(date)

    override fun getAllWaterDrinks(): Flow<Int?>  = dao.getAllWaterDrinks()

}