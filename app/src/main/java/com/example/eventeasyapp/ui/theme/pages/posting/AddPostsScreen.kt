package com.example.eventeasyapp.ui.theme.pages.posting
import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.eventeasyapp.data.EventRepository
import com.example.eventeasyapp.ui.theme.EventEasyAppTheme
import com.example.eventeasyapp.ui.theme.Maroon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostsScreen(navController: NavHostController,modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column( // or whatever your parent composable is
        modifier = Modifier
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var context = LocalContext.current
        Text(
            text = "Add post",
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            color = Maroon,
            modifier = Modifier.padding(20.dp),
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )

        var productTitle by remember { mutableStateOf(TextFieldValue("")) }
        var productTime by remember { mutableStateOf(TextFieldValue("")) }
        var productLocation by remember { mutableStateOf(TextFieldValue("")) }
        var productPrice by remember { mutableStateOf(TextFieldValue("")) }
        var productDesc by remember { mutableStateOf(TextFieldValue("")) }

        OutlinedTextField(
            value = productTitle,
            onValueChange = { productTitle = it },
            label = { Text(text = "Post title *") },
            leadingIcon = {Icon(Icons.Outlined.Description, contentDescription = null)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(10.dp))


        OutlinedTextField(
            value = productTime,
            onValueChange = { productTime = it },
            label = { Text(text = "Post time *") },
            leadingIcon = {Icon(Icons.Outlined.AccessTime, contentDescription = null)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = productLocation,
            onValueChange = { productLocation = it },
            label = { Text(text = "Post location google maps link *") },
            leadingIcon = {Icon(Icons.Outlined.LocationOn, contentDescription = null)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            label = { Text(text = "Post price *") },
            leadingIcon = {Icon(Icons.Outlined.MonetizationOn, contentDescription = null)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = productDesc,
            onValueChange = { productDesc = it },
            label = { Text(text = "Post description *") },
            leadingIcon = {Icon(Icons.Outlined.Info, contentDescription = null)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )


        Spacer(modifier = Modifier.height(10.dp))

        //---------------------IMAGE PICKER START-----------------------------------//

        ImagePicker(modifier,context, navController, productTitle.text.trim(), productTime.text.trim(),
            productLocation.text.trim(),productPrice.text.trim(), productDesc.text.trim())

        //---------------------IMAGE PICKER END-----------------------------------//
    }

}

@Composable
fun ImagePicker(modifier: Modifier = Modifier,
                context: Context,
                navController: NavHostController,
                title:String,
                time:String,
                location:String,
                price:String,
                desc:String) {
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
                },colors = ButtonDefaults.buttonColors(Maroon)) {
                Text(
                    text = "Select Image"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                //-----------WRITE THE UPLOAD LOGIC HERE---------------//
                var productRepository = EventRepository(navController,context)
                productRepository.savePosts(title, time, location,price,desc,imageUri!!)


            },colors = ButtonDefaults.buttonColors(Maroon)) {
                Text(text = "Upload")
            }
        }
    }
}



@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AddProductsScreenPreview() {
    EventEasyAppTheme {
        AddPostsScreen(rememberNavController())
    }
}