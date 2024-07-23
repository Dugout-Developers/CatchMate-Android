plugins {
    id("catchmate.android.application")
}

android {
    namespace = "com.catchmate.android"

    defaultConfig {
        applicationId = "com.catchmate.android"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))
}
