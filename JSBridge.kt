package com.robodouble.pro

import android.content.Context
import android.content.Intent
import android.webkit.JavascriptInterface

class JSBridge(private val context: Context) {

    @JavascriptInterface
    fun enviarSinais(lista: String) {

        val intent = Intent(context, SignalService::class.java)
        intent.putExtra("lista", lista)

        context.startForegroundService(intent)
    }
}
