package com.uax.extretur.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationBarView
import com.uax.extretur.R
import com.uax.extretur.adapters.AdaptadorForum
import com.uax.extretur.databinding.ActivityForumBinding
import com.uax.extretur.model.Forum

class ForumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForumBinding
    private lateinit var listaForum: ArrayList<Forum>
    private lateinit var adaptadorForum: AdaptadorForum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instancias()

        binding.navForum.navLayout.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    binding.navForum.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    binding.navForum.navLayout.menu.findItem(R.id.foro).itemId -> {
                        //TODO: completar cuando haga el foro
                        false
                    }
                    //TODO: terminar de completar los intents

                    else -> false
                }
            }
        })

    }

    private fun instancias () {
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
}