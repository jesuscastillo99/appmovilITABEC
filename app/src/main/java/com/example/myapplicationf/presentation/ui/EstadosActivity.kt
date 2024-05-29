package com.example.myapplicationf.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplicationf.R
import com.example.myapplicationf.databinding.ActivityEstadosBinding
import com.example.myapplicationf.databinding.ActivityMenuBinding

class EstadosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEstadosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstadosBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}