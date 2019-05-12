package org.droidwiki.reviewextractor.adapter

import org.droidwiki.reviewextractor.domain.NoReviewFound
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertNotNull

private const val REVIEW_SCHEMA = "<html><head><script type=\"application/ld+json\">\n" +
        "{\n" +
        "    \"@context\": \"https://schema.org/\",\n" +
        "    \"@type\": \"Review\",\n" +
        "    \"mainEntityOfPage\": {\n" +
        "        \"@type\": \"WebPage\",\n" +
        "        \"@id\": \"https://www.tomsguide.com/us/google-pixel-3a,review-6452.html\"\n" +
        "    },\n" +
        "    \"reviewRating\": {\n" +
        "      \"@type\": \"Rating\",\n" +
        "      \"worstRating\": \"0\",\n" +
        "      \"bestRating\": \"10\",\n" +
        "      \"ratingValue\": \"4.5\"\n" +
        "    }\n" +
        "}</script></head><body>Some text</body></html>"

private const val PRODUCT_SCHEMA = "<html><head><script type=\"application/ld+json\">\n" +
        "{\n" +
        "  \"@context\": \"https://schema.org\",\n" +
        "  \"@type\": \"Product\",\n" +
        "  \"review\": {\n" +
        "    \"reviewRating\": {\n" +
        "      \"@type\": \"rating\",\n" +
        "      \"ratingValue\": 4.5,\n" +
        "      \"worstRating\": \"0\",\n" +
        "      \"bestRating\": \"10\"\n" +
        "    }\n" +
        "  }" +
        "}</script></head><body>Some text</body></html>"

private const val NON_HTTPS_CONTEXT = "<html><head><script type=\"application/ld+json\">\n" +
        "{\n" +
        "  \"@context\": \"http://schema.org\",\n" +
        "  \"@type\": \"Product\",\n" +
        "  \"review\": {\n" +
        "    \"reviewRating\": {\n" +
        "      \"@type\": \"rating\",\n" +
        "      \"ratingValue\": \"4,5\",\n" +
        "      \"worstRating\": \"0\",\n" +
        "      \"bestRating\": \"10\"\n" +
        "    }\n" +
        "  }" +
        "}</script></head><body>Some text</body></html>"

private const val WRONG_CONTEXT = "<html><head><script type=\"application/ld+json\">\n" +
        "{\n" +
        "    \"@context\": \"https://another.org/\"\n" +
        "}</script></head><body>Some text</body></html>"

private const val MISSING_RATINGS = "<html><head><script type=\"application/ld+json\">\n" +
        "{\n" +
        "    \"@context\": \"https://schema.org/\"\n" +
        "}</script></head><body>Some text</body></html>"

class LdJsonExtractorTest {
    @Test
    fun `extracts from Review application json-ld`() {
        val reviewExtractor = LdJsonExtractor()

        val review = reviewExtractor.extract(REVIEW_SCHEMA)

        assertEquals(4.5, review.ratingValue())
        assertEquals(10.0, review.bestRating)
    }

    @Test
    fun `extracts from Product application json-ld`() {
        val reviewExtractor = LdJsonExtractor()

        val review = reviewExtractor.extract(PRODUCT_SCHEMA)

        assertEquals(4.5, review.ratingValue())
        assertEquals(10.0, review.bestRating)
    }

    @Test
    fun `extracts with non-https schema context application json-ld`() {
        val reviewExtractor = LdJsonExtractor()

        val review = reviewExtractor.extract(NON_HTTPS_CONTEXT)

        assertNotNull(review)
    }

    @Test
    fun `fails on missing ld+json`() {
        val reviewExtractor = LdJsonExtractor()

        assertThrows<NoReviewFound> { reviewExtractor.extract("") }
    }

    @Test
    fun `fails on wrong context`() {
        val reviewExtractor = LdJsonExtractor()

        assertThrows<NoReviewFound> { reviewExtractor.extract(WRONG_CONTEXT) }
    }

    @Test
    fun `fails on missing ratings`() {
        val reviewExtractor = LdJsonExtractor()

        assertThrows<NoReviewFound> { reviewExtractor.extract(MISSING_RATINGS) }
    }
}