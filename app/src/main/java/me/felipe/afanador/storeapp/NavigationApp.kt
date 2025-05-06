package me.felipe.afanador.storeapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import me.felipe.afanador.storea.LoginScreen


@Composable
fun NavigationApp () {
    val mynavController= rememberNavController()
    var myStartDestination: String="login"

    val auth= Firebase.auth
    val currentUser=auth.currentUser

    if (currentUser!= null){
        myStartDestination="home"
    }else{
        myStartDestination="home"
    }


    NavHost(
        navController = mynavController ,
        startDestination= myStartDestination
    ){
        composable("login") {
            LoginScreen(onClickRegister={
                mynavController.navigate(("register"))
            },
                onSuccesfulLogin = {
                    mynavController.navigate("home"){
                        popUpTo("login"){inclusive=true}
                    }
                })
        }
        composable("register") {
            RegisterScreen(onClickBack ={
                mynavController.popBackStack()
            },
                onSuccefulRegistre={
                    mynavController.navigate("home"){
                        popUpTo(0)
                    }
                })
        }
        composable("home") {
            HomeScreen(onClickLogout = {
                mynavController.navigate("login"){
                    popUpTo(0)
                }
            })
        }
    }
}