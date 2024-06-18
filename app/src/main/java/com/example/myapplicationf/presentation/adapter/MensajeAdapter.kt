package com.example.myapplicationf.presentation.adapter


import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationf.data.model.MensajeModelo
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.myapplicationf.R
import com.example.myapplicationf.databinding.ItemMensajeBinding
import com.example.myapplicationf.presentation.ui.MenuActivity

class MensajeAdapter(private val context: Context, private val listaMensajes: List<MensajeModelo>) : RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val binding = ItemMensajeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MensajeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val mensaje = listaMensajes[position]
        holder.bind(mensaje)
//        // Alternar colores
//        if (position % 2 == 0) {
//            holder.binding.fondo1.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.itabec))
//            holder.binding.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.itabec3))
//        } else {
//            holder.binding.fondo2.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.itabec2))
//            holder.binding.cardView2.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.itabec2))
//        }

    }

    override fun getItemCount(): Int {
        return listaMensajes.size
    }



    inner class MensajeViewHolder( val binding: ItemMensajeBinding)  : RecyclerView.ViewHolder(binding.root) {

        fun bind(mensaje: MensajeModelo) {
            binding.apply {
                val userName = MenuActivity.getUserName(context)
                mensajeu.text = mensaje.mensaje
                fecham.text = mensaje.fecha
                usuario.text = userName
                respuesta.text = mensaje.respuesta
                fechar.text = mensaje.fecharespuesta
            }
        }
    }
}