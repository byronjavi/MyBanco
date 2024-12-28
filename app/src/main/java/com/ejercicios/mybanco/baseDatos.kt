package com.ejercicios.mybanco

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class baseDatos(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{

    companion object
    {
        private const val DATABASE_NAME = "MiDatabase.db"
        private const val DATABASE_VERSION = 1

        // Nombre de la tabla y columnas
        const val TABLE_NAME = "Usr1"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_APPELLIDO = "apellido"
        const val COLUMN_CEDULA = "cedula"
        const val COLUMN_DIR = "direccion"
        const val COLUMN_USER = "usuario"
        const val COLUMN_PASS = "password"
        const val COLUMN_SALDO = "saldo"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT,
                $COLUMN_APPELLIDO TEXT,
                $COLUMN_CEDULA TEXT,
                $COLUMN_DIR TEXT,
                $COLUMN_USER TEXT,
                $COLUMN_PASS TEXT,
                $COLUMN_SALDO REAL
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Método para insertar datos
    fun insertUser(nombre: String, apellido: String, cedula: String, direccion: String,usuario: String, password: String, saldo: Float): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_APPELLIDO, apellido)
            put(COLUMN_CEDULA, cedula)
            put(COLUMN_DIR, direccion)
            put(COLUMN_USER, usuario)
            put(COLUMN_PASS, password)
            put(COLUMN_SALDO, saldo)
        }
        return db.insert(TABLE_NAME, null, contentValues)
    }

    /********************************************************************************************/
    fun ObtenerUsuario(user: String, pass: String): List<Usuario>
    {
        val db = this.readableDatabase
        val userList = mutableListOf<Usuario>()

        // Consulta con múltiples criterios
        val cursor = db.query(
            TABLE_NAME,                     // Tabla
            null,                           // Columnas (null para todas)
            "$COLUMN_USER = ? AND $COLUMN_PASS = ?", // Condición WHERE
            arrayOf(user.toString(), pass.toString()),  // Argumentos WHERE
            null,                           // Agrupación
            null,                           // Filtro HAVING
            null                            // Orden
        )

        if (cursor.moveToFirst()) {
            do {
                val myId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val myNombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                val myApellido = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPELLIDO))
                val myCedula = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CEDULA))
                val myDireccion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIR))
                val myuser = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER))
                val myPass = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASS))
                val mySaldo = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_SALDO))
                userList.add(Usuario(myId,myNombre,myApellido,myCedula,myDireccion,myuser,myPass, mySaldo))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

    /**********************************************************************************************/

    fun ObtenerTodosLosUsuarios(): List<Usuario>
    {
        val db = this.readableDatabase
        val userList = mutableListOf<Usuario>()

        // Consulta con múltiples criterios
        val cursor = db.query(
            TABLE_NAME,                     // Tabla
            null,                           // Columnas (null para todas)
            null,                           // Condición WHERE
            null,                           // Argumentos WHERE
            null,                           // Agrupación
            null,                           // Filtro HAVING
            null                            // Orden
        )

        if (cursor.moveToFirst()) {
            do {
                val myId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val myNombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                val myApellido = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPELLIDO))
                val myCedula = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CEDULA))
                val myDireccion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIR))
                val myuser = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER))
                val myPass = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASS))
                val mySaldo = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_SALDO))
                userList.add(Usuario(myId,myNombre,myApellido,myCedula,myDireccion,myuser,myPass, mySaldo))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

    /**********************************************************************************************/


    fun ObtenerSaldo(cedula: String): List<Usuario>
    {
        val db = this.readableDatabase
        val userList = mutableListOf<Usuario>()

        // Consulta con múltiples criterios
        val cursor = db.query(
            TABLE_NAME,                     // Tabla
            null,                           // Columnas (null para todas)
            "$COLUMN_CEDULA = ?", // Condición WHERE
            arrayOf(cedula.toString().trim()),  // Argumentos WHERE
            null,                           // Agrupación
            null,                           // Filtro HAVING
            null                            // Orden
        )

        if (cursor.moveToFirst()) {
            do {
                val myId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val myNombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                val myApellido = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPELLIDO))
                val myCedula = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CEDULA))
                val myDireccion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIR))
                val myuser = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER))
                val myPass = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASS))
                val mySaldo = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_SALDO))
                userList.add(Usuario(myId,myNombre,myApellido,myCedula,myDireccion,myuser,myPass, mySaldo))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

    /***********************************************************************************************/
    fun actualizarSaldo(cedula: String, nuevoSaldo: Float): Boolean
    {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COLUMN_SALDO,nuevoSaldo)
        }
        val  seleccion = "cedula = ?"
        val  argumentosSeleccion = arrayOf(cedula)
        val filasActualizadas = db.update("Usr1",valores,seleccion,argumentosSeleccion )
        db.close()
        return true
    }
}
