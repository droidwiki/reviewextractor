package org.droidwiki.reviewextractor.domain

import java.math.BigDecimal
import java.math.RoundingMode

data class Review(private val ratingValue: Number, val bestRating: Number) {
    fun ratingValue(): Number {
        return when(bestRating.toDouble()) {
            5.0 -> this.ratingValue.toDouble() * 2
            100.0 -> round(this.ratingValue.toDouble() / 10)
            6.0 -> asSixBased()
            else -> this.ratingValue.toDouble()
        }
    }

    private fun round(value: Double): Number = BigDecimal.valueOf(value)
        .setScale(1, RoundingMode.HALF_EVEN)
        .toDouble()

    private fun asSixBased(): Number {
        return when(this.ratingValue) {
            1.0 -> 10.0
            1.1 -> 9.9
            1.2 -> 9.7
            1.3 -> 9.5
            1.4 -> 9.3
            1.5 -> 9.1
            1.6 -> 8.9
            1.7 -> 8.8
            1.8 -> 8.6
            1.9 -> 8.4
            2.0 -> 8.2
            2.1 -> 8.0
            2.2 -> 7.9
            2.3 -> 7.7
            2.4 -> 7.5
            2.5 -> 7.3
            2.6 -> 7.1
            2.7 -> 6.9
            2.8 -> 6.7
            2.9 -> 6.5
            3.0 -> 6.3
            3.1 -> 6.1
            3.2 -> 6.0
            3.3 -> 5.8
            3.4 -> 5.6
            3.5 -> 5.4
            3.6 -> 5.2
            3.7 -> 5.0
            3.8 -> 4.9
            3.9 -> 4.7
            4.0 -> 4.5
            4.1 -> 4.3
            4.2 -> 4.1
            4.3 -> 4.0
            4.4 -> 3.8
            4.5 -> 3.6
            4.6 -> 3.4
            4.7 -> 3.2
            4.8 -> 3.0
            4.9 -> 3.0
            5.0 -> 2.8
            5.1 -> 2.6
            5.2 -> 2.4
            5.3 -> 2.2
            5.4 -> 2.1
            5.5 -> 1.9
            5.6 -> 1.7
            5.7 -> 1.5
            5.8 -> 1.3
            5.9 -> 1.2
            else -> 1
        }
    }
}