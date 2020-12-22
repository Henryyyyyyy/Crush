package me.henry.lib_base.others.util;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;



import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 通知兼容工具类
 * <p>
 * 本类中的代码使用Android支持库中的NotificationCompatAPI。
 * 这些API允许您添加仅在较新版本Android上可用的功能，同时仍向后兼容Android4.0（API级别14）。
 * 但是，诸如内嵌回复操作等部分新功能在较旧版本上会导致发生空操作。
 * @author chenf
 */
public class NotificationCompatUtil {

    private static final String TAG = "NotificationCompatUtil";
    
    /** 参数类型-通知ID */
    public static final String EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID";

    /**
     * 通知渠道
     */
    public static class Channel {

        public Channel(String channelId, CharSequence name, int importance, int lockScreenVisibility) {
            this.channelId = channelId;
            this.name = name;
            this.importance = importance;
            this.lockScreenVisibility = lockScreenVisibility;
        }

        /** 唯一渠道ID */
        private String channelId;
        /** 用户可见名称 */
        private CharSequence name;
        /** 重要性级别 */
        private int importance;
        /** 锁定屏幕公开范围 */
        private int lockScreenVisibility;
    }

    /**
     * 创建通知
     * @param context           上下文
     * @param channel           通知渠道
     * @param icon              小图标
     * @param title             标题
     * @param text              正文文本
     * @param intent            对点按操作做出响应意图
     * @return
     */
    public static NotificationCompat.Builder createNotificationBuilder(Context context,
                                                                       Channel channel,
                                                                       int icon,
                                                                       CharSequence title,
                                                                       CharSequence text,
                                                                       Intent intent
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            validateChannel(context, channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel.channelId)
                .setSmallIcon(icon) // 小图标，这是所必需的唯一一个用户可见内容。
                .setPriority(getLowVersionPriority(channel))  // 通知优先级，优先级确定通知在Android7.1和更低版本上的干扰程度。
                .setVisibility(channel.lockScreenVisibility)   // 锁定屏幕公开范围
                .setOnlyAlertOnce(true) // 设置通知只会在通知首次出现时打断用户（通过声音、振动或视觉提示），而之后更新则不会再打断用户。
                .setLights(Color.parseColor("#3BC3FF"), 350, 350)
                .setSound(null) // 静音
                .setVibrate(new long[]{0})  // 勿震动
                ;

        // 标题，此为可选内容
        if (!TextUtils.isEmpty(title)) builder.setContentTitle(title);

        // 正文文本，此为可选内容
        if (!TextUtils.isEmpty(text)) builder.setContentText(text);

        // 设置通知的点按操作，每个通知都应该对点按操作做出响应，通常是在应用中打开对应于该通知的Activity。
        if (intent != null) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent)
                    .setAutoCancel(true);   // 在用户点按通知后自动移除通知
        }

        return builder;
    }

    /**
     * 获取低版本的优先级
     * 要支持搭载 Android 7.1（API 级别 25）或更低版本的设备，
     * 您还必须使用 NotificationCompat 类中的优先级常量针对每条通知调用 setPriority()。
     * @param channel
     * @return
     */
    private static int getLowVersionPriority(Channel channel) {
        switch (channel.importance) {
            case NotificationManager.IMPORTANCE_HIGH:
                return NotificationCompat.PRIORITY_HIGH;
            case NotificationManager.IMPORTANCE_LOW:
                return NotificationCompat.PRIORITY_LOW;
            case NotificationManager.IMPORTANCE_MIN:
                return NotificationCompat.PRIORITY_MIN;
            default:
                return NotificationCompat.PRIORITY_DEFAULT;
        }
    }

    /**
     * 验证通知渠道
     * <p>
     * 必须先创建通知渠道，然后才能在Android 8.0及更高版本上发布任何通知
     * @param context 上下文
     * @param channel 通知渠道
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void validateChannel(Context context, Channel channel) {
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channel.channelId);
        if (notificationChannel == null) {
            createChannel(context, channel);
        }
    }

    /**
     * 创建通知渠道
     * <p>
     * 反复调用这段代码也是安全的，因为创建现有通知渠道不会执行任何操作。
     * <p>
     * 注意：创建通知渠道后，您便无法更改通知行为，此时用户拥有完全控制权。不过，您仍然可以更改渠道的名称和说明。
     * @param context 上下文
     * @param channel 通知渠道
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void createChannel(Context context, Channel channel) {
        NotificationChannel notificationChannel = new NotificationChannel(channel.channelId, channel.name, channel.importance);
//        notificationChannel.setDescription(channel.description);
        notificationChannel.setLightColor(Color.parseColor("#3BC3FF"));
        notificationChannel.setSound(null, null);   // 静音
        notificationChannel.setVibrationPattern(new long[]{0}); //勿振动

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    /**
     * 显示通知
     * <p>
     * 请记得保存您传递到 NotificationManagerCompat.notify() 的通知 ID，因为如果之后您想要更新或移除通知，将需要使用这个 ID。
     * @param context      上下文
     * @param id           通知的唯一ID
     * @param notification 通知
     */
    public static void notify(Context context, int id, Notification notification) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }

    /**
     * 取消通知
     * @param context 上下文
     * @param id      通知的唯一ID
     */
    public static void cancel(Context context, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }

    /**
     * 取消所有通知
     * @param context 上下文
     */
    public static void cancelAll(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    /**
     * 判断通知权限是否打开
     * @param context
     * @return
     */
    public static boolean isNotificationEnable(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //7.0系统以上
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            return notificationManagerCompat.areNotificationsEnabled();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //4.4-7.0系统
            AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = context.getApplicationInfo();

            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;

            Class appOpsClass = null; /* Context.APP_OPS_MANAGER */

            try {
                appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);

                Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
                int value = (int) opPostNotificationValue.get(Integer.class);
                return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 跳到应用通知设置页面
     * @param context
     */
    public static void startNotificationSettingPage(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {    //8.0
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            context.startActivity(intent);
        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //5.0及以上
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
            context.startActivity(intent);
        } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {    //4.4及以上
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        } else {    //4.4以下并没有提过从app跳转到应用通知设置页面的Action，跳转到应用详情页面
            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            }
            context.startActivity(localIntent);
        }
    }

    /**
     * 获取通知开关状态
     * @param context
     * @return
     */
    public static NotificationSetting getNotificationSwitchStatus(Context context) {
        NotificationSetting switchStatus = new NotificationSetting();
        switchStatus.setMasterSwitchStatus(isNotificationEnable(context)); // 总开关状态

        // 8.0及以上系统同时需要获取通知渠道的信息
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            List<NotificationSetting.ChannelInfo> channelInfos = new ArrayList<>();

            // 遍历已有的通知渠道，获取特定的信息填充
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            for (NotificationChannel channel : notificationManager.getNotificationChannels()) {
                channelInfos.add(new NotificationSetting.ChannelInfo(channel.getId(), channel.getName().toString(), channel.getImportance(), channel.getLockscreenVisibility(), channel.getSound(), channel.shouldVibrate(), channel.canBypassDnd()));
            }

            switchStatus.setNotificationChannels(channelInfos);
        }

       // LogUtil.d(TAG, "getNotificationSwitchStatus: " + JSONUtil.toJson(switchStatus));

        return switchStatus;
    }
}
