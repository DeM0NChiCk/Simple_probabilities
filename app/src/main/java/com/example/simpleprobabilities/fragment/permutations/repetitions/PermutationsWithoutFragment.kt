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
import com.example.simpleprobabilities.databinding.FragmentPermutationsWithoutRepetitionsBinding
import com.example.simpleprobabilities.сalculations.CalculateFactorial

class PermutationsWithoutFragment : Fragment(R.layout.fragment_permutations_without_repetitions) {

    private var _binding: FragmentPermutationsWithoutRepetitionsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPermutationsWithoutRepetitionsBinding.bind(view)

        with(binding) {
            setTextWatchers()

            btnCalculatePermutationsWithout.setOnClickListener {
                calculation()
            }

            btnBackChooseMethodPermutations.setOnClickListener {
                findNavController().navigate(R.id.action_permutationsWithoutFragment_to_permutationsMainFragment)
            }
        }

        setupMenu(R.id.action_permutationsWithoutFragment_to_mainFragment2)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun calculation() {
        val n_permutations_without: EditText? =
            requireActivity().findViewById(R.id.edit_number_n_permutations_without)
        val result: TextView = requireActivity().findViewById(R.id.tw_result_permutations_without)

        try {
            if (n_permutations_without == null) {
                result.text = resources.getString(R.string.incorrectly)
            } else if (n_permutations_without.text.toString().toInt() >= 40) {
                result.text = resources.getString(R.string.number_high)
            } else {
                val res_permutations_without: Long = CalculateFactorial().factorial(
                    n_permutations_without.text.toString().toLong()
                )
                result.text =
                    resources.getString(R.string.res_calculate_permutations_without) + "$res_permutations_without"
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
            editNumberNPermutationsWithout.addTextChangedListener(textWatcher)
        }
    }

    private fun checkSetTextButton() {
        with(binding) {
            btnCalculatePermutationsWithout.isEnabled =
                !editNumberNPermutationsWithout.text.isNullOrBlank()
        }
    }
}