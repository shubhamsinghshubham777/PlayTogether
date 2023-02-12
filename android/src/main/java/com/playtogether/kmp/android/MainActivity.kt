package com.playtogether.kmp.android

import android.os.Bundle
import com.playtogether.kmp.PTApp
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent

class MainActivity : PreComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PTApp()
        }
    }
}
