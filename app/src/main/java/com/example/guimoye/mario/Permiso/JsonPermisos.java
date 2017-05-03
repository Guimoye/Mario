package com.example.guimoye.mario.Permiso;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Guimoye on 27/10/2016.
 */

public class JsonPermisos {


    ListView lista;
    ArrayList<String> correo;
    ArrayList<String> imagenes;
    ArrayList<Integer> estados;
    private JSONArray urls;

    ListViewAdapter adapter;
    Context myContext;
    View v;

    String[] getCorreo;
    String[] getTipo;
    String[] getImagenes;
    int[] getEstados;

    JSONArray alumnosJSON=null;
   // JsonUsersDelete ju;
    LayoutInflater inflaterr;
    ViewGroup containerr;

    public String getCorreos(int i){ return getCorreo[i];}
    public String getImagenes(int i){ return getImagenes[i];}
    public int getEstados(int i){ return getEstados[i];}
    public ListView getLista (){
        return lista;
    }

    private void reset(){
        getCorreo   = new String[alumnosJSON.length()];
        getTipo     = new String[alumnosJSON.length()];
        getImagenes = new String[alumnosJSON.length()];
        getEstados  = new int[alumnosJSON.length()];
        correo.clear();
        imagenes.clear();
        estados.clear();
    }

    public JsonPermisos(View v, LayoutInflater inflater, ViewGroup container, final Context myContext,
                        ListView lista, ArrayList<String> correo, ArrayList<String> imagenes, ArrayList<Integer> estados){
        this.myContext          =   myContext;
        this.lista              =   lista;
        this.correo             =   correo;
        this.imagenes           =   imagenes;
        this.estados            =   estados;
        this.inflaterr          =   inflater;
        this.containerr         =   container;
        this.v                  =   v;

        this.correo             =   new ArrayList<>();
        this.imagenes           =   new ArrayList<>();
        this.estados            =   new ArrayList<>();


    }


    public void Hilo(String direccion){

       // if(nro.equals("1")){
            RequestQueue queue;
            queue = Volley.newRequestQueue(myContext);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, direccion,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject jsonObject = null;
                            try {
                                jsonObject  = new JSONObject(response);
                                urls        = jsonObject.getJSONArray("result");


                             //  bitmaps         =   new Bitmap[urls.length()];

                                getCorreo       =   new String[urls.length()];
                                getTipo         =   new String[urls.length()];
                                getImagenes     =   new String[urls.length()];
                                getEstados      =   new int[urls.length()];

                                for(int i=0;i<urls.length();i++){
                                    //imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
                                    getCorreo[i]      =   urls.getJSONObject(i).getString("correo");
                                    getTipo[i]      =   urls.getJSONObject(i).getString("tipo");
                                    getImagenes[i]    =   urls.getJSONObject(i).getString("foto");
                                    getEstados[i]     =   urls.getJSONObject(i).getInt("status");


                                    Log.e("ajaa","ejeee: "+getCorreo[i]);
                                    Log.e("ajaa","ejeee: "+getImagenes[i]);
                                    Log.e("ajaa","ejeee: "+getEstados[i]);

                                    correo.add(getCorreo[i]);
                                    imagenes.add(getImagenes[i]);
                                    estados.add(getEstados[i]);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            adapter = new ListViewAdapter(myContext, correo,imagenes,estados,getTipo);
                            lista.setAdapter(adapter);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Esto trajoo ","trajo estooooo: "+error);
                } });

            queue.add(stringRequest);

/*
        }else{
            if(nro.equals("2")){

            }
        }

*/

    }



/*
    ju.getLista().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) {
            positionn=position;
            PopupMenu popup = new PopupMenu(getActivity(), view);
            popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                //MENU FILA
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_settings:
                            ju.Hilo(GET2+ju.getId_materiales(position),"3");
                            break;
                        case R.id.action_settings1:
                            builder = new AlertDialog.Builder(getActivity());
                            LayoutInflater inflater = getActivity().getLayoutInflater();
                            final View dialoglayout = inflater.inflate(R.layout.modificar_materiales, null);

                            umMaterial = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtMaterialesModificar);
                            umMaterial.setText(ju.getMateriales(position));

                            final Button btnMoficiarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_modificar_materiales);
                            btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ju.Hilo(direccion+"2" +
                                                    "&id_materiales="+ju.getId_materiales(position)+"" +
                                                    "&materiales="+ umMaterial.getText()+""
                                            ,"3");
                                    ad.dismiss();
                                }
                            });

                            builder.setView(dialoglayout);
                            ad = builder.create();
                            ad.show();

                            break;
                    }
                    return true;
                }
                //FIN MENU FILA
            });
            popup.show();
            return true;
        }
    });
*/

}
