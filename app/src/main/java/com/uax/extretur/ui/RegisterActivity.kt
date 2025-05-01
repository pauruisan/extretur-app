package com.uax.extretur.ui

import android.app.AlertDialog
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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
                val username = binding.editUserNameRegistro.text.toString()
                val password = binding.editPassRegistro.text.toString()

                val usernameRegex = Regex("^[a-z0-9][a-zA-Z0-9._-]{4,18}$")
                if (!usernameRegex.matches(username)) {
                    Toast.makeText(
                        this,
                        "El nombre de usuario no es válido. Debe comenzar con una letra o un número y tener entre 4 y 18 caracteres",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                val database = FirebaseDatabase.getInstance()
                var refUsuarios = database.getReference("usuarios")
                refUsuarios.orderByChild("username").equalTo(username)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                AlertDialog.Builder(this@RegisterActivity).setTitle("Username no disponible")
                                    .setMessage("El nombre de usuario ya está en uso, por favor elija otro")
                                    .setPositiveButton("Aceptar", null).show()
                            } else {
                                auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(this@RegisterActivity) { task ->
                                        if (task.isSuccessful) {
                                            val user = auth.currentUser
                                            val uid = user?.uid ?: ""
                                            val usuario = Usuario(
                                                uid = uid,
                                                nombre = nombre,
                                                apellidos = apellidos,
                                                email = email,
                                                username = username,
                                                fechaNacimiento = cumple
                                            )
                                            refUsuarios =
                                                database.getReference("usuarios").child(uid)
                                            refUsuarios.setValue(usuario)
                                            Toast.makeText(this@RegisterActivity, "Te has registrado de manera correcta", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(this@RegisterActivity, ProfileActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        } else {
                                            Toast.makeText(this@RegisterActivity, "Inicio de sesión incorrecto: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                        }}}}

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Error en la base de datos: ${error.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }})}

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