package com.uax.extretur.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uax.extretur.R
import com.uax.extretur.model.Gastro

class AdaptadorGastro(var listaGastro: ArrayList<Gastro>, private var contexto: Context) :
    RecyclerView.Adapter<AdaptadorGastro.MyHolder>() {

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen = itemView.findViewById<ImageView>(R.id.gastroImg)
        val boton = itemView.findViewById<Button>(R.id.btnGastroCard)
        val titulo = itemView.findViewById<TextView>(R.id.txtGastroTitle)
        val descripcion = itemView.findViewById<TextView>(R.id.txtGastroDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val vista = LayoutInflater.from(contexto).inflate(R.layout.gastro_card, parent, false)
        val holder = MyHolder(vista)
        return holder
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val actividad = listaGastro[position]
        holder.imagen.setImageResource(actividad.imagen)
        holder.boton
        holder.titulo.text = actividad.nombre
        holder.descripcion.text = actividad.descripcion
    }


    override fun getItemCount(): Int {
        return listaGastro.size
    }

    fun actualizarLista(listaFiltrada: ArrayList<Gastro>) {
        this.listaGastro = listaFiltrada
        notifyDataSetChanged()
    }
}