package com.example.simpleprobabilities.fragment.permutations.repetitions

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
import com.example.simpleprobabilities.databinding.FragmentPermutationsWithRepetitionsBinding
import com.example.simpleprobabilities.сalculations.CalculateFactorial

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
        val n_permutation: EditText? =
            requireActivity().findViewById(R.id.edit_number_n_permutations)
        val nk_permutation: EditText? =
            requireActivity().findViewById(R.id.edit_number_all_n_permutations)
        val a: Array<String> = nk_permutation?.text.toString().split(" ").toTypedArray()
        val result: TextView = requireActivity().findViewById(R.id.tw_result_permutations)

        try {
            if (n_permutation == null || nk_permutation == null) {
                result.text = resources.getString(R.string.incorrectly)
            } else if (n_permutation.text.toString().toInt() >= 40) {
                result.text = resources.getString(R.string.number_high)
            } else {
                val r_number_1: Long =
                    CalculateFactorial().factorial(n_permutation.text.toString().toLong())
                var r_number_2: Long = 1
                var count = 0
                for (i in a.indices) {
                    if (a[i].toInt() >= 40) {
                        result.text = resources.getString(R.string.incorrectly)
                        break
                    }
                    r_number_2 *= CalculateFactorial().factorial(a[i].toLong())
                    count += a[i].toInt()


                }
                if (count != n_permutation.text.toString().toInt()) {
                    result.text = resources.getString(R.string.incorrectly)
                } else {
                    val res_permutation: Long = r_number_1 / r_number_2
                    if (res_permutation <= 2000000000) {
                        result.text =
                            resources.getString(R.string.res_calculate_permutations_with) + "$res_permutation"
                    } else {
                        result.text = resources.getString(R.string.number_high)
                    }
                }
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