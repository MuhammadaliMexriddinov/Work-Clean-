package uz.gita.waterappgoogleplaygb.presentetion.screens.pages

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.view.clicks
import uz.gita.waterappgoogleplaygb.R
import uz.gita.waterappgoogleplaygb.data.local.entity.WaterEntity
import uz.gita.waterappgoogleplaygb.databinding.ScreenManagerBinding
import uz.gita.waterappgoogleplaygb.presentetion.adapters.WaterAdapter
import uz.gita.waterappgoogleplaygb.presentetion.dialogs.ChooseBottleDialog
import uz.gita.waterappgoogleplaygb.presentetion.dialogs.ChooseTimeDialog
import uz.gita.waterappgoogleplaygb.presentetion.viewmodels.HomeViewModel
import uz.gita.waterappgoogleplaygb.presentetion.viewmodels.impl.HomeViewModelImpl
import uz.gita.waterappgoogleplaygb.utils.Utils
import uz.gita.waterappgoogleplaygb.utils.extensions.getCurrentTime
import uz.gita.waterappgoogleplaygb.utils.extensions.getInitialDelayTime
import uz.gita.waterappgoogleplaygb.utils.extensions.yoYo
import uz.gita.waterappgoogleplaygb.workmanager.Work
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ManagerScreen : Fragment(R.layout.screen_manager) {

    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()

    private val viewBinding: ScreenManagerBinding by viewBinding(ScreenManagerBinding::bind)

    private val adapter: WaterAdapter by lazy {
        WaterAdapter()
    }

    @OptIn(FlowPreview::class)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.listWaterTracks.adapter = adapter

        viewModel.allWaterTracksFlow.onEach {
            if (it.isEmpty()){
                viewBinding.anim1.visibility=View.VISIBLE
            }
            else viewBinding.anim1.visibility=View.INVISIBLE
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.openChooseTimeFlow.onEach {
            val dialog = ChooseTimeDialog(requireContext(), getCurrentTime())
            dialog.setTimeListener {
                setAlarm(it)
                viewBinding.tvWaterTime.text = it
            }
            dialog.show()
        }.launchIn(lifecycleScope)

        viewBinding.imageAddGlass.setOnClickListener {
            viewModel.insertTracks()
            it.yoYo()
            Utils.changeData = true
            playSoundWater()
        }
        viewBinding.imageClock.clicks().debounce(100L).onEach {
            viewBinding.imageClock.yoYo()
            delay(210)
            viewModel.alarmClick()
        }.launchIn(lifecycleScope)

        viewModel.waterDrinkFlow.observe(viewLifecycleOwner) {
            val track = it ?: 0
            viewBinding.tvWaterDrink.text = "$track/${viewModel.goalTrack}"
            viewBinding.progressCircularMain.progress = (track * 100 / viewModel.goalTrack).coerceAtMost(100)
            if(track.equals(viewModel.goalTrack)) Toast.makeText(requireContext(), "the amount is sufficient", Toast.LENGTH_SHORT).show()
        }

        adapter.setOnItemMenuListener { water, anchorView ->
            showDialog(water, anchorView)
        }

        viewBinding.imageEditGlass.clicks().debounce(100).onEach {
            viewBinding.imageEditGlass.yoYo()
            delay(210)
            viewModel.waterClick()
        }.launchIn(lifecycleScope)

        viewModel.dishWater.onEach {
            viewBinding.tvWaterTrack.text = "$it ml"
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.openEditBottleFlow.onEach {
            showEditBottleDialog {
                viewModel.setDish(it)

            }
        }.launchIn(lifecycleScope)
    }

    private fun setAlarm(time: String) {
        val delay = getInitialDelayTime(time)
        val workRequest = OneTimeWorkRequest.Builder(Work::class.java)
            .addTag("5chi")
            .setInitialDelay(delay.toLong(), TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(requireContext()).enqueue(workRequest)
    }

    private fun showDialog(waterEntity: WaterEntity, anchorView: View) {
        PowerMenu.Builder(requireContext())
            .addItem(
                PowerMenuItem("Edit", R.drawable.ic_edit)
            )
            .addItem(
                PowerMenuItem("Delete", R.drawable.ic_delete)
            )
            .setDividerHeight(4)
            .setTextGravity(Gravity.CENTER)
            .setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
            .setSelectedTextColor(Color.WHITE)
            .setMenuColor(Color.WHITE)
            .setMenuRadius(16f)
            .setIconSize(18)
            .setAutoDismiss(true)
            .setOnMenuItemClickListener { position, _ ->
                if (position == 0) {
                    showEditBottleDialog {
                        viewModel.editWater(waterEntity.copy(drink = it))
                    }
                } else {
                    viewModel.deleteWater(waterEntity)
                }

            }
            .build()
            .showAsAnchorRightBottom(anchorView)

    }

    private fun showEditBottleDialog(block: (Int) -> Unit) {
        val dialog = ChooseBottleDialog(requireContext())
        dialog.setChangeBottleSelectedListener {
            block.invoke(it)
        }
        dialog.show()
    }

    private fun playSoundWater() {
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.water_flow)
        mediaPlayer.start()
    }

    override fun onResume() {
        super.onResume()
        viewModel.update()
    }

}