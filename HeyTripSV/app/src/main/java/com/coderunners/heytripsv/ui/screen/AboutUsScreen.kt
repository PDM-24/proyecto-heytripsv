package com.coderunners.heytripsv.ui.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coderunners.heytripsv.R
import com.coderunners.heytripsv.ui.theme.ButtonY
import com.coderunners.heytripsv.ui.theme.DSpacer
import com.coderunners.heytripsv.ui.theme.MainGreen
import com.coderunners.heytripsv.ui.theme.TextGray
import kotlinx.coroutines.withContext

@Composable
fun AboutUsScreen(innerPadding: PaddingValues){
    val context = LocalContext.current
    Column(
        modifier = androidx.compose.ui.Modifier
            .padding(innerPadding)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.about_us),
            color = TextGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(color = DSpacer, thickness = 1.dp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text =  stringResource(R.string.text_about),
            color = TextGray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(color = DSpacer, thickness = 1.dp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.support_us),
            color = TextGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.text_support),
            color = TextGray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://buymeacoffee.com/"))
            context.startActivity(intent) },
            modifier = Modifier.fillMaxWidth(0.8f)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonY

            ),
            border = BorderStroke(1.dp, color = ButtonY),
            shape = RoundedCornerShape(7.dp)
        ) {
            Text(text = stringResource(R.string.buy_coffee), color = TextGray)
        }
    }
}