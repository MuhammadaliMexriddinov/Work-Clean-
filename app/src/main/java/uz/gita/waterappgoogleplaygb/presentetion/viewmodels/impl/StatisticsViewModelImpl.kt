package uz.gita.waterappgoogleplaygb.presentetion.viewmodels.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.waterappgoogleplaygb.domain.WaterTrackerUseCase
import uz.gita.waterappgoogleplaygb.presentetion.viewmodels.StatisticsViewModel
import uz.gita.waterappgoogleplaygb.utils.extensions.getCurrentDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModelImpl @Inject constructor(
    private val useCase: WaterTrackerUseCase
) : StatisticsViewModel, ViewModel() {

    override val statisticsFlow = MutableSharedFlow<List<Int>>()

    override val weeklyAverageFlow = MutableSharedFlow<Int>()

    override val monthlyAverageFlow = MutableSharedFlow<Int>()

    override val averageCompletedFlow = MutableSharedFlow<Int>()

    override val monthFlow = MutableSharedFlow<String>()

    private fun getData(startC: Long, endC: Long){

        var start = startC
        viewModelScope.launch(Dispatchers.IO) {
            var allSumm = 0
            var weekly = 0
            var counter = 0
            while (start < endC) {
                val drink: Int? = useCase.getWaterTrackByDate(getCurrentDate(Date(start)))
                start += 86400000
                allSumm += drink ?: 0
                if (counter < 7) {
                    weekly += drink ?: 0
                    counter++
                }
            }
            monthlyAverageFlow.emit(allSumm / 30)
            weeklyAverageFlow.emit(weekly / 7)
            useCase.getAllWaterDrinks().collectLatest {
                val average = useCase.goalTrack()
                val track = it ?: 0
                averageCompletedFlow.emit(track * 100 / average)
            }
        }
    }

    override fun changeDataRange(start: Long, end: Long) {
        var startDate = start
        viewModelScope.launch(Dispatchers.IO) {
            val listCharts = ArrayList<Int>()

            while (startDate < end) {
                val data = Date(startDate)
                val getData = getCurrentDate(data)

                val drink = useCase.getWaterTrackByDate(getData)

                listCharts.add(drink ?: 0)
                startDate += 86400000
            }
            statisticsFlow.emit(listCharts)
        }

        getData(start,end)
    }

}