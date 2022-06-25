package com.goMars.yeongsungbus.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.goMars.yeongsungbus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val Tag: String = "MainActivity : "
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    val btnListener : View.OnClickListener = View.OnClickListener {
        startActivity(Intent(this,LongDistanceBusActivity::class.java))
    }


}