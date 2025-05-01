package com.uax.extretur.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.uax.extretur.R
import com.uax.extretur.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        acciones()
        userData()

        binding.navProfile.navLayout.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId){
                    binding.navProfile.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        true
                    }
                    binding.navProfile.navLayout.menu.findItem(R.id.foro).itemId -> {
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
                    binding.navProfile.navLayout.menu.findItem(R.id.perfil).itemId -> {
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

    }

    private fun acciones() {
        binding.btnLogout.setOnClickListener(this)
    }

    private fun userData (){
        val user = auth.currentUser
        val uid = user?.uid

        if (uid != null){
            val db = FirebaseDatabase.getInstance()
            val ref = db.getReference("usuarios").child(uid)

            ref.get().addOnSuccessListener { snapshot ->

                if (snapshot.exists()) {
                    Log.d("FIREBASE_DATA", "Snapshot encontrado: ${snapshot.value}")
                    val nombre = snapshot.child("nombre").value.toString()
                    val apellidos = snapshot.child("apellidos").value.toString()
                    val email = snapshot.child("email").value.toString()

                    binding.txtNombrePerfil.text = "$nombre $apellidos"
                    binding.txtCorreoPerfil.text = "$email"
                } else {
                    binding.txtNombrePerfil.text = "No encontrado"
                    binding.txtCorreoPerfil.text = "No encontrado"
                    Log.d("FIREBASE_DATA", "Snapshot no encontrado")
                }



            }.addOnFailureListener {
                Log.e("FIREBASE_ERROR", "Error al leer datos")
                binding.txtNombrePerfil.text = "Error"
                binding.txtCorreoPerfil.text = "Error"
            }
        }
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