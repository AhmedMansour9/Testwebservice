package gbstracking.friends;

import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.gbstracking.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import gbstracking.recycleviewfriends.Friendsetandget;
import gbstracking.recycleviewfriends.ItemClickListener;
import gbstracking.recycleviewfriends.btnclickinterface;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by HP on 20/04/2018.
 */

public class RecycleViewRequests extends RecyclerView.Adapter<RecycleViewRequests.MyViewHolder> {

     private List<emailsetandget> movie=new ArrayList<>();

    public btnclickRequests btnclick;

    public RecycleViewRequests(List<emailsetandget> movie) {
        this.movie=movie;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView title;
        public Button btndelete;
        public Button confirm;

        public MyViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.textEmailRequest);
            btndelete=view.findViewById(R.id.btndelete);
            confirm=view.findViewById(R.id.btnconfirm);

        }

    }

    public void setClickButton(btnclickRequests btnclic){
        this.btnclick=btnclic;
    }




    @Override
    public RecycleViewRequests.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerequests, parent, false);
        return new RecycleViewRequests.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewRequests.MyViewHolder holder, final int position) {
      emailsetandget email=movie.get(position);
      holder.title.setText(email.getEmail());

        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/no.otf");
        holder.title.setTypeface(typeface);

        holder.confirm.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
          btnclick.onClickCallback(view,position);

         }
     });
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnclick.onClickCallb(view,position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}

