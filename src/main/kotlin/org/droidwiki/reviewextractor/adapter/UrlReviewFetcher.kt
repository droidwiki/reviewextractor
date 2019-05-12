package org.droidwiki.reviewextractor.adapter

import org.droidwiki.reviewextractor.domain.NoReviewFound
import org.droidwiki.reviewextractor.domain.Review
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URL

@Component
class UrlReviewFetcher(private val reviewExtractors: List<ReviewExtractor>) {
    private val restTemplate = RestTemplate()

    fun fetch(url: URL): Review {
        val response = restTemplate.exchange(url.toURI(), HttpMethod.GET, botEntity(), String::class.java)

        val pageContent = response.body ?: throw NoReviewFound()

        return reviewExtractors.map {
            tryExtractor(it, url, pageContent)
        }.firstOrNull { it != null } ?: throw NoReviewFound()
    }

    private fun botEntity(): HttpEntity<String> {
        val headers = HttpHeaders()
        headers.set("User-Agent", "DroidBot/1.0; (+http://data.droidwiki.org/wiki/Bewertungen)")
        return HttpEntity(headers)
    }

    private fun tryExtractor(it: ReviewExtractor, url: URL, pageContent: String): Review? {
        try {
            return it.extract(pageContent)
        } catch (e: NoReviewFound) {
            println("Review format ${it.javaClass.simpleName} not present on $url")
        }
        return null
    }
}
