package com.example.eventeasyapp.ui.theme.pages.posting

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.eventeasyapp.models.Event
import com.example.eventeasyapp.data.EventRepository
import com.example.eventeasyapp.navigation.ROUTE_BOOKING
import com.example.eventeasyapp.navigation.ROUTE_UPDATE_POSTS
import com.example.eventeasyapp.ui.theme.EventEasyAppTheme
import com.example.eventeasyapp.ui.theme.Maroon
import com.example.eventeasyapp.ui.theme.Neutral

@Composable
fun PostsScreen(navController:NavHostController) {
    var context = LocalContext.current
    var eventRepository = EventRepository(navController, context)

    val emptyPostState = remember { mutableStateOf(Event("","" ,"", "", "", "", "")) }
    var emptyPostsListState = remember { mutableStateListOf<Event>() }

    var posts = eventRepository.viewPosts(emptyPostState, emptyPostsListState)

    Column(modifier = Modifier
        .padding(top = 40.dp)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {



        LazyColumn {
            items(posts) {
                EventItem(
                    eventTitle = it.title,
                    eventTime = it.time,
                    eventLocation = it.location,
                    eventPrice = it.price,
                    eventDesc = it.desc,
                    eventId = it.id,
                    imageUrl = it.imageUrl,
                    navController = navController,
                    eventRepository = eventRepository
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventItem(eventTitle:String,eventTime:String,eventLocation:String,
              eventPrice:String, eventId:String, eventDesc:String,
              imageUrl:String,navController:NavHostController, eventRepository: EventRepository) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var context = LocalContext.current
        ElevatedCard(
            onClick = {
                navController.navigate(ROUTE_UPDATE_POSTS+"/$eventId")
                      },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .height(200.dp)
                .width(500.dp),
                //.align(CenterHorizontally),
            colors = CardDefaults.cardColors(
                containerColor = Neutral,
                ),
            shape = RoundedCornerShape(corner= CornerSize(10.dp))
        ) {
            Row(modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(painter = rememberAsyncImagePainter(imageUrl), contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.Crop)
                Column (modifier = Modifier.padding(5.dp)){
                    Text(text = eventTitle, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)
                    Text(text = "Time: $eventTime")
                    Text(text = "Location: $eventLocation")
                    Text(text = "Price: $eventPrice")
                    Text(text = "Description: $eventDesc")
//                    IconButton(onClick = { navController.navigate(ROUTE_UPDATE_POSTS) })
//                        //colors = ButtonDefaults.buttonColors(Maroon))
//                    {
//                        Text(text = "GET TICKETS")
//
//                    }
                     Row {
//                        IconButton(
//                            onClick = { navController.navigate(ROUTE_UPDATE_POSTS)
//                                //eventRepository.updatePosts("","","","","","","")
//                            }, colors = IconButtonDefaults.iconButtonColors(
//                                containerColor = Color.White,
//                                contentColor = Neutral
//                            )
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.UploadFile,
//                                contentDescription = "Update Post"
//                            )
//                        }
                         IconButton(
                             onClick = {
                                 eventRepository.deletePost(eventId)
                             }, colors = IconButtonDefaults.iconButtonColors(
                                 containerColor = Color.White,
                                 contentColor = Neutral
                             )
                         ) {
                             Icon(
                                 imageVector = Icons.Default.DeleteForever,
                                 contentDescription = "Delete Icon"
                             )
                         }
                     }

                   }

                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

//        TextButton(onClick = { navController.navigate(ROUTE_PAYMENT) })
//        //colors = ButtonDefaults.buttonColors(Maroon))
//        {
//            Text(text = "GET TICKETS")
//
//        }


}

@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PostsScreenPreview() {
    EventEasyAppTheme {
        PostsScreen(rememberNavController())

    }
}
