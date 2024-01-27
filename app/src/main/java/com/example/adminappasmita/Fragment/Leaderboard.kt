package com.example.adminappasmita.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminappasmita.Adapter.LeaderboardAdapter
import com.example.adminappasmita.DataClass.Leaderboard_dataclass
import com.example.adminappasmita.R
import com.example.adminappasmita.databinding.FragmentLeaderboardBinding
import com.google.firebase.firestore.FirebaseFirestore


class Leaderboard : Fragment() {
    lateinit var binding:FragmentLeaderboardBinding
    private var leader:MutableList<Leaderboard_dataclass> = mutableListOf()
    private lateinit var leaderAdapter:LeaderboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding=FragmentLeaderboardBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        leaderAdapter= LeaderboardAdapter(leader)
        binding.leaderRv.adapter=leaderAdapter
        binding.leaderRv.layoutManager=LinearLayoutManager(requireContext())
        fetchfromfirestore()
    }

    private fun fetchfromfirestore() {
        leader.clear()
        val db=FirebaseFirestore.getInstance()
        db.collection("Leaderboard").orderBy("points").get().addOnSuccessListener {
            for(document in it){
                val logo:String=document.getString("logo")?:""
                val clgName:String=document.getString("Name")?:""
                val points:Int=document.getString("points")?:0
                leader.add(Leaderboard_dataclass(clgName,logo,points))
            }
            leaderAdapter.notifyDataSetChanged()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

}