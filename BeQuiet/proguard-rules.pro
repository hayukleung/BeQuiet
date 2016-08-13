# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/william/sdks/android-sdk/tools/proguard/proguard-android.txt
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

# gradle-retrolambda (https://github.com/evant/gradle-retrolambda)
-dontwarn java.lang.invoke.*

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# otto
-keepattributes *Annotation*
-keepclassmembers class ** {
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ProGuard configurations for Bugtags
-keepattributes LineNumberTable,SourceFile

-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient

# sqlcipher
-keep class net.sqlcipher.** {*;}

# mina
-keep class org.apache.mina.** {*;}
-keep class android.support.design.widget.TabLayout {*;}
-keep class android.support.design.widget.TabLayout$** {*;}

-dontwarn **
