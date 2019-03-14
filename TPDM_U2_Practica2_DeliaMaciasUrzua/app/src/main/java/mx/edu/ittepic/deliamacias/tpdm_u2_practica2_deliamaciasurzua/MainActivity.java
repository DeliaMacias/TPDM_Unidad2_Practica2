package mx.edu.ittepic.deliamacias.tpdm_u2_practica2_deliamaciasurzua;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    Propietario vector[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent insertar = new Intent(MainActivity.this, Activity_insertar.class);
                insertar.putExtra("nuevo", false);
                insertar.putExtra("telefono", vector[i].getTelefono());
                insertar.putExtra("nombre", vector[i].getNombre());
                insertar.putExtra("descripcion", vector[i].getDescripcion());
                insertar.putExtra("fecha", vector[i].getFecha());
                startActivity(insertar);
            }
        });
    }

    protected void onStart() {

        super.onStart();
        Propietario propietario = new Propietario(this);
        vector = propietario.consultar();
        String[] info = null;
        if(vector==null){
            info = new String[1];
            info[0] = "No hay propietarios resgistrados aun";
        }else{
            info = new String[vector.length];
            for (int i = 0;i<vector.length;i++){
                Propietario t = vector[i];
                info[i] = t.getNombre()+"\n";
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,info);
        lista.setAdapter(adaptador);
    }

    //Método para crear el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Metodo por si se selecciona algun item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.insertar:
                Intent insertar = new Intent(this,Activity_insertar.class);
                insertar.putExtra("nuevo", true);
                insertar.putExtra("telefono", "");
                startActivity(insertar);
                break;
            case R.id.seguros:
                Intent seguros = new Intent(this,Main2Activity.class);
                startActivity(seguros);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
