package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.data.model.Fav
import com.cevdetkilickeser.neyicez.data.model.Food
import com.cevdetkilickeser.neyicez.databinding.ItemViewFavBinding

class FavsViewHolder(var binding: ItemViewFavBinding) : RecyclerView.ViewHolder(binding.root) {
    inline fun bind(fav: Fav, crossinline onAddToCartClickListener: (Food) -> Unit) {
        with(binding) {
            Glide.with(root).load("http://kasimadalan.pe.hu/yemekler/resimler/${fav.foodImageName}")
                .into(imageViewFavCard)
            textViewNameFavCard.text = fav.foodName
            textViewPriceFavCard.text = root.context.getString(R.string.price_text, fav.foodPrice)
            buttonAddToCartFav.setOnClickListener {
                val food = Food(fav.foodId, fav.foodName, fav.foodImageName, fav.foodPrice)
                onAddToCartClickListener.invoke(food)
            }
        }
    }
}