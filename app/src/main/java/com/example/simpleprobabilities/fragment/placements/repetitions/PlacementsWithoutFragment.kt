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
import com.example.simpleprobabilities.databinding.FragmentPlacementsWithoutRepetitionsBinding
import com.example.simpleprobabilities.сalculations.CalculateFactorial
import java.math.BigInteger

class PlacementsWithoutFragment : Fragment(R.layout.fragment_placements_without_repetitions) {
    private var _binding: FragmentPlacementsWithoutRepetitionsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPlacementsWithoutRepetitionsBinding.bind(view)

        with(binding) {
            setTextWatchers()

            btnCalculatePlacementsWithout.setOnClickListener {
                calculation()
            }

            btnBackChooseMethodPlacements.setOnClickListener {
                findNavController().navigate(R.id.action_placementsWithoutFragment_to_placementsMainFragment)
            }
        }

        setupMenu(R.id.action_placementsWithoutFragment_to_mainFragment2)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun calculation() {

        with(binding){
            val nPlacementWithout = editNumberNPlacementsWithout.text.toString()
            val mPlacementWithout = editNumberMPlacementsWithout.text.toString()

            try {
                val num1PlacementWithout: BigInteger = CalculateFactorial.factorial(
                    BigInteger.valueOf(nPlacementWithout.toLong())
                )
                val num2PlacementWithout: BigInteger = CalculateFactorial.factorial(
                    BigInteger.valueOf(nPlacementWithout.toLong() - mPlacementWithout.toLong())
                )
                val numResPlacementWithout: BigInteger = num1PlacementWithout.divide(num2PlacementWithout)
                tvResultPlacementsWithout.text = getString(R.string.res_calculate_placements_without, numResPlacementWithout)
            } catch (e:Exception){
                tvResultPlacementsWithout.text = getString(R.string.incorrectly)
            }
        }
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

    private fun setTextWatchers() {
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                checkSetTextButton()
            }
        }
        with(binding) {
            editNumberNPlacementsWithout.addTextChangedListener(textWatcher)
            editNumberMPlacementsWithout.addTextChangedListener(textWatcher)
        }
    }

    private fun checkSetTextButton() {
        with(binding) {
            btnCalculatePlacementsWithout.isEnabled =
                !editNumberNPlacementsWithout.text.isNullOrBlank() &&
                        !editNumberMPlacementsWithout.text.isNullOrBlank()
        }
    }
}