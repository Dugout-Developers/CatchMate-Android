plugins {
    id("catchmate.android.library")
    id("catchmate.android.hilt")
    id("catchmate.kotlin.hilt")
}

android {
    namespace = "com.catchmate.data"
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.kakao.user)
}
