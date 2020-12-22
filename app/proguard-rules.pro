#-----------针对本项目的混淆规则--------------

# 保留实体类和类成员(变量和方法)不被混淆(根据具体情况修改entity的路径)
#-keep class com.litalk.*.bean.**{*;}
#-keep class com.litalk.module.*.bean.**{*;}
#-keep class com.litalk.lib.*.bean.**{*;}
#-keep class com.litalk.comp.*.bean.**{*;}

# 保留指定包以及所含子包下的类名和类成员(变量和方法)不被混淆
#-keep class com.litalk.database.constants.**{*;}
#-keep class com.litalk.database.beanextra.**{*;}
#-keep class com.arthenica.mobileffmpeg.**{*;}
#-keep class com.squareup.wire.** { *; }
#-keep class com.opensource.svgaplayer.proto.** { *; }
#-keep class com.litalk.media.core.bean.** { *; }
#-keep class com.litalk.media.core.db.entity.** { *; }
#-keep class com.litalk.media.ui.widget.emoji.EmojiData { *; }
#-keep class com.litalk.media.ui.bean.** { *; }

# 保留指定类名和类成员(变量和方法)不被混淆
#-keep class com.litalk.comp.dynamic.manager.AVUManager{*;}
#-keep interface com.litalk.remote.util.MessageEntityConverter{*;}
#-keep class com.litalk.remote.util.MessageEntityConverterImpl{*;}