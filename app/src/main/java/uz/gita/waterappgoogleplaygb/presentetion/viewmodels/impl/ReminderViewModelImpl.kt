package uz.gita.waterappgoogleplaygb.presentetion.viewmodels.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.waterappgoogleplaygb.data.local.entity.ReminderEntity
import uz.gita.waterappgoogleplaygb.presentetion.viewmodels.ReminderViewModel
import uz.gita.waterappgoogleplaygb.repository.ReminderRepository
import javax.inject.Inject


@HiltViewModel
class ReminderViewModelImpl @Inject constructor(
    private val reminderRepository: ReminderRepository
) : ReminderViewModel, ViewModel() {


    override val allReminderFlow = MutableSharedFlow<List<ReminderEntity>>()

    override val openTimePickerFlow = MutableSharedFlow<Unit>()

    override val openDeleteDialog = MutableSharedFlow<Unit>()

    init {
        viewModelScope.launch(Dispatchers.IO){
            reminderRepository.getAllReminder().collectLatest {
                allReminderFlow.emit(it)
            }
        }
    }


    override fun addReminderClick() {
        viewModelScope.launch {
            openTimePickerFlow.emit(Unit)
        }
    }

    override fun addReminder(time: String) {
        viewModelScope.launch(Dispatchers.IO) {
            reminderRepository.insertReminder(ReminderEntity(0, time))
        }
    }

    override fun deleteReminderClick() {
        viewModelScope.launch {
            openDeleteDialog.emit(Unit)
        }
    }

    override fun deleteReminder(reminderEntity: ReminderEntity) {
        viewModelScope.launch {
            reminderRepository.deleteReminder(reminderEntity)
        }
    }
}