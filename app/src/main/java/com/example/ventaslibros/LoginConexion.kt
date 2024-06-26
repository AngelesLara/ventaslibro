package com.example.ventaslibros

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.example.ventaslibros.databinding.ActivityLoginConexionBinding

class LoginConexion : AppCompatActivity() {
    private lateinit var binding : ActivityLoginConexionBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginConexionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.loginbtn.setOnClickListener {
            validarInfo()
        }

        binding.TxtRegistrarme.setOnClickListener {
            startActivity(Intent(this@LoginConexion, RegistroEmail::class.java))
        }

/*
        binding.TvRecuperar.setOnClickListener {
            startActivity(Intent(this@Login_email, RecuperarPassword::class.java))
        }*/

    }

    private var email = ""
    private var password = ""
    private fun validarInfo() {
        email = binding.username.text.toString().trim()
        password = binding.password.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           // binding.EtEmail.error = "Email inválido"
            //binding.EtEmail.requestFocus()
        }
        else if (email.isEmpty()){
          //  binding.EtEmail.error = "Ingrese email"
          //  binding.EtEmail.requestFocus()
        }
        else if (password.isEmpty()){
            //binding.EtPassword.error = "Ingrese password"
            //binding.EtPassword.requestFocus()
        }else{
            loginUsuario()
        }

    }

    private fun loginUsuario() {
        progressDialog.setMessage("Ingresando")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
                Toast.makeText(
                    this,
                    "Bienvenido(a)",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se pudo iniciar sesión debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}