package com.ebayk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import coil.compose.rememberImagePainter
import com.ebayk.ui.theme.AppTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                ProvideWindowInsets {
                    val systemUiController = rememberSystemUiController()
                    SideEffect {
                        systemUiController.setSystemBarsColor(Transparent)
                    }
                    Image(
                        painter = rememberImagePainter(
                            "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/~CoAAOSwjZJhSgCC/\$_57.JPG"
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .height(250.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
