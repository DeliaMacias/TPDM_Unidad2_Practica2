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

public class Main2Activity extends AppCompatActivity {

    ListView lista;
    Seguro vector[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lista = findViewById(R.id.listaSeg);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent insertar = new Intent(Main2Activity.this, Seguro_insertar.class);
                insertar.putExtra("nuevo", false);
                insertar.putExtra("idseguro", vector[i].getIdseguro());
                insertar.putExtra("descripcion", vector[i].getDescripcion());
                insertar.putExtra("fecha", vector[i].getFecha());
                insertar.putExtra("tipo", vector[i].getTipo());
                insertar.putExtra("telefono", vector[i].getTelefono());
                startActivity(insertar);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        Seguro seguro = new Seguro(this);
        vector = seguro.consultar();
        String[] info = null;
        if(vector==null){
            info = new String[1];
            info[0] = "No hay seguros resgistrados aun";
        }else{
            info = new String[vector.length];
            for (int i = 0;i<vector.length;i++){
                Seguro t = vector[i];
                info[i] = t.getDescripcion()+"\n";
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,info);
        lista.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_seguros,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Metodo por si se selecciona algun item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.insertarSeg:
                Intent insertar = new Intent(this,Seguro_insertar.class);
                insertar.putExtra("nuevo", true);
                insertar.putExtra("idseguro", "");
                startActivity(insertar);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
