package com.smp.hiltsample

import android.app.Application
import com.smp.hiltsample.auth.UserAuthSession
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication: Application() {

    @Inject
    lateinit var userAuthSession: UserAuthSession

    override fun onCreate() {
        super.onCreate()


    }

}