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
import com.example.simpleprobabilities.databinding.FragmentCombinationsWithoutRepetitionsBinding
import com.example.simpleprobabilities.сalculations.CalculateFactorial
import java.math.BigInteger

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

        with(binding) {
            val nCombinationWithout = editNumberNCombinationWithout.text.toString()
            val mCombinationWithout = editNumberMCombinationWithout.text.toString()

            try {
                if (mCombinationWithout.toLong() <= nCombinationWithout.toLong()) {
                    val num1CombinationWithout: BigInteger =
                        CalculateFactorial.factorial(
                            BigInteger.valueOf(nCombinationWithout.toLong())
                        )
                    val num2CombinationWithout: BigInteger =
                        CalculateFactorial.factorial(
                            BigInteger.valueOf(
                                nCombinationWithout.toLong() - mCombinationWithout.toLong()
                            )
                        )
                    val num3CombinationWithout: BigInteger =
                        CalculateFactorial.factorial(
                            BigInteger.valueOf(
                                mCombinationWithout.toLong()
                            )
                        )
                    val numResCombinationWithout: BigInteger =
                        num1CombinationWithout.divide(num2CombinationWithout.multiply(num3CombinationWithout))
                    tvResultCombinationWithout.text =
                        getString(R.string.res_calculate_combination_without, numResCombinationWithout)
                } else {
                    tvResultCombinationWithout.text = getString(R.string.incorrectly)
                }
            } catch (e:Exception){
                tvResultCombinationWithout.text = getString(R.string.incorrectly)
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