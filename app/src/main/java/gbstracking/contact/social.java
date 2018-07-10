package gbstracking.contact;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gbstracking.BuildConfig;
import com.gbstracking.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class social extends Fragment {

  Button share;
    public social() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.sociaal, container, false);
        share=v.findViewById(R.id.invite);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=zamaleekonlinee.zamalekonline");
//                if (null != intent.resolveActivity(getActivity().getPackageManager())) {
//                    startActivity(Intent.createChooser(intent, "Share"));
//                }

                Intent sendIntent = new Intent();

                sendIntent.setAction(Intent.ACTION_SEND);

                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=zamaleekonlinee.zamalekonline");

                sendIntent.setType("text/plain");

                startActivity(sendIntent);



            }
        });


        // Inflate the layout for this fragment
        return v;
    }

}
