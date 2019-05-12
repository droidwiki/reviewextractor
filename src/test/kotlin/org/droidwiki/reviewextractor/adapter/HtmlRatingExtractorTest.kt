package org.droidwiki.reviewextractor.adapter

import org.droidwiki.reviewextractor.domain.NoReviewFound
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

private const val VALID_HTML = "<html><body>" +
        "<div class=\"ratingresult\">\n" +
        "\t<p class=\"subtitle\">Testnote der Redaktion</p>\n" +
        "\t<p class=\"result\">2,4</p>\n" +
        "\t<span class=\"verbalization\">gut</span>\n" +
        "</body></html>"

private const val MISSING_HTML = "<html><body></body></html>"

class HtmlRatingExtractorTest {
    @Test
    fun `extracts from html notation`() {
        val extractor = HtmlRatingExtractor()

        val review = extractor.extract(VALID_HTML)

        assertEquals(7.5, review.ratingValue())
        assertEquals(6.0, review.bestRating)
    }

    @Test
    internal fun `fails on missing html schema notation`() {
        val extractor = HtmlRatingExtractor()

        assertThrows<NoReviewFound> { extractor.extract(MISSING_HTML) }
    }
}