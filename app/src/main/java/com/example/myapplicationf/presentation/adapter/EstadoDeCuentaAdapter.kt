package com.example.myapplicationf.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.myapplicationf.R
import com.example.myapplicationf.data.model.EstadoDeCuentaModelo
import com.example.myapplicationf.databinding.ItemEstadoDeCuentaBinding


class EstadoCuentaAdapter(private val listaEstados: List<EstadoDeCuentaModelo>) : RecyclerView.Adapter<EstadoCuentaAdapter.EstadoCuentaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstadoCuentaViewHolder {
        val binding = ItemEstadoDeCuentaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EstadoCuentaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EstadoCuentaViewHolder, position: Int) {
        val estado = listaEstados[position]
        holder.bind(estado)
        // Alternar colores
        if (position % 2 == 0) {
            holder.binding.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        } else {
            holder.binding.txtid.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
            holder.binding.txtfec.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
            holder.binding.txtmon.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
            holder.binding.txtsal.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
            holder.binding.txtest.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
            holder.binding.tvId.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
            holder.binding.fechaVencimiento.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
            holder.binding.monto.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
            holder.binding.saldo.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
            holder.binding.estatus.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
            holder.binding.fondo1.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.itabec))
            holder.binding.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.itabec))
        }
    }

    override fun getItemCount(): Int {
        return listaEstados.size
    }

    inner class EstadoCuentaViewHolder( val binding: ItemEstadoDeCuentaBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(estado: EstadoDeCuentaModelo) {
            binding.apply {
                tvId.text = estado.Id.toString()
                fechaVencimiento.text = estado.fechavencimiento
                monto.text = estado.monto.toString()
                saldo.text = estado.Saldo.toString()
                estatus.text = estado.estatus
            }
        }
    }
}









