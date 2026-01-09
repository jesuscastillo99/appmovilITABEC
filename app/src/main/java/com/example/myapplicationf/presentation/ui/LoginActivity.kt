package com.example.myapplicationf.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplicationf.R
import com.example.myapplicationf.databinding.ActivityLoginBinding

import com.example.myapplicationf.presentation.adapter.NoSSLVerificationSocketFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.edtCorreo.setText(setCorreo())


        binding.btnIniciarSesion.setOnClickListener {
            val email = binding.edtCorreo.text.toString().trim()
            val password = binding.edtContra.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                validarCredenciales(email, password)
            } else {
                Toast.makeText(this, "Por favor, ingrese todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRecu.setOnClickListener {
            // Crear un Intent para iniciar RecuperarActivity
            val intent = Intent(this, RecuperarActivity::class.java)

            // Iniciar la nueva actividad
            startActivity(intent)
            // Aplica las animaciones de entrada y salida
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }


    private fun validarCredenciales(usuario: String, password: String) {
        val queue = Volley.newRequestQueue(this, HurlStack(null, NoSSLVerificationSocketFactory())) // Utiliza la implementación personalizada de SSLSocketFactory
        val url = "https://sistemasiceet.tamaulipas.gob.mx/wsjc/credito.php?usuario=$usuario&pas=$password"

        val jsonObjectRequest = JsonObjectRequest(com.android.volley.Request.Method.GET, url, null,
            { response ->

                // Verificar si la respuesta contiene datos
                if (response.length() > 0) {
                    val completo= response.getString("completo")
                    val idpersona = response.getString("IdPersona")
                    saveUserName(completo, idpersona, binding.edtCorreo.text.toString())
                    // Credenciales válidas, navegar a otra actividad
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Bienvenido: $completo", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    // Credenciales inválidas
                    Toast.makeText(this, "Credenciales incorrectas. Inténtelo de nuevo.", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error de red: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.d("LoginActivity", "Error: ${error.message}")
            })

        queue.add(jsonObjectRequest)
    }

    private fun saveUserName(userName: String, idPersona: String, correo: String) {
        val sharedPreferences = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("USER_NAME", userName)
        editor.putString("USER_ID", idPersona)
        editor.putString("CORREO", correo)
        editor.apply()
    }

    private fun setCorreo(): String? {
        val sharedPreferences = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        val userCorreo = sharedPreferences.getString("CORREO", null)
        return userCorreo
    }


}