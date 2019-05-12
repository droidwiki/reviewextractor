package org.droidwiki.reviewextractor.adapter

import org.droidwiki.reviewextractor.domain.Review

interface ReviewExtractor {
    fun extract(htmlString: String): Review
}