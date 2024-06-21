package com.coderunners.heytripsv

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.coderunners.heytripsv.ui.navigation.navBarItemList
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coderunners.heytripsv.ui.navigation.BottomNavigationBar
import com.coderunners.heytripsv.ui.navigation.NavBarGraph
import com.coderunners.heytripsv.ui.theme.HeyTripSVTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContent {
            HeyTripSVTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                        NavBarGraph(
                            navController = navController,
                            mainViewModel = mainViewModel
                        )
                }
            }
        }
    }
}