package org.droidwiki.reviewextractor.adapter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.droidwiki.reviewextractor.domain.EntityId
import org.droidwiki.reviewextractor.domain.PropertyId
import org.droidwiki.reviewextractor.domain.Review
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.lang.RuntimeException
import java.net.URL

@Component
class WikibaseReviewFetcher(
    @Value("\${wikibase.apiUrl}")
    private val wikibaseApiUrl: String,
    @Value("\${wikibase.reviewProperty}")
    private val wikibaseReviewProperty: String,
    @Value("\${wikibase.ratingValueProperty}")
    private val wikibaseRatingValueProperty: String
) {
    private val restTemplate = RestTemplate()

    fun fetch(entityId: EntityId): List<Review> {
        val wbGetClaimsResponse = restTemplate.getForEntity(wbGetClaimsUri(entityId), WbGetClaimsResponse::class.java)

        if (wbGetClaimsResponse.statusCode != HttpStatus.OK || wbGetClaimsResponse.body == null) {
            throw RuntimeException()
        }

        return wbGetClaimsResponse.body!!.claims.getValue(PropertyId(wikibaseReviewProperty))
            .map { Review(ratingValueAsNumber(it), 10, referenceUrl(it)) }
    }

    private fun wbGetClaimsUri(entityId: EntityId): String = UriComponentsBuilder.fromHttpUrl(wikibaseApiUrl)
        .queryParam("format", "json")
        .queryParam("action", "wbgetclaims")
        .queryParam("entity", entityId.value)
        .queryParam("property", wikibaseReviewProperty)
        .toUriString()

    private fun referenceUrl(it: Claim): URL {
        return URL(it.references.first()
            .snaks
            .entries
            .first()
            .value
            .first()
            .datavalue
            .value as String)
    }

    private fun ratingValueAsNumber(it: Claim): Double {
        val ratingValueMap = it.qualifiers.getValue(PropertyId(wikibaseRatingValueProperty))
            .first()
            .datavalue
            .value as Map<String, String>

        return ratingValueMap.getValue("amount").replace("\\+", "").toDouble()
    }
}

class WbGetClaimsResponse {
    lateinit var claims: Map<PropertyId, List<Claim>>
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Claim {
    lateinit var id: String
    lateinit var qualifiers: Map<PropertyId, List<Snak>>
    lateinit var references: List<Reference>
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Snak {
    lateinit var datavalue: DataValue
}

class DataValue {
    lateinit var value: Any
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Reference {
    lateinit var snaks: Map<String, List<Snak>>
}
