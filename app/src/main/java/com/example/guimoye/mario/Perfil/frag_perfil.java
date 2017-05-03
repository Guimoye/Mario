package com.example.guimoye.mario.Perfil;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.mario.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Guimoye on 20/11/2016.
 */

public class frag_perfil extends Fragment {

    private String UPLOAD_URL       =   "http://192.168.1.9:8282/mario/usuarioModif.php";
    LayoutInflater inflater;
    ViewGroup container;
    ImageView imageView;
    RatingBar rtb;
    View v;
    int cont[];
    double ratings[];
    String getCorreo;
    private int PICK_IMAGE_REQUEST  =   1;
    private Bitmap bitmap;

    String[] correo,id,tipo,clave,nombre,nacionalidad,nrodocumento,telefono,ocupacion,foto,status,pregunta,respuesta;
    AutoCompleteTextView aucorreo,autipo,auclave,aunombre,aunacionalidad,aunrodocumento,autelefono,auocupacion,aupregunta,aurespuesta;
    Bitmap[] bitmaps;
    ToggleButton tb;
    Button btn_perfil_users;

    private View focusView    =   null;
    final String JSON_ARRAY="result";
    private JSONArray urls;
    String estado       ="0";

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.inflater       =   inflater;
        this.container      =   container;
        v                   =   inflater.inflate(R.layout.fragment_perfil,container,false);
        imageView           =   (ImageView)v.findViewById(R.id.imageView2);
        rtb                 =   (RatingBar)v.findViewById(R.id.ratingBarPerfil);
        tb                  =   (ToggleButton)v.findViewById(R.id.toggleButtonPerfil);
        btn_perfil_users    =   (Button)v.findViewById(R.id.btn_perfil_users);

        aucorreo            =   (AutoCompleteTextView)v.findViewById(R.id.txtCorreoPerfil);
        autipo              =   (AutoCompleteTextView)v.findViewById(R.id.txtTipoPerfil);
        auclave             =   (AutoCompleteTextView)v.findViewById(R.id.txtClavePerfil);
        aunombre            =   (AutoCompleteTextView)v.findViewById(R.id.txtNombrePerfil);
        aunacionalidad      =   (AutoCompleteTextView)v.findViewById(R.id.txtNacionalidadPerfil);
        aunrodocumento      =   (AutoCompleteTextView)v.findViewById(R.id.txtDocumentoPerfil);
        autelefono          =   (AutoCompleteTextView)v.findViewById(R.id.txtTlfnoPerfil);
        auocupacion         =   (AutoCompleteTextView)v.findViewById(R.id.txtOcupacionPerfil);
        aupregunta          =   (AutoCompleteTextView)v.findViewById(R.id.txtPreguntaPerfil);
        aurespuesta         =   (AutoCompleteTextView)v.findViewById(R.id.txtRespuestaPerfil);

        aucorreo.setEnabled(false);
        autipo.setEnabled(false);
        rtb.setNumStars(5);
        rtb.setEnabled(false);

        Bundle bundle   =   this.getArguments();
        getCorreo       =   bundle.getString("correo");
        Log.e("obtenidoo","correo obtenido: "+getCorreo);


        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    estado="1";
                }else{
                    estado="2";
                }

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), PICK_IMAGE_REQUEST);
            }
        });

        btn_perfil_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        getUser();

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getUser(){

        RequestQueue queue;
        queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.9:8282/mario/usuarioGet.php?modelo=6&correo="+getCorreo+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            urls = jsonObject.getJSONArray(JSON_ARRAY);


                            bitmaps         =   new Bitmap[urls.length()];

                            correo          =   new String[urls.length()];
                            id              =   new String[urls.length()];
                            tipo            =   new String[urls.length()];
                            clave           =   new String[urls.length()];
                            nombre          =   new String[urls.length()];
                            nacionalidad    =   new String[urls.length()];
                            nrodocumento    =   new String[urls.length()];
                            telefono        =   new String[urls.length()];
                            ocupacion       =   new String[urls.length()];
                            foto            =   new String[urls.length()];
                            status          =   new String[urls.length()];
                            pregunta        =   new String[urls.length()];
                            respuesta       =   new String[urls.length()];

                            for(int i=0;i<urls.length();i++){
                                //imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
                                correo[i]       =   urls.getJSONObject(i).getString("correo");
                                id[i]           =   urls.getJSONObject(i).getString("id");
                                tipo[i]         =   urls.getJSONObject(i).getString("tipo");
                                clave[i]        =   urls.getJSONObject(i).getString("clave");
                                nombre[i]       =   urls.getJSONObject(i).getString("nombre");
                                nacionalidad[i] =   urls.getJSONObject(i).getString("nacionalidad");
                                nrodocumento[i] =   urls.getJSONObject(i).getString("nrodocumento");
                                telefono[i]     =   urls.getJSONObject(i).getString("telefono");
                                ocupacion[i]    =   urls.getJSONObject(i).getString("ocupacion");
                                foto[i]         =   urls.getJSONObject(i).getString("foto");
                                status[i]       =   urls.getJSONObject(i).getString("status");
                                pregunta[i]     =   urls.getJSONObject(i).getString("pregunta");
                                respuesta[i]    =   urls.getJSONObject(i).getString("respuesta");

                                aucorreo.setText(correo[i]);
                                autipo.setText(tipo[i]);
                                auclave.setText(clave[i]);
                                aunombre.setText(nombre[i]);
                                aunacionalidad.setText(nacionalidad[i]);
                                aunrodocumento.setText(nrodocumento[i]);
                                autelefono.setText(telefono[i]);
                                auocupacion.setText(ocupacion[i]);
                                aupregunta.setText(pregunta[i]);
                                aurespuesta.setText(respuesta[i]);

                                if(status[i].equals("1")){
                                    tb.setChecked(true);
                                }else{ tb.setChecked(false); }

                                Picasso.with(getActivity()).invalidate(foto[i]);
                                Picasso.with(getActivity()).load(foto[i]).into(imageView);
                                //Picasso.with(ctx).load(new File("/path/to/image")).skipMemoryCache().into(imageView)
                              /*
                                Picasso.with(getActivity()).load(foto[i]).placeholder(R.drawable.fotoperfil)
                                        .error(R.drawable.fotoperfil)
                                        .into(imageView,new com.squareup.picasso.Callback(){

                                            @Override
                                            public void onSuccess() {}

                                            @Override
                                            public void onError() { }
                                        });*/
                                getRating();

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

    private void getRating(){

        RequestQueue queue;
        queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.9:8282/mario/rating.php?modelo=1&correo="+getCorreo+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject  =   new JSONObject(response);
                            urls        =   jsonObject.getJSONArray(JSON_ARRAY);
                            cont        =   new int[10];
                            ratings     =   new double[urls.length()];

                            for(int i=0;i<urls.length();i++){
                                ratings[i]       =   urls.getJSONObject(i).getDouble("score");

                                if(ratings[i]==0.5){
                                    cont[0]+=1;
                                }else{
                                    if(ratings[i]==1){
                                        cont[1]+=1;
                                    }else{
                                        if(ratings[i]==1.5){
                                            cont[2]+=1;
                                        }else{
                                            if(ratings[i]==2){
                                                cont[3]+=1;
                                            }else{
                                                if(ratings[i]==2.5){
                                                    cont[4]+=1;
                                                }else{
                                                    if(ratings[i]==3){
                                                        cont[5]+=1;
                                                    }else{
                                                        if(ratings[i]==3.5){
                                                            cont[6]+=1;
                                                        }else{
                                                            if(ratings[i]==4){
                                                                cont[7]+=1;
                                                            }else{
                                                                if(ratings[i]==4.5){
                                                                    cont[8]+=1;
                                                                }else{
                                                                    if(ratings[i]==5){
                                                                        cont[9]+=1;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            int  iPosicion = 0;
                            double iNumeroMayor = cont[0];
                            for (int x=1;x<cont.length;x++){
                                if (cont[x]>iNumeroMayor){
                                    iNumeroMayor = cont[x];
                                    iPosicion = x;
                                }
                            }


                            double aux = 0;
                            for (int i =0; i<iPosicion+1; i++){
                                aux+=0.5;
                                Log.e("pasandoo!","pasandooo! ");
                            }
                            Log.e("totaaaL ","totaaal "+aux);
                            rtb.setRating(Float.parseFloat(aux+""));

                        }catch (Exception e){}


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Esto trajoo ","trajo estooooo: "+error);
            } });

         queue.add(stringRequest);

    }


    private void uploadImage(){
        //Showing the progress dialog
        limpiarRequired();

        if(aucorreo.getText().toString().contains("@") && aucorreo.getText().toString().contains(".com") ){
            if(auclave.getText().length()!=0){
                if(aunombre.getText().length()!=0){
                    if(aunacionalidad.getText().length()!=0){
                        if(aunrodocumento.getText().length()!=0){
                            if(autelefono.getText().length()!=0){
                                if(autelefono.getText().length()!=0){
                                    if(aupregunta.getText().length()!=0){
                                        if(aurespuesta.getText().length()!=0){

                                            final ProgressDialog loading = ProgressDialog.show(getActivity(),"Cargando...","Por favor Espere...",false,false);
                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String s) {
                                                            //Disimissing the progress dialog
                                                            loading.dismiss();
                                                            //Showing toast message of the response
                                                            if(s.equals("") || s==null){
                                                                Toast.makeText(getActivity(), "Disculpe el correo que ha introducido ya existe" , Toast.LENGTH_LONG).show();
                                                            }else{
                                                                Toast.makeText(getActivity(), s , Toast.LENGTH_LONG).show();
                                                              //  Toast.makeText(getActivity(), s+" \npor favor inicia sesion nuevamente" , Toast.LENGTH_LONG).show();
                                                              //  getActivity().finish();
                                                            }

                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError volleyError) {
                                                            //Dismissing the progress dialog
                                                            loading.dismiss();

                                                            //Showing toast
                                                            Toast.makeText(getActivity(), "errado "+volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                        }
                                                    }){
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    //Converting Bitmap to String

                                                    //Creating parameters
                                                    Map<String,String> params = new Hashtable<String, String>();

                                                    //Adding parameters
                                                    params.put("getcorreo", getCorreo);
                                                    params.put("correo", aucorreo.getText().toString());
                                                    params.put("getid", id[0]);
                                                    params.put("tipo", autipo.getText().toString());
                                                    params.put("clave", auclave.getText().toString());
                                                    params.put("nombre", aunombre.getText().toString());
                                                    params.put("nacionalidad", aunacionalidad.getText().toString());
                                                    params.put("nrodocumento", aunrodocumento.getText().toString());
                                                    params.put("telefono", autelefono.getText().toString());
                                                    params.put("ocupacion", auocupacion.getText().toString());
                                                    params.put("foto", getStringImage(bitmap));
                                                    params.put("status", estado);
                                                    params.put("pregunta", aupregunta.getText().toString());
                                                    params.put("respuesta", aurespuesta.getText().toString());




                                                    //returning parameters
                                                    return params;
                                                }
                                            };

                                            //Creating a Request Queue
                                            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                                            //Adding request to the queue
                                            requestQueue.add(stringRequest);


                                        }else{
                                            vali(aurespuesta);
                                        }
                                    }else{
                                        vali(aupregunta);
                                    }
                                }else{
                                    vali(auocupacion);
                                }
                            }else{
                                vali(autelefono);
                            }
                        }else{
                            vali(aunrodocumento);
                        }
                    }else{
                        vali(aunacionalidad);
                    }
                }else{
                    vali(aunombre);
                }
            }else{
                vali(auclave);
            }

        }else{
            aucorreo.setError(getString(R.string.error_invalid_email));
            focusView   =   aucorreo;
            focusView.requestFocus();
        }

    }

    public String getStringImage(Bitmap bmp){

         ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        }catch (Exception e){ }

        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;


    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        focusView=t;
        focusView.requestFocus();
    }

    private void limpiarRequired(){
        aucorreo.setError(null);
        auclave.setError(null);
        aunombre.setError(null);
        aunacionalidad.setError(null);
        aunrodocumento.setError(null);
        autelefono.setError(null);
        auocupacion.setError(null);
        aupregunta.setError(null);
        aurespuesta.setError(null);
    }


}





