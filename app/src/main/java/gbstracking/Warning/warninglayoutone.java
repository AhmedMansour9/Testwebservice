package gbstracking.Warning;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gbstracking.R;

import gbstracking.Nvigation;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class warninglayoutone extends Fragment implements View.OnClickListener {

    TextView warn1,warn2,warn4,warn5,warn6,warn8,warn9,warn10;
     View v;
     Button agree;
    public warninglayoutone() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.warninglayoutone, container, false);
        agree=v.findViewById(R.id.skip);
        Toolbar tool=v.findViewById(R.id.tool);
        Nvigation.toggle = new ActionBarDrawerToggle(
                getActivity(), Nvigation.drawer, tool,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        Nvigation.drawer.addDrawerListener(Nvigation.toggle);
        Nvigation.toggle.syncState();
        Nvigation.toggle.setDrawerIndicatorEnabled(false);
        tool.setNavigationIcon(R.drawable. navigatwarn);
        tool.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Nvigation.drawer.isDrawerOpen(GravityCompat.START)) {
                    Nvigation.drawer.closeDrawer(GravityCompat.START);
                } else {
                    Nvigation.drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        textfont();
        agree.setOnClickListener(warninglayoutone.this);
        return v;
    }
    public void textfont(){
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/no.otf");
        warn1=v.findViewById(R.id.warn1);
        warn1.setTypeface(typeface);
        warn2=v.findViewById(R.id.warn2);
        warn2.setTypeface(typeface);
        warn4=v.findViewById(R.id.warn4);
        warn4.setTypeface(typeface);
        warn5=v.findViewById(R.id.warn5);
        warn5.setTypeface(typeface);
        warn6=v.findViewById(R.id.warn6);
        warn6.setTypeface(typeface);
        warn8=v.findViewById(R.id.warn8);
        warn8.setTypeface(typeface);
        warn9=v.findViewById(R.id.warn9);
        warn9.setTypeface(typeface);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.skip:
                getFragmentManager().beginTransaction().replace(R.id.main,new warrningmessage()).addToBackStack(null).commit();
                break;

        }
    }
}
