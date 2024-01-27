package com.example.adminappasmita.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adminappasmita.DataClass.Match
import com.example.adminappasmita.R

class AddedMatchAdapter(private val matches:List<Match>):RecyclerView.Adapter<AddedMatchAdapter.AddedMatchViewHolder>() {

    class AddedMatchViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var clg1:TextView=itemView.findViewById(R.id.clgName1)
        var clg2:TextView=itemView.findViewById(R.id.clgName2)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddedMatchViewHolder {
        val inflator=LayoutInflater.from(parent.context)
        val view=inflator.inflate(R.layout.match_rv,parent,false)
        return AddedMatchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return matches.size
    }

    override fun onBindViewHolder(holder: AddedMatchViewHolder, position: Int) {
        holder.clg1.text=matches[position].clg1
        holder.clg2.text=matches[position].clg2
    }
}
