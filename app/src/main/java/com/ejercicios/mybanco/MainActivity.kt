package com.ejercicios.mybanco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**************************************************************************************************/
/*** Class MainActivity                                                                         ***/
/*** Esta clase es la principal, valido el usuario y la contraseña y llamo a Cuenta             ***/
/**************************************************************************************************/

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etPassPrincipal: EditText = findViewById<EditText>(R.id.etPassPrincipal)
        val etUserPrincipal: EditText = findViewById<EditText>(R.id.etUserPrincipal)
        val tvTitulo: TextView = findViewById<TextView>(R.id.tvTitulo)

        val dbHelper = baseDatos(this)

        //Al presionar este boton llama a la Ventana Regitrarse, para que el usuario llene el formulario
        val btnRegistrarse: Button=findViewById<Button>(R.id.btnRegistrarse)
        btnRegistrarse.setOnClickListener{
            val intent = Intent(this, Registrarse::class.java)
            startActivity(intent)
        }

        val btnIngresar: Button = findViewById<Button>(R.id.btnIngresar)

        //Al presionar este boton valido el usuario y contraseña, si esta correcto, llamo a la clase Cuenta
        //Paso 4 parametros a la clase Cuenta, el nombre, el apellido, la cedula y el saldo
        btnIngresar.setOnClickListener {
            try {
                val myUsuario = dbHelper.ObtenerUsuario(etUserPrincipal.text.toString().trim(), etPassPrincipal.text.toString().trim())
                val user1 = etUserPrincipal.setText(myUsuario[0].usuario.toString()).toString()
                val pass1 = etUserPrincipal.setText(myUsuario[0].pass.toString()).toString()

                if (user1.isNotEmpty() && pass1.isNotEmpty()) {
                    //Toast.makeText(this, myUsuario[0].saldo.toString(), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Cuenta::class.java)
                    intent.putExtra("nombre", myUsuario[0].nombre.toString())
                    intent.putExtra("apellido", myUsuario[0].apellido.toString())
                    intent.putExtra("cedula", myUsuario[0].cedula.toString())
                    intent.putExtra("saldo", myUsuario[0].saldo)
                    startActivity(intent)
                }
            }
            catch (e: Exception)
            {
                Toast.makeText(this,"Ingrese un usuario o contraseña valido", Toast.LENGTH_SHORT).show()
            }

        }

    }
}