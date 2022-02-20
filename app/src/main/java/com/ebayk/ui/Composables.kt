package com.ebayk.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.ebayk.R
import com.ebayk.dto.Attribute
import com.ebayk.dto.Document
import com.ebayk.ui.theme.*
import com.ebayk.util.divideToPairs
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PhotoPager(pictureUrls: List<String>) {
    HorizontalPager(count = pictureUrls.size) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = rememberImagePainter(pictureUrls[page]),
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
fun AdMetadata(
    postDate: String,
    visits: Int,
    apartmentId: String,
) {
    Row(Modifier.padding(start = 8.dp, end = 8.dp, top = 24.dp, bottom = 24.dp)) {
        DrawableWrapper(
            drawableStart = R.drawable.ic_calendar,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Text(
                text = postDate.substringBefore("T").let {
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it)?.let { date ->
                        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
                    }
                }.orEmpty(),
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
fun Details(attributes: List<Attribute>) {
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
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp)) {
                Text(
                    text = it.label,
                    color = Black202020,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = if (it.unit.isNullOrBlank()) it.value else "${it.value} ${it.unit}",
                    color = Gray600,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun Features(features: List<String>) {
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
                TextWithCheckMark(text = it.first, modifier = Modifier.weight(1f))
                it.second?.let {
                    TextWithCheckMark(text = it, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun AdditionalInfo(documents: List<Document>) {
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
                    .clickable {
                        // todo: on pdf clicked
                    }
            ) {
                Row(Modifier.padding(vertical = 8.dp, horizontal = 8.dp)) {
                    DrawableWrapper(
                        drawableStart = R.drawable.ic_document,
                    ) {
                        Text(
                            text = it.title,
                            color = Black202020,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp),
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.chevron),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
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