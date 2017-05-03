package com.example.guimoye.mario;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.mario.AcercaDe.frag_acercade;
import com.example.guimoye.mario.Auto.frag_auto;
import com.example.guimoye.mario.Favorito.frag_favorito;
import com.example.guimoye.mario.Historial.frag_historial;
import com.example.guimoye.mario.Loggin.LoginActivity;
import com.example.guimoye.mario.Mensajes.frag_mensajes;
import com.example.guimoye.mario.Perfil.frag_perfil;
import com.example.guimoye.mario.Permiso.frag_permiso;
import com.example.guimoye.mario.Servicios.MyServiceCliente;
import com.example.guimoye.mario.Servicios.MyServiceLocalizame;
import com.example.guimoye.mario.Servicios.MyServiceMensaje;
import com.example.guimoye.mario.Servicios.MyServiceTaxi;
import com.example.guimoye.mario.Ubicacion.frag_ubicacion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivityTaxista extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String tipo, correo;
    ImageView imagenNav;
    View hView;
    NavigationView navigationView;
    Bundle bundle;
    Fragment fragobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_taxi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView               = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //para poner iconos originales
        //navigationView.setItemIconTintList(null);

       // LayoutInflater inflater = getLayoutInflater();
        //View listHeaderView = inflater.inflate(R.layout.header_list,null, false);
       // mDrawerList.addHeaderView(listHeaderView);

         //actualizar datos de cabezera
        Bundle bolsa        =   getIntent().getExtras();
        hView               =   navigationView.getHeaderView(0);
        TextView nombreNav  =   (TextView)hView.findViewById(R.id.nombreNav);
        TextView correoNav  =   (TextView)hView.findViewById(R.id.correoNav);
        imagenNav           =   (ImageView)hView.findViewById(R.id.imagenNav);
        nombreNav.setText("Bienvenido:");
        correo              =   bolsa.getString("correo");
        tipo                =   bolsa.getString("tipo");
        correoNav.setText(correo);
        //imagenNav.setImageResource(R.drawable.ic_menu_send);


        // para abrir fragment automaticamente
        navigationView.setCheckedItem(R.id.nav_historial);
        bundle = new Bundle();
        bundle.putString("correo",correo);
        fragobj = new frag_historial();
        fragobj.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame,  fragobj).commit();
        getUser();

        if(tipo!="Admin"){

            Intent mServiceIntent = new Intent(this.getApplicationContext(), MyServiceLocalizame.class);
            mServiceIntent.putExtra("usr", correo);
            mServiceIntent.putExtra("tip", tipo);
            startService(mServiceIntent);

            Log.e("usurio tipoo ","tipo de usuariooo: "+tipo);
            if(tipo.equals("Taxista")){
                startService(new Intent(this.getApplicationContext(), MyServiceMensaje.class));

                Intent mService = new Intent(this.getApplicationContext(), MyServiceTaxi.class);
                mService.putExtra("usr", correo);
                mService.putExtra("tip", tipo);
                startService(mService);

            }else{
                if(tipo.equals("Cliente")){
                    Intent mService1 = new Intent(this.getApplicationContext(), MyServiceCliente.class);
                    mService1.putExtra("usr", correo);
                    mService1.putExtra("tip", tipo);
                    startService(mService1);

                }
            }
        }


    }

    public void salirSistema(){

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("Mensaje de Alerta");
        ab.setMessage("¿Deseas Cerrar Sesion?");
        ab.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                stopService(new Intent(getApplicationContext(), MyServiceLocalizame.class));
               // stopService(new Intent(getApplicationContext(), MyService.class));

                if(tipo.equals("Taxista")){
                    stopService(new Intent(getApplicationContext(), MyServiceMensaje.class));
                    stopService(new Intent(getApplicationContext(), MyServiceTaxi.class));
                }else{
                    if(tipo.equals("Cliente")){

                    }
                }

                startActivity(new Intent(MainActivityTaxista.this,LoginActivity.class));
                finish();
            }
        });

        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad = ab.create();
        ad.show();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        salirSistema();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getFragmentManager();
        bundle       = new Bundle();


         if (id == R.id.nav_perfil) {

            bundle.putString("correo",correo);
            Fragment fragobj = new frag_perfil();
            fragobj.setArguments(bundle);
            fm.beginTransaction().replace(R.id.content_frame, fragobj).commit();

        }else if (id == R.id.nav_auto) {

             navigationView.setCheckedItem(R.id.nav_auto);
             bundle.putString("correo",correo);
             Fragment fragobj = new frag_auto();
             fragobj.setArguments(bundle);
             fm.beginTransaction().replace(R.id.content_frame, fragobj).commit();

         }else  if (id == R.id.nav_terminos) {

            fm.beginTransaction().replace(R.id.content_frame, new frag_acercade()).commit();

        }else  if (id == R.id.nav_cerrar) {
            salirSistema();

        }else  if (id == R.id.nav_favorito) {

             bundle.putString("correo",correo);
             bundle.putString("tipo",tipo);
             Fragment fragobj = new frag_favorito();
             fragobj.setArguments(bundle);
             fm.beginTransaction().replace(R.id.content_frame, fragobj).commit();

         }else  if (id == R.id.nav_historial) {

             navigationView.setCheckedItem(R.id.nav_historial);
             bundle.putString("correo",correo);
             Fragment fragobj = new frag_historial();
             fragobj.setArguments(bundle);
             fm.beginTransaction().replace(R.id.content_frame, fragobj).commit();
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() { super.onResume(); }


    private void getUser(){

        RequestQueue queue;
        queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.9:8282/mario/usuarioGet.php?modelo=6&correo="+correo+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray urls = jsonObject.getJSONArray("result");


                            for(int i=0;i<urls.length();i++){
                                //imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
                               String fotoo         =   urls.getJSONObject(i).getString("foto");


                                Picasso.with(getApplicationContext()).invalidate(fotoo);
                                Picasso.with(getApplicationContext()).load(fotoo).into(imagenNav);
                                //Picasso.with(ctx).load(new File("/path/to/image")).skipMemoryCache().into(imageView)

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Esto trajoo ","trajo estooooo: "+error);
            } });

        queue.add(stringRequest);
    }


}
