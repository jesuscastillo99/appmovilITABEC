package com.example.myapplicationf.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplicationf.databinding.ActivityMenuBinding
import com.example.myapplicationf.databinding.ActivityPagosBinding

class PagosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPagosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagosBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}