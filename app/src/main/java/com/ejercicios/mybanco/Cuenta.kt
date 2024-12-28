package com.ejercicios.mybanco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**************************************************************************************************/
/*** Class Cuenta                                                                               ***/
/*** Esta clase la utilizo para mostrar el saldo y luego poder ir a la ventana transferir       ***/
/**************************************************************************************************/

class Cuenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cuenta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val myNombre = intent.getStringExtra("nombre") ?: "" //Recibo el nombre desde la clase principal
        val myApellido = intent.getStringExtra("apellido") ?: "" //Recibo el apellido desde la clase principal
        val myCedula = intent.getStringExtra("cedula") ?: ""  //Recibo la cedula desde la clase principal
        val mySaldo = intent.getFloatExtra("saldo",0.0f)  //Recibo el saldo desde la clase principal

        val tvBienvenido: TextView = findViewById<TextView>(R.id.tvBienvenido)
        val etSaldo2: EditText = findViewById<EditText>(R.id.etSaldo2)

        tvBienvenido.setText("Bienvenido "+myNombre.toString()+" "+myApellido.toString()).toString() //Al textView le asigno el nombre del usuario
        etSaldo2.setText(mySaldo.toString())

        val btnTransferir: Button = findViewById<Button>(R.id.btnTransferir)

        //Al presionar el boton transferir, le envio como parametro el nombre, apellido, cedula y saldo
        btnTransferir.setOnClickListener{
            val intent = Intent(this, Transferir::class.java) //Llamo a la ventana Transferir
            intent.putExtra("nombre",myNombre)
            intent.putExtra("apellido",myApellido)
            intent.putExtra("cedula",myCedula)
            intent.putExtra("saldo",mySaldo)
            startActivity(intent)
        }

    }


}