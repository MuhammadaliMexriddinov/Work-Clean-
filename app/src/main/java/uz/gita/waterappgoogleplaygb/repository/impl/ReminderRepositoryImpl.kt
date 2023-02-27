package uz.gita.waterappgoogleplaygb.repository.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.waterappgoogleplaygb.data.local.dao.ReminderDao
import uz.gita.waterappgoogleplaygb.data.local.entity.ReminderEntity
import uz.gita.waterappgoogleplaygb.repository.ReminderRepository
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val dao: ReminderDao
) : ReminderRepository {
    override suspend fun insertReminder(reminderEntity: ReminderEntity) = dao.insertReminder(reminderEntity)
    override suspend fun updateReminder(reminderEntity: ReminderEntity) = dao.updateReminder(reminderEntity)
    override suspend fun deleteReminder(reminderEntity: ReminderEntity) = dao.deleteReminder(reminderEntity)
    override  fun getAllReminder(): Flow<List<ReminderEntity>> = dao.getAllReminder()
}