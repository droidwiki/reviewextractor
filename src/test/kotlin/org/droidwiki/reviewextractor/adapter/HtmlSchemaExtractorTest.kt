package org.droidwiki.reviewextractor.adapter

import org.droidwiki.reviewextractor.domain.NoReviewFound
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

private const val VALID_HTML = "<html><body>" +
        "<span itemprop=\"reviewRating\" itemscope=\"\" itemtype=\"http://schema.org/Rating\" class=\"chunk rating\">\n" +
        "<span class=\"icon icon-star\"> </span>\n" +
        "<span class=\"icon icon-star\"> </span>\n" +
        "<span class=\"icon icon-star\"> </span>\n" +
        "<span class=\"icon icon-star\"> </span>\n" +
        "<span class=\"icon icon-star half\"></span>\n" +
        "<meta itemprop=\"bestRating\" content=\"5.0\">" +
        "<meta itemprop=\"worstRating\" content=\"0.0\">" +
        "<meta itemprop=\"ratingValue\" content=\"4.5\">" +
        "</span>" +
        "</body></html>"

private const val MISSING_SCHEMA = "<html><body></body></html>"

class HtmlSchemaExtractorTest {
    @Test
    fun `extracts from html schema notation`() {
        val extractor = HtmlSchemaExtractor()

        val review = extractor.extract(VALID_HTML)

        assertEquals(9.0, review.ratingValue())
        assertEquals(5, review.bestRating)
    }

    @Test
    internal fun `fails on missing html schema notation`() {
        val extractor = HtmlSchemaExtractor()

        assertThrows<NoReviewFound> { extractor.extract(MISSING_SCHEMA) }
    }
}