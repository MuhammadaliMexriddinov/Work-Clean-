package uz.gita.waterappgoogleplaygb.data.prefs

import android.content.Context
import android.content.SharedPreferences
import uz.gita.waterappgoogleplaygb.utils.SharedPreference
import javax.inject.Inject

class MySharedPref @Inject constructor(
    ctx: Context,
    sharedPreferences: SharedPreferences
) : SharedPreference(ctx, sharedPreferences) {

    var gender: String by Strings("MALE")

    var weight: Int by Ints(50)

    var dailyWater: Int by Ints(2000)
}