package com.supwisdom.penn.sw_pos.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.WindowManager;

public class BrightnessSetUtil {
    /* Android的屏幕亮度好像在2.1+的时候提供了自动调节的功能，
     * 所以，如果当开启自动调节功能的时候， 我们进行调节好像是没有一点作用的，
	 * 这点让我很是无语，结果只有进行判断，看是否开启了屏幕亮度的自动调节功能。
	 */


    /**
     * 判断是否开启了自动亮度调节
     */

    public static boolean isAutoBrightness(ContentResolver aContentResolver) {

        boolean automicBrightness = false;

        try {

            automicBrightness = Settings.System.getInt(aContentResolver,

                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;

        } catch (SettingNotFoundException e)

        {

            e.printStackTrace();

        }

        return automicBrightness;
    }

    /**
     * 获取屏幕的亮度
     */

    public static int getScreenBrightness(Activity activity) {

        int nowBrightnessValue = 0;

        ContentResolver resolver = activity.getContentResolver();

        try {

            nowBrightnessValue = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return nowBrightnessValue;
    }


    /**
     * 设置亮度
     */

    public static void setBrightness(Activity activity, int brightness) {


        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();

        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);

        activity.getWindow().setAttributes(lp);
    }

    /**
     * 停止自动亮度调节
     */

    public static void stopAutoBrightness(Activity activity) {

        Settings.System.putInt(activity.getContentResolver(),

                Settings.System.SCREEN_BRIGHTNESS_MODE,

                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * 开启亮度自动调节 *
     *
     * @param activity
     */


    public static void startAutoBrightness(Activity activity) {

        Settings.System.putInt(activity.getContentResolver(),

                Settings.System.SCREEN_BRIGHTNESS_MODE,

                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);

    }

}