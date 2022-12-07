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
import java.math.BigInteger
import kotlin.math.pow

class PlacementsWithFragment : Fragment(R.layout.fragment_placements_with_repetitions) {

    private var _binding: FragmentPlacementsWithRepetitionsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPlacementsWithRepetitionsBinding.bind(view)

        with(binding) {
            setTextWatchers()

            btnCalculatePlacements.setOnClickListener {
                calculation()
            }

            btnBackChooseMethodPlacements.setOnClickListener {
                findNavController().navigate(R.id.action_placementsWithFragment_to_placementsMainFragment)
            }
        }

        setupMenu(R.id.action_placementsWithFragment_to_mainFragment2)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

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

    private fun calculation() {

        with(binding){
            val nPlacementWith = editNumberNPlacements.text.toString()
            val mPlacementWith = editNumberMPlacements.text.toString()

            try {
                val numResPlacementWith: BigInteger = BigInteger.valueOf(
                    nPlacementWith.toDouble().pow(mPlacementWith.toDouble()).toLong()
                )
                tvResultPlacements.text = getString(R.string.res_calculate_placements_with, numResPlacementWith)
            } catch (e:Exception){
                tvResultPlacements.text = getString(R.string.incorrectly)
            }
        }
    }

    private fun setTextWatchers() {
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                checkSetTextButton()
            }
        }
        with(binding) {
            editNumberNPlacements.addTextChangedListener(textWatcher)
            editNumberMPlacements.addTextChangedListener(textWatcher)
        }
    }

    private fun checkSetTextButton() {
        with(binding) {
            btnCalculatePlacements.isEnabled = !editNumberNPlacements.text.isNullOrBlank() &&
                    !editNumberMPlacements.text.isNullOrBlank()
        }
    }
}