package com.newagesmb.androidmvvmarchitecture.navigation

import com.newagesmb.androidmvvmarchitecture.R

enum class AppNavigation {
    SPLASH,
    LOGIN,
    REGISTER,
    FORGOT_PASSWORD,
    OTP_VERIFICATION,
    CHANGE_PASSWORD,
    HOME,
    MY_MATCHES,
    PREDICTIONS,
    WINNERS,
    PRICE_POOL,
    CREATE_TEAM,
    TEAM_PREVIEW,
    MY_CONTEST_DETAILS,
    SELECTED_PLAYERS_PREVIEW,
    SCORE_CARD,
    MY_TEAM_PREVIEW

}

sealed class NavigationItem(val route: String, val icon : Int?=null,val label: String?=null){
    object Splash : NavigationItem(AppNavigation.SPLASH.name)
    object Login : NavigationItem(AppNavigation.LOGIN.name)
    object Register : NavigationItem(AppNavigation.REGISTER.name)
    object ForgotPassword : NavigationItem(AppNavigation.FORGOT_PASSWORD.name)
    object OtpVerification : NavigationItem(AppNavigation.OTP_VERIFICATION.name)
    object ChangePassword : NavigationItem(AppNavigation.CHANGE_PASSWORD.name)
    object Home : NavigationItem(AppNavigation.HOME.name, R.drawable.ic_home,"Home")
    object MyMatches : NavigationItem(AppNavigation.MY_MATCHES.name,R.drawable.ic_matches,"My Matches")
    object Predictions : NavigationItem(AppNavigation.PREDICTIONS.name,R.drawable.ic_predictions,"Prediction")
    object Winners : NavigationItem(AppNavigation.WINNERS.name,R.drawable.ic_winners,"Winners")
    object PricePool : NavigationItem(AppNavigation.PRICE_POOL.name)
    object CreateTeam : NavigationItem(AppNavigation.CREATE_TEAM.name)
    object TeamPreview : NavigationItem(AppNavigation.TEAM_PREVIEW.name)
    object MyContestDetail : NavigationItem(AppNavigation.MY_CONTEST_DETAILS.name)
    object SelectedPlayersPreview : NavigationItem(AppNavigation.SELECTED_PLAYERS_PREVIEW.name)
    object ScoreCard : NavigationItem(AppNavigation.SCORE_CARD.name)
    object MyTeamPreview : NavigationItem(AppNavigation.MY_TEAM_PREVIEW.name)

}