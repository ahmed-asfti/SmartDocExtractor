package com.ahmedalsafti.smartdocextractor

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SmartDocExtractorTest {

    @Test
    fun `default configuration has valid thresholds`() {
        val config = ExtractorConfig()
        assertTrue(config.enhanceContrast)
        assertEquals(1.25f, config.contrastLevel)
        assertTrue(config.luminanceThreshold in 0..255)
    }
}
