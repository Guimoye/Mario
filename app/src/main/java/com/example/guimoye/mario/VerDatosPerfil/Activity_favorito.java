package com.example.guimoye.mario.VerDatosPerfil;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.mario.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_favorito extends ActionBarActivity {

    LayoutInflater inflater;
    ViewGroup container;
    View v;
    String getCorreoActivity;
    String getTipoActivity;
    ListView lista;
    ArrayList<String> id_correo;
    ArrayList<String> correo;
    ArrayList<String> correo_fav;
    private JSONArray urls;
    ListViewAdapterFavorito adapter;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorito_ver);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        lista               =   (ListView) findViewById(R.id.listaFavoritos);
        Bundle bundle       =   getIntent().getExtras();
        getCorreoActivity   =   bundle.getString("correo");
        getTipoActivity     =   bundle.getString("tipo");
        Log.e("obtenidoo","correo obtenido para favorito: "+ getCorreoActivity+ "tipo: "+getTipoActivity);
        Hilo(1,"http://192.168.1.9:8282/mario/favorito.php?modelo=3&correo="+getCorreoActivity+"");

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Toast.makeText(getActivity(),"posicion "+position,Toast.LENGTH_LONG).show();
                try {


                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    //MENU FILA
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.ver:
                                    if(getTipoActivity.equals("Taxista")){
                                        Intent intent = new Intent(getApplicationContext(), VerDatosPerfilCliente.class);
                                        intent.putExtra("correo", correo_fav.get(position));
                                        intent.putExtra("correoActi", getCorreoActivity);
                                        getApplicationContext().startActivity(intent);

                                    }else{
                                        if(getTipoActivity.equals("Cliente")){
                                            Intent intent = new Intent(getApplicationContext(), VerDatosPerfilTaxi.class);
                                            intent.putExtra("correo", correo_fav.get(position));
                                            intent.putExtra("correoActi", getCorreoActivity);
                                            getApplicationContext().startActivity(intent);
                                        }
                                    }
                                break;
                            case R.id.eliminar:
                                Hilo(2,"http://192.168.1.9:8282/mario/favorito.php?modelo=2&id="+id_correo.get(position)+"");
                                break;
                        }
                        return true;
                    }
                    //FIN MENU FILA
                });
                popup.show();
                }catch (Exception e){

                }

                return true;
            }
        });


    }

    private void resetear(){
        id_correo   = new ArrayList<>();
        correo      = new ArrayList<>();
        correo_fav  = new ArrayList<>();
        Log.e("reseteandoo ","reseteandooo");
        id_correo.clear();
        correo.clear();
        correo_fav.clear();
    }

    public void Hilo(int modelo, String direccion){

        if(modelo==1){
            RequestQueue queue;
            queue = Volley.newRequestQueue(getApplicationContext());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, direccion,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            resetear();

                            try {
                                JSONObject jsonObject   =   new JSONObject(response);
                                urls                    =   jsonObject.getJSONArray("favoritos");

                                Log.e("reseteandoo ","reseteandooo FAVORITOS");
                                for(int i=0;i<urls.length();i++){
                                    Log.e("reseteandoo ","Bucleee");
                                    id_correo.add(urls.getJSONObject(i).getString("id"));
                                    correo.add( urls.getJSONObject(i).getString("correo"));
                                    correo_fav.add(urls.getJSONObject(i).getString("correo_fav"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            adapter = new ListViewAdapterFavorito();
                            lista.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Esto trajoo ","trajo estooooo: "+error);
                } });

            queue.add(stringRequest);

        }else {
            if(modelo==2){
                RequestQueue queue;
                queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.GET, direccion,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(),"Favorito eliminado con Exito",
                                        Toast.LENGTH_LONG).show();
                                Hilo(1,"http://192.168.1.9:8282/mario/favorito.php?modelo=3&correo="+getCorreoActivity+"");

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Esto trajoo ","trajo estooooo: "+error);
                    } });

                queue.add(stringRequest);
            }
        }

    }




    public class ListViewAdapterFavorito extends BaseAdapter {

        TextView txtHistorial;


        public ListViewAdapterFavorito() {
            inflater            = LayoutInflater.from(getApplicationContext());
        }


        public int getCount() {
            return correo.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        public View getView(final int position, final View convertView, ViewGroup parent) {

            inflater        = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView   = inflater.inflate(R.layout.modelo_lista_favorito, parent, false);
            txtHistorial    = (TextView) itemView.findViewById(R.id.row_txt_favorito_correo);
            txtHistorial.setText(correo_fav.get(position));
            return itemView;
        }

    }

}
