package com.example.myapplicationf.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.myapplicationf.data.model.EstadoDeCuenta

class EstadoDeCuentaAdapter(private val estadosDeCuenta: List<EstadoDeCuenta>) :
    RecyclerView.Adapter<EstadoDeCuentaAdapter.EstadoDeCuentaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstadoDeCuentaViewHolder {
        val binding = ItemEstadoDeCuentaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EstadoDeCuentaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EstadoDeCuentaViewHolder, position: Int) {
        holder.bind(estadosDeCuenta[position])
    }

    override fun getItemCount(): Int = estadosDeCuenta.size

    class EstadoDeCuentaViewHolder(private val binding: ItemEstadoDeCuentaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(estadoDeCuenta: EstadoDeCuenta) {
            binding.tvId.text = estadoDeCuenta.id.toString()
            binding.tvFechaVencimiento.text = estadoDeCuenta.fechaVencimiento
            binding.tvMonto.text = estadoDeCuenta.monto.toString()
            binding.tvSaldo.text = estadoDeCuenta.saldo.toString()
            binding.tvEstatus.text = estadoDeCuenta.estatus
        }
    }
}