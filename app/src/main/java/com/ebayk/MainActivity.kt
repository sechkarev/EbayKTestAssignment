package com.ebayk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil.compose.rememberImagePainter
import com.ebayk.ui.theme.AppTheme
import com.ebayk.ui.theme.Black202020
import com.ebayk.ui.theme.Black80202020
import com.ebayk.ui.theme.Green500
import com.ebayk.viewmodel.MainViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

@ExperimentalPagerApi
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

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
                    val apartmentInfo = viewModel.apartmentInfo.observeAsState().value!! // todo: show error?
                    LazyColumn {
                        item {
                            HorizontalPager(count = apartmentInfo.pictures.size) { page ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                ) {
                                    Image(
                                        painter = rememberImagePainter(apartmentInfo.pictures[page]),
                                        contentDescription = null,
                                        contentScale = ContentScale.FillWidth,
                                        modifier = Modifier
                                            .height(250.dp)
                                            .fillMaxWidth()
                                    )
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(vertical = 16.dp, horizontal = 16.dp)
                                            .background(
                                                color = Black80202020,
                                                shape = RoundedCornerShape(2.dp)
                                            )
                                    ) {
                                        Text(
                                            text = "${page + 1}/${apartmentInfo.pictures.size}",
                                            color = White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }
                        }
                        item {
                            Text(
                                text = apartmentInfo.title,
                                color = Black202020,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
                            )
                        }
                        item {
                            Text(
                                text = "${apartmentInfo.price.amount} ${Currency.getInstance(apartmentInfo.price.currency).symbol}",
                                color = Green500,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
