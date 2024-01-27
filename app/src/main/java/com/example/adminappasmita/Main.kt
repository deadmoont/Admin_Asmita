package com.example.adminappasmita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminappasmita.Adapter.AddedMatchAdapter
import com.example.adminappasmita.DataClass.Match
import com.example.adminappasmita.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class Main : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var mat:MutableList<Match> = mutableListOf()
    private lateinit var matchAdapter:AddedMatchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = 0xFF000000.toInt()

        binding.add.setOnClickListener {
            startActivity(Intent(this,MatchAdd::class.java))
        }
        matchAdapter= AddedMatchAdapter(mat)
        binding.matches.adapter=matchAdapter
        binding.matches.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        fetchfromfirestore()
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
}