package com.example.crossingschedule

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crossingschedule.core.ui.CrossingScheduleTheme
import com.example.crossingschedule.feature.auth.presentation.AUTH_PAGE_ROUTE
import com.example.crossingschedule.feature.auth.presentation.LoginPage
import com.example.crossingschedule.feature.auth.presentation.viewmodel.LoginViewModel
import com.example.crossingschedule.feature.auth.presentation.viewmodel.SignUpViewModel
import com.example.crossingschedule.feature.islandCreation.presentation.ISLAND_CREATION_PAGE
import com.example.crossingschedule.feature.islandCreation.presentation.IslandCreationPage
import com.example.crossingschedule.feature.islandCreation.presentation.viewmodel.IslandCreationViewModel
import com.example.crossingschedule.feature.islandSelection.presentation.ISLAND_SELECTION_PAGE
import com.example.crossingschedule.feature.islandSelection.presentation.IslandSelectionPage
import com.example.crossingschedule.feature.islandSelection.presentation.viewmodel.IslandSelectionViewModel
import com.example.crossingschedule.feature.schedule.presentation.SCHEDULE_PAGE_ROUTE
import com.example.crossingschedule.feature.schedule.presentation.SchedulePage
import com.example.crossingschedule.feature.schedule.presentation.ScheduleViewModel
import com.example.crossingschedule.feature.settings.SETTINGS_PAGE_ROUTE
import com.example.crossingschedule.feature.settings.SettingsPage
import com.example.crossingschedule.feature.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            CrossingScheduleTheme {
                NavHost(navController = navController, startDestination = AUTH_PAGE_ROUTE) {
                    composable(route = SCHEDULE_PAGE_ROUTE) { navBackStackEntry ->
                        val factory =
                            HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        val scheduleViewModel: ScheduleViewModel =
                            viewModel(ScheduleViewModel::class.java.canonicalName, factory)

                        SchedulePage(scheduleViewModel)
                    }
                    composable(route = AUTH_PAGE_ROUTE) { navBackStackEntry ->
                        val factory =
                            HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        val loginViewModel: LoginViewModel =
                            viewModel(LoginViewModel::class.java.canonicalName, factory)
                        val signUpViewModel: SignUpViewModel =
                            viewModel(SignUpViewModel::class.java.canonicalName, factory)

                        LoginPage(
                            navigateToIslandSelection = {
                                navController.navigate(ISLAND_SELECTION_PAGE) {
                                    popUpTo(route = AUTH_PAGE_ROUTE) { inclusive = true }
                                }
                            },
                            loginViewModel = loginViewModel,
                            signUpViewModel = signUpViewModel,
                        )
                    }
                    composable(route = ISLAND_SELECTION_PAGE) { navBackStackEntry ->
                        val factory =
                            HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        val islandSelectionViewModel: IslandSelectionViewModel =
                            viewModel(IslandSelectionViewModel::class.java.canonicalName, factory)

                        IslandSelectionPage(
                            islandSelectionViewModel = islandSelectionViewModel,
                            navigateToSchedule = { navController.navigate(SCHEDULE_PAGE_ROUTE) },
                            navigateToIslandCreation = { navController.navigate(ISLAND_CREATION_PAGE) }
                        )
                    }
                    composable(route = ISLAND_CREATION_PAGE) { navBackStackEntry ->
                        val factory =
                            HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        val islandCreationViewModel: IslandCreationViewModel =
                            viewModel(IslandCreationViewModel::class.java.canonicalName, factory)

                        IslandCreationPage(
                            islandCreationViewModel = islandCreationViewModel,
                            popBackStack = {navController.popBackStack()}
                        )
                    }
                    composable(route = SETTINGS_PAGE_ROUTE) { navBackStackEntry ->
                        val factory =
                            HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        val settingsViewModel: SettingsViewModel =
                            viewModel(SettingsViewModel::class.java.canonicalName, factory)

                        SettingsPage(
                            settingsViewModel = settingsViewModel
                        )
                    }
                }
            }
        }
    }
}

