package org.droidwiki.reviewextractor.domain

import java.lang.IllegalArgumentException

data class PropertyId(val value: String) {
    init {
        if (!value.startsWith("P")) {
            throw IllegalArgumentException("Wikibase property ids must start with P...")
        }
    }
}