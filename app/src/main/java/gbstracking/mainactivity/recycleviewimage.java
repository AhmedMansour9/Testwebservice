package gbstracking.mainactivity;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gbstracking.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import gbstracking.GetandSetFriendOnlineHome;
import gbstracking.contact.RecycleviewContact;
import gbstracking.contact.contactsetter;
import gbstracking.recycleviewfriends.Friendsetandget;
import gbstracking.recycleviewfriends.ItemClickListener;
import gbstracking.recycleviewfriends.MoviesAdapter;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by HP on 24/05/2018.
 */

public class recycleviewimage extends RecyclerView.Adapter<recycleviewimage.Myviewholder> {
   List<GetandSetFriendOnlineHome> listimage;
    Context context;
    public itemClickListener item;

    public recycleviewimage( List<GetandSetFriendOnlineHome> listimage) {
        this.context = context;
        this.listimage = listimage;
    }
    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.imagerecyclehome1,parent,false);
       Myviewholder my=new Myviewholder(v);

        return my;
    }

    @Override
    public void onBindViewHolder(Myviewholder holder, int position) {
        GetandSetFriendOnlineHome movie = listimage.get(position);


        String i = movie.getPhoto();
        Uri u = Uri.parse(i);

        Picasso.with(getApplicationContext())
                .load(u)
                .placeholder(R.drawable.emptyprofile)
                .into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return listimage.size();
    }
    public void clicklisten(itemClickListener items){
        this.item=items;
    }

    public  class Myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CircleImageView circleImageView;
        public Myviewholder(View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.imgfriendhome);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
        if(item!=null)item.onClick(view,getAdapterPosition());
        }
    }
}
