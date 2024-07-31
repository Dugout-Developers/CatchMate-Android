import java.io.FileInputStream
import java.util.Properties

plugins {
    id("catchmate.android.library")
    id("catchmate.android.hilt")
    id("catchmate.kotlin.hilt")
}

val properties = Properties()
properties.load(FileInputStream(rootProject.file("local.properties")))

android {
    namespace = "com.catchmate.data"

    val googleWebClientId = properties["google_web_client_id"] as? String ?: ""

    defaultConfig {
        buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", googleWebClientId)
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.kakao.user)
    implementation(libs.naver.user)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
}
