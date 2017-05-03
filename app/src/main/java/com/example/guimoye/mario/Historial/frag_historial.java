package com.example.guimoye.mario.Historial;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
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

public class frag_historial extends Fragment {

    LayoutInflater inflater;
    ViewGroup container;
    View v;
    String getCorreoActivity;
    ListView lista;
    ArrayList<String> id        = new ArrayList<>();
    ArrayList<String> correo    = new ArrayList<>();
    ArrayList<String> carrera   = new ArrayList<>();
    ListViewAdapterHistorial adapter;
    Button btn_historial;
    private JSONArray urls;
    String[] getCorreo;
    String[] getCarrera;
    String[] getId;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        this.inflater       =   inflater;
        this.container      =   container;
        v                   =   inflater.inflate(R.layout.fragment_historial,container,false);
        lista               =   (ListView) v.findViewById(R.id.listaHistorial);
        Bundle bundle       =   this.getArguments();
        getCorreoActivity   =   bundle.getString("correo");
        Log.e("obtenidoo","correo obtenido para historial: "+ getCorreoActivity);
        Hilo("http://192.168.1.9:8282/mario/historial.php?modelo=3&correo="+ getCorreoActivity +"");

        return v;
    }

    private void resetear(){

        getCorreo               =   new String[urls.length()];
        getCarrera              =   new String[urls.length()];
        getId                   =   new String[urls.length()];

        id.clear();
        correo.clear();
        carrera.clear();
    }

    public void Hilo(String direccion){

        RequestQueue queue;
        queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, direccion,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject   =   new JSONObject(response);
                            urls                    =   jsonObject.getJSONArray("result");
                            resetear();

                            for(int i=0;i<urls.length();i++){

                                getId[i]            =   urls.getJSONObject(i).getString("id");
                                getCorreo[i]        =   urls.getJSONObject(i).getString("correo_his");
                                getCarrera[i]       =   urls.getJSONObject(i).getString("carrera");

                                id.add(getId[i]);
                                correo.add(getCorreo[i]);
                                carrera.add(getCarrera[i]);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter = new ListViewAdapterHistorial(getActivity(), id, correo, carrera);
                        lista.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Esto trajoo ","trajo estooooo: "+error);
            } });

        queue.add(stringRequest);
    }




    public class ListViewAdapterHistorial extends BaseAdapter {

        Context context;
        ArrayList<String> id        = new ArrayList<>();
        ArrayList<String> correo    = new ArrayList<>();
        ArrayList<String> carrera   = new ArrayList<>();
        LayoutInflater inflater;
        AlertDialog.Builder builder;
        AlertDialog ad;
        TextView txtHistorial;


        public ListViewAdapterHistorial(Context context, ArrayList<String> id,ArrayList<String> correo, ArrayList<String> carrera) {
            this.context        = context;
            this.id             = id;
            this.correo         = correo;
            this.carrera        = carrera;
            inflater            = LayoutInflater.from(context);
            Log.e("llamooo lenarr","llenarrr ");
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

            inflater        = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView   = inflater.inflate(R.layout.modelo_lista_historial, parent, false);
            txtHistorial    = (TextView) itemView.findViewById(R.id.row_txt_historial_correo);
            btn_historial   = (Button) itemView.findViewById(R.id.btn_modelo_historial);

            txtHistorial.setText(correo.get(position));

            if(carrera.get(position).equals("1")){
                btn_historial.setBackgroundResource(R.drawable.ic_califico_on);
               // btn_historial.setBackgroundColor(Color.GREEN);
            }else{
                         //    btn_historial.setBackgroundColor(Color.RED);
                btn_historial.setBackgroundResource(R.drawable.ic_califico_off);
                btn_historial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder = new AlertDialog.Builder(context);
                        LayoutInflater inflater2 = inflater;
                        final View dialoglayout = inflater2.inflate(R.layout.activity_calificar, null);
                        final RatingBar ratingBar = (RatingBar) dialoglayout.findViewById(R.id.ratingBarCalificar);
                        Button bCalificar   = (Button) dialoglayout.findViewById(R.id.bCalificar);

                        bCalificar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                cambiarHistorial(id.get(position),correo.get(position),ratingBar.getRating());
                                Toast.makeText(context,"Usuario Calificado Exitosamente ",Toast.LENGTH_LONG).show();

                                ad.dismiss();
                            }
                        });

                        builder.setView(dialoglayout);
                        ad = builder.create();
                        ad.show();
                    }
                });

            }


            return itemView;
        }


        private void cambiarHistorial(String id, final String correoo, final double rating){
            RequestQueue queue;
            queue = Volley.newRequestQueue(context);

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    "http://192.168.1.9:8282/mario/historial.php?modelo=4&id="+id+"",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            notifyDataSetChanged();
                            calificar(correoo,rating);
                            Log.e("Esto trajoo ","CAMBIO HISTORIAL");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Esto trajoo ","trajo estooooo: "+error);
                } });

            queue.add(stringRequest);
        }


        private void calificar(String correoo, double rating){
            RequestQueue queue;
            queue = Volley.newRequestQueue(context);

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    "http://192.168.1.9:8282/mario/rating.php?modelo=2&correo="+correoo+"&score="+rating+"",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_TEXT, "He usado la aplicacion de Taxi");
                            context.startActivity(Intent.createChooser(intent, "Seleccione donde compartir la aplicación"));
                            Hilo("http://192.168.1.9:8282/mario/historial.php?modelo=3&correo="+ getCorreoActivity +"");
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
