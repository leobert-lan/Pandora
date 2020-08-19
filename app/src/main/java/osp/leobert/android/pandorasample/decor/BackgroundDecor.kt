package osp.leobert.android.pandorasample.decor

import android.content.Context
import android.graphics.Canvas
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.decor </p>
 * <p><b>Classname:</b> BackgroundDecor </p>
 * Created by leobert on 2020/8/19.
 */
class BackgroundDecor(val context: Context,
                      @ColorInt val bgColor: Int,
                      val ignoreDelegate: IgnoreDelegate? = null) : RecyclerView.ItemDecoration() {

    private fun isIgnore(pos: Int): Boolean {
        return ignoreDelegate?.isIgnore(pos) ?: false
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        if (parent.layoutManager == null) {
            return
        }

        canvas.save()
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val pos = (child.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
            if (isIgnore(pos)) {
                continue
            }

            canvas.drawColor(bgColor)
        }
        canvas.restore()
    }

}

interface IgnoreDelegate {
    fun isIgnore(pos: Int): Boolean
    class DefaultImpl internal constructor(private val ignorePositions: Array<Int>?) : IgnoreDelegate {
        override fun isIgnore(pos: Int): Boolean {
            if (ignorePositions == null) return false
            for (i in ignorePositions) {
                if (i == pos) return true
            }
            return false
        }

    }
}