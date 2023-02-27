package uz.gita.waterappgoogleplaygb.presentetion.viewmodels.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uz.gita.waterappgoogleplaygb.data.prefs.MySharedPref
import uz.gita.waterappgoogleplaygb.presentetion.viewmodels.SettingsViewModel
import javax.inject.Inject
@HiltViewModel
class SettingsViewModelImpl @Inject constructor(
    private val mySharedPref: MySharedPref
) : SettingsViewModel, ViewModel() {

    override val reminderScheduleFlow = MutableSharedFlow<Unit>()

    override val intakeGoalFlow = MutableSharedFlow<Int>()

    override val genderFlow = MutableSharedFlow<String>()

    override val weightFlow = MutableSharedFlow<Int>()

    override val openGenderDialog = MutableSharedFlow<Unit>()

    override val openTakeGoalDialog = MutableSharedFlow<Unit>()

    override val openWeightDialog = MutableSharedFlow<Unit>()

    override fun reminderScheduleClick() {
        viewModelScope.launch {
            reminderScheduleFlow.emit(Unit)
        }
    }

    override fun genderClick() {
        viewModelScope.launch {
            openGenderDialog.emit(Unit)
        }
    }

    override fun intakeGoalClick() {
        viewModelScope.launch {
            openTakeGoalDialog.emit(Unit)
        }
    }

    override fun weightClick() {
        viewModelScope.launch {
            openWeightDialog.emit(Unit)
        }
    }

    override fun getIntakeGoal(): Int  = mySharedPref.dailyWater

    override fun getWeight(): Int  = mySharedPref.weight

    override fun setGender(gender: String) {
        mySharedPref.gender = gender
        viewModelScope.launch {
            genderFlow.emit(gender)
        }
    }

    override fun setIntakeGoal(goal: Int) {
        mySharedPref.dailyWater = goal
        viewModelScope.launch {
            intakeGoalFlow.emit(goal)
        }
    }

    override fun setWeight(weight: Int) {
        mySharedPref.weight = weight
        viewModelScope.launch {
            weightFlow.emit(weight)
        }
    }

    override fun getData() {
        viewModelScope.launch {
            intakeGoalFlow.emit(mySharedPref.dailyWater)
            genderFlow.emit(mySharedPref.gender)
            weightFlow.emit(mySharedPref.weight)
        }
    }


}