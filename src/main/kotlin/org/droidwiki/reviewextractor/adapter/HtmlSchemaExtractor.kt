package org.droidwiki.reviewextractor.adapter

import org.droidwiki.reviewextractor.domain.NoReviewFound
import org.droidwiki.reviewextractor.domain.Review
import org.jsoup.Jsoup
import org.springframework.stereotype.Component

@Component
class HtmlSchemaExtractor: ReviewExtractor {
    override fun extract(htmlString: String): Review {
        val document = asDocument(htmlString)
        val bestRatingMeta = document.getElementsByAttributeValue("itemprop", "bestRating")
        val ratingValueMeta = document.getElementsByAttributeValue("itemprop", "ratingValue")

        if (bestRatingMeta.isEmpty() || ratingValueMeta.isEmpty()) {
            throw NoReviewFound()
        }

        val ratingValue = ratingValueMeta.attr("content").toDouble()
        val bestRating = bestRatingMeta.attr("content").toDouble().toInt()

        return Review(ratingValue, bestRating)
    }

    private fun asDocument(htmlString: String) = Jsoup.parse(htmlString)
}
