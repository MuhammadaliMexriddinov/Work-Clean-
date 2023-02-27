package uz.gita.waterappgoogleplaygb.presentetion.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import uz.gita.waterappgoogleplaygb.databinding.DialogSetWeightBinding

class SetWeightDialog(ctx: Context, private val weight: Int) : Dialog(ctx) {

    private lateinit var binding: DialogSetWeightBinding

    private var setWeightListener: ((Int) -> Unit)? = null

    fun setWeightListener(block: (Int) -> Unit) {
        setWeightListener = block
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogSetWeightBinding.inflate(layoutInflater)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)

        binding.numberPicker.minValue = 30
        binding.numberPicker.maxValue = 200
        binding.numberPicker.value = weight
        binding.btnSave.setOnClickListener {
            setWeightListener?.invoke(binding.numberPicker.value)
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }


}