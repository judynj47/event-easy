package com.example.eventeasyapp.ui.theme.pages.payment

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.eventeasyapp.ui.theme.EventEasyAppTheme

@Composable
fun PaymentScreen(navController: NavHostController) {
    var context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 20.dp, 0.dp, 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            val simToolKitLaunchIntent: Intent? =
                context.getApplicationContext().getPackageManager()
                    .getLaunchIntentForPackage("com.android.stk")
            if (simToolKitLaunchIntent != null) {
                context.startActivity(simToolKitLaunchIntent)
            }
        }) {
            Text(text = "Mpesa")
        }

    }
}
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PaymentScreenPreview() {
    EventEasyAppTheme {
        PaymentScreen(rememberNavController())
    }
}