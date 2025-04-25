package com.uax.extretur.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import com.uax.extretur.R
import com.uax.extretur.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        acciones()

        binding.navProfile.navLayout.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId){
                    binding.navProfile.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    binding.navProfile.navLayout.menu.findItem(R.id.foro).itemId -> {
                        //TODO: completar cuando haga el foro
                        false
                    }
                    //TODO: terminar de completar los intents

                    else -> false
                }
            }
        })

    }

    private fun acciones() {
        binding.btnLogout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btnLogout.id -> {
                auth = FirebaseAuth.getInstance()
                auth.signOut()
                Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, LogInActivity::class.java)
                startActivity(intent)
            }
        }
    }
}