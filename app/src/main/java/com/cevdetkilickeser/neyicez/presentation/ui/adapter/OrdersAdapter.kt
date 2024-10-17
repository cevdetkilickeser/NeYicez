package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.neyicez.data.model.Order
import com.cevdetkilickeser.neyicez.databinding.ItemViewOrderBinding

class OrdersAdapter(private var orderList: List<Order>) :
    RecyclerView.Adapter<OrdersViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemViewOrderBinding.inflate(layoutInflater)
        return OrdersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.bind(orderList[position])
    }
}