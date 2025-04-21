package com.uax.extretur.ui

import android.content.Intent
import java.util.Calendar
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView
import com.uax.extretur.R
import com.uax.extretur.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        //aqui los savedInstanceState
        setContentView(binding.root)

        val calendario = Calendar.getInstance()
        val hoy = calendario.timeInMillis
        binding.calendario.date = hoy

        binding.navMain.navLayout.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId){
                    binding.navMain.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        true
                    }
                    binding.navMain.navLayout.menu.findItem(R.id.foro).itemId -> {
                        //TODO: completar cuando haga el foro
                        false
                    }
                    binding.navMain.navLayout.menu.findItem(R.id.perfil).itemId -> {
                        //TODO: terminar de manejar si es login, registro o perfil
                        val intent = Intent(applicationContext, RegisterActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    //TODO: terminar de completar los intents

                    else -> false
                }
            }
        })

        acciones()

    }


    private fun acciones(){
        binding.btnMonumento.setOnClickListener(this)
        binding.btnGastro.setOnClickListener(this)
        binding.btnArbol.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btnMonumento.id -> {
                intent = Intent(applicationContext, MonumentsActivity::class.java)
                startActivity(intent)
            }
            binding.btnGastro.id -> {
                intent = Intent(applicationContext, GastroActivity::class.java)
                startActivity(intent)
            }
            binding.btnArbol.id -> {
                intent = Intent(applicationContext, NaturActivity::class.java)
                startActivity(intent)
            }
        }
    }
}