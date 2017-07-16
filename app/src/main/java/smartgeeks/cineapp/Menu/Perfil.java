package smartgeeks.cineapp.Menu;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import smartgeeks.cineapp.LoginActivity;
import smartgeeks.cineapp.MenuPrincipal;
import smartgeeks.cineapp.R;
import smartgeeks.cineapp.database.Mysql;


public class Perfil extends Fragment {

    private static final String TAG = "Perfil" ;
    View view;
    Context context;
    //PREFERENCES
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String prefId, prefNombre, prefDocumento, prefTelefono, prefDireccion,prefBirhtDay, prefUsuario, prefPassword;
    int sessionUser , prefSession;

    //SET
    TextView tvDocumento, tvNombre, tvDireccion, tvTelefono, tvBirthday;
    Button btnCerrarSession;
    FloatingActionButton fabEditarInfo ;

    //DIALOG
    Dialog dialogEditarDatos;
    EditText EdDocumentoEdit, EdNombreEdit,EdTelefonoEdit,EdDireccionEdit;
    Button btnEditarDatos ;
    String strDocumento , strNombre , strDireccion, strTelefono ;

    //VOLLEY
    String URl ;
    JSONArray jsonArray;
    StringRequest stringRequest ;
    RequestQueue requestQueue ;

    public Perfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.menu_perfil, container, false);

        context = getActivity();

        preferences = context.getSharedPreferences(getString(R.string.usuPreferences), Context.MODE_PRIVATE);
        fabEditarInfo = (FloatingActionButton) view.findViewById(R.id.fabRegistrar);

        prefId = preferences.getString(getString(R.string.usuId), "empty");
        prefNombre = preferences.getString(getString(R.string.usuNombre), "empty");
        prefDocumento = preferences.getString(getString(R.string.usuDocumento), "empty");
        prefTelefono = preferences.getString(getString(R.string.usuTelefono), "empty");
        prefDireccion = preferences.getString(getString(R.string.usuDireccion), "empty");
        prefBirhtDay = preferences.getString(getString(R.string.usuBirthDay), "empty");
        prefUsuario = preferences.getString(getString(R.string.usuUsuario), "empty");
        prefPassword = preferences.getString(getString(R.string.usuPassword), "empty");
        sessionUser = preferences.getInt(getString(R.string.usuSession), 0);


        Log.d(TAG, "idUsuario: "+prefId);

        //intance
        btnCerrarSession = (Button) view.findViewById(R.id.btnCerrarSesion);

        tvDocumento = (TextView) view.findViewById(R.id.tvDocumentoUsuario);
        tvNombre = (TextView) view.findViewById(R.id.tvNombreUsuario);
        tvDireccion= (TextView) view.findViewById(R.id.tvDireccionUsuario);
        tvTelefono= (TextView) view.findViewById(R.id.tvTelefonoUsuario);
        tvBirthday= (TextView) view.findViewById(R.id.tvBirthDayUsuario);

        //setData
        tvDocumento.setText(prefDocumento);
        tvNombre.setText(prefNombre);
        tvDireccion.setText(prefDireccion);
        tvTelefono.setText(prefTelefono);
        tvBirthday.setText(prefBirhtDay);

        btnCerrarSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePreferences();
                screenLogin();
            }
        });

        fabEditarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditarDatos = new Dialog(context);

                dialogEditarDatos.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogEditarDatos.setContentView(R.layout.dialog_editar);

                //instance
                EdDocumentoEdit = (EditText) dialogEditarDatos.findViewById(R.id.EdDocumentoEdit);
                EdNombreEdit = (EditText) dialogEditarDatos.findViewById(R.id.EdNombreEdit);
                EdTelefonoEdit = (EditText) dialogEditarDatos.findViewById(R.id.EdTelefonoEdit);
                EdDireccionEdit = (EditText) dialogEditarDatos.findViewById(R.id.EdDireccionEdit);

                btnEditarDatos = (Button) dialogEditarDatos.findViewById(R.id.btnEditarDatos) ;

                //SET DATA
                EdDocumentoEdit.setText(prefDocumento);
                EdNombreEdit.setText(prefNombre);
                EdTelefonoEdit.setText(prefTelefono);
                EdDireccionEdit.setText(prefDireccion);

                btnEditarDatos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        strNombre = EdNombreEdit.getText().toString().trim();
                        strDocumento = EdDocumentoEdit.getText().toString().trim();
                        strTelefono= EdTelefonoEdit.getText().toString().trim();
                        strDireccion = EdDireccionEdit.getText().toString().trim();

                        editDatos(strNombre, strDocumento, strTelefono, strDireccion);
                        editPreferences(strNombre, strDocumento, strTelefono, strDireccion);
                    }
                });

                dialogEditarDatos.show();

            }
        });

        return view;

    }

    private void editDatos(String strNombre, String strDocumento, String strTelefono, String strDireccion) {

        URl = Mysql.EDITAR_USUARIO +prefId + "&nombre=" + strNombre + "&docu=" + strDocumento + "&telefono=" + strTelefono +
                        "&direccion=" + strDireccion;

        URl = URl.replace(" ", "%20");
        URl = URl.replace("#","No");
        Log.d(TAG, "" + URl);

        requestQueue =  Volley.newRequestQueue(getActivity());

        stringRequest = new StringRequest(Request.Method.GET, URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG , ""+response);
                Toast.makeText(getActivity(), "Se Edito Usuario", Toast.LENGTH_SHORT).show();
                dialogEditarDatos.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG , "Error: " + error.getMessage());
            }
        });

        requestQueue.add(stringRequest);

    }

    private void editPreferences(String strNombre, String strDocumento, String strTelefono, String strDireccion){
        preferences = getActivity().getSharedPreferences(getString(R.string.usuPreferences), Context.MODE_PRIVATE);
        editor =  preferences.edit();

        editor.putString(getString(R.string.usuNombre),strNombre);
        editor.putString(getString(R.string.usuDocumento),strDocumento);
        editor.putString(getString(R.string.usuTelefono),strTelefono);
        editor.putString(getString(R.string.usuDireccion),strDireccion);

        editor.commit();

        startActivity(new Intent(getActivity(), MenuPrincipal.class));
        getActivity().overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
    }

    public void screenLogin(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
        getActivity().finish();
    }

    private void deletePreferences(){
        context = getActivity();
        preferences = context.getSharedPreferences(getString(R.string.usuPreferences) , Context.MODE_PRIVATE);
        editor = preferences.edit();

        prefSession = 0;
        editor.putInt(getString(R.string.usuSession), prefSession);


        editor.commit();
    }

}
