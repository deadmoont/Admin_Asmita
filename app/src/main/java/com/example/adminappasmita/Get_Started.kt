package com.example.adminappasmita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminappasmita.databinding.ActivityGetStartedBinding

class Get_Started : AppCompatActivity() {

    private lateinit var binding: ActivityGetStartedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = 0xFF000000.toInt()

        binding.getStartedBut.setOnClickListener {
            startActivity(Intent(this,NewMatchActivity::class.java))
            finish()
        }
    }
}