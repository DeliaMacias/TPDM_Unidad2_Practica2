package mx.edu.ittepic.deliamacias.tpdm_u2_practica2_deliamaciasurzua;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_insertar extends AppCompatActivity {

    EditText telefono, nombre, descripcion, fecha;
    Button guardar, eliminar, actualizar;
    boolean nuevo;
    String telefonoid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        telefono = findViewById(R.id.telefono);
        nombre = findViewById(R.id.nombre);
        descripcion = findViewById(R.id.descripcion);
        fecha = findViewById(R.id.fecha);
        guardar = findViewById(R.id.btn_guardar);
        eliminar = findViewById(R.id.btn_eliminar);
        actualizar = findViewById(R.id.btn_actualizar);
        nuevo = getIntent().getBooleanExtra("nuevo", true);
        telefonoid = getIntent().getStringExtra("telefono");

        if (nuevo) {
            eliminar.setVisibility(View.INVISIBLE);
            actualizar.setVisibility(View.INVISIBLE);
        } else {
            guardar.setVisibility(View.INVISIBLE);
            telefono.setText(telefonoid);
            telefono.setEnabled(false);
            nombre.setText(getIntent().getStringExtra("nombre"));
            descripcion.setText(getIntent().getStringExtra("descripcion"));
            fecha.setText(getIntent().getStringExtra("fecha"));
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
                AlertDialog.Builder comprueba = new AlertDialog.Builder(Activity_insertar.this);
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
        if (telefono.getText().toString().equals("")) {
            Toast.makeText(Activity_insertar.this, "ESCRIBE UN TELÉFONO", Toast.LENGTH_LONG);
            return false;
        }
        if (nombre.getText().toString().equals("")) {
            Toast.makeText(Activity_insertar.this, "ESCRIBE UN NOMBRE", Toast.LENGTH_LONG);
            return false;
        }
        if (descripcion.getText().toString().equals("")) {
            Toast.makeText(Activity_insertar.this, "ESCRIBE UN DESCRIPCIÓN", Toast.LENGTH_LONG);
            return false;
        }
        if (fecha.getText().toString().equals("")) {
            Toast.makeText(Activity_insertar.this, "ESCRIBE UN FECHA", Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }

    public void guardar () {
        Propietario p = new Propietario(this);
        boolean respuesta = p.insertar(new Propietario(telefono.getText().toString(), nombre.getText().toString(), descripcion.getText().toString(), fecha.getText().toString()));
        String mensaje;
        if(respuesta) {
            mensaje = "Se insertó correctamente";
            nombre.setText("");
            telefono.setText("");
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
        Propietario p = new Propietario(this);
        boolean respuesta = p.actualizar(new Propietario(telefono.getText().toString(), nombre.getText().toString(), descripcion.getText().toString(), fecha.getText().toString()));
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
        Propietario p = new Propietario(this);
        boolean respuesta = p.eliminar(new Propietario(telefono.getText().toString(), nombre.getText().toString(), descripcion.getText().toString(), fecha.getText().toString()));
        String mensaje;
        if(respuesta) {
            mensaje = "Se eliminó correctamente";
            nombre.setText("");
            telefono.setText("");
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
