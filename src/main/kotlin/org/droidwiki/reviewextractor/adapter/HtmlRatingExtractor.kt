package org.droidwiki.reviewextractor.adapter

import org.droidwiki.reviewextractor.domain.NoReviewFound
import org.droidwiki.reviewextractor.domain.Review
import org.jsoup.Jsoup
import org.springframework.stereotype.Component
import java.text.NumberFormat
import java.util.*

@Component
class HtmlRatingExtractor: ReviewExtractor {
    override fun extract(htmlString: String): Review {
        val document = asDocument(htmlString)
        val ratingValueContainer = document.getElementsByAttributeValue("class", "ratingresult")

        if (ratingValueContainer.isEmpty()) {
            throw NoReviewFound()
        }
        val ratingValueMeta = ratingValueContainer.first().getElementsByAttributeValue("class", "result")
        val ratingValue = toDouble(ratingValueMeta.text())

        return Review(ratingValue, 6.0)
    }

    private fun toDouble(numberAsText: String): Double =
        NumberFormat.getInstance(Locale.GERMAN).parse(numberAsText).toDouble()

    private fun asDocument(htmlString: String) = Jsoup.parse(htmlString)
}
