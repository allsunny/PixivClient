package com.example.zero.pixivclient.utils;

import android.content.Context;
import android.text.format.DateUtils;


public class RefreshUtil {
    /**
     *
     * @param context
     * @return string label
     */
    public static String getRefreshTime(Context context) {
        String label = DateUtils.formatDateTime(context, System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

        return label;
    }
}
