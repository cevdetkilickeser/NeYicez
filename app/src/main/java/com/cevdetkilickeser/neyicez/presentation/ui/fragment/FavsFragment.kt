package com.cevdetkilickeser.neyicez.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cevdetkilickeser.neyicez.databinding.FragmentFavsBinding
import com.cevdetkilickeser.neyicez.presentation.ui.adapter.FavsAdapter
import com.cevdetkilickeser.neyicez.presentation.viewmodel.FavsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavsFragment : Fragment() {
    private lateinit var binding: FragmentFavsBinding
    private lateinit var viewModel: FavsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavsBinding.inflate(inflater, container, false)

        initObservers()
        initListeners()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: FavsViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavs()
    }

    private fun initObservers() {

        viewModel.favList.observe(viewLifecycleOwner) {
            val favsAdapter = FavsAdapter(it)
            binding.rvFavs.adapter = favsAdapter
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun initListeners() {
        binding.imageButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}