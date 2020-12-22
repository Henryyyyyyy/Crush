package me.henry.lib_base.others.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * 版本工具类
 * @author chenf
 */
public class VersionUtil {

    /**
     * 获取App版本
     * @param context 上下文
     * @return
     */
    public static String getAppVersion(Context context) {
        String version = "1.0.0";
        try {
            version = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 版本对比工具
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     * @param version1    前一个版本
     * @param version2    后一个版本
     * @return
     */
    public static int compareVersion(String version1, String version2){
        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
        for(int i = 0 ; i<versionArray1.length ; i++){ //如果位数只有一位则自动补零（防止出现一个是04，一个是5 直接以长度比较）
            if(versionArray1[i].length() == 1){
                versionArray1[i] = "0" + versionArray1[i];
            }
        }
        String[] versionArray2 = version2.split("\\.");
        for(int i = 0 ; i<versionArray2.length ; i++){//如果位数只有一位则自动补零
            if(versionArray2[i].length() == 1){
                versionArray2[i] = "0" + versionArray2[i];
            }
        }
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }
}
