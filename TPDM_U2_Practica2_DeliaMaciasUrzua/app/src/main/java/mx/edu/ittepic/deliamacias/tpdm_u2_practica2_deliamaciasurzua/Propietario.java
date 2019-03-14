package mx.edu.ittepic.deliamacias.tpdm_u2_practica2_deliamaciasurzua;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Propietario {

    private BaseDatos base;
    private String telefono;
    private String nombre;
    private String descripcion;
    private String fecha;
    protected String error;

    public Propietario(Activity activity) {
        base =new BaseDatos(activity,"aseguradora",null,1);
    }

    public Propietario(String telefono, String nombre, String descripcion, String fecha) {
        this.telefono = telefono;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public boolean insertar(Propietario propietario){
        try{
            SQLiteDatabase t_insertar = base.getWritableDatabase();
            ContentValues datosPropietario = new ContentValues();
            datosPropietario.put("TELEFONO",propietario.getTelefono());
            datosPropietario.put("NOMBRE",propietario.getNombre());
            datosPropietario.put("DESCRIPCION",propietario.getDescripcion());
            datosPropietario.put("FECHA",propietario.getFecha());
            long insert = t_insertar.insert("PROPIETARIO",null,datosPropietario);
            t_insertar.close();

        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return true;
    }
    public Propietario[] consultar() {
        Propietario[] resultado = null;
        try{
            SQLiteDatabase t_consultar = base.getReadableDatabase();
            Cursor c = t_consultar.rawQuery("SELECT * FROM PROPIETARIO",null);
            if (c.moveToFirst()){
                resultado = new Propietario[c.getCount()];
                int i=0;
                do{
                    String telefono = c.getString(0);
                    String nombre = c.getString(1);
                    String descripcion = c.getString(2);
                    String fecha = c.getString(3);
                    resultado[i] = new Propietario(telefono,nombre,descripcion,fecha);
                    i++;

                }while (c.moveToNext());
            }else{
                error = "Error! No hay datos que mostrar";
            }
            t_consultar.close();
        }catch (SQLiteException e){
            error= e.getMessage();
            return null;
        }
        return  resultado;
    }

    public  Propietario[] consultar(String clave){
        Propietario[] resultado = null;

        try{
            SQLiteDatabase t_consultar = base.getReadableDatabase();
            String[] vector = {clave};
            Cursor c;
            String[] columnas={"TELEFONO","NOMBRE","DESCRIPCION","FECHA"};
            c= t_consultar.query("PROPIETARIO",columnas,"TELEFONO =?",vector,null,null,null,null);

        }catch(SQLiteException e){
            return  null;
        }
        return  resultado;
    }

    public boolean actualizar (Propietario propietario) {
        try{
            String[] vector = {propietario.getTelefono()+""};
            ContentValues datos = new ContentValues();
            datos.put("NOMBRE", propietario.getNombre());
            datos.put("DESCRIPCION", propietario.getDescripcion());
            datos.put("FECHA", propietario.getFecha());
            SQLiteDatabase t_actualizar = base.getWritableDatabase();
            long resultado = t_actualizar.update("PROPIETARIO", datos,"TELEFONO = ?",vector);
            t_actualizar.close();
            if(resultado == -1){
                return false;
            }
        }catch(SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return true;
    }

    public boolean eliminar (Propietario propietario){
        int resultado;
        try{
            String[] vector = {propietario.getTelefono()+""};
            SQLiteDatabase t_eliminar = base.getWritableDatabase();
            resultado = t_eliminar.delete("PROPIETARIO", "TELEFONO =?", vector );
            t_eliminar.close();
            if(resultado==-1){
                return false;
            }
        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return true;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


}
