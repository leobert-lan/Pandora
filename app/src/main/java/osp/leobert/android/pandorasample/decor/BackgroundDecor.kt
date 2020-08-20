package osp.leobert.android.pandorasample.decor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample.decor </p>
 * <p><b>Classname:</b> BackgroundDecor </p>
 * Created by leobert on 2020/8/19.
 */
class BackgroundDecor(val context: Context,
                      @ColorInt val bgColor: Int,
                      val ignoreDelegate: IgnoreDelegate? = null) : RecyclerView.ItemDecoration() {

    val bgDrawable = ColorDrawable(bgColor)

    private fun isIgnore(pos: Int): Boolean {
        return ignoreDelegate?.isIgnore(pos) ?: false
    }

    private val mBounds = Rect()

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        if (parent.layoutManager == null) {
            return
        }

        canvas.save()
        val left: Int
        val right: Int
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right,
                    parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val pos = (child.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
            if (isIgnore(pos)) {
                continue
            }
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom: Int = mBounds.bottom + child.translationY.roundToInt()
            val top: Int = bottom - child.height
            bgDrawable.setBounds(left, top, right, bottom)
            bgDrawable.draw(canvas)
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