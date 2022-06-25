package com.goMars.yeongsungbus.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import com.goMars.yeongsungbus.R
import com.goMars.yeongsungbus.databinding.ActivityMainBinding
import com.goMars.yeongsungbus.methods.screenSize.ScreenSize

class MainActivity : AppCompatActivity() {
    val Tag: String = "MainActivity : "
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener(btnListener)

    }

    val btnListener : View.OnClickListener = View.OnClickListener {
        startActivity(Intent(this,LongDistanceBusActivity::class.java))
    }


}