package com.goMars.yeongsungbus.screen

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TabHost
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.goMars.yeongsungbus.R
import com.goMars.yeongsungbus.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    val Tag: String = "MainActivityTest"
    private lateinit var binding: ActivityMainBinding
    private var isFabOpen = false // Fab 버튼 default는 닫혀있음


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE)

        var count: Int = 0 // collapse 닫힌거 확인
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

        val location = IntArray(2) //좌표배열
        var x = location[0]
        var y = location[1]


        tabhost.currentTab = 0

        binding.fabadd.setOnClickListener {
            toggleFab()
        }

        // 플로팅 버튼 클릭 이벤트 - 개발자
        binding.fabhuman.setOnClickListener {
            Toast.makeText(applicationContext, "개발자 버튼 클릭!", Toast.LENGTH_SHORT).show()
        }
        // 플로팅 버튼 클릭 이벤트 - payco
        binding.fabpay.setOnClickListener {
            Toast.makeText(applicationContext, "결제 버튼 클릭!", Toast.LENGTH_SHORT).show()
        }

        val btnfloating = arrayOf(binding.fabhuman,binding.fabpay) //floating button 클릭 리스너
        for(i:Int in 0 until btnfloating.size) {
            btnfloating[i].setOnClickListener(fabListener)
        }


    }


    // fab 버튼 클릭했을때
    private val fabListener : View.OnClickListener = View.OnClickListener { view: View? ->
        when(view?.id){
            R.id.fabhuman -> {
                val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView: View = inflater.inflate(R.layout.activity_popup, null)

                var width : Int = LinearLayout.LayoutParams.MATCH_PARENT
                var height : Int = LinearLayout.LayoutParams.MATCH_PARENT
                var popupWindow : PopupWindow = PopupWindow(popupView,width,height)

                popupWindow.showAtLocation(view, Gravity.CENTER,0,0)
                popupView.setOnTouchListener(View.OnTouchListener(){
                    view, motionEvent ->
                    popupWindow.dismiss()
                    return@OnTouchListener true
                })

            }
            R.id.fabpay -> {
                startActivity(Intent(this, LongDistanceBusActivity::class.java))
                overridePendingTransition(R.anim.slide_up_out,R.anim.slide_up_in)
            }
        }
    }



    private fun toggleFab() {

        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션
        if (isFabOpen) {
            ObjectAnimator.ofFloat(binding.fabpay, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabhuman, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabadd, View.ROTATION, 45f, 0f).apply { start() }


        } else { // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션
            ObjectAnimator.ofFloat(binding.fabpay, "translationY", -360f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabhuman, "translationY", -180f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabadd, View.ROTATION, 0f, 45f).apply { start() }
        }
        isFabOpen = !isFabOpen
    }


}


