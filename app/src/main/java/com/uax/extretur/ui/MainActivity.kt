package com.uax.extretur.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView
import com.uax.extretur.R
import com.uax.extretur.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(),OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        //aqui los savedInstanceState
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.navMain.navLayout.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId){
                    binding.navMain.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        true
                    }
                    binding.navMain.navLayout.menu.findItem(R.id.foro).itemId -> {
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
                    binding.navMain.navLayout.menu.findItem(R.id.perfil).itemId -> {
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
                val intent = Intent(applicationContext, MonumentsActivity::class.java)
                startActivity(intent)
            }
            binding.btnGastro.id -> {
                val intent = Intent(applicationContext, GastroActivity::class.java)
                startActivity(intent)
            }
            binding.btnArbol.id -> {
                val intent = Intent(applicationContext, NaturActivity::class.java)
                startActivity(intent)
            }
        }
    }
    }

