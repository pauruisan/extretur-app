package com.uax.extretur.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.uax.extretur.R
import com.uax.extretur.model.Forum
import com.uax.extretur.ui.DetailForumActivity

class AdaptadorForum(var listaForum: ArrayList<Forum>, var contexto: Context) : RecyclerView.Adapter<AdaptadorForum.MyHolder>() {

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boton = itemView.findViewById<Button>(R.id.btnForoCard)
        val titulo = itemView.findViewById<TextView>(R.id.txtForoTitle)
        val creador = itemView.findViewById<TextView>(R.id.txtForoCreator)
        val fecha = itemView.findViewById<TextView>(R.id.txtForoDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorForum.MyHolder {
        val vista = LayoutInflater.from(contexto).inflate(R.layout.foro_card, parent, false)
        val holder: AdaptadorForum.MyHolder = MyHolder(vista)
        return holder
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val tema = listaForum[position]
        holder.boton.setOnClickListener {
            val intent = Intent(contexto, DetailForumActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("tema", tema)
            intent.putExtras(bundle)
                contexto.startActivity(intent)
        }
        holder.titulo.text = tema.titulo
        holder.creador.text = tema.creador
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