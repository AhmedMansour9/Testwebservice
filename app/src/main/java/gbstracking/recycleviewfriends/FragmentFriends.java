package gbstracking.recycleviewfriends;



/**
 * Created by HP on 19/04/2018.
 */

import android.Manifest;
import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.gbstracking.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import butterknife.ButterKnife;
import butterknife.OnClick;
import gbstracking.CheckgbsAndNetwork;
import gbstracking.Userlogin.MainActivity;
import gbstracking.contact.RecycleviewContact;
import gbstracking.friends.ActivityFriend;
import gbstracking.mainactivity.home;


import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class FragmentFriends extends Fragment implements ItemClickListener,btnclickinterface,SwipeRefreshLayout.OnRefreshListener{

    public FragmentFriends() {
        // Required empty public constructor
    }
    SwipeRefreshLayout mSwipeRefreshLayout;
    public ValueEventListener mListener;
    String email;
    View v;
    String id;
    String ID;
    public String key;
    StorageReference s;
    ImageView btnBottomSheet;
    BottomSheetBehavior sheetBehavior;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    EditText editemai;
    FirebaseAuth mAuth;
    public static String EMail;
    String IDd;
    public ArrayList<Friendsetandget> moviesList;
    Friendsetandget l;
    String userr;
    FirebaseUser userR;
    DatabaseReference mDatabas;
    DatabaseReference mDatabasE;
    String USerid;
    UserID userid;
    DatabaseReference data;
    ProgressDialog progressDialog;
    ArrayList<Integer> myArrayList=new ArrayList<Integer>();
    ArrayList<Boolean> myArrayListboolean=new ArrayList<Boolean>();
    CheckgbsAndNetwork checkInfo;
    CoordinatorLayout cor;
    public static ArrayList<Integer> listPositions;
    public static ArrayList<Boolean> listBoolean;
    DatabaseReference databaseReference;
    DatabaseReference mDatabaseRef;
    ChildEventListener child;
    public static Boolean check;
    Context context;
    private RecyclerViewReadyCallback recyclerViewReadyCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_listfriends, container, false);
        data = FirebaseDatabase.getInstance().getReference("FriendsRequests");
        mDatabasE = FirebaseDatabase.getInstance().getReference("Friends");
        mDatabas = FirebaseDatabase.getInstance().getReference("Users");
        context=this.getContext();
         moviesList = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Friends");
        editemai = v.findViewById(R.id.editfriend);
        checkInfo=new CheckgbsAndNetwork(getApplicationContext());
        cor=v.findViewById(R.id.cor);
        EMail = editemai.getText().toString().trim();

        s = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userR = mAuth.getCurrentUser();
        IDd = userR.getUid();
        userr = userR.getEmail();
        btnBottomSheet = v.findViewById(R.id.add);
//        friend = v.findViewById(R.id.findyourfriend);
        progressDialog = new ProgressDialog(getApplicationContext());
        Recyclview();
//        RecycleviewSerach();
        SwipRefresh();



        AddFriendd();
        ButtonSheet();


        return v;
    }
    public interface RecyclerViewReadyCallback {
        void onLayoutReady();
    }

    private boolean hasId(String idc){
        if(!TextUtils.isEmpty(idc)) {
            for (Friendsetandget fr : moviesList) {
                if (fr.getUsername().equals(idc)) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    public void SendatatoAdapter() {

       if(checkInfo.isNetworkAvailable(getApplicationContext())) {
           moviesList.clear();
           mAdapter.notifyDataSetChanged();
           mSwipeRefreshLayout.setRefreshing(true);

           mDatabasE = FirebaseDatabase.getInstance().getReference("Friends");
            mDatabasE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.hasChild(IDd)){
                mDatabasE.child(IDd).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        moviesList.clear();
                        mAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(true);

                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            Friendsetandget friendsetandget = data.getValue(Friendsetandget.class);

                            if (friendsetandget != null && !hasId(friendsetandget.getUsername())) {
                                moviesList.add(0, friendsetandget);
                                mAdapter.notifyDataSetChanged();
                                mSwipeRefreshLayout.setRefreshing(false);

                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }else {
                mSwipeRefreshLayout.setEnabled(false);
            }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       }else{
           snackbarinternet();
       }
    }
    private void recycle_animation(RecyclerView recyclerView)
    {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onClick(View view, int position) {

//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            if(MoviesAdapter.filteredList.isEmpty()){
                l = moviesList.get(position);
                Intent i = new Intent(getApplicationContext(), ActivityFriend.class);
                i.putExtra("Email2", l.GetEmail());
                i.putExtra("Photo2", l.getPhoto());
                i.putExtra("id", l.getId());
                i.putExtra("username",l.getUsername());
                startActivity(i);
//            }else if(!MoviesAdapter.filteredList.isEmpty()){
//                l = MoviesAdapter.filteredList.get(position);
//                Intent i = new Intent(getApplicationContext(), ActivityFriend.class);
//                i.putExtra("Email2", l.GetEmail());
//                i.putExtra("Photo2", l.getPhoto());
//                i.putExtra("id", l.getId());
//                i.putExtra("username",l.getUsername());
//                startActivity(i);
//
//            }
//        }else{
//            CheckgbsAndNetwork chec=new CheckgbsAndNetwork(getActivity());
//            chec.showSettingsAlert();
//        }

    }
    @Override
    public void onClickCallback(View view , final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.deletefriend));
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(checkInfo.isNetworkAvailable(getApplicationContext())) {
                    id = moviesList.get(position).getId();
                    email = moviesList.get(position).getEmail();

                    Deleteuser(email, position);
                    Deleteuser2(id);
                    dialog.cancel();
                }else {
                    snackbarinternet();

                }
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();


    }
    public void snackbarinternet(){
        Snackbar.make(cor,getResources().getString(R.string.Nointernet),1500).show();

    }
    @Override
    public void onRefresh() {
        SendatatoAdapter();

    }

    public void AddFriend(){
      editemai = v.findViewById(R.id.editfriend);
         EMail = editemai.getText().toString();

        if(EMail.equals(userR.getEmail())){
            Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
        } else {
            mDatabas.orderByChild("email").equalTo(EMail).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
           if (dataSnapshot.exists()) {
             for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
           userid = dataSnapshot1.getValue(UserID.class);
           USerid=userid.getId();
                      }
          mDatabasE.child(IDd).orderByChild("email").equalTo(EMail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
           if(dataSnapshot.exists()){
           Toast.makeText(getApplicationContext(), getResources().getString(R.string.useralready), Toast.LENGTH_SHORT).show();
               }
            else{
          data.child(USerid).orderByChild("email").equalTo(userR.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
                   if(dataSnapshot.exists()){
          Toast.makeText(getApplicationContext(), getResources().getString(R.string.requestsentAlready) , Toast.LENGTH_SHORT).show();
          }
                    else if(!dataSnapshot.exists()){
            data.child(userid.getId()).push().child("email").setValue(userR.getEmail());
                       editemai.setText("");
                       Toast.makeText(getApplicationContext(), getResources().getString(R.string.requestsent), Toast.LENGTH_SHORT).show();}}
                @Override
                  public void onCancelled(DatabaseError databaseError) {}});}}
                 @Override
                 public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalidemail), Toast.LENGTH_SHORT).show();
                        editemai.setText("");
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }

     }
     public void Deleteuser(String email, final int position){
          databaseReference = FirebaseDatabase.getInstance().getReference("Friends").child(IDd);
         databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 for (DataSnapshot child : dataSnapshot.getChildren()) {
                     Log.d("User key", child.getKey());
                     key=child.getKey();
                     moviesList.remove(position);
                     child.getRef().removeValue();
//                     mDatabasE.removeEventListener(mListener);
                     mAdapter.notifyDataSetChanged();


                 }
             }
             @Override
             public void onCancelled(DatabaseError databaseError) {}
         });


     }
       public void Deleteuser2(String id){

           DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Friends").child(id);
           databaseReference.orderByChild("email").equalTo(userr).addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                       dataSnapshot1.getRef().removeValue();




                   }
               }
               @Override
               public void onCancelled(DatabaseError databaseError) {
               }
           });

       }




    public void Recyclview(){
        recyclerView = v.findViewById(R.id.recycler_friend);
        recyclerView.setHasFixedSize(true);
        mAdapter = new MoviesAdapter(moviesList,context);
        mAdapter.setClickButton(FragmentFriends.this);
        mAdapter.setClickListener(FragmentFriends.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);
        recycle_animation(recyclerView);
    }
//    public void RecycleviewSerach(){
//        friend.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                mAdapter.getFilter().filter(charSequence);
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//    }
    public void SwipRefresh(){
        mSwipeRefreshLayout =  v.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                SendatatoAdapter();

            }
        });
    }

    public void AddFriendd()
    {
        Button btn = v.findViewById(R.id.friendrequest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if(checkInfo.isNetworkAvailable(getApplicationContext())) {
                    AddFriend();
                }else {
                    snackbarinternet();
                }
            }
        });
    }
    public void ButtonSheet(){
        LinearLayout layoutBottomSheet = v.findViewById(R.id.buttomsheet222);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }

            @OnClick
            public void toggleBottomSheet() {
            }
        });

        btnBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

    }


}
