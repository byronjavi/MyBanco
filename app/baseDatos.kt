import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class baseDatos(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{

    companion object
    {
        private const val DATABASE_NAME = "MyDatabase.db"
        private const val DATABASE_VERSION = 1

        // Nombre de la tabla y columnas
        const val TABLE_NAME = "Users"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_APPELLIDO = "apellido"
        const val COLUMN_CEDULA = "cedula"
        const val COLUMN_DIR = "direccion"
        const val COLUMN_TELF = "telefono"
        const val COLUMN_PASS = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT,
                $COLUMN_APPELLIDO TEXT,
                $COLUMN_CEDULA TEXT,
                $COLUMN_DIR TEXT,
                $COLUMN_TELF TEXT,
                $COLUMN_PASS TEXT
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Método para insertar datos
    fun insertUser(nombre: String, apellido: String, cedula: String, direccion: String, telefono: String, password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_APPELLIDO, apellido)
            put(COLUMN_CEDULA, cedula)
            put(COLUMN_DIR, direccion)
            put(COLUMN_TELF, telefono)
            put(COLUMN_PASS, password)
        }
        return db.insert(TABLE_NAME, null, contentValues)
    }
}
