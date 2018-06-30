package gbstracking.recycleviewfriends;

/**
 * Created by HP on 31/03/2018.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.Snackbar;
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

import gbstracking.CheckgbsAndNetwork;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{


    private ArrayList<Friendsetandget> mArrayList=new ArrayList<>();


     public Boolean bollean;
    public ItemClickListener clickListene;
    public btnclickinterface btnclick;
     public switchinterface switche;
     ArrayList<Integer> lis=new ArrayList<>();
    ArrayList<Boolean> lisbool=new ArrayList<>();
    public   int selectedPos;
   public  ArrayList<Friendsetandget> filteredList = new ArrayList<>();
    View itemView;
    CheckgbsAndNetwork checkInfo;

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


                if (clickListene != null) clickListene.onClick(view, getLayoutPosition());

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

        mArrayList = moviesList;
        filteredList=moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itsmrecyclefriends, parent, false);
        return new MyViewHolder(itemView);
    }
//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    filteredList = mArrayList;
//                } else {
//                    ArrayList<Friendsetandget> filteredListtt = new ArrayList<>();
//                    for (Friendsetandget androidVersion : mArrayList) {
//                        if (androidVersion.getUsername().toLowerCase().contains(charString) ) {
//                            filteredListtt.add(androidVersion);
//                        }
//                    }
//                    filteredList = filteredListtt;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filteredList;
//                return filterResults;
//            }
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                filteredList = (ArrayList<Friendsetandget>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Friendsetandget movie = filteredList.get(position);
        checkInfo=new CheckgbsAndNetwork(getApplicationContext());
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

            if (lis != null) {

                for (int I = 0; I < lis.size(); I++) {
                    if (position == lis.get(I)) {
                        holder.switchClick.setChecked(lisbool.get(I));

                    }
                }


        }
       holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnclick.onClickCallback(view,holder.getAdapterPosition());
            }
        });

       holder.switchClick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   switche.onClickCall(compoundButton, holder.getAdapterPosition(), b);

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
        return filteredList.size();
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




