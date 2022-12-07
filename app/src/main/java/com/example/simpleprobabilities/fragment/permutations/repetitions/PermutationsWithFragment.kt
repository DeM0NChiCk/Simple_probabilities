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
import com.example.simpleprobabilities.сalculations.CalculateFactorial
import java.math.BigInteger

class PermutationsWithFragment : Fragment(R.layout.fragment_permutations_with_repetitions) {

    private var _binding: FragmentPermutationsWithRepetitionsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPermutationsWithRepetitionsBinding.bind(view)

        with(binding) {
            setTextWatchers()

            btnCalculatePermutations.setOnClickListener {
                calculate()
            }

            btnBackChooseMethodPermutations.setOnClickListener {
                findNavController().navigate(R.id.action_permutationsWithFragment_to_permutationsMainFragment)
            }
        }

        setupMenu(R.id.action_permutationsWithFragment_to_mainFragment2)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun calculate() {

        with(binding){
            val nPermutationWith = editNumberNPermutations.text.toString()
            val nkPermutationWith = editNumberAllNPermutations.text.toString()
            val allValueN: Array<String> = nkPermutationWith.split(" ").toTypedArray()

            try {
                val num1PermutationWith: BigInteger = CalculateFactorial.factorial(
                    BigInteger.valueOf(nPermutationWith.toLong())
                )
                var num2PermutationWith = BigInteger.ONE
                var count = 0L
                for (i in allValueN.indices) {
                    num2PermutationWith = num2PermutationWith.multiply(CalculateFactorial.factorial(
                        BigInteger.valueOf(allValueN[i].toLong())
                    ))
                    count += allValueN[i].toLong()
                }
                if (count != nPermutationWith.toLong()) {
                    tvResultPermutations.text = getString(R.string.incorrectly)
                } else {
                    val numResPermutationWith: BigInteger = num1PermutationWith.divide(num2PermutationWith)
                    tvResultPermutations.text =
                        getString(R.string.res_calculate_permutations_with, numResPermutationWith)
                }

            } catch (e:Exception){
                tvResultPermutations.text = getString(R.string.incorrectly)
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
            editNumberNPermutations.addTextChangedListener(textWatcher)
            editNumberAllNPermutations.addTextChangedListener(textWatcher)
        }
    }

    private fun checkSetTextButton() {
        with(binding) {
            btnCalculatePermutations.isEnabled = !editNumberNPermutations.text.isNullOrBlank() &&
                    !editNumberAllNPermutations.text.isNullOrBlank()
        }
    }
}