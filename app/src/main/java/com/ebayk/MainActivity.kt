package com.ebayk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil.compose.rememberImagePainter
import com.ebayk.ui.*
import com.ebayk.ui.theme.*
import com.ebayk.util.divideToPairs
import com.ebayk.viewmodel.MainViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
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
                            PhotoPager(pictureUrls = apartmentInfo.pictures)
                        }
                        item {
                            Text(
                                text = apartmentInfo.title,
                                color = Black202020,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
                            )
                        }
                        item {
                            Text(
                                text = "${apartmentInfo.price.amount} ${Currency.getInstance(apartmentInfo.price.currency).symbol}",
                                color = Green500,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                            )
                        }
                        item {
                            Text(
                                text = "${apartmentInfo.address.street}, ${apartmentInfo.address.zipCode} ${apartmentInfo.address.city}",
                                color = Gray600,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                                    .clickable {
                                        // todo: open maps
                                    }
                            )
                        }
                        item {
                            AdMetadata(
                                postDate = apartmentInfo.postDate,
                                visits = apartmentInfo.visits,
                                apartmentId = apartmentInfo.id,
                            )
                        }
                        if (apartmentInfo.attributes.isNotEmpty()) {
                            item {
                                Details(attributes = apartmentInfo.attributes)
                            }
                        }
                        if (apartmentInfo.features.isNotEmpty()) {
                            item {
                                Features(features = apartmentInfo.features)
                            }
                        }
                        if (apartmentInfo.documents.isNotEmpty()) {
                            item {
                                AdditionalInfo(documents = apartmentInfo.documents)
                            }
                        }
                        if (apartmentInfo.description.isNotBlank()) {
                            item {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(8.dp)
                                            .background(WhiteF2F2F2)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.description),
                                        color = Black202020,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(WhiteF2F2F2)
                                            .padding(start = 8.dp, end = 8.dp)
                                    )
                                    Text(
                                        text = apartmentInfo.description,
                                        color = Black202020,
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 12.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
