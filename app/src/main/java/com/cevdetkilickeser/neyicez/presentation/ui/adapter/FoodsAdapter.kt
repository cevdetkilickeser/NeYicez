package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.neyicez.data.model.Foods
import com.cevdetkilickeser.neyicez.databinding.FoodsCardBinding
import javax.inject.Inject

class FoodsAdapter @Inject constructor(
    private val foodList: List<Foods>,
    private val onFoodClickListener: (Foods) -> Unit,
    private val onAddToCartClickListener: (Foods) -> Unit
) : RecyclerView.Adapter<FoodsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FoodsCardBinding.inflate(layoutInflater, parent, false)
        return FoodsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodsViewHolder, position: Int) {
        holder.bind(foodList[position], onFoodClickListener, onAddToCartClickListener)
    }
}