package com.goMars.yeongsungbus.methods.screenSize

import android.app.Activity
import android.graphics.Point
import android.view.Display
import com.goMars.yeongsungbus.screen.MainActivity


class ScreenSize {
    companion object {
        var standardSize_X : Int = 0
        var standardSize_Y : Int = 0
        var density : Float = 0F

        fun getScreenSize(activity:Activity) : String{
            val display : Display = activity.windowManager.defaultDisplay
            var size : Point = Point()
            display.getSize(size)

            return size.toString()
        }

    }
}