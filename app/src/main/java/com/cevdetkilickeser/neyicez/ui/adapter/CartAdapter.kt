package com.cevdetkilickeser.neyicez.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cevdetkilickeser.neyicez.data.entity.Cart
import com.cevdetkilickeser.neyicez.databinding.CartCardBinding
import com.cevdetkilickeser.neyicez.ui.viewmodel.CartViewModel

class CartAdapter(var mContext: Context, var cartList: List<Cart>, var viewModel: CartViewModel) :
    RecyclerView.Adapter<CartAdapter.CartCardHolder>() {
    inner class CartCardHolder(var binding: CartCardBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartCardHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = CartCardBinding.inflate(layoutInflater)
        return CartCardHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartCardHolder, position: Int) {
        val cart = cartList.get(position)
        val b = holder.binding
        val totalPrc = cart.yemek_fiyat * cart.yemek_siparis_adet

        Glide.with(mContext).load("http://kasimadalan.pe.hu/yemekler/resimler/${cart.yemek_resim_adi}").into(b.imageViewCartCard)
        b.textViewNameCartCard.text = cart.yemek_adi
        b.textViewPriceCartCard.text= "₺ ${cart.yemek_fiyat}"
        b.textViewQtyCartCard.text = cart.yemek_siparis_adet.toString()
        b.textViewTotalPrcCartCard.text = "₺ $totalPrc"

        b.imageViewDeleteCartCard.setOnClickListener {
            viewModel.deleteFromCart(cart.sepet_yemek_id)
        }
    }
}
