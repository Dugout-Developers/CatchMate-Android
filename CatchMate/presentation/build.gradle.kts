plugins {
    id("catchmate.android.library")
}

android {
    namespace = "com.catchmate.presentation"

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.legacy.support.v4)

    implementation(libs.circleimageview)
}
