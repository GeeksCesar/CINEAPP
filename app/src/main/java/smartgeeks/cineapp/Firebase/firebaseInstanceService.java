package smartgeeks.cineapp.Firebase;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import smartgeeks.cineapp.R;

/**
 * Created by cesarlizcano on 06/03/17.
 */

public class firebaseInstanceService extends FirebaseInstanceIdService{

    private static final String TAG = "MyFirebaseIIDService";
    String recent_token;

    @Override
    public void onTokenRefresh() {

        recent_token = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREFERENCE), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.FCM_TOKEN), recent_token);

        Log.d(TAG, "token: " + recent_token);

        editor.commit();
    }

}
