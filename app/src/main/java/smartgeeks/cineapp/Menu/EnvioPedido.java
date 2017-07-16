package smartgeeks.cineapp.Menu;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import smartgeeks.cineapp.R;
import smartgeeks.cineapp.database.Mysql;
import smartgeeks.cineapp.database.Pedido;

import static smartgeeks.cineapp.R.id.btnEnviarPedidoId;


public class EnvioPedido extends Fragment {

    View view;
    Snackbar snackbar;
    private static final String TAG = "EnvioPedidoTest";
    EditText etTelefonoCliente, etDireccionCliente, etObservaciones, etNombreCliente;
    ImageView btnEnviarPedido;
    CheckBox cbEfectivo, cbTarjeta;

    String MediodePago="";
    String producto;
    String listaPedidoFinal="";

    int precioTotalCompra=0;
    int preciofinal;

    //Dialog ALert
    ProgressDialog progressDialog;
    Dialog dialogSuccess, dialogFailed;
    Context context;
    ImageView ivALertaDialog;
    Button btnOk;

    //Preferences
    SharedPreferences preferences;
    String prefNombreUsuario,prefPhoneUsuario,prefDireccionUsuario, prefIdUsuario;

    //VOLLEY
    RequestQueue requestQueue;
    StringRequest stringRequest;
    String URl;
    String nombreUser, phoneUser, direccionUser , observacionUser;

    public EnvioPedido() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.menu_envio_pedido, container, false);

        etNombreCliente = (EditText) view.findViewById(R.id.etNombreLCiente);
        etDireccionCliente = (EditText)view.findViewById(R.id.etDireccionC);
        etTelefonoCliente = (EditText)view.findViewById(R.id.etTelefonoCliente);
        etObservaciones = (EditText)view.findViewById(R.id.etObservaciones);
        cbEfectivo = (CheckBox)view.findViewById(R.id.cbefectivo);
        cbTarjeta = (CheckBox)view.findViewById(R.id.cbtarjeta);
        btnEnviarPedido = (ImageView) view.findViewById(btnEnviarPedidoId);

        cargarDatosUsuario();
        etNombreCliente.setText(prefNombreUsuario);
        etTelefonoCliente.setText(prefPhoneUsuario);
        etDireccionCliente.setText(prefDireccionUsuario);

        cbEfectivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cbEfectivo.isChecked()){
                    cbTarjeta.setChecked(false);
                    MediodePago = "Pago en Efectivo";
                }else {
                    MediodePago = "";
                }
            }
        });

        cbTarjeta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (cbTarjeta.isChecked()){
                   cbEfectivo.setChecked(false);
                   MediodePago= "Pago con Tarjeta";
               }else {
                   MediodePago = "";
               }
            }
        });


        List<Pedido> pedidoFinal = Pedido.listAll(Pedido.class);
        for (int i= 0; i<pedidoFinal.size(); i++){
            producto = pedidoFinal.get(i).getCantidadProducto() + "-" + pedidoFinal.get(i).getDescripcion();
            int idPelicula = pedidoFinal.get(i).getIdProducto();
            listaPedidoFinal = listaPedidoFinal + "" + producto + ",";
            preciofinal = pedidoFinal.get(i).getPrecioProducto();
            precioTotalCompra = precioTotalCompra + preciofinal;

            Log.d(TAG, "idPelicula: "+idPelicula);
        }

        //CLICK BUTTON
        btnEnviarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreUser = etNombreCliente.getText().toString();
                phoneUser= etTelefonoCliente.getText().toString();
                direccionUser= etDireccionCliente.getText().toString();
                observacionUser = etObservaciones.getText().toString();

                if (!cbEfectivo.isChecked() && !cbTarjeta.isChecked()){
                    snackbar = Snackbar.make(view, getResources().getString(R.string.alertInformacion), Snackbar.LENGTH_SHORT);
                    View viewSnackar = snackbar.getView();

                    TextView mTextView = (TextView) viewSnackar.findViewById(android.support.design.R.id.snackbar_text);
                    viewSnackar.setBackgroundColor(Color.parseColor("#3565c0"));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                        mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    else
                        mTextView.setGravity(Gravity.CENTER_HORIZONTAL);

                    snackbar.show();
                }else{
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage(getResources().getString(R.string.progressMessage));
                    progressDialog.show();

                    URl = Mysql.PEDIDO + "iduser=" + prefIdUsuario + "&pedido=" + listaPedidoFinal + "&valor=" + precioTotalCompra +
                            "&observ=" + observacionUser + "&pago=" + MediodePago;

                    URl = URl.replace("#", "No");
                    URl= URl.replace(" ", "%20");

                    //method
                    EnviarDatosPedido(URl);
                }

            }
        });

        return view;
    }


    private void cargarDatosUsuario(){
        context = getActivity();
        preferences = context.getSharedPreferences(getString(R.string.usuPreferences), Context.MODE_PRIVATE);

        prefIdUsuario = preferences.getString(getString(R.string.usuId), "Empty");
        prefNombreUsuario = preferences.getString(getString(R.string.usuNombre), "Empty");
        prefPhoneUsuario = preferences.getString(getString(R.string.usuTelefono), "Empty");
        prefDireccionUsuario = preferences.getString(getString(R.string.usuDireccion), "Empty");

        Log.d(TAG, "idUsuario: " +prefIdUsuario);

    }

    private void EnviarDatosPedido(String URl){
        Log.d(TAG, "URl: "+URl);

        requestQueue = Volley.newRequestQueue(getActivity());

        stringRequest = new StringRequest(Request.Method.GET, URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showDialogSuccess();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "erro: "+error.getMessage().toString());
                showDialogError();
            }
        });

        requestQueue.add(stringRequest);
    }


    private void showDialogError(){
        progressDialog.dismiss();

        dialogFailed = new Dialog(getActivity());
        dialogFailed.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFailed.setContentView(R.layout.dialog_alerta);

        ivALertaDialog = (ImageView) dialogFailed.findViewById(R.id.imgAlerta);
        btnOk= (Button) dialogFailed.findViewById(R.id.btnOk);

        ivALertaDialog.setImageResource(R.mipmap.txt_no_hay_conexion);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFailed.cancel();
            }
        });

        dialogFailed.show();
    }

    private void showDialogSuccess(){
        progressDialog.dismiss();

        dialogSuccess = new Dialog(getActivity());
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.dialog_alerta);

        ivALertaDialog = (ImageView) dialogSuccess.findViewById(R.id.imgAlerta);
        btnOk= (Button) dialogSuccess.findViewById(R.id.btnOk);

        ivALertaDialog.setImageResource(R.mipmap.txt_envio_exitoso);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuccess.cancel();
                Fragment fragmentInicio = new CategoriasPeliculas();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, fragmentInicio , "INICIO").commit();
            }
        });

        dialogSuccess.show();
        Pedido.deleteAll(Pedido.class);
    }

}
