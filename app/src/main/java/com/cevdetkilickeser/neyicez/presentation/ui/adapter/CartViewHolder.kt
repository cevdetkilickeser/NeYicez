package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.databinding.ItemViewCartBinding

class CartViewHolder(var binding: ItemViewCartBinding) : RecyclerView.ViewHolder(binding.root) {
    inline fun bind(
        cart: Cart,
        crossinline onClickDeleteButton: (Cart) -> Unit,
        calculateCartItemTotal: (Int, Int) -> Int
    ) {
        val totalPrc = calculateCartItemTotal(cart.foodPrice, cart.foodOrderQuantity)

        with(binding) {
            Glide.with(root)
                .load("http://kasimadalan.pe.hu/yemekler/resimler/${cart.foodImageName}")
                .into(imageViewCartCard)
            textViewNameCartCard.text = cart.foodName
            textViewPriceCartCard.text = root.context.getString(R.string.price_text, cart.foodPrice)
            textViewQtyCartCard.text = cart.foodOrderQuantity.toString()
            textViewTotalPrcCartCard.text = root.context.getString(R.string.price_text, totalPrc)

            imageViewDeleteCartCard.setOnClickListener {
                onClickDeleteButton.invoke(cart)
            }
        }
    }
}
