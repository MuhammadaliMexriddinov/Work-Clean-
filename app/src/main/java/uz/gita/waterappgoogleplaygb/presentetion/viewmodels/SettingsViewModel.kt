package uz.gita.waterappgoogleplaygb.presentetion.viewmodels

import kotlinx.coroutines.flow.SharedFlow


interface SettingsViewModel {

    val reminderScheduleFlow: SharedFlow<Unit>

    val intakeGoalFlow: SharedFlow<Int>

    val genderFlow: SharedFlow<String>

    val weightFlow: SharedFlow<Int>

    val openGenderDialog: SharedFlow<Unit>

    val openTakeGoalDialog: SharedFlow<Unit>

    val openWeightDialog: SharedFlow<Unit>

    fun reminderScheduleClick()

    fun genderClick()

    fun intakeGoalClick()

    fun weightClick()

    fun getIntakeGoal():Int

    fun getWeight():Int

    fun setGender(gender: String)

    fun setIntakeGoal(goal: Int)

    fun setWeight(weight: Int)

    fun getData()

}