package org.droidwiki.reviewextractor.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class PropertyIdTest {
    @Test
    fun `creates valid property ID`() {
        assertEquals("P75", PropertyId("P75").value)
    }

    @Test
    fun `fails on missing starting P`() {
        assertThrows<IllegalArgumentException> { PropertyId("75") }
    }
}