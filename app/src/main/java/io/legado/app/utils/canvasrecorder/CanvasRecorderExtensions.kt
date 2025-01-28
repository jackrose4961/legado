package io.legado.app.utils.canvasrecorder

import android.graphics.Canvas
import android.view.View
import androidx.core.graphics.withSave

inline fun CanvasRecorder.recordIfNeeded(
    width: Int,
    height: Int,
    block: Canvas.() -> Unit
): Boolean {
    if (!needRecord()) return false
    record(width, height, block)
    return true
}

fun CanvasRecorder.recordIfNeeded(view: View): Boolean {
    if (!needRecord()) return false
    record(view.width, view.height) {
        view.draw(this)
    }
    return true
}

inline fun CanvasRecorder.record(width: Int, height: Int, block: Canvas.() -> Unit) {
    try {
        val canvas = beginRecording(width, height)
        canvas.withSave {
            block()
        }
    } finally {
        endRecording()
    }
}

inline fun CanvasRecorder.recordIfNeededThenDraw(
    canvas: Canvas,
    width: Int,
    height: Int,
    block: Canvas.() -> Unit
) {
    recordIfNeeded(width, height, block)
    draw(canvas)
}
