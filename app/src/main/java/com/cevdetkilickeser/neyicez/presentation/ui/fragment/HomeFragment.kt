package com.cevdetkilickeser.neyicez.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cevdetkilickeser.neyicez.data.model.Foods
import com.cevdetkilickeser.neyicez.databinding.FragmentHomeBinding
import com.cevdetkilickeser.neyicez.presentation.ui.adapter.FoodsAdapter
import com.cevdetkilickeser.neyicez.presentation.viewmodel.HomeViewModel
import com.cevdetkilickeser.neyicez.utils.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.filteredFoodsList.observe(viewLifecycleOwner) { filteredFoods ->
            val homeAdapter = FoodsAdapter(filteredFoods, ::onFoodClickListener, ::onAddToCartClickListener)
            binding.rvHome.adapter = homeAdapter
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchFoods(newText)
                return true
            }
        })

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: HomeViewModel by viewModels()
        viewModel = tempViewModel
    }

    private fun onFoodClickListener(food: Foods) {
        val action = HomeFragmentDirections.homeToDetail(food)
        navigate(binding.root, action)
    }

    private fun onAddToCartClickListener(food: Foods) {
        viewModel.addToCart(food.yemek_adi, food.yemek_resim_adi, food.yemek_fiyat)
    }
}