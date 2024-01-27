package com.example.adminappasmita.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.adminappasmita.Adapter.AddedMatchAdapter
import com.example.adminappasmita.DataClass.Match
import com.example.adminappasmita.Fragment.Add
import com.example.adminappasmita.Fragment.AddMatch
import com.example.adminappasmita.Fragment.AddScore
import com.example.adminappasmita.R
import com.example.adminappasmita.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class Main : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var mat:MutableList<Match> = mutableListOf()
    private lateinit var matchAdapter:AddedMatchAdapter

    private lateinit var bottomNavigationView: BottomNavigationView

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_score -> {
                    if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) !is AddScore) {
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        loadFragment(AddScore())
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_match -> {
                    if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) !is Add) {
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        loadFragment(Add())
                    }
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = 0xFF000000.toInt()


        bottomNavigationView = findViewById(R.id.bottom)
        bottomNavigationView.setOnNavigationItemSelectedListener(
            onNavigationItemSelectedListener
        )
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        loadFragment(AddMatch())
        bottomNavigationView.selectedItemId = R.id.navigation_match
    }

    private fun fetchfromfirestore() {

        mat.clear()
        FirebaseFirestore.getInstance().collection("Match").get().addOnSuccessListener { documents->
            for(document in documents){
                val clg1=document.getString("")?:""
                val clg2=document.getString("")?:""
                val match=document.getString("")?:""
                val time=document.getString("")?:""

                mat.add(Match(clg1,clg2,match,time))
            }
            matchAdapter.notifyDataSetChanged()
        }.addOnFailureListener {
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}