package com.example.simpleprobabilities.fragment.combinations.repetitions

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
import com.example.simpleprobabilities.databinding.FragmentCombinationsWithoutRepetitionsBinding
import com.example.simpleprobabilities.сalculations.CalculateFactorial

class CombinationsWithoutFragment : Fragment(R.layout.fragment_combinations_without_repetitions) {

    private var _binding: FragmentCombinationsWithoutRepetitionsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCombinationsWithoutRepetitionsBinding.bind(view)

        with(binding) {
            setTextWatchers()

            btnCalculateCombinationWithout.setOnClickListener {
                calculation()
            }

            btnBackChooseMethodCombinationsWithout.setOnClickListener {
                findNavController().navigate(R.id.action_combinationsWithoutFragment_to_combinationsMainFragment)
            }
        }

        setupMenu(R.id.action_combinationsWithoutFragment_to_mainFragment2)
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
        val n_combinations_without: EditText? =
            requireActivity().findViewById(R.id.edit_number_n_combination_without)
        val m_combinations_without: EditText? =
            requireActivity().findViewById(R.id.edit_number_m_combination_without)
        val result: TextView = requireActivity().findViewById(R.id.tw_result_combination_without)

        try {
            if (n_combinations_without == null || m_combinations_without == null) {
                result.text = resources.getString(R.string.res_calculate_combination_without)
            } else if (n_combinations_without.text.toString()
                    .toInt() >= 40 || m_combinations_without.text.toString().toInt() >= 40
            ) {
                result.text = resources.getString(R.string.number_high)
            } else if (
                m_combinations_without.text.toString()
                    .toInt() <= n_combinations_without.text.toString().toInt()
            ) {
                val num_1_combination_without: Long = CalculateFactorial().factorial(
                    n_combinations_without.text.toString().toLong()
                )
                val num_2_combination_without: Long = CalculateFactorial().factorial(
                    n_combinations_without.text.toString()
                        .toLong() - m_combinations_without.text.toString().toLong()
                )
                val num_3_combination_without: Long = CalculateFactorial().factorial(
                    m_combinations_without.text.toString().toLong()
                )
                val number_recult_combination =
                    num_1_combination_without / (num_2_combination_without * num_3_combination_without)
                result.text =
                    resources.getString(R.string.res_calculate_combination_without) + "$number_recult_combination"
            } else {
                result.text = resources.getString(R.string.incorrectly)
            }

        } catch (e: Exception) {
            result.text = resources.getString(R.string.res_calculate_combination_without)
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
            editNumberNCombinationWithout.addTextChangedListener(textWatcher)
            editNumberMCombinationWithout.addTextChangedListener(textWatcher)
        }
    }

    private fun checkSetTextButton() {
        with(binding) {
            btnCalculateCombinationWithout.isEnabled =
                !editNumberNCombinationWithout.text.isNullOrBlank() &&
                        !editNumberMCombinationWithout.text.isNullOrBlank()
        }
    }
}