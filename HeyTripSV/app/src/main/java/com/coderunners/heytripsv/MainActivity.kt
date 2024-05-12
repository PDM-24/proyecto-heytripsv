package com.coderunners.heytripsv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.coderunners.heytripsv.ui.navigation.navBarItemList
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coderunners.heytripsv.ui.navigation.BottomNavigationBar
import com.coderunners.heytripsv.ui.navigation.NavBarGraph
import com.coderunners.heytripsv.ui.theme.HeyTripSVTheme
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.TextGray

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeyTripSVTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute: String? =navBackStackEntry?.destination?.route
                    val navItems =  navBarItemList()

                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(itemsList = navItems, currentRoute = currentRoute) {
                                currentNavigationItem ->
                                navController.navigate(currentNavigationItem.route){
                                    navController.graph.startDestinationRoute?.let{startDestinationRoute ->
                                        popUpTo(startDestinationRoute){
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop=true
                                    restoreState = true
                                }
                            }
                        }
                    ) {
                            innerPadding ->
                        NavBarGraph(
                            navController = navController,
                            innerPadding = innerPadding
                        )
                    }
                }
            }
        }
    }
}