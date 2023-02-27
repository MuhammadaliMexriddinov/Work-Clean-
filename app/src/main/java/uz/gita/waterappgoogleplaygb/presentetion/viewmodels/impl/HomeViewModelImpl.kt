package uz.gita.waterappgoogleplaygb.presentetion.viewmodels.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.waterappgoogleplaygb.data.local.entity.WaterEntity
import uz.gita.waterappgoogleplaygb.domain.WaterTrackerUseCase
import uz.gita.waterappgoogleplaygb.presentetion.viewmodels.HomeViewModel
import uz.gita.waterappgoogleplaygb.utils.extensions.getCurrentDate
import uz.gita.waterappgoogleplaygb.utils.extensions.getCurrentTime

import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val useCase: WaterTrackerUseCase
) : HomeViewModel, ViewModel() {

    override val allWaterTracksFlow = MutableSharedFlow<List<WaterEntity>>()

    private var dish = 200

    override val dishWater = MutableSharedFlow<Int>()

    override val waterDrinkFlow = useCase.getWaterTrackDaily(getCurrentDate())

    override var goalTrack: Int = useCase.goalTrack()

    override val openEditBottleFlow = MutableSharedFlow<Unit>()

    override val goalDrinkFlow = MutableSharedFlow<Int>()

    override val openChooseTimeFlow = MutableSharedFlow<Unit>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dishWater.emit(dish)
            useCase.getWaterTracks(getCurrentDate()).collectLatest {
                allWaterTracksFlow.emit(it)
            }
        }
    }

    override fun insertTracks() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.insertWaterEntity(WaterEntity(0, dish, getCurrentDate(), getCurrentTime()))
        }
    }

    override fun setDish(dish: Int) {
        this.dish = dish
        viewModelScope.launch {
            dishWater.emit(dish)
        }
    }

    override fun alarmClick() {
        viewModelScope.launch {
            openChooseTimeFlow.emit(Unit)
        }
    }

    override fun waterClick() {
        viewModelScope.launch {
            openEditBottleFlow.emit(Unit)
        }
    }

    override fun update() {
        goalTrack = useCase.goalTrack()
    }


    override fun deleteWater(waterEntity: WaterEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteWaterEntity(waterEntity)
        }
    }

    override fun editWater(waterEntity: WaterEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateWaterEntity(waterEntity)
        }
    }
}