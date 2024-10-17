package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.databinding.ItemViewCartBinding

class CartAdapter(
    private var cartList: List<Cart>,
    private val onClickDeleteButton: (Cart) -> Unit,
    private val calculateCartItemTotal: (Int, Int) -> Int
) : RecyclerView.Adapter<CartViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemViewCartBinding.inflate(layoutInflater)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartList[position], onClickDeleteButton, calculateCartItemTotal)
    }
}
