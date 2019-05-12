package org.droidwiki.reviewextractor.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ReviewTest {
    @Test
    internal fun `returns rating value`() {
        assertEquals(4.0, Review(4, 10).ratingValue())
    }

    @Test
    fun `converts 5-based to 10-based rating`() {
        assertEquals(4.0, Review(2, 5).ratingValue())
    }

    @Test
    fun `converts 100-based to 10-based rating`() {
        assertEquals(9.2, Review(92, 100).ratingValue())
    }

    @Test
    fun `converts 100-based comma value to 10-based rating`() {
        assertEquals(9.3, Review(92.6, 100).ratingValue())
    }

    @ParameterizedTest
    @MethodSource("sixBasedRatings")
    internal fun `converts 6-based to 10-based rating`(sixBasedValue: Number, expectedValue: Number) {
        assertEquals(expectedValue, Review(sixBasedValue, 6).ratingValue())
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        private fun sixBasedRatings(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(1.0, 10.0),
                Arguments.of(1.1, 9.9),
                Arguments.of(1.2, 9.7),
                Arguments.of(1.3, 9.5),
                Arguments.of(1.4, 9.3),
                Arguments.of(1.5, 9.1),
                Arguments.of(1.6, 8.9),
                Arguments.of(1.7, 8.8),
                Arguments.of(1.8, 8.6),
                Arguments.of(1.9, 8.4),
                Arguments.of(2.0, 8.2),
                Arguments.of(2.1, 8.0),
                Arguments.of(2.2, 7.9),
                Arguments.of(2.3, 7.7),
                Arguments.of(2.4, 7.5),
                Arguments.of(2.5, 7.3),
                Arguments.of(2.6, 7.1),
                Arguments.of(2.7, 6.9),
                Arguments.of(2.8, 6.7),
                Arguments.of(2.9, 6.5),
                Arguments.of(3.0, 6.3),
                Arguments.of(3.1, 6.1),
                Arguments.of(3.2, 6.0),
                Arguments.of(3.3, 5.8),
                Arguments.of(3.4, 5.6),
                Arguments.of(3.5, 5.4),
                Arguments.of(3.6, 5.2),
                Arguments.of(3.7, 5.0),
                Arguments.of(3.8, 4.9),
                Arguments.of(3.9, 4.7),
                Arguments.of(4.0, 4.5),
                Arguments.of(4.1, 4.3),
                Arguments.of(4.2, 4.1),
                Arguments.of(4.3, 4.0),
                Arguments.of(4.4, 3.8),
                Arguments.of(4.5, 3.6),
                Arguments.of(4.6, 3.4),
                Arguments.of(4.7, 3.2),
                Arguments.of(4.8, 3.0),
                Arguments.of(4.9, 3.0),
                Arguments.of(5.0, 2.8),
                Arguments.of(5.1, 2.6),
                Arguments.of(5.2, 2.4),
                Arguments.of(5.3, 2.2),
                Arguments.of(5.4, 2.1),
                Arguments.of(5.5, 1.9),
                Arguments.of(5.6, 1.7),
                Arguments.of(5.7, 1.5),
                Arguments.of(5.8, 1.3),
                Arguments.of(5.9, 1.2),
                Arguments.of(6.0, 1)
            )
        }
    }
}