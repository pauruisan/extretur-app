package com.uax.extretur.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.uax.extretur.R
import com.uax.extretur.adapters.AdaptadorMonumentos
import com.uax.extretur.databinding.ActivityMonumentsBinding
import com.uax.extretur.model.Monumento

class MonumentsActivity : AppCompatActivity(), OnItemSelectedListener {
    private lateinit var binding: ActivityMonumentsBinding

    //lista
    private lateinit var listaMonumentos: ArrayList<Monumento>
    //adaptador
    private lateinit var adaptadorMonumentos: AdaptadorMonumentos

    //TO-DO: revisar intent. El titulo de extretur muy largo.
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMonumentsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        instancias()
        acciones()
        }

    private fun instancias() {
        listaMonumentos = arrayListOf(
            Monumento ("Acueducto Los Milagros","Acueducto romano en Mérida","Badajoz", R.drawable.monumento_acueducto),
            Monumento ("Monasterio de Tentudía","Monasterio ubicado en Tentudía","Badajoz", R.drawable.monumentos_tentudia)
        )
        //inicializar el adaptador
        adaptadorMonumentos = AdaptadorMonumentos(listaMonumentos, this)
        //binding.spinnerMonumentos.adapter = adaptadorMonumentos
        if (resources.configuration.orientation == 1){
            binding.monumentCards.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }else if (resources.configuration.orientation == 2){
            binding.monumentCards.layoutManager = GridLayoutManager(this, 2)
        }

            binding.monumentCards.adapter = adaptadorMonumentos

    }

    private fun acciones (){

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //val monumentoSeleccionado: Monumento = adaptadorMonumentos.getItem(position)

        //vas a la lista y te quedas solo con los monumentos que tienen como atributo marca la misma que tiene el spinner seleccionada
        val listaFiltrada: ArrayList<Monumento> = listaMonumentos.filter {
            it.provincia.equals("Badajoz")
        } as ArrayList<Monumento>
        adaptadorMonumentos.actualizarLista(listaFiltrada)
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}