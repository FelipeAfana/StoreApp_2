package me.felipe.afanador.storeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.util.stream.Stream


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (onClickLogout:()->Unit={}) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var urlImagenes = listOf(
        "https://img.freepik.com/vector-gratis/plantilla-banner-horizontal-rebajas-viernes-negro_23-2150867247.jpg?semt=ais_hybrid&w=740",
        "https://img.freepik.com/vector-gratis/plantilla-divertida-banner-black-friday-bocadillo-dialogo-fondo-zoom-comico_69286-219.jpg?semt=ais_hybrid&w=740",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiap75oQd40j9R2cBtV4Yb1P37Ap07hHga_w&s",
        "https://img.freepik.com/vector-gratis/banner-venta-horizontal-viernes-negro-plano_23-2149115134.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTdclRVUaVbzEd1uxJZD4HISqwnlBkmy1Wizg&s"
    )
    Scaffold(
        topBar = {
             MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Bienvenido",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPading->
        Column (modifier = Modifier.padding(innerPading)){
            Text("Promociones mas destacadas",
                modifier = Modifier
                    .padding(start=16.dp,
                        top=16.dp,
                        bottom = 8.dp))
            LazyRow (
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(start = 16.dp,end=16.dp )
            ){
                item { PromoCard(urlImagenes[0]) }
                item { PromoCard(urlImagenes[1]) }
                item { PromoCard(urlImagenes[2]) }
                item { PromoCard(urlImagenes[3]) }
                item { PromoCard(urlImagenes[4]) }
            }
        }
    }
}



@Composable
fun PromoCard(urlImagen:String) {
    Card (modifier = Modifier.height(180.dp)
        .width(300.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ){
        Image(
            painter = rememberAsyncImagePainter(urlImagen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale =  ContentScale.Crop
        )
    }
}