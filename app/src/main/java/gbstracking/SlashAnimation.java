package gbstracking;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import com.gbstracking.R;

import gbstracking.Userlogin.loginmain;


/**
 * Created by HP on 18/03/2018.
 */

public class SlashAnimation extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_slide1);
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/no.otf");
//       TextView location=findViewById(R.id.textloc);
//        location.setTypeface(typeface);
//        TextView emergency=findViewById(R.id.textemer);
//        emergency.setTypeface(typeface);
        new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                        Intent intent=new Intent(SlashAnimation.this,loginmain.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                },2000);


//        ImageView image=findViewById(R.id.idsplash);
//        TextView text1=findViewById(R.id.textone);
//        TextView text2=findViewById(R.id.texttwo);
//        Animation an= AnimationUtils.loadAnimation(this,R.anim.frombottom);
//        final Animation AN= AnimationUtils.loadAnimation(this,R.anim.tofromtop);
//        image.setAnimation(AN);
//        text1.setAnimation(an);
//        text2.setAnimation(an);
//
//
//
//        AN.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });


    }

}
