package org.droidwiki.reviewextractor.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class EntityIdTest {
    @Test
    fun `creates valid entity ID`() {
        assertEquals("Q311", EntityId("Q311").value)
    }

    @Test
    fun `fails on missing starting Q`() {
        assertThrows<IllegalArgumentException> { EntityId("311") }
    }
}