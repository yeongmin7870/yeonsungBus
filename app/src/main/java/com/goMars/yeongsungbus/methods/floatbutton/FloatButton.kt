package com.goMars.yeongsungbus.methods.floatbutton

import android.animation.ObjectAnimator
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.goMars.yeongsungbus.R
import com.goMars.yeongsungbus.databinding.ActivityMainBinding
import com.goMars.yeongsungbus.screen.LongDistanceBusActivity
import com.goMars.yeongsungbus.screen.MainActivity


class FloatButton : MainActivity() {

    // fab 버튼 클릭했을때
    override fun fabListener(): View.OnClickListener = View.OnClickListener { view: View? ->
        when (view?.id) {
            R.id.fabhuman -> {
                val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView: View = inflater.inflate(R.layout.activity_popup, null)

                var width: Int = LinearLayout.LayoutParams.MATCH_PARENT
                var height: Int = LinearLayout.LayoutParams.MATCH_PARENT
                var popupWindow: PopupWindow = PopupWindow(popupView, width, height)

                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
                popupView.setOnTouchListener(View.OnTouchListener() { view, motionEvent ->
                    popupWindow.dismiss()
                    return@OnTouchListener true
                })

            }
            R.id.fabpay -> {
                startActivity(Intent(this, LongDistanceBusActivity::class.java))
                overridePendingTransition(R.anim.slide_up_out, R.anim.slide_up_in)
            }
        }
    }


    fun toggleFab() {


        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션
        if (isFabOpen) {
            ObjectAnimator.ofFloat(binding.fabcommon.fabpay, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabcommon.fabhuman, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabcommon.fabadd, View.ROTATION, 45f, 0f)
                .apply { start() }


        } else { // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션
            ObjectAnimator.ofFloat(binding.fabcommon.fabpay, "translationY", -360f)
                .apply { start() }
            ObjectAnimator.ofFloat(binding.fabcommon.fabhuman, "translationY", -180f)
                .apply { start() }
            ObjectAnimator.ofFloat(binding.fabcommon.fabadd, View.ROTATION, 0f, 45f)
                .apply { start() }
        }
        isFabOpen = !isFabOpen
    }

}