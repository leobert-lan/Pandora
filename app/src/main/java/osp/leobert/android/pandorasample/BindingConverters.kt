package osp.leobert.android.pandorasample

import android.view.View
import androidx.databinding.BindingConversion

/**
 * <p><b>Package:</b> osp.leobert.android.pandorasample </p>
 * <p><b>Classname:</b> BindingConverters </p>
 * Created by leobert on 2020/8/24.
 */
object BindingConverters {
    /**
     * `android:visibility="@{boolean}"`
     */
    @BindingConversion
    @JvmStatic
    fun booleanToVisible(isVisible: Boolean): Int{
        return if(isVisible) View.VISIBLE else View.GONE
    }
}