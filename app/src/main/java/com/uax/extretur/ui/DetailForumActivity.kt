package com.uax.extretur.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.uax.extretur.databinding.ActivityDetailForumBinding
import com.uax.extretur.model.Forum

class DetailForumActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailForumBinding
    val db = FirebaseFirestore.getInstance()
    private lateinit var tema: Forum
    private lateinit var listaComentarios: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailForumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        tema = bundle?.getSerializable("tema")!! as? Forum ?: throw IllegalArgumentException("No se ha recibido el tema")

        binding.btnDeleteTheme.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnDeleteTheme.id -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("¿Estás seguro de que quieres eliminar este tema?")
                    .setPositiveButton("Eliminar") { dialog, id ->
                        db.collection("temas").document(tema.uid).delete().addOnSuccessListener {
                            Toast.makeText(applicationContext, "Tema eliminado", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                    .setNegativeButton("Cancelar", null).create()
                    builder.show()


            }
        }
    }
}