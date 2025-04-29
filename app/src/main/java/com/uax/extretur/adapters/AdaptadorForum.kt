package com.uax.extretur.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uax.extretur.R
import com.uax.extretur.model.Forum

class AdaptadorForum(var listaForum: ArrayList<Forum>, var contexto: Context) :
    RecyclerView.Adapter<AdaptadorForum.MyHolder>() {

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boton = itemView.findViewById<Button>(R.id.btnForoCard)
        val titulo = itemView.findViewById<TextView>(R.id.txtForoTitle)
        val descripcion = itemView.findViewById<TextView>(R.id.txtForoDesc)
        val fecha = itemView.findViewById<TextView>(R.id.txtForoDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorForum.MyHolder {
        val vista = LayoutInflater.from(contexto).inflate(R.layout.foro_card, parent, false)
        val holder: AdaptadorForum.MyHolder = MyHolder(vista)
        return holder
    }

    override fun onBindViewHolder(holder: AdaptadorForum.MyHolder, position: Int) {
        val tema = listaForum[position]
        holder.boton
        holder.titulo.text = tema.titulo
        holder.descripcion.text
        holder.fecha.text = tema.fecha
    }


    override fun getItemCount(): Int {
        return listaForum.size
    }

    fun actualizarLista(listaFiltrada: ArrayList<Forum>) {
        this.listaForum = listaFiltrada
        notifyDataSetChanged()
    }
}