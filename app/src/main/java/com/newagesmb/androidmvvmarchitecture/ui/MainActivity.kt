package com.newagesmb.androidmvvmarchitecture.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.local.preferences.DataStoreManager
import com.newagesmb.androidmvvmarchitecture.navigation.AppNavHost
import com.newagesmb.androidmvvmarchitecture.navigation.AppNavigation
import com.newagesmb.androidmvvmarchitecture.ui.home.BottomMenu
import com.newagesmb.androidmvvmarchitecture.ui.join_contest.create_team.JoinContestViewModel
import com.newagesmb.androidmvvmarchitecture.utils.Constents
import com.newagesmb.androidmvvmarchitecture.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dataStoreManager: DataStoreManager
    var keepSplashOnScreen = true
    var bearerToken = ""
    val viewModel by viewModels<MainViewModel>()
    val joinContestViewModel by viewModels<JoinContestViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        lifecycleScope.launch {
            delay(200)
            keepSplashOnScreen = false
        }
//        setContentView(R.layout.activity_main)
//        supportActionBar?.hide()

        setContent{

            val navController = rememberNavController()

            viewModel.navController = navController
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            Scaffold( bottomBar = {
                if (currentRoute in listOf(
                        AppNavigation.HOME.name,
                        AppNavigation.MY_MATCHES.name,
                        AppNavigation.PREDICTIONS.name,
                        AppNavigation.WINNERS.name
                    )
                ) {
                    BottomMenu(navController = navController)
//                        ChangeStatusBarColor(color = Color(0xFF08101E), darkIcons = false)
                }
            }
            ){ innerPadding->
                Log.d("Padding",innerPadding.calculateBottomPadding().toString())
                Box(modifier = Modifier.padding(innerPadding)) {

                    AppNavHost(navController = navController)
                }
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(mMessageReceiver)
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(
            mMessageReceiver,
            IntentFilter(Intent.ACTION_MAIN)
        )

    }
    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            onLocalBroadcastReceiver(intent)
        }
    }

    fun onLocalBroadcastReceiver(intent: Intent) {
        // Get extra data included in the Intent
        val status = intent.getIntExtra(Constents.INTENT_BROADCAST, -1)
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {

            if (status == Constents.LOGOUT) {
                try {
                    lifecycleScope.launch {
                        val currentRoute =
                            viewModel.navController?.currentBackStackEntry?.destination?.route
                        if (currentRoute != AppNavigation.LOGIN.name || currentRoute != AppNavigation.FORGOT_PASSWORD.name || currentRoute != AppNavigation.REGISTER.name) {
                            dataStoreManager.setToken("")
                            dataStoreManager.setUserLoggedIn(false)
                            viewModel.navController?.navigate(AppNavigation.LOGIN.name){
                                popUpTo(0){
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
//                        var isUserLoggedIn = dataStoreManager.isUserLoggedIn().collectLatest {
//                            Log.d("UserLoggedIn",it.toString())
//                        }
//                        if (isUserLoggedIn) {
//                            bearerToken = dataStoreManager.getToken().first()!!
//                            if (bearerToken.isNullOrEmpty()) {
//
//
//
//                            }
//                        }

                    }
                } catch (e: Exception) {

                }

            }
        }
    }
}