package com.cevdetkilickeser.neyicez.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.data.model.Food
import com.cevdetkilickeser.neyicez.databinding.FragmentDetailBinding
import com.cevdetkilickeser.neyicez.presentation.viewmodel.DetailViewModel
import com.cevdetkilickeser.neyicez.utils.onClickBackButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var food: Food

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        loadDetailPage()

        initObservers()

        initListeners()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: DetailViewModel by viewModels()
        viewModel = tempViewModel

        val bundle: DetailFragmentArgs by navArgs()
        food = bundle.food
    }

    private fun initObservers() {
        viewModel.qty.observe(viewLifecycleOwner) { qty ->
            with(binding) {
                textViewQtyDetail.text = viewModel.qty.value.toString()
                textViewTotalPrcDetail.text = getString(R.string.price_text, food.foodPrice * qty)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.checkBoxFavDetail.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.checkBoxFavDetail.visibility = View.VISIBLE
            }
        }

        viewModel.isFav.observe(viewLifecycleOwner) { isFav ->
            binding.checkBoxFavDetail.isChecked = isFav
        }
    }

    private fun initListeners() {

        binding.checkBoxFavDetail.setOnClickListener {
            if (binding.checkBoxFavDetail.isChecked) {
                viewModel.addToFavs(food)
            } else {
                viewModel.deleteFromFavs(food)
            }
        }

        binding.imageButtonBack.setOnClickListener {
            onClickBackButton(this)
        }

        binding.buttonDecrease.setOnClickListener {
            viewModel.decreaseQty()
        }

        binding.buttonIncrease.setOnClickListener {
            viewModel.increaseQty()
        }

        binding.buttonAddToCart.setOnClickListener {
            val qty = binding.textViewQtyDetail.text.toString().toInt()
            viewModel.addToCart(food, qty)
        }
    }

    private fun loadDetailPage() {

        viewModel.favCheck(food)

        with(binding) {
            Glide.with(requireContext())
                .load("http://kasimadalan.pe.hu/yemekler/resimler/${food.foodImageName}")
                .into(imageViewDetail)
            textViewNameDetail.text = food.foodName
            textViewPriceDetail.text = getString(R.string.price_text, food.foodPrice)
        }
    }
}