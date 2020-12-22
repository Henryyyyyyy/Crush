package me.henry.lib_base.others.util;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 通知开关状态
 */
public class NotificationSetting implements Parcelable {

    /** 总开关状态 */
    private boolean masterSwitchStatus;
    /** 渠道信息列表 */
    private List<ChannelInfo> notificationChannels;

    public NotificationSetting() {
    }

    protected NotificationSetting(Parcel in) {
        masterSwitchStatus = in.readByte() != 0;
        notificationChannels = in.createTypedArrayList(ChannelInfo.CREATOR);
    }

    public boolean isMasterSwitchStatus() {
        return masterSwitchStatus;
    }

    public void setMasterSwitchStatus(boolean masterSwitchStatus) {
        this.masterSwitchStatus = masterSwitchStatus;
    }

    public List<ChannelInfo> getNotificationChannels() {
        return notificationChannels;
    }

    public void setNotificationChannels(List<ChannelInfo> notificationChannels) {
        this.notificationChannels = notificationChannels;
    }

    public static final Creator<NotificationSetting> CREATOR = new Creator<NotificationSetting>() {
        @Override
        public NotificationSetting createFromParcel(Parcel in) {
            return new NotificationSetting(in);
        }

        @Override
        public NotificationSetting[] newArray(int size) {
            return new NotificationSetting[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (masterSwitchStatus ? 1 : 0));
        dest.writeTypedList(notificationChannels);
    }

    /**
     * 渠道信息
     */
    public static class ChannelInfo implements Parcelable {
        /** 唯一渠道ID */
        private String mId;
        /** 用户可见名称 */
        private String mName;
        /** 重要性级别 */
        private int mImportance;
        /** 锁屏通知 */
        private int mLockscreenVisibility;
        /** 铃声 */
        private Uri mSound;
        /** 振动 */
        private boolean mVibrationEnabled;
        /** 允许打扰 */
        private boolean mBypassDnd;

        public ChannelInfo(String mId, String mName, int mImportance, int mLockscreenVisibility, Uri mSound, boolean mVibrationEnabled, boolean mBypassDnd) {
            this.mId = mId;
            this.mName = mName;
            this.mImportance = mImportance;
            this.mLockscreenVisibility = mLockscreenVisibility;
            this.mSound = mSound;
            this.mVibrationEnabled = mVibrationEnabled;
            this.mBypassDnd = mBypassDnd;
        }

        protected ChannelInfo(Parcel in) {
            mId = in.readString();
            mName = in.readString();
            mImportance = in.readInt();
            mLockscreenVisibility = in.readInt();
            mSound = in.readParcelable(Uri.class.getClassLoader());
            mVibrationEnabled = in.readByte() != 0;
            mBypassDnd = in.readByte() != 0;
        }

        public String getId() {
            return mId;
        }

        public void setId(String mId) {
            this.mId = mId;
        }

        public String getName() {
            return mName;
        }

        public void setName(String mName) {
            this.mName = mName;
        }

        public int getImportance() {
            return mImportance;
        }

        public void setImportance(int mImportance) {
            this.mImportance = mImportance;
        }

        public int getLockscreenVisibility() {
            return mLockscreenVisibility;
        }

        public void setLockscreenVisibility(int mLockscreenVisibility) {
            this.mLockscreenVisibility = mLockscreenVisibility;
        }

        public Uri getSound() {
            return mSound;
        }

        public void setSound(Uri mSound) {
            this.mSound = mSound;
        }

        public boolean isVibrationEnabled() {
            return mVibrationEnabled;
        }

        public void setVibrationEnabled(boolean mVibrationEnabled) {
            this.mVibrationEnabled = mVibrationEnabled;
        }

        public boolean isBypassDnd() {
            return mBypassDnd;
        }

        public void setBypassDnd(boolean mBypassDnd) {
            this.mBypassDnd = mBypassDnd;
        }

        public static final Creator<ChannelInfo> CREATOR = new Creator<ChannelInfo>() {
            @Override
            public ChannelInfo createFromParcel(Parcel in) {
                return new ChannelInfo(in);
            }

            @Override
            public ChannelInfo[] newArray(int size) {
                return new ChannelInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mId);
            dest.writeString(mName);
            dest.writeInt(mImportance);
            dest.writeInt(mLockscreenVisibility);
            dest.writeParcelable(mSound, flags);
            dest.writeByte((byte) (mVibrationEnabled ? 1 : 0));
            dest.writeByte((byte) (mBypassDnd ? 1 : 0));
        }
    }
}
