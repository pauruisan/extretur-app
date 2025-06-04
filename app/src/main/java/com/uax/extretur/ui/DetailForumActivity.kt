package com.uax.extretur.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.uax.extretur.databinding.ActivityDetailForumBinding
import com.uax.extretur.model.Comentario
import com.uax.extretur.model.Forum
import java.text.SimpleDateFormat
import java.util.Locale

class DetailForumActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailForumBinding
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    private lateinit var tema: Forum
    private lateinit var listaComentarios: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailForumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        listaComentarios = arrayListOf()

        val bundle = intent.extras
        tema = bundle?.getSerializable("tema")!! as? Forum
            ?: throw IllegalArgumentException("No se ha recibido el tema")
        binding.txtThemeTitle.text = tema.titulo
        binding.txtThemeComments.text = listaComentarios.joinToString("\n") as CharSequence?

        acciones()
        cargarComentarios()
    }

    private fun acciones (){
        binding.btnDeleteTheme.setOnClickListener(this)
        binding.btnInsertComment.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnDeleteTheme.id -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("¿Estás seguro de que quieres eliminar este tema?")
                    .setPositiveButton("Eliminar") { dialog, id ->
                        db.collection("temas").document(tema.uid.toString()).delete()
                            .addOnSuccessListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Tema eliminado",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(applicationContext, ForumActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                    }
                    .setNegativeButton("Cancelar", null).create()
                builder.show()
            }

            binding.btnInsertComment.id -> {
                val textoComentario = binding.editInsertComment.text.toString()
                if (textoComentario.isNotEmpty()) {
                    val nuevoComentario = Comentario(
                        creador = auth.currentUser?.email.toString(),
                        contenido = textoComentario.toString(),
                        fecha = Timestamp.now()
                    )
                    db.collection("temas").document(tema.uid.toString()).collection("comentarios")
                        .add(nuevoComentario).addOnSuccessListener {
                        Toast.makeText(
                            applicationContext,
                            "Comentario insertado",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.editInsertComment.text.clear()
                        cargarComentarios()
                    }
                        .addOnFailureListener {
                            Toast.makeText(
                                this,
                                "Error al insertar el comentario",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
        }
    }

    private fun cargarComentarios() {
        db.collection("temas").document(tema.uid.toString()).collection("comentarios").orderBy("fecha",
            Query.Direction.ASCENDING).get()
            .addOnSuccessListener { result ->
                listaComentarios.clear()
                for (doc in result){
                    val c = doc.toObject(Comentario::class.java)
                    val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(c.fecha.toDate())
                    val texto = "($fecha) ${c.creador}: ${c.contenido}"
                    listaComentarios.add(texto)
                }
                binding.txtThemeComments.text = listaComentarios.joinToString("\n")
            }
    }
}