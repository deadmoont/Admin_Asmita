package com.example.adminappasmita.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.adminappasmita.DataClass.Leaderboard_dataclass
import com.example.adminappasmita.R


class LeaderboardAdapter(private val leader:List<Leaderboard_dataclass>):RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {
    class LeaderboardViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            val logo:ImageView = itemView.findViewById(R.id.logo)
            val clg_name = itemView.findViewById<TextView>(R.id.clgName)
            val points = itemView.findViewById<TextView>(R.id.points)
            val increaseButton = itemView.findViewById<ImageView>(R.id.increase)
            val decreaseButton = itemView.findViewById<ImageView>(R.id.decrease)
            val savebtn=itemView.findViewById<Button>(R.id.save)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
//        val inflator =
        val view = LayoutInflater.from(parent.context).inflate(R.layout.leaderboard_rv, parent, false)
        return LeaderboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return leader.size
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val team = leader[position]
        holder.clg_name.text = team.name
        holder.points.text = team.points.toString()

        Glide.with(holder.itemView.context)
            .load(team.img)
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(holder.logo)

        holder.increaseButton.setOnClickListener {
            team.points += 1
            holder.points.text=team.points.toString()
        }
        holder.decreaseButton.setOnClickListener {
                team.points -= 1
                holder.points.text=team.points.toString()
        }
        holder.savebtn.setOnClickListener {
            notifyDataSetChanged()
        }

    }
}


