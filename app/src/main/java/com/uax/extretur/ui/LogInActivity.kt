package com.uax.extretur.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import com.uax.extretur.R
import com.uax.extretur.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogIn.setOnClickListener{
            val email = binding.editCorreoLogIn.text.toString()
            val password = binding.editPassLogIn.text.toString()

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Inicio de sesión incorrecto: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.navLogIn.navLayout.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId){
                    binding.navLogIn.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    binding.navLogIn.navLayout.menu.findItem(R.id.foro).itemId -> {
                        //TODO: completar cuando haga el foro
                        false
                    }
                    //TODO: terminar de completar los intents

                    else -> false
                }
            }
        })
    }
}