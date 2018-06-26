package gbstracking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gbstracking.R;

import gbstracking.Userlogin.UserloginMain;
import gbstracking.Userlogin.loginmain;


/**
 * Created by HP on 21/12/2016.
 */
public class SpLash_Screen extends Activity {
    private static int SPLASH_TIME_OUT = 2500;
    private ImageView imageLogo,imglogo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        imageLogo = findViewById(R.id.logosplash);
        imglogo2 = findViewById(R.id.logofriend);
        Animation animation = AnimationUtils.loadAnimation(this , R.anim.logo);
        imageLogo.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity


                Intent i = new Intent(SpLash_Screen.this, loginmain.class);
                startActivity(i);

                // close this activity
                finish();

            }

        }, SPLASH_TIME_OUT);

        Animation animatio = AnimationUtils.loadAnimation(this , R.anim.logo);
        imglogo2.startAnimation(animatio);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity


                Intent i = new Intent(SpLash_Screen.this, loginmain.class);
                startActivity(i);

                // close this activity
                finish();

            }

        }, SPLASH_TIME_OUT);
    }






    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
