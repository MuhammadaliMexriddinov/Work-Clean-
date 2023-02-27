package uz.gita.waterappgoogleplaygb.presentetion.screens.pages

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.waterappgoogleplaygb.R
import uz.gita.waterappgoogleplaygb.databinding.ScreenGeneralBinding
import uz.gita.waterappgoogleplaygb.presentetion.dialogs.SetIntakeGoalDialog
import uz.gita.waterappgoogleplaygb.presentetion.dialogs.SetWeightDialog
import uz.gita.waterappgoogleplaygb.presentetion.viewmodels.SettingsViewModel
import uz.gita.waterappgoogleplaygb.presentetion.viewmodels.impl.SettingsViewModelImpl

@AndroidEntryPoint
class GeneralScreen:Fragment(R.layout.screen_general) {

    private val viewModel: SettingsViewModel by viewModels<SettingsViewModelImpl>()

    private val viewBinding: ScreenGeneralBinding by viewBinding(ScreenGeneralBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewBinding.containerIntake.setOnClickListener {
            showTakeGoalDialog()
        }

        viewBinding.containerGender.setOnClickListener {
            showGenderDialog()
        }
        viewBinding.containerWeight.setOnClickListener {
            showWeightDialog()
        }

        viewModel.intakeGoalFlow.onEach {
            viewBinding.tvIntakeGoal.text = "$it ml"
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.genderFlow.onEach {
            viewBinding.tvGender.text = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.weightFlow.onEach {
            viewBinding.tvWeight.text = "$it kg"
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun showWeightDialog() {
        val dialog = SetWeightDialog(requireContext(), viewModel.getWeight())
        dialog.setWeightListener {
            viewModel.setWeight(it)
        }
        dialog.show()
    }

    private fun showTakeGoalDialog() {
        val dialog = SetIntakeGoalDialog(requireContext(), viewModel.getIntakeGoal())
        dialog.setChangeWaterGoal {
            viewModel.setIntakeGoal(it)
        }
        dialog.show()
    }

    private fun showGenderDialog() {
        PowerMenu.Builder(requireContext())
            .addItem(
                PowerMenuItem("Male")
            )
            .addItem(
                PowerMenuItem("Female")
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
                    viewModel.setGender("Male")
                } else {
                    viewModel.setGender("Female")
                }

            }
            .build()
            .showAsAnchorRightBottom(viewBinding.tvGender)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getData()
    }

}