package com.example.myapplicationf.presentation.ui

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.myapplicationf.data.model.MensajeModelo
import com.example.myapplicationf.databinding.ActivityMensajesBinding
import com.example.myapplicationf.presentation.adapter.MensajeAdapter
import com.example.myapplicationf.presentation.adapter.NoSSLVerificationSocketFactory
import org.json.JSONArray
import java.net.URLEncoder

class MensajesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMensajesBinding
    private lateinit var adapter: MensajeAdapter
    private val listaMensajes = mutableListOf<MensajeModelo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMensajesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.visibility = View.VISIBLE
        binding.rvMensajes.layoutManager = LinearLayoutManager(this)

        cargarMensajes(getUserId())

        binding.btnEnviar.setOnClickListener {
            val mensaje = binding.editText.text.toString().trim()
            if (mensaje.isNotEmpty()) {
                enviarMensaje2(getUserId(), mensaje)
            } else {
                Toast.makeText(this, "Por favor, escribe un mensaje", Toast.LENGTH_SHORT).show()
            }
        }
        addKeyboardVisibilityListener()
    }

    private fun addKeyboardVisibilityListener() {
        val rootView = binding.main
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.rootView.height
            val keypadHeight = screenHeight - rect.bottom

            if (keypadHeight > screenHeight * 0.15) {

                binding.rvMensajes.scrollToPosition(adapter.itemCount - 1)
            }
        }
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

        val queue = Volley.newRequestQueue(
            this,
            HurlStack(null, NoSSLVerificationSocketFactory())
        ) // Utiliza la implementación personalizada de SSLSocketFactory
        val url = "https://sistemasiceet.tamaulipas.gob.mx/wsjc/credito.php?mensajes=$idpersona"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("MensajesActivity", "Response: ${getUserId()}")
                Log.d("MensajesActivity", "Response: $response")
                val listaMensajes = parsearRespuesta(response)
                adapter = MensajeAdapter(this, listaMensajes)
                binding.rvMensajes.adapter = adapter

                binding.progressBar.visibility = View.GONE
                binding.rvMensajes.visibility = View.VISIBLE

                // Desplazarse al último elemento
                if (listaMensajes.isNotEmpty()) {
                    binding.rvMensajes.scrollToPosition(listaMensajes.size - 1)
                }
            },
            { error ->
                binding.progressBar.visibility = View.GONE
                binding.rvMensajes.visibility = View.VISIBLE
                Toast.makeText(this, " ${error.message}", Toast.LENGTH_SHORT).show()
                Log.d("MensajesActivity", "${error.message}")
            })

        queue.add(jsonArrayRequest)
    }

    private fun enviarMensaje2(idPersona: String?, mensaje: String) {
        val url = "https://sistemasiceet.tamaulipas.gob.mx/wsjc/credito.php"
        val mensajeCodificado = URLEncoder.encode(mensaje, "UTF-8")
        val urlString = "$url?msj=$mensajeCodificado&id=$idPersona"

        val requestQueue = Volley.newRequestQueue(
            this,
            HurlStack(null, NoSSLVerificationSocketFactory())
        ) // Utiliza la implementación personalizada de SSLSocketFactory

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, urlString, null,
            { response ->
                // Verificar que la respuesta contiene al menos un objeto
                if (response.length() > 0) {
                    val jsonObject = response.getJSONObject(0)
                    val mensajeRespuesta = jsonObject.optString("mensaje", "Mensaje enviado correctamente")
                    Toast.makeText(this, mensajeRespuesta, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Respuesta vacía del servidor", Toast.LENGTH_SHORT).show()
                }
                // Limpiar el EditText
                binding.editText.setText("")
                // Actualizar la lista de mensajes (si es necesario)
                cargarMensajes(idPersona)
            },
            { error ->
                Toast.makeText(this, "Error al enviar el mensaje: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.e("MensajesActivity", "Error al enviar el mensaje", error)
            })

        requestQueue.add(jsonArrayRequest)
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