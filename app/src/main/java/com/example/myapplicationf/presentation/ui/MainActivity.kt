package com.example.myapplicationf.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationf.R
import com.example.myapplicationf.data.dao.EscuelaDao
import com.example.myapplicationf.data.model.EscuelaModel
import com.example.myapplicationf.databinding.ActivityMainBinding
import com.example.myapplicationf.presentation.adapter.EscuelaAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.pcs.libpcs.UtilsMessage

class MainActivity : AppCompatActivity(), EscuelaAdapter.IOnClickListener {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    override fun onResume() {
        super.onResume()

        leerEscuela(binding.etBuscar.text.toString().trim())
    }

    private fun initListener(){
        binding.rvLista.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter=EscuelaAdapter(this@MainActivity)
        }

        binding.etBuscar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                leerEscuela(binding.etBuscar.text.toString().trim())
            }

        })

        binding.fabNuevo.setOnClickListener {
            startActivity(
                Intent(this, OperacionEscuelaActivity::class.java)
            )
        }
    }

    private fun leerEscuela(dato: String){
        var msgError=""

        lifecycleScope.launch {
            binding.progressBar.isVisible=true

            val result = withContext(Dispatchers.IO) {
                try {
                    EscuelaDao.listar(dato)
                } catch (e: Exception){
                    msgError=e.message.orEmpty()
                    emptyList<EscuelaModel>()
                }
            }

            binding.progressBar.isVisible=false

            if(msgError.isNotEmpty()){
                UtilsMessage.showAlertOk(
                    "ERRORxd", msgError, this@MainActivity
                )
            }

            (binding.rvLista.adapter as EscuelaAdapter).setList(result)
        }
    }

    private fun eliminar(model: EscuelaModel){
        var msgError=""

        lifecycleScope.launch {
            binding.progressBar.isVisible=true

            withContext(Dispatchers.IO) {
                try {
                    EscuelaDao.eliminar(model)
                } catch (e: Exception){
                    msgError=e.message.orEmpty()

                }
            }

            binding.progressBar.isVisible=false

            if(msgError.isNotEmpty()){
                UtilsMessage.showAlertOk(
                    "ERROR", msgError, this@MainActivity
                )
                return@launch
            }

            UtilsMessage.showToast(this@MainActivity, "Registro eliminado")
            leerEscuela(binding.etBuscar.text.toString().trim())
        }
    }

    override fun clickEditar(model: EscuelaModel) {
        startActivity(
            Intent(this, OperacionEscuelaActivity::class.java).apply {
                putExtra("id", model.id)
                putExtra("idpersona", model.idpersona)
                putExtra("municipio", model.municipio)
            }
        )
    }

    override fun clickEliminar(model: EscuelaModel) {
        MaterialAlertDialogBuilder(this).apply {
            setCancelable(false)
            setTitle("ELIMINAR")
            setMessage("¿Desea eliminar el registro: ${model.municipio}?")

            setPositiveButton("SI"){ dialog, _ ->
                eliminar(model)
                dialog.dismiss()
            }

            setNegativeButton("NO"){ dialog, _ ->
                dialog.cancel()
            }
        }.create().show()
    }
}

