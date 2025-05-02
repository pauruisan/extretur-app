package com.uax.extretur.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationBarView
import com.uax.extretur.R
import com.uax.extretur.adapters.AdaptadorForum
import com.uax.extretur.databinding.ActivityForumBinding
import com.uax.extretur.model.Forum
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.uax.extretur.databinding.DialogNewThemeBinding
import java.util.Date

class ForumActivity : AppCompatActivity(), View.OnClickListener, DialogInterface.OnShowListener {
    private lateinit var binding: ActivityForumBinding
    private lateinit var alertDialog: AlertDialog
    private lateinit var dialogView: DialogNewThemeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var listaForum: ArrayList<Forum>
    private lateinit var adaptadorForum: AdaptadorForum
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        acciones()
        instancias()

        binding.navForum.navLayout.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    binding.navForum.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        true
                    }

                    binding.navForum.navLayout.menu.findItem(R.id.foro).itemId -> {
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

                    binding.navForum.navLayout.menu.findItem(R.id.perfil).itemId -> {
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

    private fun instancias() {
        listaForum = arrayListOf()
        adaptadorForum = AdaptadorForum(listaForum, this)

        if (resources.configuration.orientation == 1) {
            binding.foroCards.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        } else if (resources.configuration.orientation == 2) {
            binding.foroCards.layoutManager = GridLayoutManager(this, 2)
        }

        binding.foroCards.adapter = adaptadorForum

    }

    private fun acciones() {
        binding.btnAddTheme.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnAddTheme.id -> {
                dialogView = DialogNewThemeBinding.inflate(LayoutInflater.from(this))
                alertDialog = AlertDialog.Builder(this)
                    .setTitle("Crear nuevo tema")
                    .setView(dialogView.root)
                    .setPositiveButton("Publicar", null)
                    .setCancelable(false).create()

                alertDialog.setOnShowListener(this)
                alertDialog.show()
            } } }

    override fun onShow(dialog: DialogInterface?) {
        val btnPublicar = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE) //TODO: crear btn cancelar y volver atrás. La app se rompe al hacer click en Publicar

        btnPublicar.setOnClickListener {
            val titulo = dialogView.editTituloTheme.text.toString()
            val contenido = dialogView.editContentTheme.text.toString()
            val temaSeleccionado = dialogView.spinnerTheme.selectedItem.toString()

            if (titulo.isNotEmpty() && contenido.isNotEmpty()) {
                val Forum = Forum(
                    titulo = titulo,
                    contenido = contenido,
                    fecha = Date().toString(),
                    creador = auth.currentUser?.email.toString(), //TODO: cambiar email por username
                    tema = temaSeleccionado
                )
                db.collection("temas").add(Forum)
                    .addOnSuccessListener { documentReference ->
                        Forum.id = documentReference.id
                        listaForum.add(Forum)
                        adaptadorForum.notifyItemInserted(listaForum.size - 1)

                        val intent = Intent(applicationContext, DetailForumActivity::class.java)
                        startActivity(intent)
                            alertDialog.dismiss()}

                            .addOnFailureListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Error al crear el tema",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Rellena todos los campos, por favor.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    }
