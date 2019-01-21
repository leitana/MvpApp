package com.lx.mvpapp.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;

import com.lx.mvpapp.MyApplication;
import com.lx.mvpapp.R;
import com.yjx.sharelibrary.Share;

/**
 * Created by linxiao on 2018/10/10.
 */

public class ThemeUtil {
    //默认主题颜色
    private static int defaultThemeColor = Color.rgb(251,91,129);
    private static Context context = MyApplication.mContext;

    public static void setThemeColor(int color) {
        Share.putInt("themeColor", color);
    }

    public static void setThemePosition(int position) {
        Share.putInt("themePosition", position);
    }

    public static int getThemePosition() {
        return Share.getInt("themePosition", 0);
    }

    public static int getThemeColor(){
        return Share.getInt("themeColor", defaultThemeColor);
    }

    public static int getToolBarColor() {
        return getThemeColor();
    }

    public static ColorStateList getNaviItemIconTinkList(){
        int position = getThemePosition();
        Resources resources = context.getResources();
        ColorStateList colorStateList;
        switch (position){
            case 0:
                colorStateList = resources.getColorStateList(R.color.theme_redbase_nav_menu_icontint);
                return colorStateList;
            case 1:
                colorStateList = resources.getColorStateList(R.color.theme_blue_navi_menu_icontint);
                return colorStateList;
            case 2:
                colorStateList = resources.getColorStateList(R.color.theme_lightblue_navi_menu_icontint);
                return colorStateList;
            case 3:
                colorStateList = resources.getColorStateList(R.color.theme_black_navi_menu_icontint);
                return colorStateList;
            case 4:
                colorStateList =resources.getColorStateList(R.color.theme_teal_navi_menu_icontink);
                return colorStateList;
            case 5:
                colorStateList = resources.getColorStateList(R.color.theme_brown_navi_menu_icontint);
                return colorStateList;
            case 6:
                colorStateList = resources.getColorStateList(R.color.theme_green_navi_menu_icontink);
                return colorStateList;
            case 7:
                colorStateList = resources.getColorStateList(R.color.theme_red_navi_menu_icontink);
                return colorStateList;
        }
        colorStateList = resources.getColorStateList(R.color.theme_redbase_tablayout_text_colorlist);
        return colorStateList;
    }
}
