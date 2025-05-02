package me.felipe.afanador.storeapp

import android.util.Patterns
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.util.fastCbrt
import org.intellij.lang.annotations.Pattern

//retorna true si una cadena de texto es valido o null
//retornar el error de porque no es valido
fun  ValidateEmail (email:String): Pair<Boolean, String> {
    return  when{
        !Patterns.EMAIL_ADDRESS.matcher(email).matches()->Pair(false,"El correo no es valido")
        email.isEmpty()-> Pair(false,"El correo es requerido.")
        !email.endsWith("@test.com")->Pair(false,"El correo no es corporativo")
        else->Pair(true,"")
    }
}

fun ValidatePasword (password:String): Pair<Boolean, String> {
    return  when{
        password.isEmpty()->Pair(false, "La contraseña es requerida")
        password.length<8->Pair(false, "La contraseña tiene que ser mayor a 8 caracteres")
        !password.any(){it.isDigit()}->Pair(false,"La contraseña requiere un numero")
        else-> Pair(true,"")
    }
}
fun ValidateName (name: String):Pair<Boolean,String>{
    return when{
        name.isEmpty()->Pair(false, "EL nombre es requerida")
        name.length<3->Pair(false, "El nombre tiene que ser al menos de 3 caracteres")
        else-> Pair(true,"")
    }

}

fun ValidateConfirmePassword (password: String,confirmarpassword:String):Pair<Boolean,String>{
    return when{

        confirmarpassword != password->Pair(false,"Las contraseñas no coisiden")
        else ->Pair(true,"")
    }
}