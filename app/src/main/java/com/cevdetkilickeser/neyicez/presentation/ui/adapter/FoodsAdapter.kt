package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.neyicez.data.model.Food
import com.cevdetkilickeser.neyicez.databinding.ItemViewFoodBinding

class FoodsAdapter(
    private val foodList: List<Food>,
    private val onFoodClickListener: (Food) -> Unit,
    private val onAddToCartClickListener: (Food) -> Unit
) : RecyclerView.Adapter<FoodsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemViewFoodBinding.inflate(layoutInflater, parent, false)
        return FoodsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodsViewHolder, position: Int) {
        holder.bind(foodList[position], onFoodClickListener, onAddToCartClickListener)
    }
}