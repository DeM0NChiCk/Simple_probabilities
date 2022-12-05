package com.example.simpleprobabilities.сalculations

class CalculateFactorial {
    fun factorial(num: Long): Long {
        var result = 1L
        for (i in 2..num) result *= i
        return result
    }
}