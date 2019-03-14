package mx.edu.ittepic.deliamacias.tpdm_u2_practica2_deliamaciasurzua;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.nio.file.Files;

public class Seguro_insertar extends AppCompatActivity {

    EditText id, descripcion, fecha, tipo, telefono;
    Button eliminar, guardar, actualizar;
    boolean nuevo;
    int idseguro;
    Propietario[] vector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguro_insertar);

        id = findViewById(R.id.idseguroSeg);
        descripcion = findViewById(R.id.descripcionSeg);
        fecha =findViewById(R.id.fechaSeg);
        tipo = findViewById(R.id.tipoSeg);
        telefono = findViewById(R.id.telefonoSeg);
        eliminar = findViewById(R.id.btn_eliminarSeg);
        guardar = findViewById(R.id.btn_guardarSeg);
        actualizar = findViewById(R.id.btn_actualizarSeg);
        nuevo = getIntent().getBooleanExtra("nuevo", false);
        idseguro = getIntent().getIntExtra("idseguro",0);

        if (nuevo) {
            eliminar.setVisibility(View.INVISIBLE);
            actualizar.setVisibility(View.INVISIBLE);
        } else {
            guardar.setVisibility(View.INVISIBLE);
            id.setText(idseguro+"");
            id.setEnabled(false);
            tipo.setText(getIntent().getStringExtra("tipo"));
            descripcion.setText(getIntent().getStringExtra("descripcion"));
            fecha.setText(getIntent().getStringExtra("fecha"));
            telefono.setText( getIntent().getStringExtra("telefono"));
        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar()) {
                    guardar();
                }
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar()) {
                    modificar();
                }
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder comprueba = new AlertDialog.Builder(Seguro_insertar.this);
                comprueba.setTitle("Estas Seguro?")
                        .setMessage("Deseas Eliminar el Propietario")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                borrar();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .show();
            }
        });

    }


    public boolean validar () {
        if (id.getText().toString().equals("")) {
            Toast.makeText(Seguro_insertar.this, "ESCRIBE UN ID", Toast.LENGTH_LONG);
            return false;
        }
        if (tipo.getText().toString().equals("")) {
            Toast.makeText(Seguro_insertar.this, "ESCRIBE UN TIPO", Toast.LENGTH_LONG);
            return false;
        }
        if (descripcion.getText().toString().equals("")) {
            Toast.makeText(Seguro_insertar.this, "ESCRIBE UN DESCRIPCIÓN", Toast.LENGTH_LONG);
            return false;
        }
        if (fecha.getText().toString().equals("")) {
            Toast.makeText(Seguro_insertar.this, "ESCRIBE UN FECHA", Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }

    public void guardar () {
        Seguro s = new Seguro(this);
        boolean respuesta = s.insertar(new Seguro(Integer.parseInt(id.getText().toString()), descripcion.getText().toString(), fecha.getText().toString(), tipo.getText().toString(), telefono.getText().toString()));
        String mensaje;
        if(respuesta) {
            mensaje = "Se insertó correctamente";
            tipo.setText("");
            id.setText("");
            descripcion.setText("");
            fecha.setText("");
        } else {
            mensaje = "No se pudo insertar";
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        Toast.makeText(this,"Guardando...",Toast.LENGTH_LONG).show();
        alerta.setTitle("ATENCION").setMessage(mensaje).setPositiveButton("ok",null).show();
    }

    public void modificar (){
        Seguro s = new Seguro(this);
        boolean respuesta = s.actualizar(new Seguro(Integer.parseInt(id.getText().toString()), descripcion.getText().toString(), fecha.getText().toString(), tipo.getText().toString(),  telefono.getText().toString()));
        String mensaje;
        if(respuesta) {
            mensaje = "Se actualizó correctamente";
        } else {
            mensaje = "No se pudo actualizar";
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        Toast.makeText(this,"Actualizando...",Toast.LENGTH_LONG).show();
        alerta.setTitle("ATENCION").setMessage(mensaje).setPositiveButton("ok",null).show();
    }

    public void borrar (){
        Seguro s = new Seguro(this);
        boolean respuesta = s.eliminar(new Seguro(Integer.parseInt(id.getText().toString()), descripcion.getText().toString(), fecha.getText().toString(), tipo.getText().toString(),   telefono.getText().toString()));
        String mensaje;
        if(respuesta) {
            mensaje = "Se eliminó correctamente";
            tipo.setText("");
            id.setText("");
            descripcion.setText("");
            fecha.setText("");
            eliminar.setEnabled(false);
            actualizar.setEnabled(false);
        } else {
            mensaje = "No se pudo eliminar";
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        Toast.makeText(this,"Eliminando...",Toast.LENGTH_LONG).show();
        alerta.setTitle("ATENCION").setMessage(mensaje).setPositiveButton("ok",null).show();
    }

}
