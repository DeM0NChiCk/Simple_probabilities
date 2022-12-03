package com.example.simpleprobabilities.fragment.permutations.repetitions

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.simpleprobabilities.R
import com.example.simpleprobabilities.databinding.FragmentPermutationsWithRepetitionsBinding

class PermutationsWithFragment: Fragment (R.layout.fragment_permutations_with_repetitions) {

    private var _binding: FragmentPermutationsWithRepetitionsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPermutationsWithRepetitionsBinding.bind(view)

        with(binding){
            setTextWatchers()
        }

        setupMenu(R.id.action_permutationsWithFragment_to_mainFragment2)
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

    private fun setTextWatchers() {
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                checkSetTextButton()
            }
        }
        with(binding){
            editNumberNPermutations.addTextChangedListener(textWatcher)
            editNumberAllNPermutations.addTextChangedListener(textWatcher)
        }
    }

    private fun checkSetTextButton(){
        with(binding) {
            btnCalculatePermutations.isEnabled = !editNumberNPermutations.text.isNullOrBlank() &&
                    !editNumberAllNPermutations.text.isNullOrBlank()
        }
    }
}