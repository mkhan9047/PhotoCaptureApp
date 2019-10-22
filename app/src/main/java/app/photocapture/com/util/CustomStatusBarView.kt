package app.photocapture.com.util

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import app.photocapture.com.util.Util

import kotlin.math.roundToInt

/**
 * This is a custom status bar view which works with AppTheme.GradientStatusBar inorder to achieve
 * gradient or custom colors at status bar
 * @author Al. Mujahid Khan
 * */
class CustomStatusBarView : View {

    /**
     * Fields
     * */
    private var mStatusBarHeight: Int = 0

    /**
     * Constructors
     * */
    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mStatusBarHeight = Util.dpToPx(24).roundToInt()
            return insets.consumeSystemWindowInsets()
        }
        return insets
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mStatusBarHeight)
    }
}