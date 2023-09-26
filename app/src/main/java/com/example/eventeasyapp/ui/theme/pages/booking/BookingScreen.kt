package com.example.eventeasyapp.ui.theme.pages.booking

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.eventeasyapp.data.AuthRepository
import com.example.eventeasyapp.data.EventRepository
import com.example.eventeasyapp.models.Event
import com.example.eventeasyapp.navigation.ROUTE_LOGIN
import com.example.eventeasyapp.ui.theme.EventEasyAppTheme
import com.example.eventeasyapp.ui.theme.LightBurgundy

@Composable
fun BookingScreen(navController: NavHostController, id:String) {
    Column(modifier = Modifier
        //.padding(top = 40.dp, end = 20.dp)
        .fillMaxSize()
        .wrapContentSize(Alignment.Center),

        //.verticalScroll(state = scrollState),

        horizontalAlignment = Alignment.CenterHorizontally) {

        var context = LocalContext.current
        var eventRepository= EventRepository(navController, context)


        val emptyPostState = remember { mutableStateOf(Event("","","","","","","")) }
        var emptyPostsListState = remember { mutableStateListOf<Event>() }

        var event = eventRepository.viewPosts(emptyPostState, emptyPostsListState)



        LazyColumn{
            items(event){
                if (it.id.equals(id)){
                    EventItems(
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

}

@Composable
fun EventItems(eventTitle:String,eventTime:String,eventLocation:String, eventPrice:String, eventDesc: String,
               eventId:String, imageUrl:String,navController:NavHostController, eventRepository: EventRepository) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .wrapContentSize(Alignment.Center),
        verticalAlignment = Alignment.CenterVertically

    ) {
        var context = LocalContext.current
        var count by remember {
            mutableStateOf(0)
        }
        var price by remember {
            mutableStateOf(eventPrice.toDouble())
        }

        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
            //,verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl), contentDescription = null,
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(30.dp))
            Column {
                Text(text = "Title: $eventTitle",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold)
                Text(text = "Time: $eventTime",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold)
                Text(text = "Location: $eventLocation",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold)
                Text(text = "Price: $price",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold)
                Text(text = "Desc: $eventDesc",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold)

                Row (
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Button(onClick = {
                        if (count!=0){
                            count--
                            price = eventPrice.toDouble()*count
                        }
                    },
                        colors = ButtonDefaults.buttonColors(LightBurgundy)) {
                        Text(text = "-",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(text = count.toString())

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(onClick = {
                        count++
                        price = eventPrice.toDouble()*count
                    }
                        ,
                        colors = ButtonDefaults.buttonColors(LightBurgundy)) {
                        Text(text = "+",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    var authRepository = AuthRepository(navController,context)
                    if(!authRepository.isLoggedIn()){
                        navController.navigate(ROUTE_LOGIN)
                    }else{
                        val simToolKitLaunchIntent: Intent? =
                            context.getApplicationContext().getPackageManager()
                                .getLaunchIntentForPackage("com.android.stk")
                        if (simToolKitLaunchIntent != null) {
                            context.startActivity(simToolKitLaunchIntent)
                        }

                    }
                },
                    colors = ButtonDefaults.buttonColors(LightBurgundy)) {
                    Text(text = "BOOK NOW !",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                }
            }
        }
    }


 }



@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun BookingScreenPreview() {
    EventEasyAppTheme {
        BookingScreen(navController = rememberNavController(),id="")
    }
}




