package smartgeeks.cineapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import smartgeeks.cineapp.Menu.Carrito;
import smartgeeks.cineapp.Menu.CategoriasPeliculas;
import smartgeeks.cineapp.Menu.MisPeliculas;
import smartgeeks.cineapp.Menu.Perfil;
import smartgeeks.cineapp.database.Pedido;
import smartgeeks.cineapp.validation.checkConnection;

public class MenuPrincipal extends AppCompatActivity {

    private static final String TAG = "MainActivityTest" ;
    Dialog dialogVaciarPedido;
    ImageView ivAlerta;
    BottomNavigationView navigation;
    Button btnOk;
    checkConnection connection = new checkConnection();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nav_botton_movie:
                   setFragment(0);
                    return true;
                case R.id.nav_botton_carrito:
                   setFragment(1);
                    return true;

                case R.id.nav_botton_perfil:
                   setFragment(2);
                    return true;

                case R.id.nav_botton_delete:
                    List<Pedido> PedidoBorrar = Pedido.listAll(Pedido.class);

                   setFragment(1);

                    //DIALOG
                    dialogVaciarPedido = new Dialog(MenuPrincipal.this);
                    dialogVaciarPedido.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogVaciarPedido.setContentView(R.layout.dialog_alerta);

                    ivAlerta = (ImageView) dialogVaciarPedido.findViewById(R.id.imgAlerta);
                    btnOk= (Button) dialogVaciarPedido.findViewById(R.id.btnOk);

                    long count = Pedido.count(Pedido.class);

                    if (count > 0){
                        Log.e(TAG, "hay datos");
                        Pedido.deleteAll(Pedido.class);
                        ivAlerta.setImageResource(R.mipmap.txt_vaciar_pedido);
                    }else if (count == 0){
                        Log.e(TAG, "No hay Datos");
                        ivAlerta.setImageResource(R.mipmap.txt_no_hay_pedido);
                    }

                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogVaciarPedido.cancel();
                        }
                    });

                    dialogVaciarPedido.show();

                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        if (!connection.verificaConexion(this)){
            connection.showAlertDialog(this, getResources().getString(R.string.cone_error), getResources().getString(R.string.cone_tryAgain), false);
            Log.d(TAG, "no hay conexion");
        }else {
            Log.d(TAG, "hay conexion");
        }

        setFragment(0);
    }

    public void setFragment(int pos){
        Fragment fragment = null;
        boolean fragmentSeleccionado = false;

        switch (pos){
            case 0:
                fragment = new CategoriasPeliculas();
                fragmentSeleccionado = true;
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment,"PELICULAS").commit();

                break;

            case 1:
                fragment = new Carrito();
                fragmentSeleccionado = true;
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment,"CARRITO").commit();
                break;


            case 2:
                fragment = new Perfil();
                fragmentSeleccionado = true;
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment,"PERFIL").commit();

                break;


        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){

            FragmentManager fragmentManager = getSupportFragmentManager();

            Fragment fragmentPeliculas = fragmentManager.findFragmentByTag("PELICULAS");
            Fragment fragmentCategoria = fragmentManager.findFragmentByTag("CATEGORIA");
            Fragment fragmentCarrito1 = fragmentManager.findFragmentByTag("CARRITO");
            Fragment fragmentCarrito2 = fragmentManager.findFragmentByTag("CARRITO2");
            Fragment fragmentPerfil = fragmentManager.findFragmentByTag("PERFIL");
            Fragment fragmentDatos = fragmentManager.findFragmentByTag("DATOS");
            Fragment fragmentInicio = fragmentManager.findFragmentByTag("INICIO");


            if (fragmentPeliculas != null) {
                if (fragmentPeliculas.isVisible()) {
                    fragmentPeliculas = new CategoriasPeliculas();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, fragmentPeliculas, "CATEGORIA").commit();
                    Log.i(TAG,"PELICULAS");
                }
            }else if (fragmentCarrito1 != null){
                if (fragmentCarrito1.isVisible()){

                    Fragment fragmentinicio = new CategoriasPeliculas();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, fragmentinicio, "INICIO").commit();
                    Log.i("entro","sinreg");
                }
            }else if (fragmentCarrito2 != null){
                if (fragmentCarrito2.isVisible()){
                    fragmentCarrito2 = new MisPeliculas();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, fragmentCarrito2, "PELICULAS").commit();
                }
            }else if (fragmentDatos != null){
                if (fragmentDatos.isVisible()){
                    fragmentDatos = new Carrito();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, fragmentDatos, "DATOS").commit();
                    Log.i(TAG,"frag datos");
                }
            }


        }

        return super.onKeyDown(keyCode, event);
    }



}
