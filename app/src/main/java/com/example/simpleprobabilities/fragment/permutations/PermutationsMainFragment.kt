package com.example.simpleprobabilities.fragment.permutations

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.simpleprobabilities.R
import com.example.simpleprobabilities.databinding.FragmentPermutationsBinding

class PermutationsMainFragment: Fragment(R.layout.fragment_permutations) {
    private var _binding: FragmentPermutationsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPermutationsBinding.bind(view)

        with(binding){
            btnPermutationsWith.setOnClickListener{
                findNavController().navigate(R.id.action_permutationsMainFragment_to_permutationsWithFragment)
            }
            btnPermutationsWithout.setOnClickListener {
                findNavController().navigate(R.id.action_permutationsMainFragment_to_permutationsWithoutFragment)
            }
        }

        setupMenu(R.id.action_permutationsMainFragment_to_mainFragment2)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setupMenu(r1: Int){
        val menuHost = requireActivity() as MenuHost

        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_main, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.mainFragment -> {
                            findNavController().navigate(r1)
                            true
                        }
                        else -> false
                    }
                }
            }, viewLifecycleOwner
        )
    }
}