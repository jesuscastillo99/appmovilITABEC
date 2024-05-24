package com.example.myapplicationf.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplicationf.R
import com.airbnb.lottie.LottieAnimationView
import com.example.myapplicationf.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar y reproducir la animación Lottie
        val lottieAnimationView: LottieAnimationView = binding.lottieAnimationView
        lottieAnimationView.setAnimation("loading.json") // Si el archivo está en assets
        lottieAnimationView.playAnimation()

        // Duración de la splash screen (en milisegundos)
        val splashScreenDuration = 3000L // 3 segundos

        // Pasar a la siguiente actividad después de la duración especificada
        Handler(Looper.getMainLooper()).postDelayed({
            // Iniciar MainActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            // Finalizar la SplashScreenActivity
            finish()
        }, splashScreenDuration)

    }

}