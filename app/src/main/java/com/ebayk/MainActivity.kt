package com.ebayk

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.ebayk.ui.AdvertisementScreen
import com.ebayk.viewmodel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

@ExperimentalPagerApi
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AdvertisementScreen(
                apartmentInfoLiveData = viewModel.apartmentInfo,
                onAddressClick = this::openLocationOnMap,
                onDocumentClick = this::openDocument,
                onPictureClick = this::openImage,
                onErrorMessageClick = viewModel::onErrorMessageClick,
                onShareButtonClick = this::shareAd,
            )
        }
    }

    private fun shareAd() {
        // todo: share ad
    }

    private fun openImage(imageUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.parse(imageUrl), "image/*")
        }
        startActivity(intent)
    }

    private fun openLocationOnMap(latitude: String, longitude: String) {
        val uri = String.format(Locale.getDefault(), "geo:%s,%s", latitude, longitude)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(Intent.createChooser(intent, getString(R.string.select_app_to_open_location)))
    }

    private fun openDocument(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(Intent.createChooser(intent, getString(R.string.select_app_to_open_pdf)))
    }
    // todo: навбар не должен быть прозрачным; статус бару хорошо бы темнеть, когда я не смотрю картинки.
}
