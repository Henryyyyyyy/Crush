package me.henry.lib_base.others.util;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * 进程工具类
 */
public class ProcessUtil {

    public static final String TAG = ProcessUtil.class.getSimpleName();
    
    /**
     * 获取当前进程名称
     * @return 进程名称
     */
    public static String getName() {
        Log.d(TAG, "getProcessName: ");
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 当前是否运行于主进程
     * @param context 上下文
     * @return
     */
    public static  boolean isAppRunningOnMainProcess(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager.getRunningAppProcesses() != null) {
            for (ActivityManager.RunningAppProcessInfo appProcessInfo : activityManager.getRunningAppProcesses()) {
                if (appProcessInfo.pid == pid) {
                    if (appProcessInfo.processName.equals(context.getPackageName())) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检测App是否运行于前台
     * @param context   上下文
     * @return
     */
    public static boolean isAppRunningOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 指定进程是否正在运行
     * @param context       上下文
     * @param processName   进程名
     * @return
     */
    public static boolean isRunning(Context context, String processName){
        ActivityManager am=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processList = am.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo info:processList){
            if (info.processName.equals(processName)){
                return true;
            }
        }
        return false;
    }

}
