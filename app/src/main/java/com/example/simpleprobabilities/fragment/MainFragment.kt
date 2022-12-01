package com.example.simpleprobabilities.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.simpleprobabilities.R
import com.example.simpleprobabilities.databinding.FragmentMainBinding


class MainFragment: Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        with(binding){
            btnCombinations.setOnClickListener{
                findNavController().navigate(R.id.action_mainFragment2_to_combinationsMainFragment)
            }
            btnPermutations.setOnClickListener{
                findNavController().navigate(R.id.action_mainFragment2_to_permutationsMainFragment)
            }
            btnPlacements.setOnClickListener{
                findNavController().navigate(R.id.action_mainFragment2_to_placementsMainFragment)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}