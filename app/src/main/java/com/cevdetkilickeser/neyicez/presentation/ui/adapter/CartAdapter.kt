package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.databinding.CartCardBinding
import com.cevdetkilickeser.neyicez.presentation.viewmodel.CartViewModel

class CartAdapter(private var cartList: List<Cart>, var viewModel: CartViewModel) :
    RecyclerView.Adapter<CartAdapter.CartCardHolder>() {
    inner class CartCardHolder(var binding: CartCardBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartCardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CartCardBinding.inflate(layoutInflater)
        return CartCardHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartCardHolder, position: Int) {
        val cart = cartList[position]
        val b = holder.binding
        val totalPrc = cart.foodPrice * cart.foodOrderQuantity

        Glide.with(b.root).load("http://kasimadalan.pe.hu/yemekler/resimler/${cart.foodImageName}")
            .into(b.imageViewCartCard)
        b.textViewNameCartCard.text = cart.foodName
        b.textViewPriceCartCard.text = b.root.context.getString(R.string.price_text, cart.foodPrice)
        b.textViewQtyCartCard.text = cart.foodOrderQuantity.toString()
        b.textViewTotalPrcCartCard.text =  b.root.context.getString(R.string.price_text, totalPrc)

        b.imageViewDeleteCartCard.setOnClickListener {
            viewModel.deleteFromCart(cart.cartFoodId)
        }
    }
}
