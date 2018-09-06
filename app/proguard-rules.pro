# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn
# 代码压缩级别
-optimizationpasses 5

# 使用大小写混合
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
# 混淆时预校验
-dontpreverify
# 记录日志
-verbose
# 混淆时所用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# AirFares
#-keep class com.tempus.airfares.view.widget.** { *;}
#-keep class com.tempus.airfares.base.** { *; }
-keep class com.tempus.tourism.ui.** { *; }
#-keep class com.tempus.airfares.base.utils.js.** { *; }
# 保留sdk系统自带的一些内容
#@proguard_debug_start
# ------------------ Keep LineNumbers and properties ---------------- #
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-renamesourcefileattribute TbsSdkJava
-keepattributes SourceFile,LineNumberTable
#@proguard_debug_end
# Native
-keepclasseswithmembernames class * {
    native <methods>;
}

# 自定义View
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
 #如果你的项目中用到了webview的复杂操作 ，最好加入
-keepclassmembers class * extends android.webkit.WebChromeClient {
   public void openFileChooser(...);
}

# Enum
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class android.support.v4.view.** {*; }
-keep class * extends android.app.Dialog
-ignorewarnings
-keep class com.tencent.android.tpush.**  {* ;}
-keep class com.tencent.mid.**  {* ;}
-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**
-keep class com.bugtags.library.** {*;}
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
-dontwarn com.bugtags.library.vender.**
-dontwarn com.bugtags.library.**

-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
# 组件
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

# R
-keep public class com.tempus.tourism.R$*{
   public static final int *;
}
#1.support-v7-appcompat
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

#2.rxjava2
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#3.retrolambda
-dontwarn java.lang.invoke.*

#4.support-v4
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }

# Support library
-keep class android.support.** { *; }
-keep interface android.support.** { *; }

#For Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-ignorewarnings

# OkHttp
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**

#OkHttp3
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

#Java RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson 下面替换成自己的实体类
-keep class com.tempus.wallet.model.** { *; }

#For Glide
-ignorewarnings
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.** { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule

#For Rxlifecycle2
-ignorewarnings
-dontwarn com.trello.rxlifecycle2.components.**
-keep class com.trello.rxlifecycle2.components.** { *; }

# butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

#EventBus
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
# JSONObject
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

# Serializable Parcelable
-keepnames class * implements java.io.Serializable
-keep public class * implements android.os.Parcelable {*;}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# JavaScriptInterface
-keepattributes *JavascriptInterface*
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
 }
