package com.example.eventeasyapp.ui.theme.pages.posting

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.eventeasyapp.data.EventRepository
import com.example.eventeasyapp.models.Event
import com.example.eventeasyapp.ui.theme.EventEasyAppTheme
import com.example.eventeasyapp.ui.theme.LightBurgundy
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun UpdatePostsScreen(navController: NavHostController,id:String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var context = LocalContext.current
        var productTitle by remember { mutableStateOf("") }
        var productTime by remember { mutableStateOf("") }
        var productLocation by remember { mutableStateOf("") }
        var productPrice by remember { mutableStateOf("") }
        var productDesc by remember { mutableStateOf("") }
        var imageUri by remember { mutableStateOf("") }
        var imagePainter = rememberImagePainter(imageUri)
        var counter by remember { mutableStateOf(0) }


        var currentDataRef = FirebaseDatabase.getInstance().getReference()
            .child("Events/$id")
        currentDataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (counter < 1) {
                    counter++
                    var product = snapshot.getValue(Event::class.java)
                    productTitle = product!!.title
                    productTime = product!!.time
                    productLocation = product!!.location
                    productPrice = product!!.price
                    productDesc = product!!.desc
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        Text(
            text = "Update post",
            fontSize = 20.sp,
            //fontFamily = FontFamily.Cursive,
            color = LightBurgundy,
            modifier = Modifier.padding(20.dp),
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )




        OutlinedTextField(
            value = productTitle,
            onValueChange = { productTitle = it },
            label = { Text(text = "Post title *") },
            leadingIcon = { Icon(Icons.Outlined.Description, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(20.dp))


        OutlinedTextField(
            value = productTime,
            onValueChange = { productTime = it },
            label = { Text(text = "Post time *") },
            leadingIcon = { Icon(Icons.Outlined.AccessTime, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = productLocation,
            onValueChange = { productLocation = it },
            label = { Text(text = "Post location *") },
            leadingIcon = { Icon(Icons.Outlined.LocationOn, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            label = { Text(text = "Post price *") },
            leadingIcon = { Icon(Icons.Outlined.MonetizationOn, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = productDesc,
            onValueChange = { productDesc = it },
            label = { Text(text = "Post description *") },
            leadingIcon = { Icon(Icons.Outlined.Info, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )


        Spacer(modifier = Modifier.height(20.dp))



        UpdateImagePicker(
            context = context,
            navController = navController,
            productTitle = productTitle,
            productTime = productTime,
            productLocation = productLocation,
            productDesc = productDesc,
            productPrice = productPrice,
            id = id
        )

    }
}


@Composable
fun UpdateImagePicker(modifier: Modifier = Modifier, context: Context, navController: NavHostController,
                productTitle:String,
                productTime:String,
                productLocation:String,
                      productPrice:String,
                      productDesc:String,
                      id:String) {
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        }
    )

    Column(modifier = modifier,) {
        if (hasImage && imageUri != null) {
            val bitmap = MediaStore.Images.Media.
            getBitmap(context.contentResolver,imageUri)
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Selected image")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp), horizontalAlignment = Alignment.CenterHorizontally,) {
            Button(
                onClick = {
                    imagePicker.launch("image/*")
                },colors = ButtonDefaults.buttonColors(LightBurgundy)
            ) {
                Text(
                    text = "Select Image"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                //-----------WRITE THE UPLOAD LOGIC HERE---------------//
                var productRepository = EventRepository(navController, context)
                productRepository.updatePosts(
                    productTitle.trim(),
                    productTime.trim(),
                    productLocation.trim(),
                    productPrice.trim(),
                    productDesc.trim(),

                    id,
                    imageUri!!
                )


            }, colors = ButtonDefaults.buttonColors(LightBurgundy)
                ) {
                Text(text = "Upload")
            }
        }
    }
}





@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun UpdatePostsScreenPreview() {
          EventEasyAppTheme {
              UpdatePostsScreen(rememberNavController(), id = "")
          }
}