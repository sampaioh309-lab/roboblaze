package com.robodouble.pro

import android.app.*
import android.content.Intent
import android.os.*
import androidx.core.app.NotificationCompat
import kotlin.random.Random
import java.util.*

class SignalService : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private var sinais = mutableListOf<Sinal>()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.getStringExtra("lista")?.let {
            processarLista(it)
        }

        startForeground(1, criarNotificacao("Robô ativo"))

        iniciarLoop()

        return START_STICKY
    }

    private fun processarLista(lista: String) {

        sinais.clear()

        val linhas = lista.split("\n")

        for (linha in linhas) {

            if (linha.isEmpty()) continue

            val partes = linha.split(" → ")
            val horario = partes[0]
            val sinal = partes[1]

            val (h, m) = horario.split(":").map { it.toInt() }

            val alvo = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, h)
                set(Calendar.MINUTE, m)
                set(Calendar.SECOND, 0)
            }.timeInMillis

            sinais.add(Sinal(horario, sinal, alvo))
        }
    }

    private fun iniciarLoop() {

        handler.post(object : Runnable {
            override fun run() {

                val agora = System.currentTimeMillis()

                for (s in sinais) {

                    val diff = s.alvo - agora

                    // 1 MINUTO ANTES
                    if (diff in 59000..60000 && !s.aviso1) {
                        enviarAlerta("⏰ 1 MIN → ${s.horario} ${s.sinal}")
                        s.aviso1 = true
                    }

                    // 10 SEGUNDOS ANTES
                    if (diff in 9000..10000 && !s.aviso2) {
                        enviarAlerta("🚨 AGORA → ${s.horario} ${s.sinal}")
                        s.aviso2 = true
                    }
                }

                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun enviarAlerta(msg: String) {

        val notification = NotificationCompat.Builder(this, "robo_channel")
            .setContentTitle("🚨 ROBÔ DOUBLE")
            .setContentText(msg)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_ALL)
            .build()

        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(Random.nextInt(), notification)
    }

    private fun criarNotificacao(texto: String): Notification {

        val channelId = "robo_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Robô",
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("🤖 Robô rodando")
            .setContentText(texto)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

data class Sinal(
    val horario: String,
    val sinal: String,
    val alvo: Long,
    var aviso1: Boolean = false,
    var aviso2: Boolean = false
)
