package com.example.inventory.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.inventory.ui.createItem.CreateAction
import com.example.inventory.ui.createItem.CreateItemRoute
import com.example.inventory.ui.createItem.CreateItemViewModel
import com.example.inventory.ui.createItem.formScreens.CreateItemStartScreen
import com.example.inventory.ui.createItem.formScreens.ImagePreviewScreen
import com.example.inventory.ui.createItem.formScreens.ItemDetailsScreen
import com.example.inventory.ui.home.HomeRoute


@Composable
fun AppNavigation(
    vM : CreateItemViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    val uiStateShared by vM.uiState.collectAsStateWithLifecycle()
    val onActionShared: (CreateAction) -> Unit = vM::onAction

    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME
    ){
        composable(AppDestinations.HOME){
            HomeRoute(
                onCreateNewItemClick = {
                    navController.navigate(AppDestinations.CREATE_FLOW)
                }
            )
        }
        navigation(
            startDestination = AppDestinations.STEP_1_SOURCE,
            route = AppDestinations.CREATE_FLOW
        ){
            composable(AppDestinations.STEP_1_SOURCE) {
                CreateItemStartScreen(
                    onAction = onActionShared,
                    onChooseImgClick = {
                        navController.navigate(AppDestinations.STEP_2_PREVIEW)
                    }
                )
            }
            composable(AppDestinations.STEP_2_PREVIEW) {
                ImagePreviewScreen(
                    uiState = uiStateShared,
                    onAction = onActionShared,
                    onContinueClick = {
                        navController.navigate(AppDestinations.STEP_3_DETAILS)
                    }
                )
            }
            composable(AppDestinations.STEP_3_DETAILS) {
                ItemDetailsScreen(
                    uiState = uiStateShared,
                    onAction = onActionShared,
                    onCreateClick = {
                        navController.navigate(AppDestinations.HOME) {
                            popUpTo(AppDestinations.CREATE_FLOW) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

        }



    }

}