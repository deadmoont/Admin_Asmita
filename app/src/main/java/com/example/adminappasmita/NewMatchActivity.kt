package com.example.adminappasmita

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NewMatchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_match)

        window.statusBarColor = 0xFF000000.toInt()
    }
}