package com.goMars.yeongsungbus.screen

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.goMars.yeongsungbus.R
import com.goMars.yeongsungbus.databinding.ActivityLongdistancebusBinding
import com.goMars.yeongsungbus.databinding.ActivityMainBinding

class LongDistanceBusActivity : AppCompatActivity() {
    val Tag: String ="LongDistanceBusTest"
    private lateinit var ActivityMainBinding: ActivityMainBinding
    private lateinit var binding: ActivityLongdistancebusBinding
    private var isFabOpen = false // Fab 버튼 default는 닫혀있음


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLongdistancebusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            overridePendingTransition(R.anim.slide_up_out, R.anim.slide_up_in)
        })

        binding.btnpaco.setOnClickListener(View.OnClickListener {
            val intent = packageManager.getLaunchIntentForPackage("com.nhnent.payapp")
            try {
                startActivity(intent) //페이코앱이 있으면 그냥 실행
            }catch (e : Exception){
                startActivity(
                    Intent(    //페이코 앱이 없으면 플레이스토어로 가기
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id="+"com.nhnent.payapp")
                    )
                )
            }

        })

        val btnfloating = arrayOf(binding.fabcommon.fabhuman,binding.fabcommon.fabpay) //floating button 클릭 리스너
        for(i:Int in 0 until btnfloating.size) {
            btnfloating[i].setOnClickListener(fabListener())
        }

        binding.fabcommon.fabadd.setOnClickListener {
            toggleFab()
        }

    }



    // fab 버튼 클릭했을때
    open fun fabListener(): View.OnClickListener = View.OnClickListener { view: View? ->
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
        }
    }



    private fun toggleFab() {

        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션
        if (isFabOpen) {
            ObjectAnimator.ofFloat(binding.fabcommon.fabpay, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabcommon.fabhuman, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabcommon.fabadd, View.ROTATION, 45f, 0f).apply { start() }


        } else { // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션
            ObjectAnimator.ofFloat(binding.fabcommon.fabpay, "translationY", -360f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabcommon.fabhuman, "translationY", -180f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabcommon.fabadd, View.ROTATION, 0f, 45f).apply { start() }
        }
        isFabOpen = !isFabOpen
    }
}