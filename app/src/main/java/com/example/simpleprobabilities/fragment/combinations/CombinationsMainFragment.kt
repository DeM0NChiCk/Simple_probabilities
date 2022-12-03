package com.example.simpleprobabilities.fragment.combinations

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
import com.example.simpleprobabilities.databinding.FragmentCombinationsBinding

class CombinationsMainFragment : Fragment(R.layout.fragment_combinations) {
    private var _binding: FragmentCombinationsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCombinationsBinding.bind(view)

        with(binding) {
            btnCombinationsWith.setOnClickListener {
                findNavController().navigate(R.id.action_combinationsMainFragment_to_combinationsWithFragment)
            }
            btnCombinationsWithout.setOnClickListener {
                findNavController().navigate(R.id.action_combinationsMainFragment_to_combinationsWithoutFragment)
            }
        }

        setupMenu(R.id.action_combinationsMainFragment_to_mainFragment2) // функция для перехода в галвное меню
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    /* можно создать только в данном классе
     *иначе возникает ошибка java.lang.IllegalStateException: Fragment - пока не испарвил
     */
    private fun setupMenu(r1: Int) {
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