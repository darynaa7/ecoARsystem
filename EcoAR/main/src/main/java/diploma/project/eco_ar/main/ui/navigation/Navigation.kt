package diploma.project.eco_ar.main.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import diploma.project.eco_ar.core.data.repository.ReportsRepository
import diploma.project.eco_ar.core.ui.miscellaneous.ListenToEvents
import diploma.project.eco_ar.core.ui.miscellaneous.LoaderController
import diploma.project.eco_ar.core.ui.navigation.ChildrenNavDisplay
import diploma.project.eco_ar.core.ui.navigation.getMainRouteTransitionSpecForMainRouteFour
import diploma.project.eco_ar.core.ui.navigation.getMainRouteTransitionSpecForMainRouteOne
import diploma.project.eco_ar.core.ui.navigation.getMainRouteTransitionSpecForMainRouteThree
import diploma.project.eco_ar.core.ui.navigation.getMainRouteTransitionSpecForMainRouteTwo
import diploma.project.eco_ar.core.ui.navigation.nested.NestedRoutesBackStack
import diploma.project.eco_ar.core.ui.navigation.routes.AuthRoutes
import diploma.project.eco_ar.core.ui.navigation.routes.MainRoutes
import diploma.project.eco_ar.feature_ar.ui.screens.ARScreen
import diploma.project.eco_ar.feature_ar.ui.viewModel.AREvent
import diploma.project.eco_ar.feature_ar.ui.viewModel.ARUiAction
import diploma.project.eco_ar.feature_ar.ui.viewModel.ARViewModel
import diploma.project.eco_ar.feature_home.ui.screens.HomeScreen
import diploma.project.eco_ar.feature_home.ui.viewModel.HomeEvent
import diploma.project.eco_ar.feature_home.ui.viewModel.HomeUiAction
import diploma.project.eco_ar.feature_home.ui.viewModel.HomeViewModel
import diploma.project.eco_ar.feature_map.ui.screens.MapScreen
import diploma.project.eco_ar.feature_map.ui.viewModel.MapViewModel
import diploma.project.eco_ar.feature_profile.ui.screens.PermissionsScreen
import diploma.project.eco_ar.feature_profile.ui.screens.ProfileEditScreen
import diploma.project.eco_ar.feature_profile.ui.screens.ProfileScreen
import diploma.project.eco_ar.feature_profile.ui.screens.ReportsScreen
import diploma.project.eco_ar.feature_profile.ui.screens.SettingsScreen
import diploma.project.eco_ar.feature_profile.ui.viewModel.profile.ProfileUiAction
import diploma.project.eco_ar.feature_profile.ui.viewModel.profile.ProfileViewModel
import diploma.project.eco_ar.feature_profile.ui.viewModel.profileEdit.ProfileEditEvent
import diploma.project.eco_ar.feature_profile.ui.viewModel.profileEdit.ProfileEditUiAction
import diploma.project.eco_ar.feature_profile.ui.viewModel.profileEdit.ProfileEditViewModel
import diploma.project.eco_ar.feature_profile.ui.viewModel.reports.ReportsEvent
import diploma.project.eco_ar.feature_profile.ui.viewModel.reports.ReportsUiAction
import diploma.project.eco_ar.feature_profile.ui.viewModel.reports.ReportsViewModel
import diploma.project.eco_ar.feature_profile.ui.viewModel.settings.SettingsEvent
import diploma.project.eco_ar.feature_profile.ui.viewModel.settings.SettingsUiAction
import diploma.project.eco_ar.feature_profile.ui.viewModel.settings.SettingsViewModel
import diploma.project.eco_ar.feature_statistics.ui.screens.StatisticsScreen
import diploma.project.eco_ar.feature_statistics.ui.viewModel.StatisticsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

fun EntryProviderScope<NavKey>.addMainRoutes(backStack: NestedRoutesBackStack) {
    entry<MainRoutes.RouteOne>(
        metadata = getMainRouteTransitionSpecForMainRouteOne()
    ) { parentRoute ->
        ChildrenNavDisplay(
            backStack = backStack,
            parent = parentRoute,
            entryProvider = entryProvider {
                entry<MainRoutes.RouteOne.Home> {
                    val viewModel = koinViewModel<HomeViewModel>()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    ListenToEvents(viewModel.events) { event, _, onError ->
                        when (event) {
                            is HomeEvent.FetchReportFailure -> onError(event.message)
                        }
                    }

                    HomeScreen(
                        uiState = uiState,
                        onUiAction = { action ->
                            when (action) {
                                HomeUiAction.NavigateToAR -> backStack.push(MainRoutes.RouteOne.ARCamera, parentRoute)
                            }
                        }
                    )
                }
                entry<MainRoutes.RouteOne.ARCamera> {
                    val viewModel = koinViewModel<ARViewModel>()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    ListenToEvents(viewModel.events) { event, _, onError ->
                        when (event) {
                            is AREvent.FetchReportFailure -> onError(event.message)
                        }
                    }

                    ARScreen(
                        uiState = uiState,
                        onAction = viewModel::onAction,
                        onUiAction = { action ->
                            when (action) {
                                is ARUiAction.NavigateBack -> backStack.pop(parentRoute)
                            }
                        }
                    )
                }
            }
        )
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    entry<MainRoutes.RouteTwo>(
        metadata = getMainRouteTransitionSpecForMainRouteTwo()
    ) { parentRoute ->
        ChildrenNavDisplay(
            backStack = backStack,
            parent = parentRoute,
            entryProvider = entryProvider {
                entry<MainRoutes.RouteTwo.Map> {
                    val viewModel = koinViewModel<MapViewModel>()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    MapScreen(
                        uiState = uiState,
                        onAction = viewModel::onAction
                    )
                }
            }
        )
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    entry<MainRoutes.RouteThree>(
        metadata = getMainRouteTransitionSpecForMainRouteThree()
    ) { parentRoute ->
        ChildrenNavDisplay(
            backStack = backStack,
            parent = parentRoute,
            entryProvider = entryProvider {
                entry<MainRoutes.RouteThree.Statistics> {
                    val viewModel = koinViewModel<StatisticsViewModel>()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    StatisticsScreen(
                        uiState = uiState,
                        onAction = viewModel::onAction
                    )
                }
            }
        )
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    entry<MainRoutes.RouteFour>(
        metadata = getMainRouteTransitionSpecForMainRouteFour()
    ) { parentRoute ->
        ChildrenNavDisplay(
            backStack = backStack,
            parent = parentRoute,
            entryProvider = entryProvider {
                entry<MainRoutes.RouteFour.Profile> {
                    val viewModel = koinViewModel<ProfileViewModel>()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    LaunchedEffect(Unit) {
                        viewModel.reloadUserData()
                    }

                    ProfileScreen(
                        uiState = uiState,
                        onUiAction = { action ->
                            when (action) {
                                is ProfileUiAction.NavigateToReports -> backStack.push(MainRoutes.RouteFour.Reports, parentRoute)
                                is ProfileUiAction.NavigateToSettings -> backStack.push(MainRoutes.RouteFour.Settings, parentRoute)
                                is ProfileUiAction.NavigateToProfileEdit -> backStack.push(MainRoutes.RouteFour.ProfileEdit, parentRoute)
                            }
                        }
                    )
                }
                entry<MainRoutes.RouteFour.ProfileEdit> {
                    val viewModel = koinViewModel<ProfileEditViewModel>()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    ListenToEvents(viewModel.events) { event, _, onError ->
                        when (event) {
                            ProfileEditEvent.SaveSuccess -> backStack.pop(parentRoute)
                            is ProfileEditEvent.SaveDataFailure -> onError(event.message)
                            is ProfileEditEvent.SaveProfilePictureFailure -> onError(event.message)
                        }
                    }

                    LoaderController(uiState.isLoading)

                    ProfileEditScreen(
                        uiState = uiState,
                        onAction = viewModel::onAction,
                        onUiAction = { action ->
                            when (action) {
                                is ProfileEditUiAction.NavigateBack -> backStack.pop(parentRoute)
                            }
                        }
                    )
                }
                entry<MainRoutes.RouteFour.Reports> {
                    val viewModel = koinViewModel<ReportsViewModel>()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    ListenToEvents(viewModel.events) { event, _, onError ->
                        when (event) {
                            is ReportsEvent.ExportToCSVFailure -> onError(event.message)
                            is ReportsEvent.ExportToPDFFailure -> onError(event.message)
                        }
                    }

                    ReportsScreen(
                        uiState = uiState,
                        onAction = viewModel::onAction,
                        onUiAction = { action ->
                            when (action) {
                                is ReportsUiAction.NavigateBack -> backStack.pop(parentRoute)
                            }
                        }
                    )
                }
                entry<MainRoutes.RouteFour.Settings> {
                    val viewModel = koinViewModel<SettingsViewModel>()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    val reportsRepository = koinInject<ReportsRepository>()

                    ListenToEvents(viewModel.events) { event, _, onError ->
                        when (event) {
                            SettingsEvent.LogOutSuccess -> {
                                reportsRepository.clearCache()
                                backStack.clearAndPush(AuthRoutes.SignIn)
                            }
                            is SettingsEvent.LogOutFailure -> onError(event.message)
                        }
                    }

                    LoaderController(uiState.isLoading)

                    SettingsScreen(
                        onAction = viewModel::onAction,
                        onUiAction = { action ->
                            when (action) {
                                is SettingsUiAction.NavigateBack -> backStack.pop(parentRoute)
                                is SettingsUiAction.NavigateToPermissions -> backStack.push(MainRoutes.RouteFour.Permissions, parentRoute)
                            }
                        }
                    )
                }
                entry<MainRoutes.RouteFour.Permissions> {
                    PermissionsScreen(
                        onNavigateBack = {
                            backStack.pop(parentRoute)
                        }
                    )
                }
            }
        )
    }
}