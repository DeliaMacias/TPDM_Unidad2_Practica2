package mx.edu.ittepic.deliamacias.tpdm_u2_practica2_deliamaciasurzua;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import mx.edu.ittepic.deliamacias.tpdm_u2_practica2_deliamaciasurzua.BaseDatos;

public class Seguro {

    private BaseDatos base;
    private int idseguro;
    private String descripcion;
    private String fecha;
    private String tipo;
    private String telefono;
    protected  String error;

    public Seguro (Activity activity) {
        base = new BaseDatos(activity, "aseguradora", null, 1);
    }

    public Seguro (int idseguro, String descripcion, String fecha, String tipo, String telefono) {
        this.idseguro = idseguro;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.telefono = telefono;
    }

    public boolean insertar(Seguro seguro){
        try{
            SQLiteDatabase t_insertar = base.getWritableDatabase();
            ContentValues datosSeguro = new ContentValues();
            datosSeguro.put("IDSEGURO",seguro.getIdseguro());
            datosSeguro.put("DESCRIPCION",seguro.getDescripcion());
            datosSeguro.put("FECHA",seguro.getFecha());
            datosSeguro.put("TIPO",seguro.getTipo());
            datosSeguro.put("TELEFONO",seguro.getTelefono());
            long insert = t_insertar.insert("SEGURO",null,datosSeguro);
            t_insertar.close();
        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return true;
    }

    public Seguro[] consultar() {
        Seguro[] resultado = null;
        try{
            SQLiteDatabase t_consultar = base.getReadableDatabase();
            Cursor c = t_consultar.rawQuery("SELECT * FROM SEGURO",null);
            if (c.moveToFirst()){
                resultado = new Seguro[c.getCount()];
                int i=0;
                do{
                    int idseguro = c.getInt(0);
                    String descripcion = c.getString(1);
                    String fecha = c.getString(2);
                    String tipo = c.getString(3);
                    String telefono = c.getString(4);
                    resultado[i] = new Seguro(idseguro,descripcion,fecha, tipo, telefono);
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

    public Seguro[] consultar(String clave){
        Seguro[] resultado = null;
        try{
            SQLiteDatabase t_consultar = base.getReadableDatabase();
            String[] vector = {clave};
            Cursor c;
            String[] columnas={"IDSEGURO","DESCRIPCION","FECHA","TIPO", "TELEFONO"};
            c= t_consultar.query("SEGURO",columnas,"IDSEGURO = ?",vector,null,null,null,null);
        }catch(SQLiteException e){
            return  null;
        }
        return resultado;
    }

    public boolean actualizar (Seguro seguro) {
        try{
            String[] vector = {seguro.getIdseguro()+""};
            ContentValues datos = new ContentValues();
            datos.put("DESCRIPCION", seguro.getDescripcion());
            datos.put("FECHA", seguro.getFecha());
            datos.put("TIPO", seguro.getTipo());
            datos.put("TELEFONO", seguro.telefono);
            SQLiteDatabase t_actualizar = base.getWritableDatabase();
            long resultado = t_actualizar.update("SEGURO", datos,"IDSEGURO = ?",vector);
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

    public boolean eliminar (Seguro seguro){
        int resultado;
        try{
            String[] vector = {seguro.getIdseguro()+""};
            SQLiteDatabase t_eliminar = base.getWritableDatabase();
            resultado = t_eliminar.delete("SEGURO", "IDSEGURO =?", vector );
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

    public int getIdseguro() {
        return idseguro;
    }

    public void setIdseguro(int idseguro) {
        this.idseguro = idseguro;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
