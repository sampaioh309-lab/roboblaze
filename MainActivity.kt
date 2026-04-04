package com.robodouble.pro

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        setContentView(webView)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        // 🔗 Conecta com JS
        webView.addJavascriptInterface(JSBridge(this), "Android")

        // 🔥 COLOCA SEU LINK AQUI
        webView.loadUrl("https://SEU-SITE-AQUI.com")

        // 🚀 Inicia o serviço
        val intent = Intent(this, SignalService::class.java)
        startForegroundService(intent)
    }
}
