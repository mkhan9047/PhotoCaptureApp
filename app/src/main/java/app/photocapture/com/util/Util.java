package app.photocapture.com.util;

import android.content.res.Resources;

public class Util {
    /**
     * This method converts dp to pixels
     *
     * @param dp desired amount of dp
     * @return amount in pixels
     */
    public static float dpToPx(int dp) {
        return Math.round(Resources.getSystem().getDisplayMetrics().density * dp);
    }
}
