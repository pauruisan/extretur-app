package com.uax.extretur.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.uax.extretur.adapters.AdaptadorMonumentos
import com.uax.extretur.databinding.ActivityMonumentsBinding
import com.uax.extretur.model.Monumento

class MonumentsActivity : AppCompatActivity(), OnItemSelectedListener {
    private lateinit var binding: ActivityMonumentsBinding

    //lista
    private lateinit var listaMonumentos: ArrayList<Monumento>
    //adaptador
    private lateinit var adaptadorMonumentos: AdaptadorMonumentos


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMonumentsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        instancias()
        acciones()
        }

    private fun instancias() {
        listaMonumentos = arrayListOf(
            //Monumento ("nombre", R.drawable.imagen), ..... son los objetos momumento esto en realidad tiene que venir de BBDD
        )
        //inicializar el adaptador
        adaptadorMonumentos = AdaptadorMonumentos(listaMonumentos, applicationContext)
        binding.spinnerMonumentos.adapter = adaptadorMonumentos
    }

    private fun acciones (){

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val monumentoSeleccionado: Monumento = adaptadorMonumentos.getItem(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}