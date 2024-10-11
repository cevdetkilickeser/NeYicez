package com.cevdetkilickeser.neyicez.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.data.model.Foods
import com.cevdetkilickeser.neyicez.databinding.FragmentDetailBinding
import com.cevdetkilickeser.neyicez.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var food: Foods
    private lateinit var rate: String
    private var qty = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        loadDetailPage()

        binding.imageButtonBack.setOnClickListener {
            backToHome(it)
        }

        binding.checkBoxFavDetail.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked){
                viewModel.addToFav(food.yemek_adi,food.yemek_resim_adi,food.yemek_fiyat,1)
                binding.checkBoxFavDetail.isChecked = true
            }else{
                viewModel.deleteFromFav(food.yemek_adi)
                binding.checkBoxFavDetail.isChecked = false
            }
        }

        binding.buttonDecrease.setOnClickListener {
            decreaseQty()
        }

        binding.buttonIncrease.setOnClickListener {
            increaseQty()
        }

        binding.buttonAddToCart.setOnClickListener {
            addToCart()
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: DetailViewModel by viewModels()
        viewModel = tempViewModel
    }

    private fun loadDetailPage(){
        val bundle:DetailFragmentArgs by navArgs()
        rate = bundle.rate
        food = bundle.food
        binding.checkBoxFavDetail.isChecked = viewModel.checkFav(food.yemek_adi)

        Glide.with(requireContext()).load("http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}").into(binding.imageViewDetail)
        binding.ratingBarDetail.rating = rate.toFloat()
        binding.textViewNameDetail.text = food.yemek_adi
        binding.textViewPriceDetail.text = "₺ ${food.yemek_fiyat}"
        binding.textViewQtyDetail.text = qty.toString()
        binding.textViewTotalPrcDetail.text = "₺ ${food.yemek_fiyat}"
    }

    private fun increaseQty(){
        if (qty < 10) qty++
        binding.textViewQtyDetail.text = qty.toString()
        binding.textViewTotalPrcDetail.text = "₺ ${qty*food.yemek_fiyat}"
    }

    private fun decreaseQty(){
        if (qty > 1) qty--
        binding.textViewQtyDetail.text = qty.toString()
        binding.textViewTotalPrcDetail.text = "₺ ${qty*food.yemek_fiyat}"
    }

    private fun addToCart(){
        viewModel.addToCart(food.yemek_adi,food.yemek_resim_adi,food.yemek_fiyat,qty)
    }

    private fun backToHome(view:View){
        Navigation.findNavController(view).navigate(R.id.detailToHome)
    }
}