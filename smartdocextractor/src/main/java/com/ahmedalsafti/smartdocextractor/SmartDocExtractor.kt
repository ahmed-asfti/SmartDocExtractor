package com.ahmedalsafti.smartdocextractor

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.Rect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * SmartDocExtractor
 * Isolates document content onto a pure white canvas without destructive auto-cropping.
 */
class SmartDocExtractor(
    private val config: ExtractorConfig = ExtractorConfig()
) {

    /**
     * Processes the [sourceBitmap] asynchronously, cleans background noise,
     * and places the preserved document onto a clean white canvas.
     */
    suspend fun extractToWhiteCanvas(
        sourceBitmap: Bitmap,
        enhanceContrast: Boolean = config.enhanceContrast
    ): Bitmap = withContext(Dispatchers.Default) {
        val width = sourceBitmap.width
        val height = sourceBitmap.height

        // 1. Create a pure white output canvas matching original dimensions (no destructive crop)
        val outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(outputBitmap)
        canvas.drawColor(Color.WHITE)

        // 2. Detect active content bounds to isolate background shadows
        val contentBounds = detectContentBounds(sourceBitmap)

        // 3. Apply optional contrast and sharpness enhancement for text clarity
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        if (enhanceContrast) {
            paint.colorFilter = createDocumentEnhancementFilter(config.contrastLevel)
        }

        // 4. Draw the isolated document onto the white canvas preserving original coordinates
        canvas.drawBitmap(sourceBitmap, contentBounds, contentBounds, paint)

        return@withContext outputBitmap
    }

    /**
     * Scans pixel luminance to find the bounding box of the document content
     * while stripping out dark outer table/background edges.
     */
    private fun detectContentBounds(bitmap: Bitmap): Rect {
        val width = bitmap.width
        val height = bitmap.height
        var minX = width
        var minY = height
        var maxX = 0
        var maxY = 0

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        for (y in 0 until height step 2) {
            for (x in 0 until width step 2) {
                val pixel = pixels[y * width + x]
                val luminance = calculateLuminance(pixel)

                // Identify paper/content region based on brightness threshold
                if (luminance > config.luminanceThreshold) {
                    if (x < minX) minX = x
                    if (x > maxX) maxX = x
                    if (y < minY) minY = y
                    if (y > maxY) maxY = y
                }
            }
        }

        // Fallback to full image if bounds are invalid
        if (minX >= maxX || minY >= maxY) {
            return Rect(0, 0, width, height)
        }

        return Rect(minX, minY, maxX, maxY)
    }

    private fun calculateLuminance(color: Int): Int {
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        return (0.299 * r + 0.587 * g + 0.114 * b).toInt()
    }

    private fun createDocumentEnhancementFilter(contrast: Float): ColorMatrixColorFilter {
        val scale = contrast
        val translate = (-0.5f * scale + 0.5f) * 255f
        val matrix = ColorMatrix(
            floatArrayOf(
                scale, 0f, 0f, 0f, translate,
                0f, scale, 0f, 0f, translate,
                0f, 0f, scale, 0f, translate,
                0f, 0f, 0f, 1f, 0f
            )
        )
        return ColorMatrixColorFilter(matrix)
    }
}
