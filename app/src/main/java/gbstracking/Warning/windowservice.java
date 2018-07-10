package gbstracking.Warning;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gbstracking.R;

import gbstracking.Nvigation;
import gbstracking.SendNotifications.MyNotification;
import gbstracking.Userlogin.loginmain;
import gbstracking.friends.TabsFriends;

import static gbstracking.Nvigation.ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE;

/**
 * Created by HP on 15/05/2018.
 */

public class windowservice extends Service {
    int mStartMode;
   LayoutInflater li;
   WindowManager wm;
   Button moredetail,cancel;
    View myview;
    String messg,streeet,date,day,title;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** Called when the service is being created. */
    @Override
    public void onCreate() {

        if (Context.NOTIFICATION_SERVICE!=null) {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(0);
        }
    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Context.NOTIFICATION_SERVICE!=null) {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(0);
        }
        if (intent != null && intent.getExtras() != null){
            title= intent.getStringExtra("title");
             messg= intent.getStringExtra("msg");
             streeet=intent.getStringExtra("address");
             date= intent.getStringExtra("time");
            day= intent.getStringExtra("day");

        }
        li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                windowmanger();

            } else{
                Intent i= new Intent(getApplicationContext(),loginmain.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        }else if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
             windowmanger();
        }

        return mStartMode;
    }
public void windowmanger(){
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                //WindowManager.LayoutParams.TYPE_INPUT_METHOD |
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER;
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/no.otf");

        myview = li.inflate(R.layout.my_alert_dialog, null);
        TextView texttitle = myview.findViewById(R.id.textTitle);
        TextView textmessage = myview.findViewById(R.id.Message);
        TextView textstreet = myview.findViewById(R.id.streeet);
        TextView textdate = myview.findViewById(R.id.tim);
        TextView textday = myview.findViewById(R.id.Da);
        texttitle.setTypeface(typeface);
        textmessage.setTypeface(typeface);
        textstreet.setTypeface(typeface);
        textday.setTypeface(typeface);
        textdate.setTypeface(typeface);
        textmessage.setText(messg);
        textstreet.setText(streeet);
        textdate.setText(date);
        textday.setText(day);
        texttitle.setText(title);
        myview.setOnTouchListener(new View.OnTouchListener() {
            double x, y, pressedX, pressedY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                WindowManager.LayoutParams update = params;
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = update.x;
                        y = update.y;

                        pressedX = motionEvent.getX();
                        pressedY = motionEvent.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        update.x = (int) (x + (motionEvent.getRawX() - pressedX));
                        update.y = (int) (y + (motionEvent.getRawY() - pressedY));
                        wm.updateViewLayout(myview, update);

                    default:
                        break;

                }

                return false;
            }
        });

        cancel = myview.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wm != null) {
                    wm.removeView(myview);
                    stopSelf();
                }

            }
        });
        moredetail = myview.findViewById(R.id.moredetails);
        moredetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wm != null) {
                    wm.removeView(myview);
                    stopSelf();
                    Intent i = new Intent(getApplicationContext(), loginmain.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);


                }
            }
        });
        wm.addView(myview, params);


    }




    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {

    }
}
