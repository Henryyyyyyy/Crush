##-----------第三方类库的混淆规则--------------
#
#-dontwarn com.alibaba.**
#-dontwarn com.android.**
#-dontwarn org.apache.**
#-dontwarn com.google.**
#-dontwarn com.taobao.**
#-dontwarn com.umeng.**
#-dontwarn org.jivesoftware.**
#-dontwarn com.tencent.**
#-dontwarn com.sina.**
#-dontwarn com.alipay.**
#-dontwarn ta.utdid2.**
#-dontwarn org.json.alipay.**
#-dontwarn butterknife.internal.**
#-dontwarn com.kyleduo.**
#-dontwarn com.bigkoo.**
#-dontwarn okio.**
#-dontwarn com.squareup.okhttp.**
#-dontwarn org.android.**
#-dontwarn com.hyphenate.chat.**
#-dontwarn com.hisun.**
#-dontwarn com.unionpay.**
#-dontwarn rx.internal.**
#-dontwarn com.tbruyelle.**
#-dontwarn com.antfortune.freeline.**
#-dontwarn com.tbruyelle.**
#-dontwarn io.reactivex.**
#-dontwarn com.github.nkzawa.**
#-dontwarn me.imid.**
#-dontwarn tv.danmaku.**
#-dontwarn javax.**
#-dontwarn org.eclipe.**
#-dontwarn org.jetbrains.**
#-dontwarn kotlin.**
#-dontwarn com.yalantis.ucrop**
#-dontwarn anet.channel.**
#-dontwarn org.reactivestreams.**
#-dontwarn okhttp3.**
#
#-keep class com.alibaba.**{*;}
#-keep class com.google.**{*;}
#-keep class com.taobao.**{*;}
#-keep class org.apache.**{*;}
#-keep class com.android.**{*;}
#-keep class com.umeng.**{*;}
#-keep class org.jivesoftware.** {*;}
#-keep class anet.channel.** {*;}
#-keep class anet.**. {*;}
#-keep class org.reactivestreams.**. {*;}
#-keep class com.tencent.**{*;}
#-keep class com.sina.**{*;}
#-keep class com.alipay.**{*;}
#-keep class ta.utdid2.**{*;}
#-keep class org.json.alipay.**{*;}
#-keep class butterknife.**{*;}
#-keep class com.kyleduo.**{*;}
#-keep class com.bigkoo.**{*;}
#-keep class com.tencent.mm.sdk.** {*;}
#-keep class okio.** {*;}
#-keep class com.squareup.okhttp.** { *;}
#-keep class org.android.** { *;}
#-keep class com.hyphenate.chat.** { *;}
#-keep class com.hisun.** { *;}
#-keep class com.unionpay.** { *;}
#-keep class rx.internal.** { *;}
#-keep class io.reactivex.** { *;}
#-keep class com.tbruyelle.** { *;}
#-keep class com.antfortune.freeline.** { *;}
#-keep class com.github.nkzawa.** {*;}
#-keep class me.imid.**{*;}
#-keep class tv.danmaku.**{*;}
#-keep class javax.**{*;}
#-keep class org.eclipe.**{*;}
#-keep class org.jetbrains.**{*;}
#
#-keep class com.tmall.**{*;}
#-keep class kotlin.**{*;}
#-keep class android.widget.Space.**{*;}
#-keep class okhttp3.**{*;}
#
##eventbus
#-keepattributes *Annotation*
#-keepclassmembers class ** {
#@de.greenrobot.event.Subscribe <methods>;
#}
#-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#-keepclassmembers class ** {
#@org.greenrobot.eventbus.Subscribe <methods>;
#}
#-keep enum de.greenrobot.event.ThreadMode { *; }
#
##okhttp
#-dontwarn com.squareup.okhttp3.**
#-keep class com.squareup.okhttp3.** { *;}
#-dontwarn okio.**
#
##glide
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
#**[] $VALUES;
#public *;
#}
#-keep class com.bumptech.**{*;}
#
##retrofit
#-dontwarn okio.**
#-dontwarn javax.annotation.**
##解决使用Retrofit+rxJava联网时，在6.0系统出现java.lang.InternalError奔溃的问题:http://blog.csdn.net/mp624183768/article/details/79242147
#-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
#long producerIndex;
#long consumerIndex;
#}
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
#rx.internal.util.atomic.LinkedQueueNode producerNode;
#}
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
#rx.internal.util.atomic.LinkedQueueNode consumerNode;
#}
#
##butterknife
#-keep class butterknife.** { *; }
#-dontwarn butterknife.internal.**
#-keep class **$$ViewBinder { *; }
#-keepclasseswithmembernames class * {
#@butterknife.* <fields>;
#}
#-keepclasseswithmembernames class * {
#@butterknife.* <methods>;
#}
#
##greendao
#-keep class org.greenrobot.greendao.**{*;}
#-keep public interface org.greenrobot.greendao.**
#-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
#public static java.lang.String TABLENAME;
#}
#-keep class **$Properties
#-keep class net.sqlcipher.database.**{*;}
#-keep public interface net.sqlcipher.database.**
#-dontwarn net.sqlcipher.database.**
#-dontwarn org.greenrobot.greendao.**
#
##友盟分享
#-dontshrink
#-dontoptimize
#-dontwarn com.google.android.maps.**
#-dontwarn android.webkit.WebView
#-dontwarn com.umeng.**
#-dontwarn com.tencent.weibo.sdk.**
#-dontwarn com.facebook.**
#-keep public class javax.**
#-keep public class android.webkit.**
#-dontwarn android.support.v4.**
#-keep enum com.facebook.**
#-keepattributes Exceptions,InnerClasses,Signature
#-keepattributes *Annotation*
#-keepattributes SourceFile,LineNumberTable
#
#-keep public interface com.facebook.**
#-keep public interface com.tencent.**
#-keep public interface com.umeng.socialize.**
#-keep public interface com.umeng.socialize.sensor.**
#-keep public interface com.umeng.scrshot.**
#
#-keep public class com.umeng.socialize.* {*;}
#
#-keep class com.facebook.**
#-keep class com.facebook.** { *; }
#-keep class com.umeng.scrshot.**
#-keep public class com.tencent.** {*;}
#-keep class com.umeng.socialize.sensor.**
#-keep class com.umeng.socialize.handler.**
#-keep class com.umeng.socialize.handler.*
#-keep class com.umeng.weixin.handler.**
#-keep class com.umeng.weixin.handler.*
#-keep class com.umeng.qq.handler.**
#-keep class com.umeng.qq.handler.*
#-keep class UMMoreHandler{*;}
#-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
#-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
#-keep class im.yixin.sdk.api.YXMessage {*;}
#-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
#-keep class com.tencent.mm.sdk.** {
#*;
#}
#-keep class com.tencent.mm.opensdk.** {
#*;
#}
#-keep class com.tencent.wxop.** {
#*;
#}
#-keep class com.tencent.mm.sdk.** {
#*;
#}
#-dontwarn twitter4j.**
#-keep class twitter4j.** { *; }
#
#-keep class com.tencent.** {*;}
#-dontwarn com.tencent.**
#-keep class com.kakao.** {*;}
#-dontwarn com.kakao.**
#-keep public class com.umeng.com.umeng.soexample.R$*{
# public static final int *;
#}
#-keep public class com.linkedin.android.mobilesdk.R$*{
# public static final int *;
#}
#-keepclassmembers enum * {
# public static **[] values();
# public static ** valueOf(java.lang.String);
#}
#
#-keep class com.tencent.open.TDialog$*
#-keep class com.tencent.open.TDialog$* {*;}
#-keep class com.tencent.open.PKDialog
#-keep class com.tencent.open.PKDialog {*;}
#-keep class com.tencent.open.PKDialog$*
#-keep class com.tencent.open.PKDialog$* {*;}
#-keep class com.umeng.socialize.impl.ImageImpl {*;}
#-keep class com.sina.** {*;}
#-dontwarn com.sina.**
#-keep class  com.alipay.share.sdk.** {
#*;
#}
#
#-keepnames class * implements android.os.Parcelable {
# public static final ** CREATOR;
#}
#
#-keep class com.linkedin.** { *; }
#-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
#-keepattributes Signature
#
## 友盟统计
#-keep class com.umeng.commonsdk.** {*;}
#-keepclassmembers class * {
#public <init> (org.json.JSONObject);
#}
#-keep public class [com.zongxueguan.naochanle_android].R$*{
#public static final int *;
#}
#-keepclassmembers enum * {
#public static **[] values();
#public static ** valueOf(java.lang.String);
#}
#
##友盟推送
#-dontwarn com.umeng.**
#-dontwarn com.taobao.**
#-dontwarn anet.channel.**
#-dontwarn anetwork.channel.**
#-dontwarn org.android.**
#-dontwarn org.apache.thrift.**
#-dontwarn com.xiaomi.**
#-dontwarn com.huawei.**
#-dontwarn com.meizu.**
#
#-keepattributes *Annotation*
#
#-keep class com.taobao.** {*;}
#-keep class org.android.** {*;}
#-keep class anet.channel.** {*;}
#-keep class com.umeng.** {*;}
#-keep class com.xiaomi.** {*;}
#-keep class com.huawei.** {*;}
#-keep class com.meizu.** {*;}
#-keep class org.apache.thrift.** {*;}
#
#-keep class com.alibaba.sdk.android.**{*;}
#-keep class com.ut.**{*;}
#-keep class com.ta.**{*;}
#
#-keep public class **.R$*{
#   public static final int *;
#}
#-dontwarn com.alibaba.sdk.android.utils.**
#-keepclasseswithmembernames class ** {
#    native <methods>;
#}
#-keepattributes Signature
#-keep class sun.misc.Unsafe { *; }
#-keep class com.taobao.** {*;}
#-keep class com.alibaba.** {*;}
#-keep class com.alipay.** {*;}
#-keep class com.ut.** {*;}
#-keep class com.ta.** {*;}
#-keep class anet.**{*;}
#-keep class anetwork.**{*;}
#-keep class org.android.spdy.**{*;}
#-keep class org.android.agoo.**{*;}
#-keep class android.os.**{*;}
#-keep class org.json.**{*;}
#-dontwarn com.taobao.**
#-dontwarn com.alibaba.**
#-dontwarn com.alipay.**
#-dontwarn anet.**
#-dontwarn org.android.spdy.**
#-dontwarn org.android.agoo.**
#-dontwarn anetwork.**
#-dontwarn com.ut.**
#-dontwarn com.ta.**
#
##高德地图混淆配置
##3D 地图 V5.0.0之前：
#-keep   class com.amap.api.maps.**{*;}
#-keep   class com.autonavi.amap.mapcore.*{*;}
#-keep   class com.amap.api.trace.**{*;}
##3D 地图 V5.0.0之后：
#-keep   class com.amap.api.maps.**{*;}
#-keep   class com.autonavi.**{*;}
#-keep   class com.amap.api.trace.**{*;}
##定位
#-keep class com.amap.api.location.**{*;}
#-keep class com.amap.api.fence.**{*;}
#-keep class com.loc.**{*;}
#-keep class com.autonavi.aps.amapapi.model.**{*;}
##搜索
#-keep   class com.amap.api.services.**{*;}
##2D地图
#-keep class com.amap.api.maps2d.**{*;}
#-keep class com.amap.api.mapcore2d.**{*;}
##导航
#-keep class com.amap.api.navi.**{*;}
#-keep class com.autonavi.**{*;}
##海外地图
#-keep class com.mapbox.mapboxsdk.**{ *; }
#
##BRVAH
#-keep class com.chad.library.adapter.** {
#*;
#}
#-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
#-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
#-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
#     <init>(...);}
#
##WebRTC
#-dontwarn org.webrtc.NetworkMonitorAutoDetect
#-dontwarn android.net.Network
#-keep class org.webrtc.** { *; }
#
##OPPO Push
#-keep public class * extends android.app.Service
#-keep class com.heytap.msp.** { *;}
#
##VIVO Push
#-dontwarn com.vivo.push.**
#-keep class com.vivo.push.**{*; }
#-keep class com.vivo.vms.**{*; }
#-keep class com.litalk.lib.vivo.push.PushMessageReceiverImpl{*;}
#
##HuaWei Push
##-ignorewarning
#-keepattributes *Annotation*
#-keepattributes Exceptions
#-keepattributes InnerClasses
#-keepattributes Signature
#-keepattributes SourceFile,LineNumberTable
#-keep class com.hianalytics.android.**{*;}
#-keep class com.huawei.updatesdk.**{*;}
#-keep class com.huawei.hms.**{*;}
#-keep class com.huawei.android.hms.agent.**{*;}
#
##Mi Push
# -keep class com.litalk.lib.mi.push.MiPushReceiver {*;}
#
##Arouter
#-keep public class com.alibaba.android.arouter.routes.**{*;}
#-keep public class com.alibaba.android.arouter.facade.**{*;}
#-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
#
## 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
#-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
#
## 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
## -keep class * implements com.alibaba.android.arouter.facade.template.IProvider
#
#-keep class com.danikula.videocache.**{*;}
#
##mp4parser
#-keep public class com.mp4parser.** {*;}
#-keep public class org.mp4parser.** {*;}
#-keep public class com.coremedia.** {*;}
#-keep public class com.googlecode.** {*;}
#-keep public class org.aspectj.** {*;}
#
##webrtc
#-keep public class org.webrtc.** {*;}
#-keep public class org.whispersystems.libwebrtc_android {*;}
#
## xingge
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep class com.tencent.android.tpush.** {*;}
#-keep class com.tencent.mid.** {*;}
#-keep class com.qq.taf.jce.** {*;}
#-keep class com.tencent.bigdata.** {*;}
#
## meizu
#-dontwarn com.meizu.cloud.pushsdk.**
#-keep class com.meizu.cloud.pushsdk.**{*;}
#
## ucrop
#-dontwarn com.yalantis.ucrop**
#-keep class com.yalantis.ucrop** { *; }
#-keep interface com.yalantis.ucrop** { *; }
#
#-keep class com.alibaba.android.vlayout.** {*;}
#
## MapStruct
#-keep class org.mapstruct.** { *; }
#-keep interface org.mapstruct.** { *; }