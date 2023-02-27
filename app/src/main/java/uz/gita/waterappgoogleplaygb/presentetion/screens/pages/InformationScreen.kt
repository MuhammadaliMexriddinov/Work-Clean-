package uz.gita.waterappgoogleplaygb.presentetion.screens.pages

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.waterappgoogleplaygb.R
import uz.gita.waterappgoogleplaygb.databinding.ScreenInfoBinding

class InformationScreen:  Fragment(R.layout.screen_info) {

    private  val binding by viewBinding(ScreenInfoBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tuit.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tuit.uz/"))
            startActivity(browserIntent)
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.txtGita.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ga-ie.facebook.com/pg/gitauzofficial/posts/"))
            startActivity(browserIntent)
        }

    }
}