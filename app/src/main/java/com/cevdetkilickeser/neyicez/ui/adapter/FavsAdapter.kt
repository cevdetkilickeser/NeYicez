package com.cevdetkilickeser.neyicez.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cevdetkilickeser.neyicez.data.entity.Cart
import com.cevdetkilickeser.neyicez.databinding.FavCardBinding
import com.cevdetkilickeser.neyicez.ui.viewmodel.FavsViewModel

class FavsAdapter(var mContext: Context, var favList: List<Cart>, var viewModel: FavsViewModel) : RecyclerView.Adapter<FavsAdapter.FavCardHolder>() {

    inner class FavCardHolder(var binding: FavCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavCardHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = FavCardBinding.inflate(layoutInflater, parent,false)
        return FavCardHolder(binding)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: FavCardHolder, position: Int) {
        val food = favList.get(position)
        val b = holder.binding

        Glide.with(mContext).load("http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}").into(b.imageViewFavCard)
        b.textViewNameFavCard.text = food.yemek_adi
        b.textViewPriceFavCard.text = "₺ ${food.yemek_fiyat}"
        b.buttonAddToCartFavCard.setOnClickListener {
            viewModel.addToCart(food.yemek_adi,food.yemek_resim_adi,food.yemek_fiyat,1)
        }
    }
}