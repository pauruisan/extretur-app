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
import com.uax.extretur.model.Natur

class AdaptadorNatur(var listaNatur: ArrayList<Natur>, var contexto: Context) : RecyclerView.Adapter<AdaptadorNatur.MyHolder>() {

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen = itemView.findViewById<ImageView>(R.id.naturImg)
        val boton = itemView.findViewById<Button>(R.id.btnNaturCard)
        val titulo = itemView.findViewById<TextView>(R.id.txtNaturTitle)
        val descripcion = itemView.findViewById<TextView>(R.id.txtNaturDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val vista = LayoutInflater.from(contexto).inflate(R.layout.natur_card, parent, false)
        val holder: MyHolder = MyHolder(vista)
        return holder
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val actividad = listaNatur[position]
        holder.imagen.setImageResource(actividad.imagen)
        holder.boton
        holder.titulo.text = actividad.nombre
        holder.descripcion.text = actividad.descripcion
    }


    override fun getItemCount(): Int {
        return listaNatur.size
    }

    public fun actualizarLista(listaFiltrada: ArrayList<Natur>){
        this.listaNatur = listaFiltrada
        notifyDataSetChanged()
    }
}