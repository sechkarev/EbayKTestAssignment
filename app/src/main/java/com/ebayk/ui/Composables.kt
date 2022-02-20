package com.ebayk.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.ebayk.R
import com.ebayk.ui.theme.Black80202020
import com.ebayk.ui.theme.DrawableWrapper
import com.ebayk.ui.theme.Gray600
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