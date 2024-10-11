package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.neyicez.data.model.Orders
import com.cevdetkilickeser.neyicez.databinding.CartCardBinding
import com.cevdetkilickeser.neyicez.presentation.viewmodel.OrdersViewModel

class OrdersAdapter (var mContext: Context, var orderList: List<Orders>, var viewModel: OrdersViewModel) :
RecyclerView.Adapter<OrdersAdapter.OrdersCardHolder>() {
    inner class OrdersCardHolder(var binding: CartCardBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersCardHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = CartCardBinding.inflate(layoutInflater)
        return OrdersCardHolder(binding)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrdersCardHolder, position: Int) {
        val order = orderList.get(position)
        val b = holder.binding
    }
}