package com.uax.extretur.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.uax.extretur.R
import com.uax.extretur.databinding.ActivityDetailNaturBinding
import com.uax.extretur.model.Monumento
import com.uax.extretur.model.Natur

class DetailNaturActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailNaturBinding
    private lateinit var actividad: Natur
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNaturBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("datos")
        actividad = bundle!!.getSerializable("actividad")!! as Natur
        binding.txtNaturTitle.text = actividad.nombre
        binding.txtNaturDesc.text = actividad.descripcion

        binding.txtDificultad.text = actividad.dificultad

        }
    }