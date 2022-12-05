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
import com.example.simpleprobabilities.databinding.FragmentCombinationsWithRepetitionsBinding
import com.example.simpleprobabilities.сalculations.CalculateFactorial
import java.math.BigInteger

class CombinationsWithFragment : Fragment(R.layout.fragment_combinations_with_repetitions) {

    private var _binding: FragmentCombinationsWithRepetitionsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCombinationsWithRepetitionsBinding.bind(view)

        with(binding) {
            setTextWatchers()
            btnCalculateCombination.setOnClickListener {
                calculation()
            }
            btnBackChooseMethodCombinations.setOnClickListener {
                findNavController().navigate(R.id.action_combinationsWithFragment_to_combinationsMainFragment)
            }
        }

        setupMenu(R.id.action_combinationsWithFragment_to_mainFragment2)
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
        val n_combination: EditText? =
            requireActivity().findViewById(R.id.edit_number_n_combination)
        val m_combination: EditText? =
            requireActivity().findViewById(R.id.edit_number_m_combination)
        val result: TextView = requireActivity().findViewById(R.id.tw_result_combination)
        result.text = resources.getString(R.string.res_calculate_combination_with)
        try {
            if (n_combination == null || m_combination == null) {
                result.text = resources.getString(R.string.incorrectly)
            } else {
                val number_1: BigInteger = CalculateFactorial().factorial(
                    BigInteger.valueOf(n_combination.text.toString().toLong() + m_combination.text.toString()
                        .toLong() - 1 )
                )
                val number_2: BigInteger =
                    CalculateFactorial().factorial(BigInteger.valueOf(n_combination.text.toString().toLong() - 1))
                val number_3: BigInteger =
                    CalculateFactorial().factorial(BigInteger.valueOf(m_combination.text.toString().toLong()))
                val number_res: BigInteger = number_1.divide(number_2.multiply(number_3))
                result.text =
                    resources.getString(R.string.res_calculate_combination_with) + "$number_res"
            }

        } catch (e: Exception) {
            result.text = resources.getString(R.string.incorrectly)
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
            editNumberNCombination.addTextChangedListener(textWatcher)
            editNumberMCombination.addTextChangedListener(textWatcher)
        }
    }

    private fun checkSetTextButton() {
        with(binding) {
            btnCalculateCombination.isEnabled = !editNumberNCombination.text.isNullOrBlank() &&
                    !editNumberMCombination.text.isNullOrBlank()
        }
    }
}