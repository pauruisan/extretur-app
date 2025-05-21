package com.uax.extretur.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uax.extretur.R
import com.uax.extretur.model.Gastro
import com.uax.extretur.ui.DetailMonumentsActivity

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
        val plato = listaGastro[position]
        Glide.with(contexto).load(plato.imagen).placeholder(R.drawable.chef_hat_24px).error(R.drawable.chef_hat_24px).into(holder.imagen)
        holder.boton.setOnClickListener {
            val intent = Intent(contexto, DetailMonumentsActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val bundle = Bundle()
            bundle.putSerializable("plato", plato)
            intent.putExtra("datos", bundle)
            contexto.startActivity(intent)
        }
        holder.titulo.text = plato.nombre
        holder.descripcion.text = plato.descripcion
    }


    override fun getItemCount(): Int {
        return listaGastro.size
    }

    fun actualizarLista(listaFiltrada: ArrayList<Gastro>) {
        this.listaGastro = listaFiltrada
        notifyDataSetChanged()
    }
}