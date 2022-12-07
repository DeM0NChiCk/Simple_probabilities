package com.example.simpleprobabilities.fragment.combinations.repetitions

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
        with(binding) {
            val nCombination = editNumberNCombination.text.toString()
            val mCombination = editNumberMCombination.text.toString()

            try {
                val num1CombinationsWith: BigInteger = CalculateFactorial.factorial(
                    BigInteger.valueOf(
                        nCombination.toLong() + mCombination.toLong() - 1
                    )
                )
                val num2CombinationsWith: BigInteger = CalculateFactorial.factorial(
                    BigInteger.valueOf(
                        nCombination.toLong() - 1
                    )
                )
                val num3CombinationsWith: BigInteger = CalculateFactorial.factorial(
                    BigInteger.valueOf(
                        mCombination.toLong()
                    )
                )
                val numResCombinationsWith: BigInteger = num1CombinationsWith.divide(
                    num2CombinationsWith.multiply(num3CombinationsWith)
                )
                tvResultCombination.text =
                    getString(R.string.res_calculate_combination_with, numResCombinationsWith)
            } catch (e: Exception) {
                tvResultCombination.text = getString(R.string.incorrectly)
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