package com.uax.extretur.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uax.extretur.databinding.ActivityDetailGastroBinding
import com.uax.extretur.model.Gastro

class DetailGastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailGastroBinding
    private lateinit var plato: Gastro
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("datos")
        plato = bundle!!.getSerializable("plato")!! as Gastro
        binding.txtGastroTitle.text = plato.nombre
        binding.txtGastroDesc.text = plato.descripcion
    }
}