package uz.gita.waterappgoogleplaygb.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.gita.waterappgoogleplaygb.data.local.entity.WaterEntity

@Dao
interface WaterTrackerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWater(waterEntity: WaterEntity)

    @Update
    suspend fun updateWater(waterEntity: WaterEntity)

    @Delete
    suspend fun deleteWater(waterEntity: WaterEntity)

    @Query("SELECT*FROM water_track WHERE date=:date")
    fun getWaterTracks(date: String): Flow<List<WaterEntity>>

    @Query("SELECT SUM(drink) FROM water_track WHERE date=:date")
    fun getWaterDrinks(date: String): LiveData<Int>

    @Query("SELECT SUM(drink) FROM water_track WHERE date=:date")
    suspend fun getWaterDrinksByDate(date: String): Int?

    @Query("SELECT SUM(drink) FROM water_track WHERE date>=:start AND date<=:end")
    fun getWaterTrackByDate(start: String, end: String): Flow<List<Int>>

    @Query("SELECT AVG(drink) FROM water_track")
    fun getAllWaterDrinks(): Flow<Int?>

}