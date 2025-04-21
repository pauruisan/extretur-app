package com.uax.extretur.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationBarView
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

    //TODO: revisar intent.
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMonumentsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.navMonuments.navLayout.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    binding.navMonuments.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    binding.navMonuments.navLayout.menu.findItem(R.id.foro).itemId -> {
                        //TODO: completar cuando haga el foro
                        false
                    }
                    //TODO: terminar de completar los intents

                    else -> false
                }
            }
        })

        instancias()
        acciones()
    }

    private fun instancias() {
        listaMonumentos = arrayListOf(
            Monumento(
                "Acueducto Los Milagros",
                "Acueducto romano en Mérida",
                "Badajoz",
                R.drawable.monumento_acueducto
            ),
            Monumento(
                "Monasterio de Tentudía",
                "Monasterio ubicado en Tentudía",
                "Badajoz",
                R.drawable.monumentos_tentudia
            )
        )
        //inicializar el adaptador
        adaptadorMonumentos = AdaptadorMonumentos(listaMonumentos, this)

        if (resources.configuration.orientation == 1) {
            binding.monumentCards.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        } else if (resources.configuration.orientation == 2) {
            binding.monumentCards.layoutManager = GridLayoutManager(this, 2)
        }

        binding.monumentCards.adapter = adaptadorMonumentos

    }

    private fun acciones() {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val listaFiltrada: ArrayList<Monumento> = listaMonumentos.filter {
            it.provincia.equals("Badajoz")
        } as ArrayList<Monumento>
        adaptadorMonumentos.actualizarLista(listaFiltrada)
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}