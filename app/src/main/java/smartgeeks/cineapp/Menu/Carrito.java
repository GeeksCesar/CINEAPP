package smartgeeks.cineapp.Menu;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import smartgeeks.cineapp.Adapter.PeliculasenCarrito;
import smartgeeks.cineapp.R;
import smartgeeks.cineapp.database.Pedido;


public class Carrito extends Fragment {

    View view;
    Snackbar snackbar;
    private static final String TAG  = "CarritoFragment";

    ImageView btnContinuarPedido, ivValidacion;

    TextView tvTotalPagar;
    private int totalPagar;

    Context context;
    List<Pedido> listcarrito = null;
    List<Pedido> listExterna;

    //Recyclerview
    RecyclerView rvPeliculasCarrito;
    GridLayoutManager layoutManager;
    PeliculasenCarrito peliculasenCarrito = new PeliculasenCarrito();



    public Carrito() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.menu_carrito, container, false);

        tvTotalPagar = (TextView) view.findViewById(R.id.tvTotalAPagar);
        btnContinuarPedido = (ImageView) view.findViewById(R.id.btnContinuarPedidoId);
        ivValidacion= (ImageView) view.findViewById(R.id.ivValidacion);

        //Recyclereview config
        rvPeliculasCarrito = (RecyclerView) view.findViewById(R.id.rvPeliculasCarrito);
        layoutManager = new GridLayoutManager(getActivity() , 1);
        rvPeliculasCarrito.setLayoutManager(layoutManager);

        context  = getActivity();


        //CLICK CONTINUAR PEDIDO
        btnContinuarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalPagar == 0){
                    snackbar = Snackbar.make(view, "Tu Pedido esta Vacio" , Snackbar.LENGTH_SHORT );
                    View viewsnck = snackbar.getView();

                    TextView tvsnack  = (TextView) viewsnck.findViewById(android.support.design.R.id.snackbar_text);

                    viewsnck.setBackgroundColor(Color.parseColor("#1565c0"));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                        tvsnack.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }else {
                        tvsnack.setGravity(Gravity.CENTER_HORIZONTAL);
                    }

                    snackbar.show();
                }else {
                    Fragment fragment = new EnvioPedido();
                    getFragmentManager().beginTransaction().replace(R.id.content, fragment, "DATOS").commit();
                }
            }
        });

        //CARRITO COMPRAR
        cargarCarritoCompras();
        initSwipe();
        emptyPedido();


        return view;
    }

    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT){
                    int idP = listExterna.get(position).getIdProducto();
                    Log.i("idP",""+idP);
                    peliculasenCarrito.removeItem(position);

                    List<Pedido> pedidos = Pedido.find(Pedido.class,"id_producto = ?",""+idP);
                    Pedido pedido1 = pedidos.get(0);

                    pedido1.delete();

                    ActualizarTotalaPagar();
                    emptyPedido();

                }else{

                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvPeliculasCarrito);
    }

    private void cargarCarritoCompras(){
        listcarrito = Pedido.listAll(Pedido.class);

        ActualizarTotalaPagar();
        listExterna = listcarrito;

        peliculasenCarrito = new PeliculasenCarrito(listcarrito,getActivity());
        rvPeliculasCarrito.setAdapter(peliculasenCarrito);

    }

    private void ActualizarTotalaPagar(){
        totalPagar = 0;

        tvTotalPagar.setText(""+totalPagar+"");

        for (int i=0; i<listcarrito.size(); i++ ){
            int precio = listcarrito.get(i).getPrecioProducto();
            int idPelicula = listcarrito.get(i).getIdProducto();


            totalPagar = totalPagar + precio;
            Log.i(TAG, "total a pagar: "+totalPagar);
            Log.i(TAG, "idPelicula"+idPelicula);

            tvTotalPagar.setText(""+totalPagar+"");
        }
    }

    private void emptyPedido(){
        long count  = Pedido.count(Pedido.class);

        if (count == 0){
            ivValidacion.setVisibility(View.VISIBLE);
        }else if (count > 0){
            ivValidacion.setVisibility(View.INVISIBLE);
        }
    }
}
