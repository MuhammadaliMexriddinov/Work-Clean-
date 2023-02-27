package uz.gita.waterappgoogleplaygb.presentetion.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterappgoogleplaygb.BuildConfig
import uz.gita.waterappgoogleplaygb.R
import uz.gita.waterappgoogleplaygb.databinding.ScreenMainBinding
import uz.gita.waterappgoogleplaygb.presentetion.adapters.MainTabAdapter

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {

    private val binding: ScreenMainBinding by viewBinding(ScreenMainBinding::bind)


    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pageMain.adapter = MainTabAdapter(requireActivity())

        /**
         * TabLayout
         * */
        TabLayoutMediator(binding.mainTab, binding.pageMain) { tab, pos ->
            when (pos) {
                0 -> {
                    tab.setIcon(R.drawable.home)
                }
                1 -> {
                    tab.setIcon(R.drawable.statistics)
                }
                2 -> {
                    tab.setIcon(R.drawable.reminder)
                }
                else -> {
                    tab.setIcon(R.drawable.profile)
                }
            }



        }.attach()

        binding.pageMain.setPageTransformer { page, position ->
            if (position >= -1.0f || position <= 1.0f) {
                val height = page.getHeight().toFloat()
                val scaleFactor = Math.max(0.85f, 1.0f - Math.abs(position))
                val vertMargin = height * (1.0f - scaleFactor) / 2.0f
                val horzMargin = page.getWidth().toFloat() * (1.0f - scaleFactor) / 2.0f
                page.setPivotY(0.5f * height)
                if (position < 0.0f) {
                    page.setTranslationX(horzMargin - vertMargin / 2.0f)
                } else {
                    page.setTranslationX(-horzMargin + vertMargin / 2.0f)
                }
                page.setScaleX(scaleFactor)
                page.setScaleY(scaleFactor)
                page.setAlpha(0.5f + (scaleFactor - 0.85f) / 0.14999998f * 0.5f)
            }
        }

        /**
         * VP2
         * */
        binding.pageMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        binding.txtCategory.text = "Home"
                    }
                    1 -> {
                        binding.txtCategory.text = "Statistics"
                    }
                    2 -> {
                        binding.txtCategory.text = "Reminder"
                    }
                    else -> {
                        binding.txtCategory.text = "General"
                    }
                }
            }
        })

        /**
         * NavigationView
         * */
        binding.navigationView.setNavigationItemSelectedListener {



            when (it.itemId) {
                R.id.dictionary -> {
                    try {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ga-ie.facebook.com/pg/gitauzofficial/posts/"))
                        startActivity(browserIntent)
                    }catch (e:Exception){
                        Toast.makeText(requireContext(), "not found!!!", Toast.LENGTH_SHORT).show()
                    }
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.about -> {
                    //findNavController().navigate(HomeScreenDirections.actionHomeFragmentToAboutScreen())
                    findNavController().navigate(MainScreenDirections.actionMainScreenToInformationScreen())
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.share -> {
                    try {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                        var shareMessage = "\nLet me recommend you this application\n\n"
                        shareMessage =
                            """
                            ${shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID}
                            
                            
                            """.trimIndent()
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    } catch (e: Exception) {
                        //e.toString();
                    }
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.exit_app -> {
                    requireActivity().finish()
                }
            }
            return@setNavigationItemSelectedListener true
        }


        binding.menu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

}