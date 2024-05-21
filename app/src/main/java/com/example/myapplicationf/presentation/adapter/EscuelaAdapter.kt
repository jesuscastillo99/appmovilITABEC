package com.example.myapplicationf.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationf.data.model.EscuelaModel
import com.example.myapplicationf.databinding.ItemsEscuelaBinding

class EscuelaAdapter(
    private val iOnClickListener: IOnClickListener
) : RecyclerView.Adapter<EscuelaAdapter.EscuelaViewHolder>() {

    private var lista= emptyList<EscuelaModel>()

    interface IOnClickListener{
        fun clickEditar(model: EscuelaModel)
        fun clickEliminar(model: EscuelaModel)
    }

    inner class EscuelaViewHolder(private val binding: ItemsEscuelaBinding): RecyclerView.ViewHolder(binding.root){
        fun enlazar(model: EscuelaModel){
            binding.tvTitulo.text= model.id.toString()
            binding.tvDato1.text= model.idpersona.toString()
            binding.tvDato2.text= model.municipio

            binding.ibEliminar.setOnClickListener { iOnClickListener.clickEliminar(model) }
            binding.ibEditar.setOnClickListener { iOnClickListener.clickEditar(model) }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EscuelaViewHolder {
       return EscuelaViewHolder(
           ItemsEscuelaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
       )
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: EscuelaViewHolder, position: Int) {
        holder.enlazar(lista[position])
    }

    fun setList(listaEscuela: List<EscuelaModel>){
        this.lista = listaEscuela
        notifyDataSetChanged()
    }
}