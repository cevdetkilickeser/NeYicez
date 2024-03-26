package com.cevdetkilickeser.neyicez.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cevdetkilickeser.neyicez.data.entity.Cart
import com.cevdetkilickeser.neyicez.databinding.FragmentCartBinding
import com.cevdetkilickeser.neyicez.ui.adapter.CartAdapter
import com.cevdetkilickeser.neyicez.ui.viewmodel.CartViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: CartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        viewModel.cartList.observe(viewLifecycleOwner){
            val cartAdapter = CartAdapter(requireContext(),it,viewModel)
            binding.rvCart.adapter = cartAdapter
        }

        viewModel.totalPrice.observe(viewLifecycleOwner){
            binding.textViewTotalPrcCart.text = it
        }

        binding.buttonApproveOrder.setOnClickListener {
            approveOrder()
            Snackbar.make(it,"Siparişiniz alındı. Siparişler menüsünden takip edebilirsiniz.",Snackbar.LENGTH_SHORT).show()
        }

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

    private fun approveOrder(){
        viewModel.approveOrder()
    }
}