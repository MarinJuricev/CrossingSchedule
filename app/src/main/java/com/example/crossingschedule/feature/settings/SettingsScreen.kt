package com.example.crossingschedule.feature.settings

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle

const val SETTINGS_PAGE_ROUTE = "SETTINGS_PAGE_ROUTE"

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {

//    val dataset by settingsViewModel.observeConstantStream().collectAsState(0)

    val lifecycleOwner = LocalLifecycleOwner.current
    val locationFlowLifecycleAware = remember(settingsViewModel, lifecycleOwner) {
        settingsViewModel
            .observeConstantStream()
            .flowWithLifecycle(lifecycleOwner.lifecycle)
    }

    val lifeycleAwareDataset by locationFlowLifecycleAware.collectAsState(initial = 0)

    Column {
//        Text(
//            text = "Regular collect $dataset",
//            fontSize = 30.sp
//        )
        Text(
            text = "LifeCycleAware: $lifeycleAwareDataset",
            fontSize = 30.sp
        )
    }

//    Log.d("SettingScreen - regular", "SettingsScreen: $dataset")
    Log.d("SettingScreen - aware", "SettingsScreen: $lifeycleAwareDataset")
}