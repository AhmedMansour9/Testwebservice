package gbstracking.Warning;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.gbstracking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import gbstracking.Nvigation;
import gbstracking.friends.emailsetandget;
import gbstracking.recycleviewfriends.Friendsetandget;
import gbstracking.recycleviewfriends.MoviesAdapter;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by HP on 09/05/2018.
 */

public class warrningmessage extends Fragment implements ItemClickListen{
    View v;
    RecyclerView recyclerView;
    RecycleFriendsNamed mov;
    List<emailsetandget> moviesList;
    Button save,finish;
    ArrayList<String> araaystring=new ArrayList<>();
    EditText secretnumber;
    SharedPreferences.Editor share;
    TextView warn7,scret;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.warrningmessage, container, false);
        finish=v.findViewById(R.id.finish);
        Toolbar tool=v.findViewById(R.id.toolb);
        save=v.findViewById(R.id.btnsave);
        secretnumber=v.findViewById(R.id.editnumber);
        moviesList=new ArrayList<>();
        moviesList.clear();
         share=getActivity().getSharedPreferences("Number", Context.MODE_PRIVATE).edit();
         share.clear();
         share.commit();
       araaystring.clear();
        secretnumber.setEnabled(true);
        save.setEnabled(true);
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
        Recyclview();
        getFriendsName();
        btnsave();
        letschoosebtn();

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sahre=getActivity().getSharedPreferences("Number",Context.MODE_PRIVATE);
                String number=sahre.getString("Num","");
                if(number.isEmpty()||araaystring.isEmpty()){

                }else {
                    SharedPreferences.Editor sharet=getApplicationContext().getSharedPreferences("Warr", MODE_PRIVATE).edit();
                    sharet.putBoolean("warnmessa",true);
                    sharet.apply();

                    getFragmentManager().beginTransaction().replace(R.id.warnmessage,new warnimgernecy()).addToBackStack(null).commit();
                }
            }
        });
        return v;
    }
    private boolean hasId(String idc){
        if(!TextUtils.isEmpty(idc)) {
            for (emailsetandget fr : moviesList) {
                if (fr.getUsername().equals(idc)) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
    public void getFriendsName(){
        moviesList.clear();
        final String Id=FirebaseAuth.getInstance().getCurrentUser().getUid();
         final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Friends");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(Id)){
                    databaseReference.child(Id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapsho) {
                            for(DataSnapshot dataSnapshot1:dataSnapsho.getChildren()){
                                emailsetandget e=dataSnapshot1.getValue(emailsetandget.class);
                                if(e!=null &&!hasId(e.getUsername())) {
                                    moviesList.add(0, e);
                                    mov.notifyDataSetChanged();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void Recyclview(){
        recyclerView = v.findViewById(R.id.recycler_friendname);
        recyclerView.setHasFixedSize(true);
        mov = new RecycleFriendsNamed(moviesList);
        mov.setClickListener(warrningmessage.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mov);

    }
    public void btnsave(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String number=secretnumber.getText().toString();
                if(number.length()<4){
                    secretnumber.setError(getResources().getString(R.string.errornumber));
                }else {
                    share.putString("Num",number);
                    share.commit();
                    secretnumber.setEnabled(false);
                    save.setEnabled(false);
                }
            }
        });
    }


    public void textfont(){
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/no.otf");
        scret=v.findViewById(R.id.scret);
        scret.setTypeface(typeface);
        warn7=v.findViewById(R.id.warn7);
        warn7.setTypeface(typeface);
    }
    public void letschoosebtn(){
        Button btn=v.findViewById(R.id.btnchoose);
        final CardView re4=v.findViewById(R.id.Ra2);
        final CardView re3=v.findViewById(R.id.Ra);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                re4.setVisibility(View.GONE);
                re3.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        String Emaail =moviesList.get(position).getEmail();
        String user =moviesList.get(position).getUsername();
        Toast.makeText(getApplicationContext(), user+"  "+getResources().getString(R.string.isslect), Toast.LENGTH_SHORT).show();
         araaystring.add(Emaail);
         moviesList.remove(position);
         mov.notifyDataSetChanged();
        Gson i=new Gson();
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("Usersname", MODE_PRIVATE).edit();
        String jsonFavorites = i.toJson(araaystring);
        editor.putString("users", jsonFavorites);
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        moviesList.clear();
    }

    @Override
    public void onPause() {
        super.onPause();
        moviesList.clear();
    }
}
