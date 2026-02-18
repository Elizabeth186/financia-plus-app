package com.financiaplus.app.core.utils

object Masks {

    private fun digitsOnly(input: String): String = input.filter { it.isDigit() }

    fun dui(input: String): String {
        val digits = digitsOnly(input).take(9)
        return if (digits.length <= 8) digits
        else "${digits.substring(0, 8)}-${digits.substring(8)}"
    }

    fun phoneSv(input: String): String {
        val digits = digitsOnly(input).take(8)
        return if (digits.length <= 4) digits
        else "${digits.substring(0, 4)}-${digits.substring(4)}"
    }
}