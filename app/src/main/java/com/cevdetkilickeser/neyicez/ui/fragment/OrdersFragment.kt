package com.cevdetkilickeser.neyicez.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.databinding.FragmentOrdersBinding
import com.cevdetkilickeser.neyicez.ui.adapter.OrdersAdapter
import com.cevdetkilickeser.neyicez.ui.viewmodel.OrdersViewModel

class OrdersFragment : Fragment() {
    private lateinit var binding: FragmentOrdersBinding
    private lateinit var viewModel: OrdersViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)

        //viewModel.orderList.observe(viewLifecycleOwner){
        //    val orderAdapter = OrdersAdapter(requireContext(),it,viewModel)

        //}

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val tempViewModel: OrdersViewModel by viewModels()
        //viewModel = tempViewModel
    }

}