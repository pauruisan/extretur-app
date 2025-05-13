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
import com.uax.extretur.adapters.AdaptadorGastro
import com.uax.extretur.databinding.ActivityGastroBinding
import com.uax.extretur.model.Gastro
import com.google.firebase.auth.FirebaseAuth

class GastroActivity : AppCompatActivity(), OnItemSelectedListener {

    private lateinit var binding: ActivityGastroBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var listaGastro: ArrayList<Gastro>
    private lateinit var adaptadorGastro: AdaptadorGastro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navGastro.navLayout.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId){
                    binding.navGastro.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    binding.navGastro.navLayout.menu.findItem(R.id.foro).itemId -> {
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
                    binding.navGastro.navLayout.menu.findItem(R.id.perfil).itemId -> {
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
        listaGastro = arrayListOf(
            Gastro ("Acueducto Los Milagros","Acueducto romano en Mérida","Badajoz", R.drawable.nav_home),
            Gastro ("Monasterio de Tentudía","Monasterio ubicado en Tentudía","Badajoz", R.drawable.monumentos_tentudia)
        )
        //inicializar el adaptador
        adaptadorGastro = AdaptadorGastro(listaGastro, this)
        //binding.spinnerMonumentos.adapter = adaptadorMonumentos
        if (resources.configuration.orientation == 1){
            binding.gastroCards.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }else if (resources.configuration.orientation == 2){
            binding.gastroCards.layoutManager = GridLayoutManager(this, 2)
        }

        binding.gastroCards.adapter = adaptadorGastro

    }

    private fun acciones (){

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val listaFiltrada: ArrayList<Gastro> = listaGastro.filter {
            it.nombre.equals("Baja")
        } as ArrayList<Gastro>
        adaptadorGastro.actualizarLista(listaFiltrada)
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}