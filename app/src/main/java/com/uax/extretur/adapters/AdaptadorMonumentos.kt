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
import com.uax.extretur.model.Monumento

class AdaptadorMonumentos (var listaMonumentos: ArrayList<Monumento>, var contexto: Context) : RecyclerView.Adapter<AdaptadorMonumentos.MyHolder>() {

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //saco cada uno de los elementos q hay dentro del xml (patron de la fila)
        val imagen = itemView.findViewById<ImageView>(R.id.monumentImg)
        val boton = itemView.findViewById<Button>(R.id.btnMonumentCard)
        val titulo = itemView.findViewById<TextView>(R.id.txtMonumentTitle)
        val descripcion = itemView.findViewById<TextView>(R.id.txtMonumentDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        //Crear el patron de las filas
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
        holder.imagen.setImageResource(monumento.imagen)
        holder.boton
        holder.titulo.text = monumento.nombre
        holder.descripcion.text = monumento.descripcion
    }

    public fun actualizarLista(listaFiltrada: ArrayList<Monumento>){
        this.listaMonumentos = listaFiltrada
        notifyDataSetChanged()
    }
}