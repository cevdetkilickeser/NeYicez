package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.neyicez.data.model.Foods
import com.cevdetkilickeser.neyicez.databinding.FoodsCardBinding
import com.cevdetkilickeser.neyicez.presentation.viewmodel.HomeViewModel
import javax.inject.Inject

class FoodsAdapter @Inject constructor(
    private val viewModel: HomeViewModel,
    private val foodList: List<Foods>,
    private val onClickListener: (Foods) -> Unit
) : RecyclerView.Adapter<FoodsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FoodsCardBinding.inflate(layoutInflater, parent, false)
        return FoodsViewHolder(binding, viewModel)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodsViewHolder, position: Int) {
        holder.bind(foodList[position], onClickListener)
    }
}