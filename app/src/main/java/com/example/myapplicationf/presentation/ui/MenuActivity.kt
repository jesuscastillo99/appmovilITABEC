package com.example.myapplicationf.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationf.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = getUserName()
        binding.txtUser.text = userName

        binding.btnEstado.setOnClickListener {
            val intent = Intent(this, EstadosActivity::class.java)
            startActivity(intent)
        }

        binding.btnMensaje.setOnClickListener {
            val intent = Intent(this, MensajesActivity::class.java)
            startActivity(intent)
        }

        binding.btnPagos.setOnClickListener {
            val intent = Intent(this, PagosActivity::class.java)
            startActivity(intent)
        }

        binding.btnRenov.setOnClickListener {
            val intent = Intent(this, RenovacionActivity::class.java)
            startActivity(intent)
        }

        binding.btnlogout.setOnClickListener {
            logout()
        }

    }
    private fun getUserName(): String? {
        val sharedPreferences = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        return sharedPreferences.getString("USER_NAME", null)
    }

    private fun logout() {
        // Limpiar SharedPreferences
        val sharedPreferences = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Navegar de vuelta a LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        //Limpiar pila de actividades para no poder regresar
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}