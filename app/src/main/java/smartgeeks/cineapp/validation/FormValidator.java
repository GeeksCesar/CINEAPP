package smartgeeks.cineapp.validation;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by cesarlizcano on 14/04/17.
 */

public class FormValidator {

    public static boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password){
        return !TextUtils.isEmpty(password) && password.trim().length() >=6 ;
    }

    public static boolean nameEmpty (String name){
        return !TextUtils.isEmpty(name);
    }

    public static boolean direccionEmpty(String direccion){
        return !TextUtils.isEmpty(direccion);
    }

    public static boolean isValidPhone(String phone){
        return !TextUtils.isEmpty(phone);
    }

    public static boolean isValidUser(String usuario){
        return !TextUtils.isEmpty(usuario) && usuario.trim().length() >=6 ;
    }

    public static boolean isDocumentoValid(String documento){
        return  !TextUtils.isEmpty(documento) && (documento.trim().length() >=7 || documento.trim().length() > 11);
    }
}
