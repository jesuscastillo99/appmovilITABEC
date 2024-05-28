package com.example.myapplicationf.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplicationf.R
import com.example.myapplicationf.databinding.ActivityLoginBinding
import com.example.myapplicationf.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEstado.setOnClickListener {
            val intent = Intent(this, EstadosActivity::class.java)
            startActivity(intent)
        }

        binding.btnMensaje.setOnClickListener {
            val intent = Intent(this, MensajesActivity::class.java)
            startActivity(intent)
        }

        binding.btnPagos.setOnClickListener {
            val intent = Intent(this,PagosActivity::class.java)
            startActivity(intent)
        }

        binding.btnRenov.setOnClickListener {
            val intent = Intent(this, RenovacionActivity::class.java)
            startActivity(intent)
        }


    }
}