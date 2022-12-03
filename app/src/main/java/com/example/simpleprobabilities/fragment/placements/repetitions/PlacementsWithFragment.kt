package com.example.simpleprobabilities.fragment.placements.repetitions

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
import com.example.simpleprobabilities.databinding.FragmentPlacementsWithRepetitionsBinding

class PlacementsWithFragment: Fragment(R.layout.fragment_placements_with_repetitions) {

    private var _binding: FragmentPlacementsWithRepetitionsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPlacementsWithRepetitionsBinding.bind(view)

        with(binding){
            setTextWatchers()
        }

        setupMenu(R.id.action_placementsWithFragment_to_mainFragment2)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    // TODO: сделать возврат на выбор способа потворения
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
            editNumberNPlacements.addTextChangedListener(textWatcher)
            editNumberMPlacements.addTextChangedListener(textWatcher)
        }
    }
    // TODO: сделать проверку для n и m
    private fun checkSetTextButton(){
        with(binding) {
            btnCalculatePlacements.isEnabled = !editNumberNPlacements.text.isNullOrBlank() &&
                    !editNumberMPlacements.text.isNullOrBlank()
        }
    }
}