package com.ebayk.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun DrawableWrapper(
    modifier: Modifier = Modifier,
    @DrawableRes drawableStart: Int,
    content: @Composable () -> Unit,
) {
    ConstraintLayout(modifier) {
        val (drawableStartRef, contentRef) = createRefs()
        Box(
            Modifier.constrainAs(contentRef) {
                start.linkTo(drawableStartRef.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            content()
        }
        Image(
            painter = painterResource(id = drawableStart),
            contentDescription = null,
            Modifier.constrainAs(drawableStartRef) {
                top.linkTo(contentRef.top)
                bottom.linkTo(contentRef.bottom)
                start.linkTo(parent.start)
            }
        )
    }
}