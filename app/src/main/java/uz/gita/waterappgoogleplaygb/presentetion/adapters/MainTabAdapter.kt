package uz.gita.waterappgoogleplaygb.presentetion.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.waterappgoogleplaygb.databinding.ScreenManagerBinding
import uz.gita.waterappgoogleplaygb.presentetion.screens.pages.*

class MainTabAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ManagerScreen()
            1 -> StatisticScreen()
            2 -> ReminderScreen()
            else->GeneralScreen()
        }
    }
}