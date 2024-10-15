package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.neyicez.data.model.Fav
import com.cevdetkilickeser.neyicez.databinding.FavCardBinding

class FavsAdapter(
    private var favList: List<Fav>
) : RecyclerView.Adapter<FavsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FavCardBinding.inflate(layoutInflater, parent,false)
        return FavsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: FavsViewHolder, position: Int) {
        holder.bind(favList[position])
    }
}