package com.example.adminappasmita.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.adminappasmita.DataClass.IIIT_Options
import com.example.adminappasmita.R

class CustomSpinnerAdapter(context: Context, resource: Int, items: List<IIIT_Options>) :
    ArrayAdapter<IIIT_Options>(context, resource, items) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View =
            convertView ?: inflater.inflate(R.layout.custom_spinner_item, parent, false)

        val textView = view.findViewById<TextView>(R.id.textView)
        val imageView = view.findViewById<ImageView>(R.id.imageView)

        val currentItem = getItem(position)
        textView.text = currentItem?.name ?: ""

        // Load image using Glide
        Glide.with(context)
            .load(currentItem?.img)
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)

        return view
    }
}
