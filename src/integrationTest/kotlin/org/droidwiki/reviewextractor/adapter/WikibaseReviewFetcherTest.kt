package org.droidwiki.reviewextractor.adapter

import org.droidwiki.reviewextractor.domain.EntityId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class WikibaseReviewFetcherTest {
    @Autowired
    private lateinit var wikibaseReviewFetcher: WikibaseReviewFetcher

    @Test
    fun `fetches the review of a Wikibase entity`() {
        val reviews = wikibaseReviewFetcher.fetch(EntityId("Q311"))

        assertEquals(8, reviews.size)
        assertEquals(8.0, reviews.first().ratingValue())
    }
}