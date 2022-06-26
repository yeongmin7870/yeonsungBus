package com.goMars.yeongsungbus.screen

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
import com.goMars.yeongsungbus.R
import com.goMars.yeongsungbus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val Tag: String = "MainActivityTest"
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE)

    }


    val btnListener : View.OnClickListener = View.OnClickListener {
        startActivity(Intent(this,LongDistanceBusActivity::class.java))
    }



}

