package com.example.simpleprobabilities.сalculations

import java.math.BigInteger

object CalculateFactorial {
    fun factorial(num: BigInteger): BigInteger {
        var result = BigInteger.ONE
        var i = BigInteger.valueOf(2)
        while (i.compareTo(num)==-1 || i.compareTo(num)==0){
            result = result.multiply(i)
            i = i.plus(BigInteger.ONE)
        }
        return result
    }
}