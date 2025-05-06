package me.felipe.afanador.storeapp

import android.app.Activity
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RegisterScreen (onClickBack:()-> Unit={},onSuccefulRegistre:()->Unit={}) {

    val auth=Firebase.auth
    val activity = LocalView.current.context as Activity

    //Estado de los input
    var inputName by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf("") }
    var inputContraseña by remember { mutableStateOf("") }
    var inputConfirmarContraseña by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var contraseñaError by remember { mutableStateOf("") }
    var contraseñaconfirmarError by remember { mutableStateOf("") }

    var registerError by remember { mutableStateOf("") }


    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Arrow Back"
                        )
                    }
                }
            )
        }
    )
    { innerPading ->
        Column(
            modifier = Modifier
                .padding(innerPading)
                .padding(horizontal = 32.dp)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "Icon register",
                modifier = Modifier.size(150.dp)
            )
            Text("Register", fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9900)
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = inputName,
                onValueChange = {inputName= it},
                label = {Text("Nombre")},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Person"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    if (nameError.isNotEmpty()){
                        Text(
                            text = nameError,
                            color = Color.Red
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = inputEmail,
                onValueChange = {inputEmail= it},
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
                            text =emailError,
                            color = Color.Red
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = inputContraseña,
                onValueChange = {inputContraseña= it },
                label = {Text("Contraseña")},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Person"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    if (contraseñaError.isNotEmpty()){
                        Text(
                            text = contraseñaError,
                            color = Color.Red
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = inputConfirmarContraseña,
                onValueChange = {inputConfirmarContraseña=it},
                label = {Text("Confirmar Contraseña")},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Person"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    if (contraseñaconfirmarError.isNotEmpty()){
                        Text(
                            text = contraseñaconfirmarError,
                            color = Color.Red
                        )
                    }
                },
            )

            if (registerError.isNotEmpty()){
                Text(registerError, color = Color.Red)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {

                val isValidName:Boolean= ValidateName(inputName).first
                val isValidEmail:Boolean= ValidateEmail(inputEmail).first
                val isValidPassword:Boolean= ValidatePasword(inputContraseña).first
                val isValidPasswordConfirmar:Boolean=
                    ValidateConfirmePassword(inputContraseña,inputConfirmarContraseña).first

                nameError= ValidateName(inputName).second
                emailError=ValidateEmail(inputEmail).second
                contraseñaError= ValidatePasword( inputContraseña).second
                contraseñaconfirmarError= ValidateConfirmePassword(inputContraseña,inputConfirmarContraseña).second



                if (isValidName && isValidEmail && isValidPassword && isValidPasswordConfirmar){
                    auth.createUserWithEmailAndPassword(inputEmail,inputContraseña).
                            addOnCompleteListener(activity){task->
                                if (task.isSuccessful){
                                    onSuccefulRegistre()
                                }else {
                                    registerError=when(task.isSuccessful){

                                        else->"Error al registrarse"
                                    }
                                }
                            }
                }else {
                    registerError="Hubo un error al registrar"
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
                Text("Iniciar Sesión")
            }
        }
    }
}

