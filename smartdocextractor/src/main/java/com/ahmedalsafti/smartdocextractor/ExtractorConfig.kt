package com.ahmedalsafti.smartdocextractor

/**
 * Configuration parameters for [SmartDocExtractor].
 *
 * @property enhanceContrast Applies text/ink contrast boost when true.
 * @property contrastLevel Multiplier for contrast enhancement (default 1.25f).
 * @property luminanceThreshold Brightness threshold (0-255) used to separate paper from dark backgrounds.
 */
data class ExtractorConfig(
    val enhanceContrast: Boolean = true,
    val contrastLevel: Float = 1.25f,
    val luminanceThreshold: Int = 140
)
