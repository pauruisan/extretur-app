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
import com.uax.extretur.model.Monumento
import com.uax.extretur.ui.DetailMonumentsActivity

class AdaptadorMonumentos (var listaMonumentos: ArrayList<Monumento>, var contexto: Context) : RecyclerView.Adapter<AdaptadorMonumentos.MyHolder>() {

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen = itemView.findViewById<ImageView>(R.id.monumentImg)
        val boton = itemView.findViewById<Button>(R.id.btnMonumentCard)
        val titulo = itemView.findViewById<TextView>(R.id.txtMonumentTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val vista = LayoutInflater.from(contexto).inflate(R.layout.monument_card, parent, false)
        val holder: MyHolder = MyHolder(vista)
        return holder
    }

    override fun getItemCount(): Int {
        //me dice cuantas filas hay
        return listaMonumentos.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        //Me dice como se comporta cada fila
        val monumento = listaMonumentos[position]
        Glide.with(contexto).load(monumento.imagen).placeholder(R.drawable.fort_24px).error(R.drawable.fort_24px).into(holder.imagen)
        holder.boton.setOnClickListener {
            val intent = Intent(contexto, DetailMonumentsActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val bundle = Bundle()
            bundle.putSerializable("monumento", monumento)
            intent.putExtra("datos", bundle)
            contexto.startActivity(intent)
        }
        holder.titulo.text = monumento.nombre

    }

    fun actualizarLista(listaFiltrada: ArrayList<Monumento>){
        this.listaMonumentos = listaFiltrada
        notifyDataSetChanged()
    }
}