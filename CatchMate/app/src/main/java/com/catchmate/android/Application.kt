package com.catchmate.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        // kakao sdk 초기화
        KakaoSdk.init(this, "${BuildConfig.KAKAO_NATIVE_APP_KEY}")
    }
}
