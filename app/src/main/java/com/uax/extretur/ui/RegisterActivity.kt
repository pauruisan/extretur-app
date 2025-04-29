package com.uax.extretur.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.uax.extretur.model.Usuario
import com.uax.extretur.databinding.ActivityRegisterBinding
import android.app.DatePickerDialog
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import com.google.android.material.navigation.NavigationBarView
import com.uax.extretur.R
import java.util.Calendar

class RegisterActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.navRegister.navLayout.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    binding.navRegister.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        true
                    }

                    binding.navRegister.navLayout.menu.findItem(R.id.foro).itemId -> {
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

                    binding.navRegister.navLayout.menu.findItem(R.id.perfil).itemId -> {
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
        acciones()

    }


    private fun acciones() {
        binding.btnRegister.setOnClickListener(this)
        binding.editCumpleRegistro.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnRegister.id -> {
                val nombre = binding.editNombreRegistro.text.toString()
                val apellidos = binding.editApellidosRegistro.text.toString()
                val email = binding.editCorreoRegistro.text.toString()
                val cumple = binding.editCumpleRegistro.text.toString()
                val password = binding.editPassRegistro.text.toString()

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val uid = user?.uid ?: ""
                            val usuario = Usuario(
                                uid = uid,
                                nombre = nombre,
                                apellidos = apellidos,
                                email = email,
                                fechaNacimiento = cumple
                            )

                            val database = FirebaseDatabase.getInstance()
                            val refUsuarios = database.getReference("usuarios")
                            refUsuarios.child(uid).setValue(usuario)
                            Toast.makeText(
                                this,
                                "Te has registrado de manera correcta",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, ProfileActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Inicio de sesión incorrecto: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }

            binding.editCumpleRegistro.id -> {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(this)
                datePickerDialog.setOnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = String.format(
                        "%02d/%02d/%04d",
                        selectedDay,
                        selectedMonth + 1,
                        selectedYear
                    )
                    binding.editCumpleRegistro.setText(formattedDate)
                }
                datePickerDialog.datePicker.maxDate = calendar.timeInMillis
                datePickerDialog.updateDate(year, month, day)

                datePickerDialog.datePicker.setOnDateChangedListener { _, newYear, newMonth, newDay ->
                    val formattedDate = String.format(
                        "%02d/%02d/%04d",
                        newDay,
                        newMonth + 1,
                        newYear
                    )
                    binding.editCumpleRegistro.setText(formattedDate)
                    datePickerDialog.dismiss()
                }
                datePickerDialog.show()

            }
        }
    }
}