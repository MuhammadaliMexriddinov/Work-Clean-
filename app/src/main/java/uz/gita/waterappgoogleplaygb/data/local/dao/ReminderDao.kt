package uz.gita.waterappgoogleplaygb.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.gita.waterappgoogleplaygb.data.local.entity.ReminderEntity

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminderEntity: ReminderEntity)


    @Update
    suspend fun updateReminder(reminderEntity: ReminderEntity)

    @Delete
    suspend fun deleteReminder(reminderEntity: ReminderEntity)

    @Query("SELECT*FROM reminder")
    fun getAllReminder(): Flow<List<ReminderEntity>>

}