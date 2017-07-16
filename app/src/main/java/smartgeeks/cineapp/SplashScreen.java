package smartgeeks.cineapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progresBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        progresBar = (ProgressBar) findViewById(R.id.progresBar);
        progresBar.getIndeterminateDrawable().setColorFilter(Color.rgb(18,174,128), PorterDuff.Mode.SRC_IN);


        Thread splash = new Thread(){
            @Override
            public void run() {
                try {
                    //Duracion
                    sleep(3*1000);
                    //intent al MainActivity
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                    //removiendo activity
                    finish();

                }catch (Exception e){

                }
            }
        };

        splash.start();

    }
}
