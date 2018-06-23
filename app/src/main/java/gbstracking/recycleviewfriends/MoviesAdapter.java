package gbstracking.recycleviewfriends;

/**
 * Created by HP on 31/03/2018.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.gbstracking.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> implements Filterable{

    public ArrayList<Friendsetandget> moviesList=new ArrayList<>();

    private ArrayList<Friendsetandget> mArrayList;


     public Boolean bollean;
    public ItemClickListener clickListene;
    public btnclickinterface btnclick;
     public switchinterface switche;
     ArrayList<Integer> lis=new ArrayList<>();
    ArrayList<Boolean> lisbool=new ArrayList<>();
   public static ArrayList<Friendsetandget> filteredList = new ArrayList<>();
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public de.hdodenhof.circleimageview.CircleImageView photoo;
        public ImageView btndelete;
        public ImageView onlinee;
        public  Switch switchClick;
        public MyViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.textfriendd);
            photoo=view.findViewById(R.id.imgfriend);
            btndelete=view.findViewById(R.id.imagedelete);
            onlinee=view.findViewById(R.id.online);
                    view.setOnClickListener(this);
            switchClick=itemView.findViewById(R.id.simpleSwitch);
            switchClick.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (clickListene != null) clickListene.onClick(view, getAdapterPosition());
        }
    }
    public void setClickListen(switchinterface switchinterface) {
        this.switche = switchinterface;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListene = itemClickListener;
    }
    public void setClickButton(btnclickinterface btnclic){
        this.btnclick=btnclic;
    }
    public MoviesAdapter(ArrayList<Friendsetandget> moviesList ) {
        this.moviesList = moviesList;
        mArrayList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itsmrecyclefriends, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                filteredList.clear();
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    moviesList = mArrayList;
                } else {

                    for (Friendsetandget androidVersion : mArrayList) {
                        if (androidVersion.getUsername().toLowerCase().contains(charString)) {
                            filteredList.add(androidVersion);}}
                    moviesList = filteredList;}
                FilterResults filterResults = new FilterResults();
                filterResults.values = moviesList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                moviesList = (ArrayList<Friendsetandget>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Friendsetandget movie = moviesList.get(position);

        holder.switchClick.setChecked(true);


        if (movie.getUsername() != null) {
            holder.title.setText(movie.getUsername());
        } else if (movie.getUsername() == null) {
            holder.title.setText(movie.GetEmail());
        }

        String i = movie.getPhoto();
        Uri u = Uri.parse(i);

        Picasso.with(getApplicationContext())
                .load(u)
                .placeholder(R.drawable.emptyprofile)
                .into(holder.photoo);

        lisbool=FragmentFriends.listBoolean;
        lis = FragmentFriends.listPositions;
        if(lis!=null){

        for (int I=0;I<lis.size();I++) {
            if(position==lis.get(I)){
                holder.switchClick.setChecked(lisbool.get(I));

            }
        }
        }
        final int index = position;

       holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnclick.onClickCallback(view,index);
            }
        });

       holder.switchClick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
           switche.onClickCall(compoundButton,index,b);

           }
       });



        bollean=movie.getOnline();
       if(bollean==true){
           holder.onlinee.setVisibility(View.VISIBLE);
       }else {
           holder.onlinee.setVisibility(View.INVISIBLE);
       }

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    }




