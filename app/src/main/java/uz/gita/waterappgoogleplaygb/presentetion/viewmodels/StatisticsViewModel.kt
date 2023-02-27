package uz.gita.waterappgoogleplaygb.presentetion.viewmodels

import kotlinx.coroutines.flow.SharedFlow

interface StatisticsViewModel {

    val statisticsFlow: SharedFlow<List<Int>>

    val weeklyAverageFlow: SharedFlow<Int>

    val monthlyAverageFlow: SharedFlow<Int>

    val averageCompletedFlow: SharedFlow<Int>

    val monthFlow: SharedFlow<String>

    fun changeDataRange(start:Long,end:Long)

}