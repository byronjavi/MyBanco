package com.ejercicios.mybanco

import android.os.Bundle
import android.widget.*
import android.text.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import android.widget.Button
import android.widget.EditText

/**************************************************************************************************/
/*** Class Transferir                                                                           ***/
/*** Esta clase la utilizo para transferir dinero de un usuario a otro                          ***/
/**************************************************************************************************/

class Transferir : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_transferir)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Recibo como parametros, el nombre, appellido, cedula y saldo
        val myNombre = intent.getStringExtra("nombre") ?: ""
        val myApellido = intent.getStringExtra("apellido") ?: ""
        val myCedula = intent.getStringExtra("cedula") ?: ""
        val mySaldo = intent.getFloatExtra("saldo", 0.0f)
        val btnEnviar: Button = findViewById<Button>(R.id.btnEnviar)

        val dbHelper = baseDatos(this)

        val etCantidadTransferir: EditText = findViewById<EditText>(R.id.etCantidadTransferir)
        val etSaldoDisponible2: EditText = findViewById<EditText>(R.id.etSaldoDisponible2)
        etSaldoDisponible2.setText(mySaldo.toString())

        val mysUsuarios = dbHelper.ObtenerTodosLosUsuarios() //Obtengo todos los usuarios desde la base de datos
        val spinner: Spinner = findViewById<Spinner>(R.id.spinner) //Declro mi lista desplegable
        val nombresUsuarios = mysUsuarios.map { "${it.nombre} ${it.apellido}" } //asigno todos los usuarios
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresUsuarios)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adaptador  //lleno mi lista desplegable

        //Esta funcion compara si el valor a transferir es menor o igual que el saldo disponible
        fun validarCantidadTransferir(cant1: Float, cant2: Float): Boolean
        {
            if (cant1 < cant2)
                return false
            else
                return true
        }

        btnEnviar.setOnClickListener {
            try {
                val num1 = etSaldoDisponible2.text.toString().trim()
                val num2 = etCantidadTransferir.text.toString().trim()

                if (etCantidadTransferir.text.toString().trim().isNotBlank()) {
                    if (validarCantidadTransferir(num1.toFloat(), num2.toFloat()))
                    {
                        try
                        {
                            val dbHelper = baseDatos(this)
                            val nuevoSaldo = mySaldo - num2.toFloat()
                            dbHelper.actualizarSaldo(myCedula,nuevoSaldo)
                            //dbHelper.actualizarSaldo(myCedula,nuevoSaldo)

                            val obtenerSaldo = dbHelper.ObtenerSaldo(mysUsuarios[spinner.selectedItemPosition].cedula.toString())
                            val saldoBeneficiario = obtenerSaldo[0].saldo.toFloat()
                            val nuevoSaldoBeneficiario = saldoBeneficiario + num2.toFloat()
                            dbHelper.actualizarSaldo(mysUsuarios[spinner.selectedItemPosition].cedula.toString(),nuevoSaldoBeneficiario)

                            Toast.makeText(this, "Transaccion exitosa", Toast.LENGTH_SHORT).show()
                        }
                        catch (e: Exception)
                        {
                            Toast.makeText(this, "Error al realizar transacción", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        Toast.makeText(this, "No puedes transferir un valor superior al saldo disponible", Toast.LENGTH_SHORT).show()
                        etCantidadTransferir.setText("")
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Debe escribir la cantidad que desea transferir",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //Toast.makeText(this,mysUsuarios[spinner.selectedItemPosition].id.toString(), Toast.LENGTH_SHORT).show()
            } catch (e: Exception)
            {
                Toast.makeText(this,e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}