import java.io.FileInputStream
import java.util.Properties

plugins {
    id("catchmate.android.application")
}

val properties = Properties()
properties.load(FileInputStream(rootProject.file("local.properties")))

android {
    namespace = "com.catchmate.android"

    val kakaoNativeAppKey = properties["kakao_native_app_key"] as? String ?: ""

    defaultConfig {
        applicationId = "com.catchmate.android"
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", kakaoNativeAppKey)
        manifestPlaceholders["kakaoNativeAppKeyManifest"] = properties["kakao_native_app_key_manifest"] as String
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    implementation(libs.kakao.user)
}
