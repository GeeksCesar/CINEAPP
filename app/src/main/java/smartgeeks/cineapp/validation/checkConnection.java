package smartgeeks.cineapp.validation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.ContextThemeWrapper;

import smartgeeks.cineapp.R;

/**
 * Created by cesarlizcano on 06/03/17.
 */

public class checkConnection extends Activity {

    private AlertDialog alertConecccion;

    //Alert Diaolog para verificar conexion a internet
    public void showAlertDialog(Context context, String title, String message, Boolean status) {

        alertConecccion = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogTheme))
                .setTitle(title)
                .setMessage(message)
                .setIcon((status) ? R.mipmap.check_success : R.mipmap.check_error)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertConecccion.dismiss();
                    }
                }).show();
    }

    public boolean verificaConexion(Context ctx) {
        boolean connected = false;

        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan ñapa
        for (int i = 0; i < redes.length; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }
        return connected;
    }

}
