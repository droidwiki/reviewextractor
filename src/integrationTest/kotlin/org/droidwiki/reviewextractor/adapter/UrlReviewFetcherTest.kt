package org.droidwiki.reviewextractor.adapter

import org.droidwiki.reviewextractor.adapter.UrlReviewFetcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.URL

@SpringBootTest
@ExtendWith(SpringExtension::class)
class UrlReviewFetcherTest {
    @Autowired
    lateinit var urlReviewFetcher: UrlReviewFetcher

    @Test
    fun `extracts review from techradar-com`() {
        val review = urlReviewFetcher.fetch(URL("https://www.techradar.com/reviews/google-pixel-3-review"))

        assertEquals(9.0, review.ratingValue())
    }

    @Test
    fun `extracts review from tomsguide-com`() {
        val review = urlReviewFetcher.fetch(URL("https://www.tomsguide.com/us/google-pixel-3,review-5841.html"))

        assertEquals(9.0, review.ratingValue())
    }

    @Test
    fun `extracts review from cnet-com`() {
        val review = urlReviewFetcher.fetch(URL("https://www.cnet.com/reviews/google-pixel-3-2018-review/"))

        assertEquals(9.0, review.ratingValue())
    }

    @Test
    fun `extracts review from notebookcheck-com`() {
        val review = urlReviewFetcher.fetch(URL("https://www.notebookcheck.com/Test-Samsung-Galaxy-S10-Smartphone.413912.0.html"))

        assertEquals(9.2, review.ratingValue())
    }

    @Test
    fun `extracts review from t3-com`() {
        val review = urlReviewFetcher.fetch(URL("https://www.t3.com/reviews/samsung-galaxy-s10-review"))

        assertEquals(10.0, review.ratingValue())
    }

    @Test
    fun `extracts review from netzwelt-de`() {
        val review = urlReviewFetcher.fetch(URL("https://www.netzwelt.de/samsung-galaxy-s10/testbericht-review.html"))

        assertEquals(8.4, review.ratingValue())
    }

    @Test
    fun `extracts review from chip-de`() {
        val review = urlReviewFetcher.fetch(URL("https://www.chip.de/test/Samsung-Galaxy-S10-Test_161588359.html"))

        assertEquals(9.6, review.ratingValue())
    }

    @Test
    fun `extracts review from computerbild-de`() {
        val review = urlReviewFetcher.fetch(URL("https://www.computerbild.de/artikel/cb-Tests-Handy-Google-Pixel-3-im-Test-Spitzenmodell-22538541.html"))

        assertEquals(7.5, review.ratingValue())
    }
}