package com.newagesmb.androidmvvmarchitecture.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.newagesmb.androidmvvmarchitecture.ui.details.ContestDetailsScreen
import com.newagesmb.androidmvvmarchitecture.ui.details.MyTeamPreviewScreen
import com.newagesmb.androidmvvmarchitecture.ui.details.scorecard.LeagueTableScreen
import com.newagesmb.androidmvvmarchitecture.ui.details.scorecard.ScoreCardTypeScreen
import com.newagesmb.androidmvvmarchitecture.ui.forgotpassword.ChangePasswordScreen
import com.newagesmb.androidmvvmarchitecture.ui.forgotpassword.ForgotPasswordScreen
import com.newagesmb.androidmvvmarchitecture.ui.home.HomeScreen
import com.newagesmb.androidmvvmarchitecture.ui.home.MyMatchesScreen
import com.newagesmb.androidmvvmarchitecture.ui.home.PredictionScreen
import com.newagesmb.androidmvvmarchitecture.ui.join_contest.SelectedPlayerPreviewScreen
import com.newagesmb.androidmvvmarchitecture.ui.join_contest.TeamPreviewScreen
import com.newagesmb.androidmvvmarchitecture.ui.join_contest.create_team.CreateTeamScreen
import com.newagesmb.androidmvvmarchitecture.ui.join_contest.create_team.JoinContestViewModel
import com.newagesmb.androidmvvmarchitecture.ui.login.LoginScreen
import com.newagesmb.androidmvvmarchitecture.ui.join_contest.price.PricePoolScreen
import com.newagesmb.androidmvvmarchitecture.ui.register.OTPScreen
import com.newagesmb.androidmvvmarchitecture.ui.register.RegisterScreen
import com.newagesmb.androidmvvmarchitecture.ui.splash.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Splash.route,
    sharedViewModel: JoinContestViewModel = hiltViewModel()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable(NavigationItem.Splash.route) {
            SplashScreen(navController)
        }

        composable(NavigationItem.Login.route) {
            LoginScreen(navController)
        }

        composable(NavigationItem.Register.route) {
            RegisterScreen(navController)
        }

        composable(NavigationItem.ForgotPassword.route) {
            ForgotPasswordScreen(navController)
        }

        composable(NavigationItem.OtpVerification.route) {
            OTPScreen()
        }

        composable(NavigationItem.ChangePassword.route) {
            ChangePasswordScreen()
        }

        composable(NavigationItem.Home.route) {

            HomeScreen(navController)
        }

        composable(
            "${NavigationItem.PricePool.route}/{tournament_id}/{tournament_type}", arguments = listOf(
                navArgument("tournament_id") { type = NavType.IntType },navArgument("tournament_type") { type = NavType.IntType })

        ) { backStackEntry ->
            Log.d("Called","true")


            PricePoolScreen(
                tournamentId = backStackEntry.arguments?.getInt("tournament_id")!!,
                tournamentType = backStackEntry.arguments?.getInt("tournament_type")!!,
                navController, sharedViewModel
            )
        }
        composable(
            "${NavigationItem.CreateTeam.route}/{tournament_id}/{tournament_type}/{tournament_price}/{team_list}", arguments = listOf(
                navArgument("tournament_id") { type = NavType.IntType },navArgument("tournament_type") { type = NavType.IntType },navArgument("tournament_price") { type = NavType.StringType },navArgument("team_list") { type = NavType.StringType })
        ) { backStackEntry ->

            CreateTeamScreen(
                tournamentId = backStackEntry.arguments?.getInt("tournament_id")!!,
                tournamentType = backStackEntry.arguments?.getInt("tournament_type")!!,
                tournamentPrice = backStackEntry.arguments?.getString("tournament_price")!!,
                teamList = backStackEntry.arguments?.getString("team_list")!!,
                navController,sharedViewModel
            )
        }
        composable(NavigationItem.TeamPreview.route) {
            TeamPreviewScreen(navController,sharedViewModel)
        }
        composable(NavigationItem.MyMatches.route) {
            MyMatchesScreen(navController)
        }
        composable(NavigationItem.Predictions.route) {
            PredictionScreen()
        }
        composable(NavigationItem.Winners.route) {
            PredictionScreen()
        }
        composable("${NavigationItem.MyContestDetail.route}/{tournament_id}", arguments = listOf(
            navArgument("tournament_id") { type = NavType.IntType })) {backStackEntry ->
            ContestDetailsScreen(tournamentId = backStackEntry.arguments?.getInt("tournament_id")!!,navController,sharedViewModel)
        }
        composable(NavigationItem.SelectedPlayersPreview.route) {
            SelectedPlayerPreviewScreen(navController,sharedViewModel)
        }
        composable("${NavigationItem.ScoreCard.route}/{matches}", arguments = listOf(

            navArgument("matches") { type = NavType.StringType })) {backStackEntry ->
//            LeagueTableScreen()
            ScoreCardTypeScreen(matches = backStackEntry.arguments?.getString("matches")!!,navController)
        }
        composable("${NavigationItem.MyTeamPreview.route}/{teams}", arguments = listOf(
            navArgument("teams") { type = NavType.StringType })) {backStackEntry ->
            MyTeamPreviewScreen(navController, teamList = backStackEntry.arguments?.getString("teams")!!)
        }
    }
}