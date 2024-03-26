package com.cevdetkilickeser.neyicez.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cevdetkilickeser.neyicez.data.entity.Foods
import com.cevdetkilickeser.neyicez.databinding.FoodsCardBinding
import com.cevdetkilickeser.neyicez.ui.fragment.HomeFragmentDirections
import com.cevdetkilickeser.neyicez.ui.viewmodel.HomeViewModel

class FoodsAdapter(private val viewModel: HomeViewModel) : ListAdapter<Foods, FoodsAdapter.FoodsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FoodsCardBinding.inflate(layoutInflater, parent, false)
        return FoodsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodsViewHolder, position: Int) {
        val food = getItem(position)
        holder.bind(food)
    }

    inner class FoodsViewHolder(private val binding: FoodsCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Foods) {
            with(binding) {
                Glide.with(root.context)
                    .load("http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}")
                    .into(imageViewFoodsCard)
                textViewNameFoodsCard.text = food.yemek_adi
                textViewPriceFoodsCard.text = "₺ ${food.yemek_fiyat}"
                textViewRateFoodsCard.text = "4.5"
                buttonAddFoodsCard.setOnClickListener {
                    viewModel.addToCart(food.yemek_adi, food.yemek_resim_adi, food.yemek_fiyat)
                }
                cardViewHome.setOnClickListener {
                    val rate = textViewRateFoodsCard.text.toString()
                    val action = HomeFragmentDirections.homeToDetail(food = food, rate = rate)
                    Navigation.findNavController(it).navigate(action)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Foods>() {
        override fun areItemsTheSame(oldItem: Foods, newItem: Foods): Boolean {
            return oldItem.yemek_id == newItem.yemek_id
        }

        override fun areContentsTheSame(oldItem: Foods, newItem: Foods): Boolean {
            return oldItem == newItem
        }
    }
}