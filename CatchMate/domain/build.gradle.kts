plugins {
    id("catchmate.android.library")
    id("kotlin-parcelize")
}

android {
    namespace = "com.catchmate.domain"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.stomp)
}
