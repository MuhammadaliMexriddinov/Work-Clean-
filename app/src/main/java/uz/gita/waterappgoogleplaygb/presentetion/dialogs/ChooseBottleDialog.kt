package uz.gita.waterappgoogleplaygb.presentetion.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import uz.gita.waterappgoogleplaygb.R
import uz.gita.waterappgoogleplaygb.databinding.DialogChooseBottleBinding


class ChooseBottleDialog(ctx: Context) : Dialog(ctx) {

    private lateinit var binding: DialogChooseBottleBinding

    private var changeBottleSelectedListener: ((Int) -> Unit)? = null

    fun setChangeBottleSelectedListener(block: (Int) -> Unit) {
        changeBottleSelectedListener = block
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogChooseBottleBinding.inflate(layoutInflater)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)
        for (i in 0 until binding.containerBottle1.childCount) {
            val view = binding.containerBottle1.getChildAt(i)
            view.setBackgroundResource(R.drawable.bg_dialog_container_selected)
            view.setOnClickListener {
                changeBottleSelectedListener?.invoke(i * 50 + 150)
                dismiss()
            }
        }
        for (i in 0 until binding.containerBottle2.childCount) {
            val view = binding.containerBottle2.getChildAt(i)
            view.setBackgroundResource(R.drawable.bg_dialog_container_selected)
            view.setOnClickListener {
                changeBottleSelectedListener?.invoke((i+3) * 50 + 150)
                dismiss()
            }
        }
    }
}