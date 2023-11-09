package es.fjmarlop.pizzettApp.vistas.cliente.conditions

import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import es.fjmarlop.pizzeta.R

@Composable
fun TermsOfUses() {
    Box(modifier = Modifier
        .padding(8.dp)
        .background(Color.White)) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
                    webChromeClient = WebChromeClient()
                    loadDataWithBaseURL(
                        null,
                        context.getString(R.string.html_termsOfUse),
                        "text/html",
                        "utf-8",
                        null
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize()
        )
    }
}