package com.example.eventeasyapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.eventeasyapp.Destinations
import com.example.eventeasyapp.data.AuthRepository
import com.example.eventeasyapp.ui.theme.pages.booking.BookingScreen
import com.example.eventeasyapp.ui.theme.pages.posting.AddPostsScreen
//import com.example.eventeasyapp.ui.theme.pages.posting.UpdatePostsScreen
import com.example.eventeasyapp.ui.theme.pages.posting.PostsScreen
import com.example.eventeasyapp.ui.theme.pages.home.HomeScreen
//import com.example.eventeasyapp.ui.theme.pages.home.HomeScreen
import com.example.eventeasyapp.ui.theme.pages.login.LoginScreen
import com.example.eventeasyapp.ui.theme.pages.payment.PaymentScreen
import com.example.eventeasyapp.ui.theme.pages.posting.UpdatePostsScreen
import com.example.eventeasyapp.ui.theme.pages.profile.ProfileScreen
import com.example.eventeasyapp.ui.theme.pages.signup.SignupScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.HomeScreen.route){
        composable(ROUTE_LOGIN){
            LoginScreen(navController)
        }
        composable(ROUTE_SIGNUP){
            SignupScreen(navController)
        }
        composable(ROUTE_HOME){
            HomeScreen(navController)
        }

        composable(ROUTE_POSTS){

            PostsScreen(navController)
        }
        composable(ROUTE_BOOKING+"/{id}"){ passedData->
            BookingScreen(navController,passedData.arguments?.getString("id")!!)
        }
        composable(ROUTE_ADD_POSTS){
                AddPostsScreen(navController)
        }
        composable(ROUTE_UPDATE_POSTS+"/{id}"){ passedData->
            UpdatePostsScreen(navController,passedData.arguments?.getString("id")!!)
        }
        composable(Destinations.Profile.route){
                ProfileScreen(navController)
        }

    }

}




            //Design view products screen
            // ViewProductsScreen(navController)

       // composable(ROUTE_UPDATE_PRODUCTS + "/{id}"){ passedData->
          //  UpdateProductsScreen(navController, passedData.arguments?.getString("id")!!)
       // }

