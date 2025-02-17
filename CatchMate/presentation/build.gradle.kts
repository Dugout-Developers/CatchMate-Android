plugins {
    id("catchmate.android.library")
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.catchmate.presentation"

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.material)
    implementation(libs.legacy.support.v4)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.circleimageview)
    implementation(libs.glide)
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.stomp)
}
