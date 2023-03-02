package com.example.jetpackcomponsecatalog

import android.app.Activity
import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

@Preview(showBackground = true)
@Composable
fun InstagramLogin(){
    Box{
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(7.dp)){
            InstagramLoginHeader(Modifier.align(Alignment.TopEnd))
            InstagramLoginBody(Modifier.align(Alignment.Center))
        }
        InstagramLoginFooter(Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun InstagramLoginHeader(modifier: Modifier){
    val activity = LocalContext.current as Activity
    IconButton(
        modifier = modifier.size(30.dp),
        onClick = { activity.finish() }
    ) {
        Icon(Icons.Default.Close, contentDescription = "Close App", Modifier.size(30.dp))
    }
}

@Composable
fun InstagramLoginBody(modifier: Modifier){
    var username by rememberSaveable{ mutableStateOf("") }
    var password by rememberSaveable{ mutableStateOf("") }
    var enabledButton by rememberSaveable{ mutableStateOf(false) }
    var showConfetti by rememberSaveable{ mutableStateOf(false) }

    Column(modifier = modifier.padding(horizontal = 7.dp)) {
        Image(painter = painterResource(id = R.drawable.insta), contentDescription = "Instagram", modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(30.dp))
        InstagramTextField(username,"Phone number, username or email", false, KeyboardType.Email) {
            username = it
            enabledButton = enableInstagramButton(username, password)
        }
        Spacer(modifier = Modifier.size(15.dp))
        InstagramTextField(password, "Password", true){
            password = it
            enabledButton = enableInstagramButton(username, password)
        }
        Spacer(modifier = Modifier.size(15.dp))
        Text(text = "Forgot password?", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4EA8E9), modifier = Modifier.align(Alignment.End).clickable (interactionSource = MutableInteractionSource(), indication = null){Log.i("Forgot", "Forgot Password")})
        Spacer(modifier = Modifier.size(25.dp))
        Button(onClick = {
            showConfetti = true
            username = ""
            password = ""
            enabledButton = false

            Timer().schedule(5000){
                showConfetti = false
            } }, enabled = enabledButton, modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = Color(0xFF78C8F9),
            backgroundColor = Color(0xFF4EA8E9),
            contentColor = Color.White,
            disabledContentColor = Color.White
        )) {
            Text(text = "Log In")
        }
        Spacer(modifier = Modifier.size(20.dp))
        InstagramLoginDivider("OR")
        Spacer(modifier = Modifier.size(40.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = R.drawable.fb), contentDescription = "Facebook", modifier = Modifier.size(20.dp))
            Text(text = "Continue as David Johnson", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4EA8E9), modifier = Modifier.padding(horizontal = 5.dp).clickable (interactionSource = MutableInteractionSource(), indication = null){ Log.i("Continua as", "Continue as message") })
        }
    }

    if(showConfetti){
        KonfettiView(
            modifier = Modifier.fillMaxSize(),
            parties = listOf(
                Party(position = Position.Relative(0.5, -0.1),
                    timeToLive = 5000,
                    emitter = Emitter(duration = 5, TimeUnit.SECONDS).perSecond(30)
                )
            ),
        )
    }
}

fun enableInstagramButton(email: String, password: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 5;
}

@Composable
fun InstagramTextField(value: String, label: String, isPassword: Boolean = false, keyboardType: KeyboardType = KeyboardType.Text, onChangeText: (String) -> Unit){
    var showPassword by rememberSaveable {
        mutableStateOf(false)
    }
    TextField(
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFFB2B2B2),
            backgroundColor = Color(0xFFF7F5F5),
            placeholderColor = Color(0xFF969696),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent),
        value = value,
        onValueChange = {onChangeText(it)},
        visualTransformation = if(showPassword){
            VisualTransformation.None
        } else {
               PasswordVisualTransformation()
               },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(5.dp)),
        placeholder = { Text(text = label, fontSize = 14.sp)},
        trailingIcon = {
            if(isPassword){
                val icon = if (showPassword) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(icon, contentDescription = "Show Password")
                }
            }
        }
    )
}


@Composable
fun InstagramLoginDivider(text: String){
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Divider(
            Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .weight(1f))
        Text(text = text, fontSize = 12.sp ,fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp), color = Color(0xFFB5B5B5))
        Divider(
            Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .weight(1f))
    }
}

@Composable
fun InstagramLoginFooter(modifier: Modifier){
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Divider()
        Row(Modifier.padding(vertical = 20.dp)) {
            Text(text = "Don't have a account?", color = Color(0xFFB5B5B5), fontSize = 12.sp)
            Text(text = "Sign Up.", fontWeight = FontWeight.Bold, fontSize = 12.sp,
                color = Color(0xFF4EA8E9), modifier = Modifier
                    .padding(start = 5.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { Log.i("Sign Up", "Sign Up Message") })
        }
    }
}