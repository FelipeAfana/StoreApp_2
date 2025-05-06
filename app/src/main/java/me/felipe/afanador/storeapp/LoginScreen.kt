package me.felipe.afanador.storea

import android.app.Activity
import android.inputmethodservice.Keyboard
import android.text.BoringLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.auth
import me.felipe.afanador.storeapp.ValidateEmail
import me.felipe.afanador.storeapp.ValidatePasword

@Preview
@Composable
fun LoginScreen (onClickRegister: ()-> Unit={}, onSuccesfulLogin:()-> Unit={}) {

    val auth =Firebase.auth
    val activity = LocalView.current.context as Activity

    var inputEmail by remember{ mutableStateOf("") }
    var inputPassword by remember{ mutableStateOf("") }
    var loginError by remember{ mutableStateOf("") }
    var emailError by remember{ mutableStateOf("") }
    var paswordError by remember{ mutableStateOf("") }

    Scaffold { 
        paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .imePadding()
                .verticalScroll(rememberScrollState())

            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "icon login",
                modifier = Modifier.size(200.dp)
            )
            Text("Iniciar Seción", fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9900)
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = inputEmail,
                onValueChange = {inputEmail = it},
                label = {Text("Email")},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    if (emailError.isNotEmpty()){
                        Text(
                            text = emailError,
                            color = Color.Red
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = inputPassword,
                onValueChange = {inputPassword= it},
                label = {Text("Contraseña")},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Email"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    if (paswordError.isNotEmpty()){
                        Text(
                            text = paswordError,
                            color = Color.Red
                        )
                    }
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (loginError.isNotEmpty()){
                Text(
                    loginError,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
            }

            Button(onClick = {

                val isValidpassword:Boolean= ValidatePasword(inputPassword).first
                val isValidEmail:Boolean= ValidateEmail(inputEmail).first
                emailError= ValidateEmail(inputEmail).second
                paswordError= ValidatePasword(inputPassword).second

                if (isValidpassword && isValidEmail){
                    auth.signInWithEmailAndPassword(inputEmail,inputPassword)
                        .addOnCompleteListener(activity){task->
                            if (task.isSuccessful){
                                onSuccesfulLogin()
                            }else{
                                loginError= when (task.exception){
                                    is FirebaseAuthInvalidCredentialsException-> "Correo o contraseña incorrecta"
                                    is FirebaseAuthInvalidUserException-> "No existe una cuenta con este correo"
                                    else-> "Error al iniciar sesión"
                                }
                            }
                        }
                }else {

                }

                             },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9900),
                    contentColor = Color.White
                )
            ) {
                Text("Iniciar sesión")
            }
            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onClickRegister) {
                Text("¿No tiene una cuenta?. Registrate",
                    color=Color(0xFFFF9900))
            }
        }
    }
}