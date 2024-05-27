package com.example.myapplicationf.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationf.databinding.ActivityRecuperarBinding

class RecuperarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecuperarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecuperarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIni.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}