package uz.gita.waterappgoogleplaygb.presentetion.viewmodels

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.SharedFlow
import uz.gita.waterappgoogleplaygb.data.local.entity.WaterEntity

interface HomeViewModel {

    val allWaterTracksFlow: SharedFlow<List<WaterEntity>>

    val dishWater: SharedFlow<Int>

    val waterDrinkFlow: LiveData<Int>

    var goalTrack: Int

    val openEditBottleFlow: SharedFlow<Unit>

    val goalDrinkFlow: SharedFlow<Int>

    val openChooseTimeFlow: SharedFlow<Unit>

    fun insertTracks()

    fun setDish(dish: Int)

    fun alarmClick()

    fun waterClick()

    fun update()

    fun deleteWater(waterEntity: WaterEntity)

    fun editWater(waterEntity: WaterEntity)


}