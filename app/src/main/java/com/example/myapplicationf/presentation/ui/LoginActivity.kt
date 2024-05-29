package com.example.myapplicationf.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationf.databinding.ActivityLoginBinding
import com.example.myapplicationf.data.model.RetrofitClient
import com.example.myapplicationf.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRegi.setOnClickListener {
            val intent = Intent(this, RecuperarActivity::class.java)
            startActivity(intent)
        }
        binding.btnIniciarSesion.setOnClickListener {
            val email = binding.edtCorreo.text.toString().trim()
            val password = binding.edtContra.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            } else {
                Toast.makeText(this, "Por favor, ingrese todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun login(email: String, password: String) {
        RetrofitClient.instance.login(email, password)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val user = response.body()
                        Toast.makeText(this@LoginActivity, "Bienvenido ${user?.completo}", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MenuActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Manejar errores HTTP aquí (como credenciales incorrectas)
                        when (response.code()) {
                            401 -> Toast.makeText(this@LoginActivity, "Datos incorrectos", Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(this@LoginActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }


}