package smartgeeks.cineapp.validation;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

import smartgeeks.cineapp.R;

/**
 * Created by cesarlizcano on 14/04/17.
 */

public class MyTextWatcher implements TextWatcher {

    Context context;
    TextInputEditText view;
    TextInputLayout Layout;

    public MyTextWatcher(Context context, TextInputEditText view, TextInputLayout layout) {
        this.context = context;
        this.view = view;
        Layout = layout;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        switch (this.view.getId()){

            case R.id.EdNameDialog:
                if (FormValidator.nameEmpty(view.getText().toString()) == false){
                    Layout.setError(context.getString(R.string.nameError));
                }else {
                    Layout.setErrorEnabled(false);
                }

                break;

            case R.id.EdDocumentoDialog:
                if (FormValidator.isDocumentoValid(view.getText().toString()) == false) {
                    Layout.setError(context.getString(R.string.docuError));
                } else {
                    Layout.setErrorEnabled(false);
                }
                break;

            case R.id.EdDireccionDialog:
                if (FormValidator.direccionEmpty(view.getText().toString()) == false){
                    Layout.setError(context.getString(R.string.direccionError));
                }else {
                    Layout.setErrorEnabled(false);
                }

                break;

            case R.id.EdTelefonoDialog:
                if (FormValidator.isValidPhone(view.getText().toString()) == false){
                    Layout.setError(context.getString(R.string.phoneError));
                }else {
                    Layout.setErrorEnabled(false);
                }
                break;

            case R.id.EdUsuarioDialog:
                if (FormValidator.isValidUser(view.getText().toString()) == false){
                    Layout.setError(context.getString(R.string.userError));
                }else {
                    Layout.setErrorEnabled(false);
                }

                break;

            case R.id.EdPasswordDialog:
                if (FormValidator.isValidPassword(view.getText().toString()) == false){
                    Layout.setError(context.getString(R.string.passwordError));
                }else {
                    Layout.setErrorEnabled(false);
                }
                break;

        }
    }
}
