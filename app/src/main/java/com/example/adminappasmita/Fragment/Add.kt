package com.example.adminappasmita.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.adminappasmita.R
import com.example.adminappasmita.databinding.FragmentAddBinding

class Add : Fragment() {
    private lateinit var binding: FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.add.setOnClickListener {
//            val nextFragment = AddMatch()
//            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
//            transaction.replace(R.id.fragmentContainer, nextFragment)
//             transaction.addToBackStack(null)
//            transaction.commit()
        }
//        matchAdapter= AddedMatchAdapter(mat)
//        binding.matches.adapter=matchAdapter
//        binding.matches.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
//        fetchfromfirestore()

    }
}