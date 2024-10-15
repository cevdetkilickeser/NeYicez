package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.databinding.FavCardBinding
import com.cevdetkilickeser.neyicez.presentation.viewmodel.FavsViewModel

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
        val food = favList[position]
        val b = holder.binding

        Glide.with(mContext).load("http://kasimadalan.pe.hu/yemekler/resimler/${food.foodImageName}").into(b.imageViewFavCard)
        b.textViewNameFavCard.text = food.foodName
        b.textViewPriceFavCard.text = b.root.context.getString(R.string.price_text, food.foodPrice)
        b.buttonAddToCartFavCard.setOnClickListener {
        }
    }
}