package com.uax.extretur.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.Timestamp
import com.uax.extretur.R
import com.uax.extretur.adapters.AdaptadorForum
import com.uax.extretur.databinding.ActivityForumBinding
import com.uax.extretur.model.Forum
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.uax.extretur.databinding.DialogNewThemeBinding
import com.uax.extretur.model.Comentario
import com.uax.extretur.model.Gastro
import com.uax.extretur.ui.GastroActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        auth = FirebaseAuth.getInstance()

        Log.v("Usuario", auth.currentUser?.uid ?:  "sign login")
        acciones()
        instancias()
        cargarTemas()

        binding.navForum.navLayout.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    binding.navForum.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
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
        listaForum.sortBy { it.fecha }
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

        binding.editSearchForum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val textoBuscado = s.toString().lowercase()
                val listaFiltrada: ArrayList<Forum> = listaForum.filter {
                    it.titulo!!.lowercase().contains(textoBuscado) || it.creador?.lowercase()!!.contains(textoBuscado)
                }.sortedBy { it.fecha }.toCollection(ArrayList())
                if (listaFiltrada.isEmpty()){
                    Toast.makeText(this@ForumActivity, "No se encontraron resultados", Toast.LENGTH_SHORT).show()
                }
                adaptadorForum.actualizarLista(listaFiltrada)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnAddTheme.id -> {
                dialogView = DialogNewThemeBinding.inflate(LayoutInflater.from(this))
                alertDialog = AlertDialog.Builder(this)
                    .setTitle("Crear nuevo tema")
                    .setView(dialogView.root)
                    .setPositiveButton("Publicar", null)
                    .setNegativeButton("Cancelar", null).create()

                alertDialog.setOnShowListener(this)
                alertDialog.show()
            }
        }
    }

    override fun onShow(dialog: DialogInterface?) {
        val btnPublicar =
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        btnPublicar.isEnabled = false


        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val titulo = dialogView.editTituloTheme.text.toString()
                val contenido = dialogView.editContentTheme.text.toString()
                btnPublicar.isEnabled = titulo.isNotBlank() && contenido.isNotBlank()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        dialogView.editTituloTheme.addTextChangedListener(watcher)
        dialogView.editContentTheme.addTextChangedListener(watcher)

        btnPublicar.setOnClickListener {
            val titulo = dialogView.editTituloTheme.text.toString()
            val contenido = dialogView.editContentTheme.text.toString()
            val temaSeleccionado = dialogView.spinnerTheme.selectedItem.toString()
            val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES"))
            val uid = auth.currentUser?.uid ?: ""

            val forum = Forum(
                uid = uid,
                titulo = titulo,
                contenido = contenido,
                fecha = formatoFecha.format(Date()),
                creador = auth.currentUser?.email
                    ?: "Anónimo",
                tema = temaSeleccionado
            )
            val primerComentario = Comentario (
                creador = auth.currentUser?.email ?: "Anónimo",
                contenido = contenido,
                fecha = Timestamp.now()
            )
            db.collection("temas").add(forum)
                .addOnSuccessListener { documentReference ->
                    forum.uid = documentReference.id
                    listaForum.add(forum)
                    adaptadorForum.notifyItemInserted(listaForum.size - 1)
                    db.collection("temas").document(forum.uid!!).collection("comentarios").add(primerComentario)
                    val intent = Intent(applicationContext, DetailForumActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("tema", forum)
                    intent.putExtras(bundle)
                    Toast.makeText(applicationContext, "Tema creado correctamente", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    alertDialog.dismiss()
                }

                .addOnFailureListener { e ->
                    Log.e("FirestoreError", "Error al crear el tema", e)
                    Toast.makeText(
                        applicationContext,
                        "Error al crear el tema: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    private fun cargarTemas() {
        db.collection("temas").orderBy("fecha", Query.Direction.DESCENDING).get().addOnSuccessListener { result ->
            listaForum.clear()
            for (doc in result) {
                val tema = doc.toObject(Forum::class.java)
                tema.uid = doc.id
                listaForum.add(tema)
            }
            adaptadorForum.notifyDataSetChanged()
        }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error al cargar los temas", e)
                Toast.makeText(this, "Error al cargar los temas", Toast.LENGTH_LONG).show()
            }
    }
}
