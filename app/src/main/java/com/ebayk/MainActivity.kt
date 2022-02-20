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
            )
        }
    }

    private fun openLocationOnMap(latitude: String, longitude: String) {
        val uri = String.format(Locale.getDefault(), "geo:%s,%s", latitude, longitude)
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
    }

    private fun openDocument(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }
    // todo: проверить на старых девайсах с навбаром
    // todo: проверить с большим текстом
}
