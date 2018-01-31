package cc.zruibin.modules.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;

import java.util.regex.Pattern;

/**
 * Created by ruibin.chow on 19/01/2018.
 */

public final class ColorUtil {

    /**获取资源中的颜色*/
    public static int getResourcesColor(Context ctx, int colorId) {

        int ret = 0x00ffffff;
        try {
            ret = ctx.getResources().getColor(colorId);
        } catch (Exception e) {
        }

        return ret;
    }

    /**将十六进制颜色代码转换为 int*/
    public static int hexToColor(String color) {
        return Color.parseColor(color);
    }

    /**修改颜色透明度*/
    public static int changeAlpha(int color, int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        return Color.argb(alpha, red, green, blue);
    }

    /**将十六进制颜色代码转换为带alpha的int, alpha: 0.0~1.0*/
    public static int hexToAlphaColor(String hexColor, double alpha) {
        int value = new Double(alpha * 255).intValue();
        int color = hexToColor(hexColor);
        color = changeAlpha(color, value);
        return color;
    }

    /** 设置不同状态时其文字颜色。 */
    public static ColorStateList getColorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[] { pressed, focused, normal, focused, unable, normal };
        int[][] states = new int[6][];
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[2] = new int[] { android.R.attr.state_enabled };
        states[3] = new int[] { android.R.attr.state_focused };
        states[4] = new int[] { android.R.attr.state_window_focused };
        states[5] = new int[] {};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

}
