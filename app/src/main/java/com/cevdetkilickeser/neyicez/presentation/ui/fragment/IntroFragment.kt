package com.cevdetkilickeser.neyicez.presentation.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.cevdetkilickeser.neyicez.R
import com.cevdetkilickeser.neyicez.databinding.FragmentIntroBinding
import com.cevdetkilickeser.neyicez.presentation.ui.adapter.IntroAdapter

class IntroFragment : Fragment() {

    private lateinit var binding: FragmentIntroBinding
    private lateinit var appPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroBinding.inflate(inflater, container, false)
        appPref = requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val introList = listOf(1, 2, 3)

        val introAdapter = IntroAdapter(requireContext(), introList)
        binding.viewPager.adapter = introAdapter
        binding.dotIndicator.attachTo(binding.viewPager)

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == (introList.size - 1)) {
                    binding.buttonSkip.visibility = View.INVISIBLE
                    binding.buttonStart.visibility = View.VISIBLE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        binding.buttonStart.setOnClickListener {
            onClickButton(it)
        }

        binding.buttonSkip.setOnClickListener {
            onClickButton(it)
        }

        return binding.root
    }

    private fun onClickButton(view: View) {
        Navigation.findNavController(view).navigate(R.id.introToLogIn)
        appPref.edit().putBoolean("introStatus", true).apply()
    }
}