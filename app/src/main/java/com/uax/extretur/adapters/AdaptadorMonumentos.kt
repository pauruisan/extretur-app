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
        TODO("Not yet implemented")
        //Crear el patron de las filas

        return listaMonumentos.size
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        //me dice cuantas filas hay
        return listaMonumentos[position]
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        TODO("Not yet implemented")
        //Me dice como se comporta cada fila
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //el aspecto de cada fila -> necesito xml
        val view: View = LayoutInflater.from(contexto).inflate(R.layout.monument_card, parent, false)
        val imagen: ImageView = view.findViewById<ImageView>(R.id.//idImageView)
        val titulo: TextView = view.findViewById<TextView>(R.id.//idTextView)

            //necesito la marca para capturar sus propiedades
            val monumento: Monumento = listaMonumentos[position]
            imagen.setImageResource(monumento.imagen)
            titulo.text = monumento.nombre
                    return view

    }
}