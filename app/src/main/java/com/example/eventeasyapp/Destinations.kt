package com.example.eventeasyapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.eventeasyapp.navigation.ROUTE_ADD_POSTS
import com.example.eventeasyapp.navigation.ROUTE_HOME
import com.example.eventeasyapp.navigation.ROUTE_POSTS
import com.example.eventeasyapp.navigation.ROUTE_PROFILE

sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: ImageVector?= null
){
    object HomeScreen: Destinations(
        route = ROUTE_HOME,
        title = "For You",
        icon = Icons.Outlined.Home
    )
    object Posts : Destinations(
        route = ROUTE_POSTS,
        title = "My Posts",
        icon = Icons.Outlined.Favorite
    )

    object AddPost: Destinations(
        route = ROUTE_ADD_POSTS,
        title = "Add post",
        icon = Icons.Outlined.Add
    )
    object Profile :Destinations(
        route = ROUTE_PROFILE,
        title = "Profile",
        icon = Icons.Outlined.Person
    )




}
