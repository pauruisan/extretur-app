package com.uax.extretur.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.uax.extretur.model.Usuario
import com.uax.extretur.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener{
            val nombre = binding.editNombreRegistro.text.toString()
            val apellidos = binding.editApellidosRegistro.text.toString()
            val email = binding.editCorreoRegistro.text.toString()
            val cumple = binding.editCumpleRegistro.text.toString()
            val password = binding.editPassRegistro.text.toString()

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
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
                    Toast.makeText(this, "Te has registrado de manera correcta", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Inicio de sesión incorrecto: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}