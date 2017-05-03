package com.example.guimoye.mario.Registrar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.mario.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Register extends ActionBarActivity {

    private AutoCompleteTextView txtCorreoRegister,txtClaveRegister,txtNombreRegister,
            txtNacionalidadRegister,txtNroDocumentoRegister,txtTlfnoRegister,txtOcupacionRegister,
            txtPreguntaRegister,txtRespuestaRegister;
    private Button bRegister,buttonChoose;
    private String clave,correo,pregunta,respuesta,tipo,nombre,nacionalidad,nrodocumento,ocupacion,status,tlfno;

    private View focusView          =   null;
    private int PICK_IMAGE_REQUEST  =   1;
    private Bitmap bitmap;
    private ImageView imageView;
    private String UPLOAD_URL       =   "http://192.168.1.9:8282/mario/usuario.php";
    private String KEY_CORREO       =   "correo";
    private String KEY_TIPO         =   "tipo";
    private String KEY_CLAVE        =   "clave";
    private String KEY_NOMBRE       =   "nombre";
    private String KEY_NACIONALIDAD =   "nacionalidad";
    private String KEY_NRODOCUMENTO =   "nrodocumento";
    private String KEY_TELEFONO     =   "telefono";
    private String KEY_OCUPACION    =   "ocupacion";
    private String KEY_FOTO         =   "foto";
    private String KEY_STATUS       =   "status";
    private String KEY_PREGUNTA     =   "pregunta";
    private String KEY_RESPUESTA    =   "respuesta";
    Spinner lista;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        txtCorreoRegister       =   (AutoCompleteTextView) findViewById(R.id.txtCorreoRegister);
        txtClaveRegister        =   (AutoCompleteTextView) findViewById(R.id.txtClaveRegister);
        txtNombreRegister       =   (AutoCompleteTextView) findViewById(R.id.txtNombreRegister);
        txtNacionalidadRegister =   (AutoCompleteTextView) findViewById(R.id.txtNacionalidadRegister);
        txtNroDocumentoRegister =   (AutoCompleteTextView) findViewById(R.id.txtDocumentoRegister);
        txtTlfnoRegister        =   (AutoCompleteTextView) findViewById(R.id.txtTlfnoRegister);
        txtOcupacionRegister    =   (AutoCompleteTextView) findViewById(R.id.txtOcupacionRegister);
        txtPreguntaRegister     =   (AutoCompleteTextView) findViewById(R.id.txtPreguntaRegister);
        txtRespuestaRegister    =   (AutoCompleteTextView) findViewById(R.id.txtRespuestaRegister);
        bRegister               =   (Button) findViewById(R.id.btn_registrar_users);
        buttonChoose            =   (Button) findViewById(R.id.buttonChoose);
        imageView               =   (ImageView) findViewById(R.id.imageView);

        lista                   =   (Spinner) findViewById(R.id.spinner);

        List<String> list = new ArrayList<String>();
        list.add("Cliente");
        list.add("Taxista");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lista.setAdapter(dataAdapter);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        //Showing the progress dialog
        limpiarRequired();

        if(txtCorreoRegister.getText().toString().contains("@") && txtCorreoRegister.getText().toString().contains(".com") ){
                if(txtClaveRegister.getText().length()!=0){
                    if(txtNombreRegister.getText().length()!=0){
                        if(txtNacionalidadRegister.getText().length()!=0){
                            if(txtNroDocumentoRegister.getText().length()!=0){
                                if(txtTlfnoRegister.getText().length()!=0){
                                    if(txtOcupacionRegister.getText().length()!=0){
                                        if(txtPreguntaRegister.getText().length()!=0){
                                            if(txtRespuestaRegister.getText().length()!=0){

                                                final ProgressDialog loading = ProgressDialog.show(this,"Cargando...","Por favor Espere...",false,false);
                                                StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String s) {
                                                                //Disimissing the progress dialog
                                                                loading.dismiss();
                                                                //Showing toast message of the response
                                                                if(s.equals("") || s==null){
                                                                    Toast.makeText(Register.this, "Disculpe el correo que ha introducido ya existe" , Toast.LENGTH_LONG).show();
                                                                }else{
                                                                    Toast.makeText(Register.this, s , Toast.LENGTH_LONG).show();
                                                                    limpiar();
                                                                    regitrarToCarreras();
                                                                }

                                                            }
                                                        },
                                                        new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError volleyError) {
                                                                //Dismissing the progress dialog
                                                                loading.dismiss();

                                                                //Showing toast
                                                                Toast.makeText(Register.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                            }
                                                        }){
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        //Converting Bitmap to String

                                                        correo          =   txtCorreoRegister.getText().toString();
                                                        tipo            =   lista.getSelectedItem().toString();
                                                        clave           =   txtClaveRegister.getText().toString();
                                                        nombre          =   txtNombreRegister.getText().toString();
                                                        nacionalidad    =   txtNacionalidadRegister.getText().toString();
                                                        nrodocumento    =   txtNroDocumentoRegister.getText().toString();
                                                        tlfno           =   txtTlfnoRegister.getText().toString().trim();
                                                        ocupacion       =   txtOcupacionRegister.getText().toString();
                                                        status          =   "0";
                                                        pregunta        =   txtPreguntaRegister.getText().toString();
                                                        respuesta       =   txtRespuestaRegister.getText().toString();

                                                        String image    =   getStringImage(bitmap);


                                                        //Getting Image Name

                                                        //Creating parameters
                                                        Map<String,String> params = new Hashtable<String, String>();

                                                        //Adding parameters
                                                        params.put(KEY_CORREO, correo);
                                                        params.put(KEY_TIPO, tipo);
                                                        params.put(KEY_CLAVE, clave);
                                                        params.put(KEY_NOMBRE, nombre);
                                                        params.put(KEY_NACIONALIDAD, nacionalidad);
                                                        params.put(KEY_NRODOCUMENTO, nrodocumento);
                                                        params.put(KEY_TELEFONO, tlfno);
                                                        params.put(KEY_OCUPACION, ocupacion);
                                                        params.put(KEY_FOTO, image);
                                                        params.put(KEY_STATUS, status);
                                                        params.put(KEY_PREGUNTA, pregunta);
                                                        params.put(KEY_RESPUESTA, respuesta);

                                                        //returning parameters
                                                        return params;
                                                    }
                                                };

                                                //Creating a Request Queue
                                                RequestQueue requestQueue = Volley.newRequestQueue(this);

                                                //Adding request to the queue
                                                requestQueue.add(stringRequest);


                                            }else{
                                                vali(txtRespuestaRegister);
                                            }
                                        }else{
                                            vali(txtPreguntaRegister);
                                        }
                                    }else{
                                        vali(txtOcupacionRegister);
                                    }
                                }else{
                                    vali(txtTlfnoRegister);
                                }
                            }else{
                                vali(txtNroDocumentoRegister);
                            }
                        }else{
                            vali(txtNacionalidadRegister);
                        }
                    }else{
                        vali(txtNombreRegister);
                    }
                }else{
                    vali(txtClaveRegister);
                }

        }else{
            txtCorreoRegister.setError(getString(R.string.error_invalid_email));
            focusView   =   txtCorreoRegister;
            focusView.requestFocus();
        }

    }

    private void regitrarToCarreras(){

        if(tipo.equals("Taxista")){

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url="http://192.168.1.9:8282/mario/carrerataxi.php?modelo=1" +
                    "&correo="+correo+"" +
                    "&tipo="+tipo+"";
            // Request a string response from the provided URL.

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("estadoooo ","REGISTRO CARRERA TAXI");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            });
            queue.add(stringRequest);

        }else{
            if(tipo.equals("Cliente")){

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url="http://192.168.1.9:8282/mario/carreracliente.php?modelo=1" +
                        "&correo="+correo+"" +
                        "&tipo="+tipo+"";
                // Request a string response from the provided URL.

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("estadoooo ","REGISTRO CARRERA CLIENTE");
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                });
                queue.add(stringRequest);

            }
        }

    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        focusView=t;
        focusView.requestFocus();
    }

    private void limpiarRequired(){
        txtCorreoRegister.setError(null);
        txtClaveRegister.setError(null);
        txtNombreRegister.setError(null);
        txtNacionalidadRegister.setError(null);
        txtNroDocumentoRegister.setError(null);
        txtTlfnoRegister.setError(null);
        txtOcupacionRegister.setError(null);
        txtPreguntaRegister.setError(null);
        txtRespuestaRegister.setError(null);
    }

    private void limpiar(){
        txtCorreoRegister.setText(null);
        txtClaveRegister.setText(null);
        txtNombreRegister.setText(null);
        txtNacionalidadRegister.setText(null);
        txtNroDocumentoRegister.setText(null);
        txtTlfnoRegister.setText(null);;
        txtOcupacionRegister.setText(null);
        txtPreguntaRegister.setText(null);
        txtRespuestaRegister.setText(null);
        imageView.setImageResource(R.drawable.fotoperfil);
    }
}
