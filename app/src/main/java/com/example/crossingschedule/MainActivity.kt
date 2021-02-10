package com.example.crossingschedule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import androidx.hilt.navigation.HiltViewModelFactory
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
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    NavHost(navController = navController, startDestination = LOGIN_PAGE_ROUTE) {
                        composable(route = SCHEDULE_PAGE_ROUTE) { navBackStackEntry ->
                            val factory =
                                HiltViewModelFactory(AmbientContext.current, navBackStackEntry)
                            val scheduleViewModel: ScheduleViewModel =
                                viewModel(ScheduleViewModel::class.java.canonicalName, factory)

                            SchedulePage(scheduleViewModel)
                        }
                        composable(route = LOGIN_PAGE_ROUTE) { navBackStackEntry ->
                            val factory =
                                HiltViewModelFactory(AmbientContext.current, navBackStackEntry)
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
}

