package org.example.app.util

import java.text.NumberFormat
import java.util.Locale

/**
 * PUBLIC_INTERFACE
 * Format cents into localized currency.
 */
fun Int.toCurrency(): String {
    val nf = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return nf.format(this / 100.0)
}
