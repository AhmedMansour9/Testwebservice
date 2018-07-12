package gbstracking.searchplaces;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gbstracking.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import gbstracking.searchplaces.InfoWindowData;

/**
 * Created by HP on 29/04/2018.
 */

public class windowinfofriend implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public windowinfofriend(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        View viewe= LayoutInflater.from(context).inflate(R.layout.custoumwindowfriends,null);


        TextView name_friend = viewe.findViewById(R.id.namefriend);


        name_friend.setText(marker.getTitle());

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

        CircleImageView iv =viewe.findViewById(R.id.imgfriendss);
        Uri u= Uri.parse(infoWindowData.getImage());
        Picasso.with(context).load(u).placeholder(R.drawable.emptyprofile).resize(250,250).into(iv);

        return viewe;
    }

    @Override
    public View getInfoContents(Marker marker) {


        return null;
    }





}
