package com.uax.extretur.ui

import android.content.Intent
import java.util.Calendar
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.uax.extretur.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        //aqui los savedInstanceState
        setContentView(binding.root)

        val calendario = Calendar.getInstance()
        val hoy = calendario.timeInMillis
        binding.calendario.date = hoy



        acciones()


    }


    private fun acciones(){
        binding.btnMonumento.setOnClickListener(this)
        binding.btnGastro.setOnClickListener(this)
        binding.btnArbol.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btnMonumento.id -> {
                intent = Intent(applicationContext, MonumentsActivity::class.java)
                startActivity(intent)
            }
            binding.btnGastro.id -> {
                //TODO: completar con el intent
            }
            binding.btnArbol.id -> {
                //TODO: completar con el intent
            }
        }
    }
}