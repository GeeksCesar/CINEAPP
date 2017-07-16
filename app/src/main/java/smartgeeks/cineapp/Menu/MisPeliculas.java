package smartgeeks.cineapp.Menu;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import smartgeeks.cineapp.Adapter.PeliculasAdapter;
import smartgeeks.cineapp.Adapter.RecyclerItemClickListener;
import smartgeeks.cineapp.Entidades.Peliculas;
import smartgeeks.cineapp.R;
import smartgeeks.cineapp.database.Mysql;
import smartgeeks.cineapp.database.Pedido;


public class MisPeliculas extends Fragment {

    View vista ;
    public static final String TAG = "MisPeliculas";

    RecyclerView rvPeliculas;
    SwipeRefreshLayout refreshLayout;
    GridLayoutManager gridLayoutManager;
    RecyclerView.LayoutManager layoutManager;
    PeliculasAdapter peliculasAdapter;

    Dialog dialogPelicula ;
    int generoSeleccionado;
    ArrayList<Peliculas> listPeliculas = new ArrayList<Peliculas>();

    String consultaPelicula;
    String NombrePelicula ;

    //int
    int newPrecioPelicula =0;
    int cntPelicula = 0;
    int preciofinal =0;

    //PREFERENCES
    Context context;
    SharedPreferences preferences;

    //VOLLEY
    RequestQueue mRequestQueue;
    StringRequest mStringRequest ;

    //variables dialog
    String nombrePelicula;
    int precioPelicula, IdPelicula;
    ImageView imgPelicula , btnAddCarrito, ivValidacionPelicula;
    TextView tvNombrePel, tvPrecioPel , tvCantidad;
    Button btnAdd, btnQuit;
    String urlImgPelicula;


    public MisPeliculas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista= inflater.inflate(R.layout.menu_peliculas, container, false);

        context = getActivity();

        refreshLayout = (SwipeRefreshLayout) vista.findViewById(R.id.swipeRefreshLayout);
        rvPeliculas= (RecyclerView) vista.findViewById(R.id.rvProductos);
        rvPeliculas.setHasFixedSize(true);
        ivValidacionPelicula = (ImageView) vista.findViewById(R.id.ivValidacionPelicula);

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rvPeliculas.setLayoutManager(layoutManager);

        preferences = context.getSharedPreferences(getString(R.string.pref_categoria), Context.MODE_PRIVATE);
        generoSeleccionado = preferences.getInt(getString(R.string.idCategoria), 202);

        Log.d(TAG, "IdGenero: "+generoSeleccionado);

        datosPeliculas();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RefreshingData();
                    }
                }, 2000);

            }
        });


        //CLICK EN PELICULA SELECCIONADA
        rvPeliculas.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                nombrePelicula = (listPeliculas.get(position).getNombrePelicula());
                precioPelicula = Integer.parseInt(String.valueOf(listPeliculas.get(position).getPrecio()));
                IdPelicula= Integer.parseInt(String.valueOf(listPeliculas.get(position).getIdPelicula()));

                newPrecioPelicula = precioPelicula;
                preciofinal = precioPelicula;
                cntPelicula =1;

                dialogPelicula = new Dialog(getActivity());
                dialogPelicula.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogPelicula.setContentView(R.layout.viewdialogpelicula);

                //INSTANCE
                imgPelicula = (ImageView) dialogPelicula.findViewById(R.id.ivImagenPelicula);
                btnAddCarrito= (ImageView) dialogPelicula.findViewById(R.id.btnAddCarrito) ;
                tvNombrePel= (TextView) dialogPelicula.findViewById(R.id.tvNombrePelicula);
                tvPrecioPel= (TextView) dialogPelicula.findViewById(R.id.tvPrecioPel);
                tvCantidad= (TextView) dialogPelicula.findViewById(R.id.tvCantidadPel);
                btnAdd= (Button) dialogPelicula.findViewById(R.id.btnAgregar);
                btnQuit= (Button) dialogPelicula.findViewById(R.id.btnQuitar);

                //llenar DIALOG
                urlImgPelicula= listPeliculas.get(position).getImageURl();
                Glide.with(getActivity()).load(urlImgPelicula).into(imgPelicula);
                tvPrecioPel.setText(""+precioPelicula);
                tvNombrePel.setText(nombrePelicula);

                //ACTION BUTTON
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cntPelicula ++ ;
                        preciofinal = newPrecioPelicula * cntPelicula ;

                        tvPrecioPel.setText(""+preciofinal);
                        tvCantidad.setText(""+cntPelicula);
                    }
                });

                btnQuit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cntPelicula > 1){
                            cntPelicula -- ;
                            preciofinal = newPrecioPelicula * cntPelicula ;

                            tvPrecioPel.setText(""+preciofinal);
                            tvCantidad.setText(""+cntPelicula);
                        }
                    }
                });

                btnAddCarrito.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Pedido pedido = new Pedido(nombrePelicula, cntPelicula, urlImgPelicula, preciofinal, 1, IdPelicula);
                        Log.i(TAG,"idPelicula: "+IdPelicula);
                        pedido.save();

                        dialogPelicula.dismiss();

                    }
                });

                dialogPelicula.show();


            }
        }));



        return vista;
    }

    public void RefreshingData() {
        datosPeliculas();
        refreshLayout.setRefreshing(false);
        refreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
    }

    private void datosPeliculas(){
        mRequestQueue = Volley.newRequestQueue(getActivity());

        consultaPelicula = Mysql.CONSULTA_PELICULA + generoSeleccionado;
        mStringRequest = new StringRequest(Request.Method.GET, consultaPelicula, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                response = response.replace("&#47","/");

                JSONArray jsonArray = null;

                try {
                    jsonArray = new JSONArray(response);
                        //method
                        Log.d(TAG, "array: "+jsonArray);
                        cargarDatosRecyclerviewPel(jsonArray);

                }catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "entro");
                    ivValidacionPelicula.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(mStringRequest);
    }

    private void cargarDatosRecyclerviewPel(JSONArray jsonArray) {
        ArrayList<Peliculas> peliculasList = new ArrayList<Peliculas>();

        for (int i= 0 ; i< jsonArray.length() ; i+=9){

            try {
             Peliculas peliculas = new Peliculas(
                     jsonArray.getInt(i),
                     jsonArray.getString(i+1),
                     jsonArray.getString(i+2),
                     jsonArray.getInt(i+3),
                     jsonArray.getString(i+4),
                     jsonArray.getString(i+5),
                     jsonArray.getString(i+6),
                     jsonArray.getInt(i+7),
                     jsonArray.getString(i+8));

                peliculasList.add(peliculas);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        listPeliculas= peliculasList;
        peliculasAdapter = new PeliculasAdapter(peliculasList, getActivity());
        rvPeliculas.setAdapter(peliculasAdapter);

    }

}
