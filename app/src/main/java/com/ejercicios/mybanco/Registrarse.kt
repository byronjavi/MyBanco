package com.ejercicios.mybanco

import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast

import android.widget.EditText

/**************************************************************************************************/
/*** Class Registrarse                                                                          ***/
/*** Esta clase la utilizo para registrar los datos del usuario y poder ingresar a la app       ***/
/**************************************************************************************************/

class Registrarse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dbHelper = baseDatos(this)
        val etnombre = findViewById<EditText>(R.id.etNombre)
        val etapellido = findViewById<EditText>(R.id.etApellido)
        val etdireccion = findViewById<EditText>(R.id.etDireccion)
        val etusuario = findViewById<EditText>(R.id.etUsuario2)
        val etcedula = findViewById<EditText>(R.id.etCedula)
        val etpassword = findViewById<EditText>(R.id.etPassword)
        val etsaldo = findViewById<EditText>(R.id.etSaldo)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener{
            val nombre = etnombre.text.toString().trim()
            val apellido = etapellido.text.toString().trim()
            val direccion = etdireccion.text.toString().trim()
            val usuario = etusuario.text.toString().trim()
            val cedula = etcedula.text.toString().trim()
            val password = etpassword.text.toString().trim()
            val saldo = etsaldo.text.toString().trim()

            if (nombre.isNotBlank()&&apellido.isNotBlank()&&direccion.isNotBlank()&&usuario.isNotBlank()&&cedula.isNotBlank()&&password.isNotBlank()&&saldo.isNotBlank())
            {
                val result = dbHelper.insertUser(nombre,apellido,cedula,direccion,usuario,password,saldo.toFloat())
                if (result!= -1L)
                {
                    Toast.makeText(this,"Usuario guardado con exito", Toast.LENGTH_SHORT).show()
                    etnombre.setText("")
                    etapellido.setText("")
                    etdireccion.setText("")
                    etcedula.setText("")
                    etsaldo.setText("")
                    etusuario.setText("")
                    etpassword.setText("")

                }
                else
                {
                    Toast.makeText(this,"Error al guardar usuario", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(this,"Por favor ingresar datos validos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}