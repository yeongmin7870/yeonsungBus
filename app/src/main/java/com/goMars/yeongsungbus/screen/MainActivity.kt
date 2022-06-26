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
    var position_flag = true

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.setTitle("                      연성대 스쿨버스")
//        supportActionBar?.setSubtitle("                      안양           시외          하나")
        setContentView(binding.root)
        binding.collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        when(item.itemId){
            R.id.action_btn1 -> { return false }
            R.id.action_btn2 -> { return false }
            else -> {return super.onOptionsItemSelected(item)}



        }
    }

    val btnListener : View.OnClickListener = View.OnClickListener {
        startActivity(Intent(this,LongDistanceBusActivity::class.java))
    }



}

