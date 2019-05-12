package org.droidwiki.reviewextractor.adapter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.droidwiki.reviewextractor.domain.NoReviewFound
import org.droidwiki.reviewextractor.domain.Review
import org.jsoup.Jsoup
import org.jsoup.nodes.DataNode
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component
import java.lang.NumberFormatException
import java.text.NumberFormat
import java.util.*

private const val SCHEMA_ORG_CONTEXT = "schema.org"

@Component
class LdJsonExtractor: ReviewExtractor {
    private val objectMapper = ObjectMapper()

    override fun extract(htmlString: String): Review =
        Optional.ofNullable(asDocument(htmlString).getElementsByTag("script")
            .map { this.extractReview(it) }
            .firstOrNull { it != null }
        ).orElseThrow(::NoReviewFound)

    private fun asDocument(htmlString: String) = Jsoup.parse(htmlString)

    private fun extractReview(it: Element): Review? {
        if (isLdJson(it)) {
            val schema = asSchema(it)
            if (hasReviewRating(schema)) {
                return schema.asReview()
            }
        }
        return null
    }

    private fun asSchema(it: Element) =
        objectMapper.readValue((it.childNode(0) as DataNode).wholeData, Schema::class.java)

    private fun hasReviewRating(schema: Schema): Boolean {
        return schema.context.contains(SCHEMA_ORG_CONTEXT) && (schema.reviewRating != null || schema.review != null)
    }

    private fun isLdJson(it: Element) = it.attr("type") == "application/ld+json"
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Schema {
    fun asReview(): Review {
        if (reviewRating != null) {
            return Review(reviewRating!!.ratingValue(), reviewRating!!.bestRating())
        }
        if (review != null) {
            return Review(review!!.reviewRating.ratingValue(), review!!.reviewRating.bestRating())
        }
        throw NoReviewFound()
    }

    @JsonProperty("@context")
    lateinit var context: String

    var reviewRating: ReviewRating? = null
    var review: SchemaReview? = null
}

@JsonIgnoreProperties(ignoreUnknown = true)
class SchemaReview {
    lateinit var reviewRating: ReviewRating
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ReviewRating {
    lateinit var ratingValue: String
    lateinit var bestRating: String

    fun ratingValue(): Number {
        return asNumber(ratingValue)
    }

    private fun asNumber(aNumber: String): Number {
        try {
            return aNumber.toDouble()
        } catch (ignored: NumberFormatException) {
        }

        try {
            return NumberFormat.getNumberInstance(Locale.GERMAN).parse(aNumber)
        } catch (ignored: NumberFormatException) {
        }

        throw NumberFormatException()
    }

    fun bestRating(): Number {
        return asNumber(bestRating)
    }
}
