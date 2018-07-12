package gbstracking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.gbstracking.R;

import gbstracking.Userlogin.loginmain;


/**
 * Created by HP on 21/12/2016.
 */
public class SpLash_Screen extends Activity {
    private FrameLayout imageLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        imageLogo = findViewById(R.id.logosplash);

        Thread timer=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intMainActivity=new Intent(SpLash_Screen.this,loginmain.class);
                    startActivity(intMainActivity);
                }
            }
        };
        timer.start();
    }






    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
