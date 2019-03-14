package mx.edu.ittepic.deliamacias.tpdm_u2_practica2_deliamaciasurzua;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {


    public BaseDatos( Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE PROPIETARIO" +
                "(TELEFONO VARCHAR(20) PRIMARY KEY NOT NULL, " +
                "NOMBRE VARCHAR(200)," +
                "DESCRIPCION VARCHAR(200)," +
                "FECHA DATE(200))");

        sqLiteDatabase.execSQL("CREATE TABLE SEGURO" +
                "(IDSEGURO VARCHAR(20) PRIMARY KEY NOT NULL, " +
                "DESCRIPCION VARHAR(200) NOT NULL, " +
                "FECHA DATE," +
                "TIPO VARCHAR(7)," +
                "TELEFONO VARCHAR(20)," +
                "FOREIGN KEY(TELEFONO) REFERENCES PROPIETARIO(TELEFONO))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
