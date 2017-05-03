package com.example.guimoye.mario.Permiso;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.guimoye.mario.R;

import java.util.ArrayList;
import java.util.List;

public class frag_permiso extends Fragment {

    ArrayList<String> correo    = new ArrayList<>();
    ArrayList<String> imagenes = new ArrayList<>();
    ArrayList<Integer> estados  = new ArrayList<>();
    JsonPermisos ju;
    AlertDialog.Builder builder;
    AlertDialog ad;
    LayoutInflater inflater2;
    ListView lista;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View    v       =   inflater.inflate(R.layout.fragment_permiso,container,false);
        inflater2       =   inflater;
        lista           =   (ListView) v.findViewById(R.id.listaPermiso);
        ju              =   new JsonPermisos(v,inflater,container,getActivity(),
                lista, correo, imagenes,estados);



        /************** ventana de busqueda *****************/
        builder     = new AlertDialog.Builder(getActivity());
        inflater2   = getActivity().getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.consultar_usuarios, null);


        final Spinner listaBusqueda                 = (Spinner) dialoglayout.findViewById(R.id.selectBusqueda);
        List<String> list = new ArrayList<String>();
        list.add("Cliente");
        list.add("Taxista");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listaBusqueda.setAdapter(dataAdapter);

        final Button btnMoficiarUsuario             = (Button) dialoglayout.findViewById(R.id.btn_ConsultarBusqueda);

        btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listaBusqueda.getSelectedItemPosition()==0){
                   Log.e("presiono ","cliente");
                    ju.Hilo("http://192.168.1.9:8282/mario/usuarioGet.php?modelo=8&tipo=Cliente");
                }else{
                    if(listaBusqueda.getSelectedItemPosition()==1){
                        Log.e("presiono ","taxista");
                        ju.Hilo("http://192.168.1.9:8282/mario/usuarioGet.php?modelo=8&tipo=Taxista");
                    }
                }

                ad.dismiss();
            }
        });

        builder.setView(dialoglayout);
        ad = builder.create();
        ad.show();

        return v;
    }

}





