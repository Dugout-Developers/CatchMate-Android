# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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
# Keep domain model classes used in presentation
-keep class com.catchmate.domain.model.** { *; }
-keep enum com.catchmate.domain.model.enumclass.** { *; }

# Generated missing rules
-dontwarn com.catchmate.domain.model.board.Board
-dontwarn com.catchmate.domain.model.board.GetBoardListResponse
-dontwarn com.catchmate.domain.model.enroll.GameInfo
-dontwarn com.catchmate.domain.model.enumclass.Club
-dontwarn com.catchmate.domain.model.user.GetUnreadInfoResponse
-dontwarn com.catchmate.domain.model.user.GetUserProfileResponse
-dontwarn java.lang.invoke.StringConcatFactory