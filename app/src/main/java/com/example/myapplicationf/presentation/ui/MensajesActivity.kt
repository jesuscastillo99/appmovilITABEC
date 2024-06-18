package com.example.myapplicationf.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.myapplicationf.R
import com.example.myapplicationf.data.model.EstadoDeCuentaModelo
import com.example.myapplicationf.data.model.MensajeModelo
import com.example.myapplicationf.databinding.ActivityMensajesBinding
import com.example.myapplicationf.databinding.ActivityMenuBinding
import com.example.myapplicationf.presentation.adapter.EstadoCuentaAdapter
import com.example.myapplicationf.presentation.adapter.MensajeAdapter
import com.example.myapplicationf.presentation.adapter.NoSSLVerificationSocketFactory
import org.json.JSONArray

class MensajesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMensajesBinding
    private lateinit var adapter: MensajeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMensajesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.visibility = View.VISIBLE
        binding.rvMensajes.layoutManager = LinearLayoutManager(this)

        cargarMensajes(getUserId())
    }

    private fun getUserId(): String? {
        val sharedPreferences = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", null)
        Log.d("EstadosActivity", "Retrieved USER_ID: $userId")
        return userId
    }
    private fun cargarMensajes(idpersona: String?) {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvMensajes.visibility = View.GONE

        val queue = Volley.newRequestQueue(this, HurlStack(null, NoSSLVerificationSocketFactory())) // Utiliza la implementación personalizada de SSLSocketFactory
        val url = "https://sistemasiceet.tamaulipas.gob.mx/wsjc/credito.php?mensajes=$idpersona"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("MensajesActivity", "Response: ${getUserId()}")
                Log.d("MensajesActivity", "Response: $response")
                val listaMensajes = parsearRespuesta(response)
                adapter = MensajeAdapter(this,listaMensajes)
                binding.rvMensajes.adapter = adapter

                binding.progressBar.visibility = View.GONE
                binding.rvMensajes.visibility = View.VISIBLE
            },
            { error ->
                binding.progressBar.visibility = View.GONE
                binding.rvMensajes.visibility = View.VISIBLE
                Toast.makeText(this, " ${error.message}", Toast.LENGTH_SHORT).show()
                Log.d("MensajesActivity", "${error.message}")
            })

        queue.add(jsonArrayRequest)
    }

    private fun parsearRespuesta(response: JSONArray): List<MensajeModelo> {
        val listaMensajes = mutableListOf<MensajeModelo>()

        for (i in 0 until response.length()) {
            val item = response.getJSONObject(i)
            val mensaje = MensajeModelo(
                idmensaje = item.getInt("idmensaje"),
                idpersona = item.getInt("idpersona"),
                fecha = item.getString("fecha"),
                mensaje = item.getString("mensaje"),
                fecharespuesta = item.getString("fecharespuesta"),
                respuesta = item.getString("respuesta")
            )
            listaMensajes.add(mensaje)
        }

        return listaMensajes
    }
}