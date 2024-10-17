package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.neyicez.data.model.Fav
import com.cevdetkilickeser.neyicez.data.model.Food
import com.cevdetkilickeser.neyicez.databinding.ItemViewFavBinding

class FavsAdapter(
    private var favList: List<Fav>,
    private val onAddToCartClickListener: (Food) -> Unit
) : RecyclerView.Adapter<FavsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemViewFavBinding.inflate(layoutInflater, parent, false)
        return FavsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: FavsViewHolder, position: Int) {
        holder.bind(favList[position], onAddToCartClickListener)
    }
}