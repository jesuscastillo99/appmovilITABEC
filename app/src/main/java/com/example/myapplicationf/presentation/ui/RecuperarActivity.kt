package com.example.myapplicationf.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationf.R
import com.example.myapplicationf.databinding.ActivityRecuperarBinding
import android.text.InputFilter
import android.text.Spanned

class RecuperarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecuperarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecuperarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIni.setOnClickListener {
            finish() // Cierra esta actividad y regresa a la anterior

            // Aplica las animaciones de entrada y salida
            overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
        }

        // En tu Activity o Fragment
        val editTextCurp = binding.edtCurp


    }


}

// Clase personalizada para restringir el formato CURP
class CurpInputFilter : InputFilter {

    private val curpPattern = Regex("[A-Z]{4}\\d{6}[A-Z]{6}\\d{2}")

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        // Concatenar el nuevo texto con el existente para validar
        val newText = dest?.toString()?.substring(0, dstart) + source + dest?.toString()?.substring(dend)

        // Solo permitir caracteres válidos y ajustar el patrón de CURP
        return if (newText.length <= 18 && newText.matches(curpPattern)) {
            null // Deja pasar el texto
        } else {
            ""  // Bloquea el texto si no coincide con el patrón
        }
    }
}



