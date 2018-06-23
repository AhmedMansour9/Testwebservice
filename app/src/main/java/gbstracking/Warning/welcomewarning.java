package gbstracking.Warning;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gbstracking.R;

import gbstracking.Nvigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class welcomewarning extends Fragment {

    View v;
    Button skip;
    public welcomewarning() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      v=inflater.inflate(R.layout.welcomewarning, container, false);
        Toolbar tool=v.findViewById(R.id.too);
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


        skip=v.findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.welcome,new warninglayoutone()).addToBackStack(null).commit();

            }
        });


          return v;
    }

}
