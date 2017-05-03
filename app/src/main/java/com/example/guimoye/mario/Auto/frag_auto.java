package com.example.guimoye.mario.Auto;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.mario.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class frag_auto extends Fragment {

    String getCorreo;

    AutoCompleteTextView aucorreo, aumodelo, auplaca, aucolor;
    Button btn_auto;

    private View focusView          =   null;
    private JSONArray urls;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View    v           =   inflater.inflate(R.layout.fragment_auto,container,false);
        btn_auto            =   (Button)v.findViewById(R.id.btn_auto);

        aucorreo            =   (AutoCompleteTextView)v.findViewById(R.id.txtCorreoAuto);
        aumodelo            =   (AutoCompleteTextView)v.findViewById(R.id.txtModeloAuto);
        auplaca             =   (AutoCompleteTextView)v.findViewById(R.id.txtPlacaAuto);
        aucolor             =   (AutoCompleteTextView)v.findViewById(R.id.txtColorAuto);


        Bundle bundle   =   this.getArguments();
        getCorreo       =   bundle.getString("correo");

        aucorreo.setEnabled(false);
        aucorreo.setText(getCorreo);

        llenarCampos();


        btn_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aumodelo.getText().length()!=0){
                    if(auplaca.getText().length()!=0){
                        if(aucolor.getText().length()!=0){

                            limpiarRequired();
                            eventHecho();

                        }else{
                            vali(aucolor);
                        }
                    }else{
                        vali(auplaca);
                    }
                }else{
                    vali(aumodelo);
                }
            }
        });


        return v;
    }


    private void llenarCampos(){
        RequestQueue queue;
        queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://192.168.1.9:8282/mario/carros.php?modelo=4&correo="+getCorreo+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {

                            jsonObject  =   new JSONObject(response);
                            urls        =   jsonObject.getJSONArray("carros");
                            aumodelo.setText(urls.getJSONObject(0).getString("modelocar"));
                            auplaca.setText(urls.getJSONObject(0).getString("placa"));
                            aucolor.setText(urls.getJSONObject(0).getString("color"));

                        }catch (Exception e){}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Esto trajoo ","trajo estooooo: "+error);
            } });

        queue.add(stringRequest);
    }


    private void eventHecho(){
        RequestQueue queue;
        queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://192.168.1.9:8282/mario/carros.php?modelo=1" +
                        "&correo="+getCorreo+"" +
                        "&modelocar="+aumodelo.getText().toString()+"" +
                        "&placa="+auplaca.getText().toString()+"" +
                        "&color="+aucolor.getText().toString()+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Toast.makeText(getActivity(),new JSONObject(response).getString("Estado"),Toast.LENGTH_LONG).show();

                        }catch (Exception e){}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Esto trajoo ","trajo estooooo: "+error);
            } });

        queue.add(stringRequest);
    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        focusView=t;
        focusView.requestFocus();
    }

    private void limpiarRequired(){
        aucorreo.setError(null);
        auplaca.setError(null);
        aumodelo.setError(null);
        aucolor.setError(null);
    }

}





