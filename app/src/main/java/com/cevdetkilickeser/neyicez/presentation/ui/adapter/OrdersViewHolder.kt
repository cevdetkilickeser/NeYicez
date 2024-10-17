package com.cevdetkilickeser.neyicez.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.neyicez.data.model.Order
import com.cevdetkilickeser.neyicez.databinding.ItemViewOrderBinding

class OrdersViewHolder(var binding: ItemViewOrderBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(order: Order) {
        with(binding) {
            orderNumber.text = order.id
            orderDate.text = order.orderDate
            orderTotal.text = order.orderTotal
        }
    }
}