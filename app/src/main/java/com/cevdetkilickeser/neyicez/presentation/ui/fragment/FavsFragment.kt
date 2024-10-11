package com.cevdetkilickeser.neyicez.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cevdetkilickeser.neyicez.databinding.FragmentFavsBinding
import com.cevdetkilickeser.neyicez.presentation.ui.adapter.FavsAdapter
import com.cevdetkilickeser.neyicez.presentation.viewmodel.FavsViewModel
import com.cevdetkilickeser.neyicez.utils.UserInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavsFragment : Fragment() {
    private lateinit var binding: FragmentFavsBinding
    private lateinit var viewModel: FavsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavsBinding.inflate(inflater, container, false)

        viewModel.favList.observe(viewLifecycleOwner){
            val favsAdapter = FavsAdapter(requireContext(),it,viewModel)
            binding.rvFavs.adapter = favsAdapter
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: FavsViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        val kullanici_fav = "f${UserInfo.currentUser!!}"
        viewModel.loadFavFoods(kullanici_fav)
    }
}