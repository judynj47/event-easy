package com.example.eventeasyapp.ui.theme.pages.home

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.eventeasyapp.data.EventRepository
import com.example.eventeasyapp.models.Event
import com.example.eventeasyapp.navigation.ROUTE_BOOKING
import com.example.eventeasyapp.ui.theme.EventEasyAppTheme
import com.example.eventeasyapp.ui.theme.Maroon
import com.example.eventeasyapp.ui.theme.MistyBlue

@Composable
fun HomeScreen(navController: NavHostController,modifier: Modifier = Modifier) {

    //Divider(color = Color.White.copy(alpha = 0.3f), thickness = 1.dp, modifier = Modifier.padding(top = 40.dp))
    Column(modifier = Modifier
        .padding(top = 40.dp, end = 20.dp)
        .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "Featured Events",
            fontSize = 10.sp,
            fontFamily = FontFamily.Serif,
            color = Maroon,
            modifier = Modifier.padding(15.dp),
            fontWeight = FontWeight.Bold,
            //textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Left
        )


        var context = LocalContext.current
        var eventRepository = EventRepository(navController, context)


        val emptyPostState = remember { mutableStateOf(Event("", "", "", "", "", "", "")) }
        var emptyPostsListState = remember { mutableStateListOf<Event>() }

        var events = eventRepository.viewPosts(emptyPostState, emptyPostsListState)


        LazyColumn {
            items(events) {
                EventsItem(
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
fun EventsItem(eventTitle:String,eventTime:String,eventLocation:String, eventPrice:String, eventDesc: String,
              eventId:String, imageUrl:String,navController:NavHostController, eventRepository: EventRepository) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var context = LocalContext.current
        ElevatedCard(
            onClick = {
                      navController.navigate(ROUTE_BOOKING+"/$eventId")
                      },
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .height(150.dp)
                .width(300.dp),
            colors = CardDefaults.cardColors(
                containerColor = MistyBlue,
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
                    Text(text = eventTitle)
                    Text(text = eventTime)
                    ClickableText(text = AnnotatedString("Location"), onClick ={
                        Log.e("tag","URL is " + eventLocation)
                        var urlIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(eventLocation)
                        )
                        context.startActivity(urlIntent)
                    } )
                    Text(text = "Price: $eventPrice")
                    //Text(text = "Description: $eventDesc")
//                    TextButton(onClick = { navController.navigate(ROUTE_PAYMENT) }) {
//                        Text(text = "GET TICKETS",
//                            fontSize = 15.sp,
//                            fontFamily = FontFamily.Serif,
//                            color = Maroon,
//                            fontWeight = FontWeight.Bold)
//
//                    }

                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

//        Row {
//            Text(
//                text = "Top Annual Events",
//                fontSize = 15.sp,
//                fontFamily = FontFamily.Serif,
//                color = Maroon,
//                fontWeight = FontWeight.Bold
//            )
//
//            val scrollState = rememberScrollState()
//            Box(
//                modifier = Modifier.scrollable(state = scrollState, orientation = Orientation.Horizontal)
//            ){
//                ElevatedCard(
//                    onClick = { navController.navigate(ROUTE_RESERVATIONS) },
//                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
//                    modifier = Modifier
//                        .height(150.dp)
//                        .width(300.dp),
//                    shape = RoundedCornerShape(corner = CornerSize(10.dp))
//                ) {
//                    Image(painter = painterResource(id = R.drawable.charity_event), contentDescription = null,
//                        modifier = Modifier
//                            .width(200.dp)
//                            .height(150.dp))
//                    Spacer(modifier = Modifier.height(10.dp))
//
//                    Image(painter = painterResource(id = R.drawable.poetry_event), contentDescription = null,
//                        modifier = Modifier
//                            .width(200.dp)
//                            .height(150.dp))
//                    Spacer(modifier = Modifier.height(10.dp))
//                    Image(painter = painterResource(id = R.drawable.pyschology_talk), contentDescription = null,
//                        modifier = Modifier
//                            .width(200.dp)
//                            .height(150.dp))
//                    Spacer(modifier = Modifier.height(10.dp))
//                    Image(painter = painterResource(id = R.drawable.film_festival), contentDescription = null,
//                        modifier = Modifier
//                            .width(200.dp)
//                            .height(150.dp))
//                    Spacer(modifier = Modifier.height(10.dp))
//
//
//
//                }
//
//            }
//
//        }

    }
}


@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenPreview() {
    EventEasyAppTheme {
        HomeScreen(rememberNavController())

    }
}
