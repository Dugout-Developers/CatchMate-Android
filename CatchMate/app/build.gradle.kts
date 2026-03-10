import com.android.build.api.dsl.Packaging
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("catchmate.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.kotlin.plugin.compose")
}

val properties = Properties()
properties.load(FileInputStream(rootProject.file("local.properties")))

android {
    namespace = "com.catchmate.android"

    val kakaoNativeAppKey = properties["kakao_native_app_key"] as? String ?: ""
    val naverClientId = properties["naver_client_id"] as? String ?: ""
    val naverClientSecret = properties["naver_client_secret"] as? String ?: ""

    defaultConfig {
        applicationId = "com.catchmate.android"
        versionCode = 20250416
        versionName = "1.2.0"

        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", kakaoNativeAppKey)
        buildConfigField("String", "NAVER_CLIENT_ID", naverClientId)
        buildConfigField("String", "NAVER_CLIENT_SECRET", naverClientSecret)
        manifestPlaceholders["kakaoNativeAppKeyManifest"] = properties["kakao_native_app_key_manifest"] as String
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    packaging {
        resources {
            excludes.add("META-INF/DEPENDENCIES")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules-release.pro",
            )
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    implementation(libs.kakao.user)
    implementation(libs.naver.user)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics.ndk)
    implementation(libs.androidx.material3)
}
