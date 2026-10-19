package com.example.keyapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    private val tgUsername = "depsekkik"
    private val validKey = "MY-KEY-888"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val screenKey = findViewById<LinearLayout>(R.id.screenKey)
        val screenPromo = findViewById<LinearLayout>(R.id.screenPromo)
        val inputKey = findViewById<EditText>(R.id.inputKey)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnBuy = findViewById<Button>(R.id.btnBuy)
        val tvPrice = findViewById<TextView>(R.id.tvPrice)
        val tvTimer = findViewById<TextView>(R.id.tvTimer)

        btnLogin.setOnClickListener {
            val key = inputKey.text.toString().trim()
            if (key == validKey) {
                screenKey.visibility = View.GONE
                showPromo(screenPromo, tvPrice, tvTimer)
            } else {
                Toast.makeText(this, "Неверный ключ", Toast.LENGTH_SHORT).show()
            }
        }

        btnBuy.setOnClickListener { openTelegram() }
    }

    private fun showPromo(screenPromo: LinearLayout, tvPrice: TextView, tvTimer: TextView) {
        screenPromo.visibility = View.VISIBLE
        
        val pulse = AnimationUtils.loadAnimation(this, android.R.anim.fade_in).apply {
            duration = 900
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
        tvPrice.startAnimation(pulse)

        object : CountDownTimer(15 * 60 * 1000L, 1000L) {
            override fun onTick(ms: Long) {
                tvTimer.text = String.format("%02d:%02d", ms / 60000, (ms % 60000) / 1000)
            }
            override fun onFinish() { tvTimer.text = "00:00" }
        }.start()
    }

    private fun openTelegram() {
        val text = "Хочу купить доступ за 888 ₽"
        val encoded = Uri.encode(text)
        val tgUri = Uri.parse("tg://resolve?domain=$tgUsername&text=$encoded")
        val webUri = Uri.parse("https://t.me/$tgUsername?text=$encoded")
        try {
            startActivity(Intent(Intent.ACTION_VIEW, tgUri))
        } catch (e: Exception) {
            startActivity(Intent(Intent.ACTION_VIEW, webUri))
        }
    }
}
