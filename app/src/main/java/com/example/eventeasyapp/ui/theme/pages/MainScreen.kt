package com.example.eventeasyapp.ui.theme.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eventeasyapp.Destinations
import com.example.eventeasyapp.data.AuthRepository
import com.example.eventeasyapp.navigation.NavigationGraph
import com.example.eventeasyapp.navigation.ROUTE_HOME
import com.example.eventeasyapp.navigation.ROUTE_LOGIN
import com.example.eventeasyapp.ui.theme.Burgundy
import com.example.eventeasyapp.ui.theme.Maroon
import com.example.eventeasyapp.ui.theme.MistyBlue
import com.example.eventeasyapp.ui.theme.MyPink
import com.example.eventeasyapp.ui.theme.Neutral

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController: NavHostController = rememberNavController()
    val bottomBarHeight = 48.dp
    val bottomBarHeightPx = with(LocalDensity.current) { bottomBarHeight.roundToPx().toFloat() }
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }
    var buttonsVisible = remember{ mutableStateOf(true) }


//    val nestedScrollConnection = remember {
//        object : NestedScrollConnection {
//            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
//
//                val delta = available.y
//                val newOffset = bottomBarOffsetHeightPx.value + delta
//                bottomBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)
//
//                return Offset.Zero
//            }
//        }
//    }

    Scaffold (
        bottomBar = {
            BottomBar(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
                    //.nestedScroll(nestedScrollConnection)
            )
        },
        topBar = {
            Column {
                TopBar(navController= navController)
            }}
    ){paddingValues->
        val scrollState = rememberScrollState()
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .scrollable(state = scrollState, orientation = Orientation.Vertical)
        ){
            NavigationGraph(navController)
        }


    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    Column {
        TopAppBar(
            title = {
                Text("EventEasy",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    color = Maroon,
                    modifier = Modifier.padding(3.dp),
                    fontWeight = FontWeight.Bold)
            }
        )
    }
}
@Composable
fun BottomBar(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    val screens = listOf(
        Destinations.HomeScreen,Destinations.Posts,Destinations.AddPost,
        Destinations.Profile)
    NavigationBar(
        modifier = modifier,
        containerColor = Maroon,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        var context = LocalContext.current
        var authRepository = AuthRepository(navController,context)

        screens.forEach { screen ->
            NavigationBarItem(
                label = {
                    Text(text = screen.title!!)
                },
                icon = {
                    Icon(imageVector = screen.icon!!,contentDescription = "")
                },
                selected = currentRoute == screen.route,
                onClick = {
                    if (screen.route.equals(ROUTE_HOME)){
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }else{
                        if (!authRepository.isLoggedIn()){
                            navController.navigate(ROUTE_LOGIN)
                        }else{
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Color.White, selectedTextColor = MyPink
                ),
            )
        }
    }

}

