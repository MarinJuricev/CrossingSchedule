package com.example.crossingschedule

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.crossingschedule.core.ui.CrossingScheduleTheme
import com.example.crossingschedule.feature.login.presentation.LOGIN_PAGE_ROUTE
import com.example.crossingschedule.feature.login.presentation.LoginPage
import com.example.crossingschedule.feature.schedule.presentation.SCHEDULE_PAGE_ROUTE
import com.example.crossingschedule.feature.schedule.presentation.SchedulePage
import com.example.crossingschedule.feature.schedule.presentation.ScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val scheduleViewModel: ScheduleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            CrossingScheduleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    NavHost(navController = navController, startDestination = LOGIN_PAGE_ROUTE) {
                        composable(route = SCHEDULE_PAGE_ROUTE) {
                            SchedulePage(scheduleViewModel)
                        }
                        composable(route = LOGIN_PAGE_ROUTE) {
                            LoginPage(
                                navigateToSchedule = { navController.navigate(SCHEDULE_PAGE_ROUTE) }
                            )
                        }
                    }
                }
            }
        }
    }
}

