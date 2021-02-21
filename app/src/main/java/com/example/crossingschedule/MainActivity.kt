package com.example.crossingschedule

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.crossingschedule.core.ui.CrossingScheduleTheme
import com.example.crossingschedule.feature.login.presentation.LOGIN_PAGE_ROUTE
import com.example.crossingschedule.feature.login.presentation.LoginPage
import com.example.crossingschedule.feature.login.presentation.LoginViewModel
import com.example.crossingschedule.feature.schedule.presentation.SCHEDULE_PAGE_ROUTE
import com.example.crossingschedule.feature.schedule.presentation.SchedulePage
import com.example.crossingschedule.feature.schedule.presentation.ScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            CrossingScheduleTheme {
                NavHost(navController = navController, startDestination = SCHEDULE_PAGE_ROUTE) {
                    composable(route = SCHEDULE_PAGE_ROUTE) { navBackStackEntry ->
                        val factory =
                            HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        val scheduleViewModel: ScheduleViewModel =
                            viewModel(ScheduleViewModel::class.java.canonicalName, factory)

                        SchedulePage(scheduleViewModel)
                    }
                    composable(route = LOGIN_PAGE_ROUTE) { navBackStackEntry ->
                        val factory =
                            HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        val loginViewModel: LoginViewModel =
                            viewModel(LoginViewModel::class.java.canonicalName, factory)

                        LoginPage(
                            navigateToSchedule = {
                                navController.navigate(SCHEDULE_PAGE_ROUTE) {
                                    popUpTo(route = LOGIN_PAGE_ROUTE) { inclusive = true }
                                }
                            },
                            loginViewModel = loginViewModel
                        )
                    }
                }
            }
        }
    }
}

