package com.goMars.yeongsungbus.screen

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TabHost
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.goMars.yeongsungbus.R
import com.goMars.yeongsungbus.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout

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


        // appbarlayout을 접었을때와 폈을때를 감지하는 메소드
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener() { appBarLayout: AppBarLayout?, verticalOffset: Int ->

            if (Math.abs(verticalOffset) - appBarLayout!!.totalScrollRange == 0) { //닫혀있을때
                count += 1
            }

            if (count >= 1) {
                if (Math.abs(verticalOffset) - appBarLayout!!.totalScrollRange != 0) { //열렸을때
                    count += 1
                    Log.d(Tag, y.toString())
                    binding.busimg.getLocationOnScreen(location) //뷰 좌표 설정
                    x = location[0]
                    y = location[1]
                    Log.d(Tag, y.toString()) //좌표값 얻는 메소드


                    if (y >= 2000) {
                        startActivity(Intent(this, MainActivity::class.java))
                        overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out)
                        count = 0
                        finish()

                    }
                }
            }
        })

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


