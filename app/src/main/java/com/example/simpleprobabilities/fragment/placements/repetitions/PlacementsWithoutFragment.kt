package com.example.simpleprobabilities.fragment.placements.repetitions

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.simpleprobabilities.R
import com.example.simpleprobabilities.databinding.FragmentPlacementsWithoutRepetitionsBinding
import com.example.simpleprobabilities.сalculations.CalculateFactorial

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
        val n_placements_without: EditText? =
            requireActivity().findViewById(R.id.edit_number_n_placements_without)
        val m_placements_without: EditText? =
            requireActivity().findViewById(R.id.edit_number_m_placements_without)
        val result: TextView = requireActivity().findViewById(R.id.tw_result_placements_without)

        try {
            if (n_placements_without == null || m_placements_without == null) {
                result.text = resources.getString(R.string.incorrectly)
            } else if (n_placements_without.text.toString()
                    .toInt() >= 11 || m_placements_without.text.toString().toInt() >= 11
            ) {
                result.text = resources.getString(R.string.number_high)
            } else {
                val num_1_placements_without: Long = CalculateFactorial().factorial(
                    n_placements_without.text.toString().toLong()
                )
                val num_2_placements_without: Long = CalculateFactorial().factorial(
                    n_placements_without.text.toString()
                        .toLong() - m_placements_without.text.toString().toLong()
                )
                val res_placements_without = num_1_placements_without / num_2_placements_without
                result.text =
                    resources.getString(R.string.res_calculate_placements_without) + "$res_placements_without"
            }

        } catch (e: Exception) {
            result.text = resources.getString(R.string.incorrectly)
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