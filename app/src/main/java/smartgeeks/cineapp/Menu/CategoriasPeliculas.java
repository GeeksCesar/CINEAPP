package smartgeeks.cineapp.Menu;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import smartgeeks.cineapp.Adapter.CategoriaAdapter;
import smartgeeks.cineapp.Adapter.RecyclerItemClickListener;
import smartgeeks.cineapp.Entidades.Categoria;
import smartgeeks.cineapp.R;
import smartgeeks.cineapp.database.Mysql;


public class CategoriasPeliculas extends Fragment {

    View view;
    String consultaCategoria ;
    ArrayList<Categoria> listCategoria = new ArrayList<Categoria>();
    RecyclerView rvCategorias;
    GridLayoutManager gridLayoutManager;
    protected RecyclerView.LayoutManager layoutManager;
    CategoriaAdapter adapter ;

    public static final String TAG = "CategoriasPeliculas"  ;

    //PREFERENCES
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;

    //VOLLEY
    RequestQueue mRequestQueue;
    StringRequest mStringRequest ;


    public CategoriasPeliculas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.menu_categorias, container, false);

        rvCategorias= (RecyclerView) view.findViewById(R.id.rvCategorias);


        //CONFIGURAR RECYCLERVIEW & CARDVIEW
        layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rvCategorias.setLayoutManager(layoutManager);
        rvCategorias.setHasFixedSize(true);


        //CLICK CATEGORIA
        rvCategorias.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Context context = getActivity();
                int idCategoria = Integer.parseInt(listCategoria.get(position).getIdCategoria());

                sharedPreferences = context.getSharedPreferences(getString(R.string.pref_categoria),Context.MODE_PRIVATE);
                sharedEditor = sharedPreferences.edit();
                sharedEditor.putInt(getString(R.string.idCategoria), idCategoria);
                sharedEditor.commit();

                Log.d(TAG, "position: "+position);

                Fragment fragment = new MisPeliculas();
                getFragmentManager().beginTransaction().replace(R.id.content, fragment, "PELICULAS").commit();

            }
        }));

        consultaCategoria= Mysql.CONSULTA_GENERO ;
        datosCategoria(consultaCategoria);

        return view;
    }

    private void datosCategoria(String URL){
        Log.d(TAG, "url: "+URL);
        mRequestQueue = Volley.newRequestQueue(getActivity());

        mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                response = response.replace("&#47","/");

                JSONArray jsonArray = null;
                try {
                    Log.d(TAG, "json: "+response);
                    jsonArray = new JSONArray(response);
                    cargarDatosRecyclerview(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(mStringRequest);

    }

    private void cargarDatosRecyclerview(JSONArray jsonArray){
        ArrayList<Categoria> categoriaList = new ArrayList<Categoria>();

        for (int i=0 ; i < jsonArray.length(); i+=4){
            Bitmap bitmap=null;

            try {
                Categoria cat = new Categoria(
                        jsonArray.getString(i+1),
                        jsonArray.getString(i+2),
                        jsonArray.getString(i),
                        jsonArray.getString(i+3)  );

                categoriaList.add(cat);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            listCategoria = categoriaList ;
            adapter = new CategoriaAdapter(getActivity(), categoriaList);
            rvCategorias.setAdapter(adapter);
        }
    }

}
