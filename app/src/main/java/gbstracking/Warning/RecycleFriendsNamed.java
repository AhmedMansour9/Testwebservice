package gbstracking.Warning;

import android.graphics.Typeface;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gbstracking.R;

import java.util.ArrayList;
import java.util.List;

import gbstracking.friends.RecycleViewRequests;
import gbstracking.friends.btnclickRequests;
import gbstracking.friends.emailsetandget;
import gbstracking.recycleviewfriends.FragmentFriends;
import gbstracking.recycleviewfriends.ItemClickListener;
import gbstracking.recycleviewfriends.MoviesAdapter;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by HP on 10/05/2018.
 */

public class RecycleFriendsNamed extends RecyclerView.Adapter<RecycleFriendsNamed.MyViewHolder> {

    private List<emailsetandget> movie=new ArrayList<>();
    public ItemClickListen itemClickListen;


    public RecycleFriendsNamed(List<emailsetandget> movie) {
        this.movie=movie;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.textfriendname);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
         if(itemClickListen!=null)itemClickListen.onClick(view,getAdapterPosition());
        }

    }
    public void setClickListener(ItemClickListen itemClickListener) {
        this.itemClickListen = itemClickListener;
    }
    @Override
    public RecycleFriendsNamed.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textitemrecycle, parent, false);
        return new RecycleFriendsNamed.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleFriendsNamed.MyViewHolder holder, final int position) {
        emailsetandget email=movie.get(position);
        holder.title.setText(email.getUsername());

        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/no.otf");
        holder.title.setTypeface(typeface);


    }

    @Override
    public int getItemCount() {
        return movie.size();
    }



}
