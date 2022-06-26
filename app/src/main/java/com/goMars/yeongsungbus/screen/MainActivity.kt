package com.goMars.yeongsungbus.screen

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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

        binding.fabadd.setOnClickListener {
            toggleFab()
        }

        // 플로팅 버튼 클릭 이벤트 - 캡처
        binding.fabhuman.setOnClickListener {
            Toast.makeText(applicationContext, "개발자 버튼 클릭!", Toast.LENGTH_SHORT).show()
        }

    }









    private fun toggleFab() {
        binding.fabhuman.visibility = View.VISIBLE
        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션
        if (isFabOpen) {
            ObjectAnimator.ofFloat(binding.fabhuman, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabadd, View.ROTATION, 45f, 0f).apply { start() }
            binding.fabhuman.visibility = View.GONE

        } else { // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션
            ObjectAnimator.ofFloat(binding.fabhuman, "translationY", -180f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabadd, View.ROTATION, 0f, 45f).apply { start() }
        }
        isFabOpen = !isFabOpen
    }

    val btnListener : View.OnClickListener = View.OnClickListener {
        startActivity(Intent(this,LongDistanceBusActivity::class.java))
    }



}

