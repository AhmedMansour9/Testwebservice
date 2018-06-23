package gbstracking.mainactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gbstracking.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by HP on 28/04/2018.
 */

public class CustomWinfoView implements GoogleMap.InfoWindowAdapter {
    private  View viewe;
    private Context comtec;
    public CustomWinfoView(Context context){
        this.comtec=context;
         viewe= LayoutInflater.from(context).inflate(R.layout.windowsinfogooglemap,null);
    }
    private void rendowWindowText(Marker marker, View view){

        String title = marker.getTitle();
        TextView tvTitle =view.findViewById(R.id.title);

        if(!title.equals("")){
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet =view.findViewById(R.id.snippet);

        if(!snippet.equals("")){
            tvSnippet.setText(snippet);
        }
    }
    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, viewe);

        return viewe;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, viewe);

        return viewe;
    }
}
