package com.example.myapplicationf.presentation.ui

import android.os.Bundle
import android.view.View
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationf.databinding.ActivityEstadosBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.myapplicationf.presentation.adapter.EstadoCuentaAdapter

import com.android.volley.Request
import com.android.volley.toolbox.HurlStack
import com.example.myapplicationf.data.model.EstadoDeCuentaModelo
import com.example.myapplicationf.presentation.adapter.NoSSLVerificationSocketFactory

import org.json.JSONArray
import javax.net.ssl.*


class EstadosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEstadosBinding
    private lateinit var adapter: EstadoCuentaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstadosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.visibility = View.VISIBLE
        binding.rvLista.layoutManager = LinearLayoutManager(this)

        cargarDatos(getUserId())
    }

    private fun getUserId(): String? {
        val sharedPreferences = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", null)
        Log.d("EstadosActivity", "Retrieved USER_ID: $userId")
        return userId
    }

    private fun cargarDatos(edocta: String?) {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvLista.visibility = View.GONE

        val queue = Volley.newRequestQueue(this, HurlStack(null, NoSSLVerificationSocketFactory())) // Utiliza la implementación personalizada de SSLSocketFactory
        val url = "https://sistemasiceet.tamaulipas.gob.mx/wsjc/credito.php?edocta=$edocta"

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                val listaEstados = parsearRespuesta(response)
                adapter = EstadoCuentaAdapter(listaEstados)
                binding.rvLista.adapter = adapter

                binding.progressBar.visibility = View.GONE
                binding.rvLista.visibility = View.VISIBLE
            },
            { error ->
                binding.progressBar.visibility = View.GONE
                binding.rvLista.visibility = View.VISIBLE
                Toast.makeText(this, " ${error.message}", Toast.LENGTH_SHORT).show()
                Log.d("EstadosActivity", "${error.message}")
            })

        queue.add(jsonArrayRequest)
    }

    private fun parsearRespuesta(response: JSONArray): List<EstadoDeCuentaModelo> {
        val listaEstados = mutableListOf<EstadoDeCuentaModelo>()

        for (i in 0 until response.length()) {
            val item = response.getJSONObject(i)
            val estado = EstadoDeCuentaModelo(
                Id = item.getInt("Id"),
                fechavencimiento = item.getString("fechavencimiento"),
                monto = item.getDouble("monto"),
                Saldo = item.getDouble("Saldo"),
                estatus = item.getString("estatus")
            )
            listaEstados.add(estado)
        }

        return listaEstados
    }
}




