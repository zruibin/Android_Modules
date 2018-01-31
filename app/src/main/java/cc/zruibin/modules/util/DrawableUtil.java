package cc.zruibin.modules.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by ruibin.chow on 19/01/2018.
 */

public final class DrawableUtil {

    public static final int DEFAULT_RIPPLE_COLOR = Color.GRAY;
    public static final int DEFAULT_COLOR = Color.parseColor("#00000000"); //透明色

    /** 从Resources里获得drawable*/
    public static Drawable getDrawableFromResources(Activity activity, @DrawableRes int id) {
        return activity.getResources().getDrawable(id, null);
    }

    public static RippleDrawable getRippleDrawable(Drawable drawable, int rippleColor) {
        RippleDrawable rippleDrawable = new RippleDrawable(ColorStateList.valueOf(rippleColor), drawable, null);
        return rippleDrawable;
    }

    public static Drawable getRippleDrawable(Drawable drawable) {
        return getRippleDrawable(drawable, DEFAULT_RIPPLE_COLOR);
    }

    public static Drawable getRectRippleDrawable() {
        return getRectRippleDrawable(Color.WHITE);
    }

    public static Drawable getRectRippleDrawable(int tintColor) {
        return getRectRippleDrawable(tintColor, DEFAULT_RIPPLE_COLOR);
    }

    public static Drawable getRectRippleDrawable(int tintColor, int rippleColor) {
        ShapeDrawable rectDrawable = new ShapeDrawable(new RectShape());
        rectDrawable.getPaint().setColor(tintColor);
        rectDrawable.getPaint().setStyle(Paint.Style.FILL); //填充
        return getRippleDrawable(rectDrawable, rippleColor);
    }

    public static Drawable getRoundRectRippleDrawable(int cornerRadius) {
        return getRoundRectRippleDrawable(DEFAULT_COLOR, DEFAULT_RIPPLE_COLOR, cornerRadius);
    }

    public static Drawable getRoundRectRippleDrawable(int tintColor, int cornerRadius) {
        return getRoundRectRippleDrawable(tintColor, DEFAULT_RIPPLE_COLOR, cornerRadius);
    }

    public static Drawable getRoundRectRippleDrawable(int tintColor, int rippleColor, int cornerRadius) {

        Drawable rectDrawable = getBorderDrawable(cornerRadius, tintColor, 0, 0);
        return getRippleDrawable(rectDrawable, rippleColor);
    }

    public static Drawable getRectDrawable() {
        return getRectDrawable(Color.WHITE);
    }

    public static Drawable getRectDrawable(int tintColor) {
        ShapeDrawable rectDrawable = new ShapeDrawable(new RectShape());
        rectDrawable.getPaint().setColor(tintColor);
        rectDrawable.getPaint().setStyle(Paint.Style.FILL); //填充
        return rectDrawable;
    }

    public static Drawable getRoundRectDrawable(int cornerRadius) {
        return getRoundRectDrawable(Color.WHITE, cornerRadius);
    }

    public static Drawable getRoundRectDrawable(int tintColor, int cornerRadius) {
        // 外部矩形弧度
        float[] outerR = new float[] { cornerRadius, cornerRadius, cornerRadius, cornerRadius,
                cornerRadius, cornerRadius, cornerRadius, cornerRadius };
        // 内部矩形与外部矩形的距离
        RectF inset = new RectF(100, 100, 50, 50);
        // 内部矩形弧度
        float[] innerRadii = new float[] { 20, 20, 20, 20, 20, 20, 20, 20 };
        RoundRectShape roundRectShape = new RoundRectShape(outerR, null, null); //无内矩形

        ShapeDrawable roundRectDrawable = new ShapeDrawable(roundRectShape);
        roundRectDrawable.getPaint().setColor(tintColor);
        roundRectDrawable.getPaint().setStyle(Paint.Style.FILL); //填充
        return roundRectDrawable;
    }

    /** 设置Selector */
    public static StateListDrawable getSelector(Context context,
                                                int idNormal, int idPressed, int idFocused, int idUnable) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = (idNormal == -1) ? null : context.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
        Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable);
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
        // View.ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled }, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_focused }, focused);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[] {}, normal);
        return bg;
    }

    /*** 设置shape(设置单独每一个圆角)*/
    public static GradientDrawable getBorderDrawable(float topLeftCA, float topRigthCA, float buttomLeftCA,
                                        float buttomRightCA, int bgColor, int storkeWidth, int strokeColor) {
        float[] circleAngleArr = {topLeftCA, topLeftCA, topRigthCA, topRigthCA,
                buttomLeftCA, buttomLeftCA, buttomRightCA, buttomRightCA};
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadii(circleAngleArr);//圆角
        gradientDrawable.setColor(bgColor); //背景色
        gradientDrawable.setStroke(storkeWidth, strokeColor); //边框宽度，边框颜色
        return gradientDrawable;
    }

    /*** 设置shape(设置圆角)*/
    public static GradientDrawable getBorderDrawable(float cornerRadius, int bgColor, int storkeWidth, int strokeColor) {
       return getBorderDrawable(cornerRadius, cornerRadius, cornerRadius, cornerRadius, bgColor, storkeWidth, strokeColor);
    }

//    /**设置shape(圆角)*/
//    public static GradientDrawable getDrawable(int bgCircleAngle, int bgColor, int width, int strokeColor) {
//        GradientDrawable gradientDrawable = new GradientDrawable();
//        gradientDrawable.setCornerRadius(bgCircleAngle);
//        gradientDrawable.setColor(bgColor);
//        gradientDrawable.setStroke(width, strokeColor);
//        return gradientDrawable;
//    }

    /** drawable 复制*/
    public static Drawable getNewDrawable(Drawable drawable) {
        return drawable.getConstantState().newDrawable();
    }

    /** drawable 各状态着色*/
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    /** drawable 着色*/
    public static Drawable tintDrawable(Drawable drawable, int tintColor) {
        ColorStateList stateList = ColorUtil.getColorStateList(tintColor, tintColor, tintColor, tintColor);
        return tintDrawable(drawable, stateList);
    }


}
