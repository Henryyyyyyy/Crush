package me.henry.lib_base.others.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Service工具类
 */
public class ServiceUtil {

    private static final String TAG = "ServiceUtil";

    /**
     * 判断指定Service是否正在运行
     *
     * @param context     上下文
     * @param serviceName Service 类全名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isRunning(Context context, String serviceName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfoList = manager.getRunningServices(Integer.MAX_VALUE);
        if (serviceInfoList.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo info : serviceInfoList) {
            if (info.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
}
