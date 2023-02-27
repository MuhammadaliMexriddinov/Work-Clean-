package uz.gita.waterappgoogleplaygb.presentetion.screens.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.waterappgoogleplaygb.R
import uz.gita.waterappgoogleplaygb.data.local.entity.ReminderEntity
import uz.gita.waterappgoogleplaygb.databinding.ScreenReminderBinding
import uz.gita.waterappgoogleplaygb.presentetion.adapters.ReminderAdapter
import uz.gita.waterappgoogleplaygb.presentetion.dialogs.ChooseTimeDialog
import uz.gita.waterappgoogleplaygb.presentetion.dialogs.DeleteDialog
import uz.gita.waterappgoogleplaygb.presentetion.viewmodels.ReminderViewModel
import uz.gita.waterappgoogleplaygb.presentetion.viewmodels.impl.ReminderViewModelImpl
import uz.gita.waterappgoogleplaygb.utils.extensions.getCurrentTime
import uz.gita.waterappgoogleplaygb.utils.extensions.getInitialDelayTime
import uz.gita.waterappgoogleplaygb.workmanager.Work
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ReminderScreen:Fragment(R.layout.screen_reminder) {
    private val viewBinding: ScreenReminderBinding by viewBinding(ScreenReminderBinding::bind)

    private val viewModel: ReminderViewModel by viewModels<ReminderViewModelImpl>()

    private val adapter: ReminderAdapter by lazy { ReminderAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.listReminder.adapter = adapter

        adapter.setOnDeleteClickListener {
            showDeleteDialog(it)
        }

        viewBinding.fabAddReminder.setOnClickListener {
            viewModel.addReminderClick()
        }


        viewModel.allReminderFlow.onEach {
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.openTimePickerFlow.onEach {
            showTimePicker()
        }.launchIn(lifecycleScope)

    }

    private fun showTimePicker() {
        val dialog = ChooseTimeDialog(requireContext(), getCurrentTime())
        dialog.setTimeListener {
            viewModel.addReminder(it)
            addWorkManager(it)
        }
        dialog.show()
    }

    private fun showDeleteDialog(reminderEntity: ReminderEntity) {
        val dialog = DeleteDialog()
        dialog.setDeleteListener {
            viewModel.deleteReminder(reminderEntity)
            WorkManager.getInstance(requireContext()).cancelAllWorkByTag(reminderEntity.time)
        }
        dialog.show(childFragmentManager,"")
    }

    private fun addWorkManager(time: String) {
        val request = PeriodicWorkRequest.Builder(Work::class.java, 24L, TimeUnit.HOURS)
            .setInitialDelay(getInitialDelayTime(time).toLong(), TimeUnit.SECONDS)
            .addTag(time)
            .build()

        WorkManager.getInstance(requireContext()).enqueue(request)
    }
}