package uz.gita.waterappgoogleplaygb.presentetion.viewmodels

import kotlinx.coroutines.flow.SharedFlow
import uz.gita.waterappgoogleplaygb.data.local.entity.ReminderEntity

interface ReminderViewModel {

    val allReminderFlow: SharedFlow<List<ReminderEntity>>

    val openTimePickerFlow: SharedFlow<Unit>

    val openDeleteDialog: SharedFlow<Unit>

    fun addReminderClick()

    fun addReminder(time: String)

    fun deleteReminderClick()

    fun deleteReminder(reminderEntity: ReminderEntity)

}