package org.droidwiki.reviewextractor.domain

import java.lang.IllegalArgumentException

data class EntityId(val value: String) {
    init {
        if (!value.startsWith("Q")) {
            throw IllegalArgumentException("Wikibase entity ids must start with Q...")
        }
    }
}