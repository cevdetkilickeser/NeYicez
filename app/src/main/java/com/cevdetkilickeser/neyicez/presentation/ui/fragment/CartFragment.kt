package com.cevdetkilickeser.neyicez.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cevdetkilickeser.neyicez.data.model.Cart
import com.cevdetkilickeser.neyicez.databinding.FragmentCartBinding
import com.cevdetkilickeser.neyicez.presentation.ui.adapter.CartAdapter
import com.cevdetkilickeser.neyicez.presentation.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        initObservers()
        initListeners()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: CartViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCart()
    }

    private fun initListeners() {
        binding.buttonApproveOrder.setOnClickListener {
            viewModel.approveOrder()
        }
    }

    private fun initObservers() {
        viewModel.cartList.observe(viewLifecycleOwner) {
            val cartAdapter = CartAdapter(it, ::onClickDeleteButton, ::calculateCartItemTotal)
            binding.rvCart.adapter = cartAdapter
        }

        viewModel.orderTotal.observe(viewLifecycleOwner) {
            binding.textViewTotalPrcCart.text = it
        }
    }

    private fun calculateCartItemTotal(foodPrice: Int, foodOrderQuantity: Int): Int {
        return viewModel.calculateCartItemTotal(foodPrice, foodOrderQuantity)
    }

    private fun onClickDeleteButton(cart: Cart) {
        viewModel.deleteFromCart(cart)
    }
}