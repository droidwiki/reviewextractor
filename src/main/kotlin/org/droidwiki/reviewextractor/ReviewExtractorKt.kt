package org.droidwiki.reviewextractor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ReviewExtractorApplication

fun main(args: Array<String>) {
    runApplication<ReviewExtractorApplication>(*args)
}