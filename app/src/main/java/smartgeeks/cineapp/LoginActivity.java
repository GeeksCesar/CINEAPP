package smartgeeks.cineapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;

import smartgeeks.cineapp.database.Mysql;
import smartgeeks.cineapp.validation.FormValidator;
import smartgeeks.cineapp.validation.MyTextWatcher;

public class LoginActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG= "LoginActivityTest";
    FloatingActionButton fabRegistrar ;
    Calendar calendar;
    DatePickerDialog pickerDialog;
    CoordinatorLayout view_login;
    Snackbar snackbar;

    //LLOGIN
    Button btnLogin;
    TextInputLayout txtInputUsuario, txtInputPassword;
    String QueryLogin,LoginUsuario, LoginPassword ;
    int session , prefSession;
    String jsonID, jsonNombre, jsonDocumento, jsonTelefono, jsonBirthDay, jsonDireccion, jsonUsuario, jsonPassword ;
    SharedPreferences preferences ;
    SharedPreferences.Editor edit ;

    //VOLLEY
    String URl ;
    JSONArray jsonArray;
    StringRequest stringRequest ;
    RequestQueue requestQueue ;
    String  IdPhone;

    //DIALOG
    Dialog dialogLogin;
    TextInputLayout inputDialog_nombre, inputDialog_Usuario, inputDialog_Documento,inputDialog_Direccion,inputDialog_Telefono,inputDialog_Password;
    TextInputEditText EdNameDialog, EdUsuarioDialog, EdDocumentoDialog,EdDireccionDialog,EdTelefonoDialog,EdPasswordDialog;
    TextView tvTitleRegistro,tvSalirDialog;
    Button btnFechaBithday,btnRegistrarme;
    String strNombre, strUsuario, strDocumento, strDireccion, strTelefono, strPassword , strBirthDay, fecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        CargarTokenFCM();
        //PREFERENCES
        preferences= getApplicationContext().getSharedPreferences(getString(R.string.usuPreferences), Context.MODE_PRIVATE);
        prefSession = preferences.getInt(getString(R.string.usuSession), 0);
        Log.d(TAG, "Session= "+prefSession);

        if (prefSession == 1){
            Log.d(TAG, "Session 1");
            screenMainActivity();
        }else {
            Log.d(TAG, "Session O");
        }

        fabRegistrar = (FloatingActionButton) findViewById(R.id.fabRegistrar);
        view_login = (CoordinatorLayout) findViewById(R.id.activity_login);

        //LOGIN
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtInputUsuario = (TextInputLayout) findViewById(R.id.textinput_user) ;
        txtInputPassword = (TextInputLayout) findViewById(R.id.textinput_password) ;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUsuario = txtInputUsuario.getEditText().getText().toString().trim();
                LoginPassword = txtInputPassword.getEditText().getText().toString().trim();

                Log.d(TAG, "LOG_USUARIO: "+LoginUsuario);
                Log.d(TAG, "LOG_PASS: "+LoginPassword);

                if (LoginUsuario.equals("") || LoginPassword.equals("")){
                    Snackbar.make(view_login, "Llenos los campos", Snackbar.LENGTH_SHORT).show();
                }else{
                    QueryLogin = Mysql.LOGIN + "usu=" +LoginUsuario + "&pass=" +LoginPassword ;

                    Login(QueryLogin);
                }
            }
        });

        //FLOATIN BUTTON
        fabRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogin = new Dialog(LoginActivity.this);

                dialogLogin.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogLogin.setContentView(R.layout.dialog_login);

                //view
                tvSalirDialog = (TextView) dialogLogin.findViewById(R.id.tvSalirDialog) ;
                btnFechaBithday = (Button) dialogLogin.findViewById(R.id.btnFechaBithday);
                btnRegistrarme =(Button) dialogLogin.findViewById(R.id.btnRegistro) ;

                //InputLayout
                inputDialog_nombre = (TextInputLayout) dialogLogin.findViewById(R.id.inputDialog_Nombre);
                inputDialog_Documento = (TextInputLayout) dialogLogin.findViewById(R.id.inputDialog_Documento);
                inputDialog_Direccion = (TextInputLayout) dialogLogin.findViewById(R.id.inputDialog_Direccion);
                inputDialog_Telefono = (TextInputLayout) dialogLogin.findViewById(R.id.inputDialog_Telefono);
                inputDialog_Usuario = (TextInputLayout) dialogLogin.findViewById(R.id.inputDialog_Usuario);
                inputDialog_Password = (TextInputLayout) dialogLogin.findViewById(R.id.inputDialog_Password);

                //Editext
                EdNameDialog = (TextInputEditText) dialogLogin.findViewById(R.id.EdNameDialog);
                EdDocumentoDialog = (TextInputEditText) dialogLogin.findViewById(R.id.EdDocumentoDialog);
                EdDireccionDialog = (TextInputEditText) dialogLogin.findViewById(R.id.EdDireccionDialog);
                EdTelefonoDialog = (TextInputEditText) dialogLogin.findViewById(R.id.EdTelefonoDialog);
                EdUsuarioDialog = (TextInputEditText) dialogLogin.findViewById(R.id.EdUsuarioDialog);
                EdPasswordDialog = (TextInputEditText) dialogLogin.findViewById(R.id.EdPasswordDialog);


                //VALIDACIONES EDITEXT
                EdNameDialog.addTextChangedListener(new MyTextWatcher(LoginActivity.this, EdNameDialog, inputDialog_nombre));
                EdDocumentoDialog.addTextChangedListener(new MyTextWatcher(LoginActivity.this, EdDocumentoDialog, inputDialog_Documento));
                EdDireccionDialog.addTextChangedListener(new MyTextWatcher(LoginActivity.this, EdDireccionDialog, inputDialog_Direccion));
                EdTelefonoDialog.addTextChangedListener(new MyTextWatcher(LoginActivity.this, EdTelefonoDialog, inputDialog_Telefono));
                EdUsuarioDialog.addTextChangedListener(new MyTextWatcher(LoginActivity.this, EdUsuarioDialog, inputDialog_Usuario));
                EdPasswordDialog.addTextChangedListener(new MyTextWatcher(LoginActivity.this, EdPasswordDialog, inputDialog_Password));

                tvSalirDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLogin.cancel();
                    }
                });

                btnFechaBithday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendar = Calendar.getInstance();

                        pickerDialog = DatePickerDialog.newInstance(
                                LoginActivity.this,
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));

                        pickerDialog.setThemeDark(true);
                        pickerDialog.vibrate(true);
                        pickerDialog.dismissOnPause(true);
                        pickerDialog.showYearPickerFirst(false);
                        pickerDialog.setAccentColor(Color.parseColor("#009688"));
                        pickerDialog.setTitle("Seleccione Fecha Cumpleaños");
                        pickerDialog.show(getFragmentManager(), "Datepickerdialog");
                    }
                });

                //ONCLICK BUTTON
                btnRegistrarme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        strDocumento = EdDocumentoDialog.getText().toString();
                        strPassword = EdPasswordDialog.getText().toString();
                        strNombre = EdNameDialog.getText().toString();
                        strDireccion = EdDireccionDialog.getText().toString();
                        strUsuario= EdUsuarioDialog.getText().toString();
                        strTelefono = EdTelefonoDialog.getText().toString();
                        strBirthDay = btnFechaBithday.getText().toString();

                        if(FormValidator.nameEmpty(strNombre) == false){
                            inputDialog_nombre.setError(getString(R.string.nameError));
                            EdNameDialog.requestFocus();
                        } else if (FormValidator.isDocumentoValid(strDocumento) == false){
                            inputDialog_Documento.setError(getString(R.string.docuError));
                            EdDocumentoDialog.requestFocus();
                        }else if (FormValidator.direccionEmpty(strDireccion) == false){
                            inputDialog_Direccion.setError(getString(R.string.direccionError));
                            EdDireccionDialog.requestFocus();
                        }else if (FormValidator.isValidPhone(strTelefono) == false){
                            inputDialog_Telefono.setError(getString(R.string.phoneError));
                            EdTelefonoDialog.requestFocus();
                        }else if (FormValidator.isValidUser(strUsuario) == false){
                            inputDialog_Usuario.setError(getString(R.string.userError));
                            EdUsuarioDialog.requestFocus();
                        }else if (FormValidator.isValidPassword(strPassword) == false){
                            inputDialog_Password.setError(getString(R.string.passwordError));
                            EdPasswordDialog.requestFocus();
                        }else if (strBirthDay.equals("FECHA CUMPLEAÑOS")){
                            Snackbar.make(view, "Seleccione Fecha Cumpleaños", Snackbar.LENGTH_SHORT).show();
                        }
                        else {
                           RegistrarUsuario(strNombre, strDocumento, strDireccion, strTelefono, fecha, strUsuario, strPassword);
                        }
                    }
                });


                dialogLogin.show();


            }
        });

    }

    private void Login(String URL) {
        Log.d(TAG, "URL: "+URL);

        requestQueue = Volley.newRequestQueue(LoginActivity.this);
        stringRequest  = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonArray = new JSONArray(response);

                    jsonID = jsonArray.getString(0);
                    jsonNombre = jsonArray.getString(1);
                    jsonDocumento = jsonArray.getString(2);
                    jsonTelefono = jsonArray.getString(3);
                    jsonDireccion = jsonArray.getString(4);
                    jsonBirthDay = jsonArray.getString(5);
                    jsonUsuario = jsonArray.getString(6);
                    jsonPassword = jsonArray.getString(7);
                    session = 1;

                    if (jsonUsuario.equals(LoginUsuario) && jsonPassword.equals(LoginPassword)){

                        preferences = getApplicationContext().getSharedPreferences(getString(R.string.usuPreferences), Context.MODE_PRIVATE);

                        edit =  preferences.edit();

                        edit.putString(getString(R.string.usuId), jsonID);
                        edit.putString(getString(R.string.usuNombre), jsonNombre);
                        edit.putString(getString(R.string.usuDocumento), jsonDocumento);
                        edit.putString(getString(R.string.usuTelefono), jsonTelefono);
                        edit.putString(getString(R.string.usuDireccion), jsonDireccion);
                        edit.putString(getString(R.string.usuBirthDay), jsonBirthDay);
                        edit.putString(getString(R.string.usuUsuario), jsonUsuario);
                        edit.putString(getString(R.string.usuPassword), jsonPassword);
                        edit.putInt(getString(R.string.usuSession), session);

                        edit.commit();

                        screenMainActivity();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    snackbar = Snackbar.make(view_login, "Verifique los Datos", Snackbar.LENGTH_SHORT)
                    .setAction("Error Datos", null);

                    snackbar.show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG , "Error: " + error.getMessage());
            }
        });

        requestQueue.add(stringRequest);
    }


    public void screenMainActivity(){
        Intent intMainActivity = new Intent(LoginActivity.this, MenuPrincipal.class);
        startActivity(intMainActivity);
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        finish();
    }


    private void RegistrarUsuario(String Nombre, String Documento, String Direccion, String Telefono, String Birthday, String Usuario, String Password){

        CargarTokenFCM();

        URl = Mysql.REGISTRAR_USUARIO + "nombre="+Nombre + "&docu=" +Documento+ "&direccion=" +Direccion+ "&telefono="+Telefono+
                "&fecha=" +Birthday+ "&user="+Usuario+ "&pass="+Password+ "&token="+IdPhone;

        URl = URl.replace(" ", "%20");
        Log.d(TAG, "" + URl);

        requestQueue =  Volley.newRequestQueue(LoginActivity.this);

        stringRequest = new StringRequest(Request.Method.GET, URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG , ""+response);
                Toast.makeText(LoginActivity.this, "Guardo Usuario", Toast.LENGTH_SHORT).show();
                dialogLogin.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG , "Error: " + error.getMessage());
            }
        });

        requestQueue.add(stringRequest);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String dia = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        String month = "";

        int mes = (monthOfYear + 1);
        if (mes <= 9) {
            month = "0" + mes;
        } else {
            month = "" + mes;
        }
        String an = "" + year;

        btnFechaBithday.setText(dia +"-"+month+"-"+year);
        fecha = "" + an + "-" + month + "-" + dia;
    }

    public void CargarTokenFCM() {
        IdPhone = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "token: "+IdPhone);
    }
}
