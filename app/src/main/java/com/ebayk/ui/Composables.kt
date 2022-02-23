package com.ebayk.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.LiveData
import coil.compose.rememberImagePainter
import com.ebayk.R
import com.ebayk.model.dto.ApartmentDetails
import com.ebayk.model.dto.Attribute
import com.ebayk.model.dto.Document
import com.ebayk.ui.theme.*
import com.ebayk.model.ApartmentInfoLoadingStatus
import com.ebayk.ext.divideToPairs
import com.ebayk.model.dto.PictureUrls
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.*

@Composable
fun AdvertisementScreen(
    apartmentInfoLiveData: LiveData<ApartmentInfoLoadingStatus>,
    onAddressClick: (String, String) -> Unit,
    onDocumentClick: (String) -> Unit,
    onPictureClick: (String) -> Unit,
    onErrorMessageClick: () -> Unit,
    onShareButtonClick: () -> Unit,
) {
    AppTheme {
        ProvideWindowInsets {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent)
            }
            when (val apartmentInfoLoadingStatus = apartmentInfoLiveData.observeAsState().value) {
                is ApartmentInfoLoadingStatus.Error -> ErrorMessage(onErrorMessageClick)
                is ApartmentInfoLoadingStatus.Loading -> LoadingMessage()
                is ApartmentInfoLoadingStatus.Success -> Advertisement(
                    apartmentInfo = apartmentInfoLoadingStatus.data,
                    onAddressClick = onAddressClick,
                    onDocumentClick = onDocumentClick,
                    onPictureClick = onPictureClick,
                    onShareButtonClick = onShareButtonClick,
                )
                else -> {}
            }
        }
    }
}

@Composable
private fun ErrorMessage(onErrorMessageClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.data_load_error),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 48.dp)
                .clickable { onErrorMessageClick() }
        )
    }
}

@Composable
private fun LoadingMessage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.data_loading),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 48.dp)
        )
    }
}

@Composable
private fun Advertisement(
    apartmentInfo: ApartmentDetails,
    onAddressClick: (String, String) -> Unit,
    onDocumentClick: (String) -> Unit,
    onPictureClick: (String) -> Unit,
    onShareButtonClick: () -> Unit,
) {
    LazyColumn {
        item {
            PhotoPager(
                pictureUrls = apartmentInfo.pictureUrls,
                onPictureClick = onPictureClick,
                onShareButtonClick = onShareButtonClick,
            )
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
                        onAddressClick(
                            apartmentInfo.address.latitude,
                            apartmentInfo.address.longitude,
                        )
                    }
            )
        }
        item {
            AdMetadata(
                postDate = apartmentInfo.formattedPostDate,
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
                AdditionalInfo(
                    documents = apartmentInfo.documents,
                    onDocumentClick = onDocumentClick,
                )
            }
        }
        if (apartmentInfo.description.isNotBlank()) {
            item {
                Description(description = apartmentInfo.description)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun PhotoPager(
    pictureUrls: List<PictureUrls>,
    onPictureClick: (String) -> Unit,
    onShareButtonClick: () -> Unit,
) {
    HorizontalPager(count = pictureUrls.size) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = rememberImagePainter(pictureUrls[page].previewUrl),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .clickable { onPictureClick(pictureUrls[page].fullSizeUrl) }
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .statusBarsPadding()
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Filled.Share),
                    tint = Color.White,
                    contentDescription = "", // todo: content description
                    modifier = Modifier
                        .size(48.dp)
                        .padding(top = 16.dp, end = 16.dp)
                        .clickable { onShareButtonClick() }
                )
            }
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
                    text = "${page + 1}/${pictureUrls.size}",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
            }
        }
    }
}

@Composable
private fun AdMetadata(
    postDate: String,
    visits: Int,
    apartmentId: String,
) {
    Row(Modifier.padding(start = 8.dp, end = 8.dp, top = 24.dp, bottom = 16.dp)) {
        DrawableWrapper(
            drawableStart = R.drawable.ic_calendar,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Text(
                text = postDate,
                color = Gray600,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            )
        }
        DrawableWrapper(drawableStart = R.drawable.ic_visits) {
            Text(
                text = visits.toString(),
                color = Gray600,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            )
        }
        Spacer(Modifier.weight(1f))
        Text(
            text = stringResource(R.string.apartment_id, apartmentId),
            color = Gray600,
            fontSize = 14.sp,
        )
    }
}

@Composable
private fun Details(attributes: List<Attribute>) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(WhiteF2F2F2)
        )
        Text(
            text = stringResource(id = R.string.details),
            color = Black202020,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp)
        )
        attributes.forEach {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(WhiteF2F2F2)
                    .padding(top = 4.dp, start = 8.dp, end = 8.dp) // todo: мне кажется, или она все равно до краев расползается?
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = it.label,
                    color = Black202020,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = if (it.unit.isNullOrBlank()) it.value else "${it.value} ${it.unit}",
                    color = Gray600,
                    fontSize = 14.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun Features(features: List<String>) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(WhiteF2F2F2)
        )
        Text(
            text = stringResource(id = R.string.features),
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
                .padding(top = 4.dp, start = 8.dp, end = 8.dp)
        )
        features.divideToPairs().forEach {
            Row(modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp)) {
                TextWithCheckMark(
                    text = it.first,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                )
                it.second?.let {
                    TextWithCheckMark(
                        text = it,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                    )
                }
            }
        }
    }
}

@Composable
private fun AdditionalInfo(
    documents: List<Document>,
    onDocumentClick: (String) -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(WhiteF2F2F2)
        )
        Text(
            text = stringResource(id = R.string.additional_info),
            color = Black202020,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp)
        )
        documents.forEach {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(WhiteF2F2F2)
                    .padding(top = 4.dp, start = 8.dp, end = 8.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDocumentClick(it.link) }
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                        .fillMaxWidth()
                ) {
                    val (textWithWrapper, chevronDrawable) = createRefs()
                    DrawableWrapper(
                        drawableStart = R.drawable.ic_document,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .constrainAs(textWithWrapper) {
                                start.linkTo(parent.start)
                                end.linkTo(chevronDrawable.start)
                            }
                    ) {
                        Text(
                            text = it.title,
                            color = Black202020,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.chevron),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .constrainAs(chevronDrawable) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                            }
                    )
                }
            }
        }
    }
}

@Composable
private fun Description(description: String) {
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
            text = description,
            color = Black202020,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 12.dp)
        )
    }
}

@Composable
private fun TextWithCheckMark(text: String, modifier: Modifier) {
    DrawableWrapper(
        drawableStart = R.drawable.ic_check,
        modifier = modifier
    ) {
        Text(
            text = text,
            color = Black202020,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}