package uz.gita.waterappgoogleplaygb.presentetion.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.SeekBar
import uz.gita.waterappgoogleplaygb.databinding.DialogSetIntakeBinding


class SetIntakeGoalDialog(ctx: Context, private val goal: Int) : Dialog(ctx) {

    private lateinit var binding: DialogSetIntakeBinding

    private var changeWaterGoal: ((Int) -> Unit)? = null

    fun setChangeWaterGoal(block: (Int) -> Unit) {
        changeWaterGoal = block
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogSetIntakeBinding.inflate(layoutInflater)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)
        val percent = goal / 40
        binding.searchBarWater.progress = percent
        binding.tvIntakeGoal.text = goal.toString()

        binding.searchBarWater.setOnSeekBarChangeListener(@SuppressLint("AppCompatCustomView")
        object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                binding.tvIntakeGoal.text = (2000 + 20 * progress).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnSave.setOnClickListener {
            dismiss()
            changeWaterGoal?.invoke(binding.tvIntakeGoal.text.toString().toInt())
        }
    }
}