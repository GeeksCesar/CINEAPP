package smartgeeks.cineapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer ;
    NavigationView navigationView;
    Toolbar toolbar ;
    private static final String TAG = "MainActivityTest" ;
    //DIALOG
    Dialog dialogVaciarPedido;
    ImageView ivAlerta;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Fragment fragment = new CategoriasPeliculas();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_principal, fragment,"PELICULAS").commit();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            Fragment fragment = new CategoriasPeliculas();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_principal, fragment).commit();
        } else {
          //  super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean fragmentSeleccionado = false;
        Fragment fragment = null;

        if (id == R.id.nav_peliculas) {
            fragment = new CategoriasPeliculas();
            fragmentSeleccionado = true;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_principal, fragment,"PELICULAS").commit();

        } else if (id == R.id.nav_carrito) {
            fragment = new Carrito();
            fragmentSeleccionado = true;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_principal, fragment,"CARRITO").commit();

        } else if (id == R.id.nav_perfil) {
            fragment = new Perfil();
            fragmentSeleccionado = true;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_principal, fragment,"PERFIL").commit();
        } else if (id == R.id.nav_vaciarcarrito) {
            List<Pedido> PedidoBorrar = Pedido.listAll(Pedido.class);

            fragment = new Carrito();
            fragmentSeleccionado = true;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_principal,fragment, "CARRITO").commit();
            //DIALOG
            dialogVaciarPedido = new Dialog(this);
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

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_principal, fragmentPeliculas, "CATEGORIA").commit();
                    Log.i(TAG,"PELICULAS");
                }
            }else if (fragmentCarrito1 != null){
                if (fragmentCarrito1.isVisible()){

                    Fragment fragmentinicio = new CategoriasPeliculas();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_principal, fragmentinicio, "INICIO").commit();
                    Log.i("entro","sinreg");
                }
            }else if (fragmentCarrito2 != null){
                if (fragmentCarrito2.isVisible()){
                    fragmentCarrito2 = new MisPeliculas();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_principal, fragmentCarrito2, "PELICULAS").commit();
                }
            }else if (fragmentDatos != null){
                if (fragmentDatos.isVisible()){
                    fragmentDatos = new Carrito();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_principal, fragmentDatos, "DATOS").commit();
                    Log.i(TAG,"frag datos");
                }
            }


        }

        return super.onKeyDown(keyCode, event);
    }
}
