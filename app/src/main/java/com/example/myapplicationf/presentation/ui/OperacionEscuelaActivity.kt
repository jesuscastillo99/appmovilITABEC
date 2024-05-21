package com.example.myapplicationf.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.myapplicationf.R
import com.example.myapplicationf.data.dao.EscuelaDao
import com.example.myapplicationf.data.model.EscuelaModel
import com.example.myapplicationf.databinding.ActivityOperacionEscuelaBinding
import com.example.myapplicationf.presentation.adapter.EscuelaAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.pcs.libpcs.UtilsCommon
import pe.pcs.libpcs.UtilsMessage

class OperacionEscuelaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOperacionEscuelaBinding

    private var _id=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOperacionEscuelaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.extras != null)
            obtenerEscuela()

        binding.fabGrabar.setOnClickListener {
            if(binding.etIdpersona.text.toString().trim().isEmpty() ||
                binding.etMunicipio.text.toString().trim().isEmpty()){
                UtilsMessage.showToast(this, "Todos los datos son obligatorios")
                return@setOnClickListener
            }

            grabar(
                EscuelaModel().apply {
                    id= _id
                    idpersona=binding.etIdpersona.text.toString().trim().toInt()
                    municipio=binding.etMunicipio.text.toString().trim()
                }
            )
        }
    }

    private fun obtenerEscuela(){
        _id=intent.extras?.getInt("id", 0)?:0
        binding.etIdpersona.setText(intent.extras?.getInt("idpersona", 0).toString())
        binding.etMunicipio.setText(intent.extras?.getString("municipio", ""))

    }

    private fun grabar(model: EscuelaModel){
        var msgError= ""

        lifecycleScope.launch {
            binding .progressBar.isVisible=true

            withContext(Dispatchers.IO){
                try {
                    EscuelaDao.grabar(model)
                } catch (e: Exception){
                    msgError=e.message.orEmpty()
                }
            }

            binding.progressBar.isVisible=false

            if(msgError.isNotEmpty()){
                UtilsMessage.showAlertOk("ERROR", msgError, this@OperacionEscuelaActivity)
                return@launch
            }

            UtilsCommon.cleanEditText(binding.root.rootView)
            binding.etMunicipio.requestFocus()
            _id=0
            UtilsMessage.showToast(this@OperacionEscuelaActivity, "Datos grabados")
        }
    }

}