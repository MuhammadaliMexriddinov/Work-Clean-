package uz.gita.waterappgoogleplaygb.repository


import kotlinx.coroutines.flow.Flow
import uz.gita.waterappgoogleplaygb.data.local.entity.ReminderEntity


interface ReminderRepository {

    suspend fun insertReminder(reminderEntity: ReminderEntity)

    suspend fun updateReminder(reminderEntity: ReminderEntity)

    suspend fun deleteReminder(reminderEntity: ReminderEntity)

    fun getAllReminder(): Flow<List<ReminderEntity>>

}