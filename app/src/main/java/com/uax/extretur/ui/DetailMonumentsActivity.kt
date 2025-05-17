package com.uax.extretur.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.uax.extretur.R
import com.uax.extretur.databinding.ActivityDetailMonumentsBinding
import com.uax.extretur.model.Monumento

class DetailMonumentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMonumentsBinding
    private lateinit var monumento: Monumento
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMonumentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("datos")
        monumento = bundle!!.getSerializable("monumento")!! as Monumento
        binding.txtMonumentTitle.text = monumento.nombre
        binding.txtMonumentDesc.text = monumento.descripcion
        val poblacion = monumento.poblacion
        binding.txtPoblacion.text = "$poblacion,"
        binding.txtProvincia.text = monumento.provincia


    }
}