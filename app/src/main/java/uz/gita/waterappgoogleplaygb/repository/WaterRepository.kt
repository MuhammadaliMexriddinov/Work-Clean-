package uz.gita.waterappgoogleplaygb.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import uz.gita.waterappgoogleplaygb.data.local.entity.WaterEntity

interface WaterRepository {

    suspend fun insertWaterEntity(waterEntity: WaterEntity)

    suspend fun updateWaterEntity(waterEntity: WaterEntity)

    suspend fun deleteWaterEntity(waterEntity: WaterEntity)

    fun goalTrack(): Int

    fun getWaterTracks(date: String): Flow<List<WaterEntity>>

    fun getWaterTracksByDate(start: String, end: String): Flow<List<Int>>

    fun getWaterTrackDaily(date: String): LiveData<Int>

    fun getAllWaterDrinks(): Flow<Int?>

    suspend fun getWaterTrackByDate(date: String): Int?

}