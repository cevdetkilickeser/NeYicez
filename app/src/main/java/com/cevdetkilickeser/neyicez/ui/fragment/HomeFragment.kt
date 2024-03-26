package com.cevdetkilickeser.neyicez.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cevdetkilickeser.neyicez.databinding.FragmentHomeBinding
import com.cevdetkilickeser.neyicez.ui.adapter.FoodsAdapter
import com.cevdetkilickeser.neyicez.ui.viewmodel.HomeViewModel
import com.cevdetkilickeser.neyicez.utils.UserInfo
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container,false)

        viewModel.filteredFoodsList.observe(viewLifecycleOwner) { filteredFoods ->
            val homeAdapter = FoodsAdapter(viewModel)
            homeAdapter.submitList(filteredFoods)
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

        auth = FirebaseAuth.getInstance()
        UserInfo.currentUser = auth.currentUser!!.email.toString()

        val tempViewModel: HomeViewModel by viewModels()
        viewModel = tempViewModel
    }
}