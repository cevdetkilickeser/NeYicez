package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cevdetkilickeser.neyicez.data.model.Foods
import com.cevdetkilickeser.neyicez.databinding.FoodsCardBinding
import com.cevdetkilickeser.neyicez.presentation.viewmodel.HomeViewModel

class FoodsViewHolder(private val binding: FoodsCardBinding, private val viewModel: HomeViewModel) : RecyclerView.ViewHolder(binding.root) {

    fun bind(food: Foods, onClickListener: (Foods) -> Unit) {
        with(binding) {
            Glide.with(root.context)
                .load("http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}")
                .into(imageViewFoodsCard)
            textViewNameFoodsCard.text = food.yemek_adi
            textViewPriceFoodsCard.text = "₺ ${food.yemek_fiyat}"
            buttonAddFoodsCard.setOnClickListener {
                viewModel.addToCart(food.yemek_adi, food.yemek_resim_adi, food.yemek_fiyat)
            }
            cardViewHome.setOnClickListener {
                onClickListener.invoke(food)
            }
        }
    }
}