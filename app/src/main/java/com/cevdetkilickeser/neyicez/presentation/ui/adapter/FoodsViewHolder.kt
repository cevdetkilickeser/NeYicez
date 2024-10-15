package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.data.model.Foods
import com.cevdetkilickeser.neyicez.databinding.FoodsCardBinding

class FoodsViewHolder(val binding: FoodsCardBinding) : RecyclerView.ViewHolder(binding.root) {

    inline fun bind(food: Foods, crossinline onFoodClickListener: (Foods) -> Unit, crossinline onAddToCartClickListener: (Foods) -> Unit) {
        with(binding) {
            Glide.with(root.context)
                .load("http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}")
                .into(imageViewFoodsCard)
            textViewNameFoodsCard.text = food.yemek_adi
            textViewPriceFoodsCard.text = root.context.getString(R.string.price_text, food.yemek_fiyat)
            buttonAddFoodsCard.setOnClickListener {
                onAddToCartClickListener.invoke(food)
            }
            cardViewHome.setOnClickListener {
                onFoodClickListener.invoke(food)
            }
        }
    }
}