package com.example.myapplicationf.presentation.ui

import android.app.Activity
import android.os.Bundle
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

    }

}