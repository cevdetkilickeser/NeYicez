package com.cevdetkilickeser.neyicez.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
            val homeAdapter = FoodsAdapter(viewModel, filteredFoods) {
                val action = HomeFragmentDirections.homeToDetail(it)
                navigate(binding.root, action)
            }
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
}