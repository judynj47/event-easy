package com.example.eventeasyapp.ui.theme.pages.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.eventeasyapp.R
import com.example.eventeasyapp.data.AuthRepository
import com.example.eventeasyapp.navigation.ROUTE_SIGNUP
import com.example.eventeasyapp.ui.theme.EventEasyAppTheme
import com.example.eventeasyapp.ui.theme.Maroon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    Surface(
        modifier = Modifier
            .padding(20.dp)
            .background(Maroon)
    ){
        Column(
            modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            var context = LocalContext.current
            Image(painter = painterResource(id = R.drawable.ticket), contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp))

            Text(
                text = "Login here",
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                color = Maroon,
                modifier = Modifier.padding(10.dp),
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
            Spacer(modifier = Modifier.height(20.dp))

            var email by remember { mutableStateOf(TextFieldValue("")) }
            var password by remember { mutableStateOf(TextFieldValue("")) }


            /*TextInput(InputType.Email, keyboardActions = KeyboardActions(onNext = {
                passwordFocusRequester.requestFocus()
            }))*/
            TextField(
                value = email,
                onValueChange = {email=it}, keyboardActions = KeyboardActions(onNext = {
                    passwordFocusRequester.requestFocus()
                }),
                label = { Text(text = "Email")},
                leadingIcon = {Icon(Icons.Outlined.Email, contentDescription = null)})

            Spacer(modifier = Modifier.height(10.dp))


            TextField(
                value = password,
                onValueChange = {password=it}, keyboardActions = KeyboardActions(onNext = {
                    passwordFocusRequester.requestFocus()
                }),
                label = { Text(text = "Password") },
                leadingIcon = {Icon(Icons.Outlined.Lock, contentDescription = null)})


            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                var authRepository = AuthRepository(navController, context)
                authRepository.login(email.text.trim(), password.text.trim())

            },
                colors = ButtonDefaults.buttonColors(Maroon)) {
                Text(text = "Login Here")

                Spacer(modifier = Modifier.height(10.dp))

            }

            Divider(color = Color.Gray.copy(alpha = 0.3f), thickness = 1.dp, modifier = Modifier.padding(top = 48.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Don't have an account?", color = Maroon)
                TextButton(onClick = { navController.navigate(ROUTE_SIGNUP) }) {
                    Text(text = "Sign up here")

                }

            }


        }
    }


}

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation

){
    object Email: InputType(
        label = "Email", icon =Icons.Default.Email,
        KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    object Password:InputType(
        label = "Password", icon = Icons.Default.Lock,
        KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(inputType: InputType, focusRequester: FocusRequester?= null,keyboardActions: KeyboardActions) {
    var value by remember {
        mutableStateOf("")
    }
    TextField(value = value, onValueChange = { value = it },
        modifier = Modifier
            .fillMaxWidth()
            .focusOrder(focusRequester ?: FocusRequester()),
        leadingIcon = { Icon(imageVector = inputType.icon, contentDescription = null)},
        label = { Text(text = inputType.label)},
        //shape = Shapes.small,
        colors = TextFieldDefaults.textFieldColors(
            //backgroundColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
            ),
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        keyboardActions = keyboardActions
        )

}
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LoginScreenPreview() {
    EventEasyAppTheme {
        LoginScreen(rememberNavController())
    }
}

