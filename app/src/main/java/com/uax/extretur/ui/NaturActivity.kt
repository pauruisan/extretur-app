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
import com.google.firebase.auth.FirebaseAuth
import com.uax.extretur.R
import com.uax.extretur.adapters.AdaptadorNatur
import com.uax.extretur.databinding.ActivityNaturBinding
import com.uax.extretur.model.Natur

class NaturActivity : AppCompatActivity(), OnItemSelectedListener {

    private lateinit var binding: ActivityNaturBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var listaNatur: ArrayList<Natur>
    private lateinit var adaptadorNatur: AdaptadorNatur

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNaturBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navNatur.navLayout.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId){
                    binding.navNatur.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    binding.navNatur.navLayout.menu.findItem(R.id.foro).itemId -> {
                        val user = auth.currentUser

                        if (user == null) {
                            val intent = Intent(applicationContext, LogInActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(applicationContext, ForumActivity::class.java)
                            startActivity(intent)
                        }
                        true
                    }
                    binding.navNatur.navLayout.menu.findItem(R.id.perfil).itemId -> {
                        val user = auth.currentUser

                        if (user == null) {
                            val intent = Intent(applicationContext, LogInActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(applicationContext, ProfileActivity::class.java)
                            startActivity(intent)
                        }
                        true
                    }
                    else -> false
                }
            }
        })

        instancias()
        acciones()
    }


    private fun instancias() {
        listaNatur = arrayListOf(
            Natur ("Acueducto Los Milagros","Acueducto romano en Mérida","Badajoz","Media", R.drawable.monumento_acueducto),
            Natur ("Monasterio de Tentudía","Monasterio ubicado en Tentudía","Badajoz","Baja", R.drawable.monumentos_tentudia)
        )
        //inicializar el adaptador
        adaptadorNatur = AdaptadorNatur(listaNatur, this)
        //binding.spinnerMonumentos.adapter = adaptadorMonumentos
        if (resources.configuration.orientation == 1){
            binding.naturCards.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }else if (resources.configuration.orientation == 2){
            binding.naturCards.layoutManager = GridLayoutManager(this, 2)
        }

        binding.naturCards.adapter = adaptadorNatur

    }

    private fun acciones (){

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //val monumentoSeleccionado: Monumento = adaptadorMonumentos.getItem(position)

        //vas a la lista y te quedas solo con los monumentos que tienen como atributo marca la misma que tiene el spinner seleccionada
        val listaFiltrada: ArrayList<Natur> = listaNatur.filter {
            it.dificultad.equals("Baja")
        } as ArrayList<Natur>
        adaptadorNatur.actualizarLista(listaFiltrada)
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
    }