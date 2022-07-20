package com.goMars.yeongsungbus.screen

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.goMars.yeongsungbus.R
import com.goMars.yeongsungbus.databinding.ActivityMainBinding
import com.goMars.yeongsungbus.methods.floatbutton.FloatButton


open class MainActivity : AppCompatActivity() {
    val Tag: String = "MainActivityTest"
    public lateinit var binding: ActivityMainBinding
    public var isFabOpen = false // Fab 버튼 default는 닫혀있음


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgGps.setOnClickListener(btngps())


        val tabhost: TabHost = findViewById<TabHost>(R.id.tabhost)
        tabhost.setup()

        val tab1 = tabhost.newTabSpec("안양") //구분할때 사용하는 것
        tab1.setIndicator("안양") //표시될 이름
        tab1.setContent(R.id.linear1) // 표시될 레이아웃 아이디
        tabhost.addTab(tab1) //탭호스트에 추가

        val tab2 = tabhost.newTabSpec("범계") //구분할때 사용하는 것
        tab2.setIndicator("범계") //표시될 이름
        tab2.setContent(R.id.linear2) // 표시될 레이아웃 아이디
        tabhost.addTab(tab2) //탭호스트에 추가

        val tab3 = tabhost.newTabSpec("시외") //구분할때 사용하는 것
        tab3.setIndicator("장거리") //표시될 이름
        tab3.setContent(R.id.linear3) // 표시될 레이아웃 아이디
        tabhost.addTab(tab3) //탭호스트에 추가

        tabhost.currentTab = 0

        binding.fabcommon.fabadd.setOnClickListener {
            toggleFab()
        }

        val btnfloating =
            arrayOf(binding.fabcommon.fabhuman, binding.fabcommon.fabpay) //floating button 클릭 리스너
        for (i: Int in 0 until btnfloating.size) {
            btnfloating[i].setOnClickListener(fabListener())
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
            R.id.fabpay -> {
                startActivity(Intent(this, LongDistanceBusActivity::class.java))
                overridePendingTransition(R.anim.slide_up_out, R.anim.slide_up_in)
            }
        }
    }


    private fun toggleFab() {

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

    private fun btngps(): View.OnClickListener = View.OnClickListener { view ->
        startActivity(Intent(this, findLoc::class.java))
        overridePendingTransition(R.anim.slide_up_out, R.anim.slide_up_in)
    }

    // 뒤로가기 2번
    private var backPressedTime : Long = 0
    override fun onBackPressed() {

        // 2초내 다시 클릭하면 앱 종료
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finishAffinity();
            System.runFinalization();
            System.exit(0);
            return
        }

        // 처음 클릭 메시지
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()

    }

}


