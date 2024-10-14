package com.example.myapplicationf.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationf.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    companion object {
        private const val PREF_NAME = "USER_PREF"
        private const val USER_NAME_KEY = "USER_NAME"

        fun getUserName(context: Context): String? {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(USER_NAME_KEY, null)
        }
    }

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

        // Ocultar los botones btnPagos y btnRenov
        binding.btnPagos.visibility = View.GONE
        binding.btnRenov.visibility = View.GONE

       /* ELIMINAR LINEAS DE CODIGO CUANDO YA SE USEN LOS BOTONES
        binding.btnPagos.setOnClickListener {
            val intent = Intent(this, PagosActivity::class.java)
            startActivity(intent)
        }

        binding.btnRenov.setOnClickListener {
            val intent = Intent(this, RenovacionActivity::class.java)
            startActivity(intent)
        }
*/
        binding.btnlogout.setOnClickListener {
            logout()
        }

    }
     fun getUserName(): String? {
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