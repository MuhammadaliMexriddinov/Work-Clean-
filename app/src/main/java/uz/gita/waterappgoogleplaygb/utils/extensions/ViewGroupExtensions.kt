package uz.gita.waterappgoogleplaygb.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo


fun ViewGroup.inflate(resId: Int): View {
    return LayoutInflater.from(this.context).inflate(resId, this, false)
}

fun View.yoYo() {
    YoYo.with(Techniques.Flash)
        .duration(200)
        .interpolate(BounceInterpolator())
        .playOn(this)
}