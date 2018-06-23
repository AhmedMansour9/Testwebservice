package gbstracking.recycleviewfriends;



/**
 * Created by HP on 19/04/2018.
 */

import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import gbstracking.friends.ActivityFriend;
import gbstracking.mainactivity.home;


import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class FragmentFriends extends Fragment implements switchinterface,ItemClickListener,btnclickinterface,SwipeRefreshLayout.OnRefreshListener{

    public FragmentFriends() {
        // Required empty public constructor
    }
    SwipeRefreshLayout mSwipeRefreshLayout;
    public ChildEventListener mListener;
    View v;
    public String key;
    StorageReference s;
    private static int Glarlly = 1;
    ImageView btnBottomSheet;
    BottomSheetBehavior sheetBehavior;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    EditText editemai;
    FirebaseAuth mAuth;
    public static String EMail;
    String IDd;
    EditText friend;
    public ArrayList<String> lst = new ArrayList<String>(); // Result will be holded Here
    public ArrayList<Friendsetandget> moviesList;
    Friendsetandget l;
    String userr;
    FirebaseUser userR;
    DatabaseReference mDatabas;
    DatabaseReference mDatabasE;
    LocationManager locationManager;
    String USerid;
    UserID userid;
    DatabaseReference data;
    ProgressDialog progressDialog;
    private Switch switchCli;
    CoordinatorLayout cor;
    public static Boolean switchboolean;
    public static Boolean Allswitch;
    public static ArrayList<Integer> listPositions;
    public static ArrayList<Boolean> listBoolean;
    ArrayList<Integer> myArrayList=new ArrayList<Integer>();
    ArrayList<Boolean> myArrayListboolean=new ArrayList<Boolean>();
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_listfriends, container, false);
        Firebase.setAndroidContext(getActivity());
        data = FirebaseDatabase.getInstance().getReference("FriendsRequests");
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        mDatabasE = FirebaseDatabase.getInstance().getReference("Friends");
        mDatabas = FirebaseDatabase.getInstance().getReference("Users");
         moviesList = new ArrayList<>();

        editemai = v.findViewById(R.id.editfriend);


        cor=v.findViewById(R.id.cor);
        EMail = editemai.getText().toString().trim();

        s = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userR = mAuth.getCurrentUser();
        IDd = userR.getUid();
        userr = userR.getEmail();
        ButterKnife.bind(getActivity());
        btnBottomSheet = v.findViewById(R.id.add);
        friend = v.findViewById(R.id.findyourfriend);
        progressDialog = new ProgressDialog(getApplicationContext());

        SavedSahredPrefrenceSwitch();
        Recyclview();
        RecycleviewSerach();
        SwipRefresh();
        AddFriendd();
        ButtonSheet();


        return v;
    }

    private boolean hasId(String idc){
        if(!TextUtils.isEmpty(idc)) {
            for (Friendsetandget fr : moviesList) {
                if (fr.getEmail().equals(idc)) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Glarlly) {
            Uri uri = data.getData();
            StorageReference storageReference = s.child("Photos").child(uri.getLastPathSegment());
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity(), taskSnapshot.getError() + "", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void SendatatoAdapter() {

        moviesList.clear();
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(true);
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Friends").child(IDd);
        mListener=mDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Friendsetandget friendsetandget=dataSnapshot.getValue(Friendsetandget.class);

                if(friendsetandget!=null &&!hasId(friendsetandget.getEmail())) {
                    System.out.println("email: "+friendsetandget.getEmail());
                    moviesList.add(0, friendsetandget);
                    mAdapter.notifyDataSetChanged();
                }

                mSwipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        if (mSwipeRefreshLayout.isRefreshing())
        {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    @Override
    public void onClick(View view, int position) {

//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            if(MoviesAdapter.filteredList.isEmpty()){
                l = moviesList.get(position);
                Intent i = new Intent(getApplicationContext(), ActivityFriend.class);
                i.putExtra("Email2", l.GetEmail());
                i.putExtra("Photo2", l.getPhoto());
                i.putExtra("id", l.getId());
                i.putExtra("username",l.getUsername());
                startActivity(i);
            }else if(!MoviesAdapter.filteredList.isEmpty()){
                l = MoviesAdapter.filteredList.get(position);
                Intent i = new Intent(getApplicationContext(), ActivityFriend.class);
                i.putExtra("Email2", l.GetEmail());
                i.putExtra("Photo2", l.getPhoto());
                i.putExtra("id", l.getId());
                i.putExtra("username",l.getUsername());
                startActivity(i);

            }
//        }else{
//            CheckgbsAndNetwork chec=new CheckgbsAndNetwork(getActivity());
//            chec.showSettingsAlert();
//        }

    }
    @Override
    public void onClickCallback(View view , final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do You Want to Delete Your Friend ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id =moviesList.get(position).getId();
                String email= moviesList.get(position).getEmail();


                Deleteuser(email,position);
                Deleteuser2(id);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();


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
           Toast.makeText(getApplicationContext(), "User Already your Friend", Toast.LENGTH_SHORT).show();
               }
            else{
           Toast.makeText(getApplicationContext(), ""+USerid
            , Toast.LENGTH_SHORT).show();
          data.child(USerid).orderByChild("email").equalTo(userR.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
                   if(dataSnapshot.exists()){
          Toast.makeText(getApplicationContext(), "Request Already Sent", Toast.LENGTH_SHORT).show();
          }
                    else if(!dataSnapshot.exists()){
            data.child(userid.getId()).push().child("email").setValue(userR.getEmail());
             Toast.makeText(getApplicationContext(), "Sending Request", Toast.LENGTH_SHORT).show();}}
                @Override
                  public void onCancelled(DatabaseError databaseError) {}});}}
                 @Override
                 public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
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
                     mAdapter.notifyDataSetChanged();
                     databaseReference.removeValue();
                     mDatabasE.child(IDd)
                             .orderByChild("email").removeEventListener(mListener);
                     mDatabas.removeEventListener(home.value);
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
                   mDatabas.removeEventListener(home.value);
               }
               @Override
               public void onCancelled(DatabaseError databaseError) {
               }
           });

       }


    @Override
    public void onClickCall(View view, int adapterPosition, Boolean A) {
           String id =moviesList.get(adapterPosition).getId();
        if(A){
            Gson i=new Gson();
            SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("SA", MODE_PRIVATE).edit();
            myArrayList.add(adapterPosition);
            String jsonFavorites = i.toJson(myArrayList);
            editor.putString("pos", jsonFavorites);
            myArrayListboolean.add(true);
            String jsonFavori = i.toJson(myArrayListboolean);
            editor.putString("Boolean", jsonFavori);
            editor.commit();

            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Friends").child(id);
            databaseReference.orderByChild("email").equalTo(userR.getEmail())
               .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        dataSnapshot1.getRef().child("privacy").setValue(true);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }else {
            SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("SA", MODE_PRIVATE).edit();
           Gson i=new Gson();
            myArrayList.add(adapterPosition);
            String jsonFavorites = i.toJson(myArrayList);
            editor.putString("pos", jsonFavorites);
            myArrayListboolean.add(false);
            String jsonFavori = i.toJson(myArrayListboolean);
            editor.putString("Boolean", jsonFavori);

            editor.commit();


            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Friends").child(id);
            databaseReference.orderByChild("email").equalTo(userR.getEmail())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                dataSnapshot1.getRef().child("privacy").setValue(false);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

        }
    }

    public void Recyclview(){
        recyclerView = v.findViewById(R.id.recycler_friend);
        recyclerView.setHasFixedSize(true);
        mAdapter = new MoviesAdapter(moviesList);
        mAdapter.setClickButton(FragmentFriends.this);
        mAdapter.setClickListener(FragmentFriends.this);
        mAdapter.setClickListen(FragmentFriends.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }
    public void RecycleviewSerach(){
        friend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAdapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
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
                AddFriend();
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
    public interface firebasecall{
        void Callback(Friendsetandget u);
    }

    public void SavedSahredPrefrenceSwitch(){
        SharedPreferences sharedPref =getActivity().getSharedPreferences("SA", MODE_PRIVATE);
        String jsonFavorit = sharedPref.getString("Boolean", null);
        Gson gson3 = new Gson();
        Boolean[] favoriteIte = gson3.fromJson(jsonFavorit,Boolean[].class);
        if(favoriteIte!=null) {
            listBoolean = new ArrayList<>();
            for (Boolean y : favoriteIte) {
                listBoolean.add(y);
            }
        }
        String jsonFavorites = sharedPref.getString("pos", null);
        Gson gson2 = new Gson();
        int[] favoriteItems = gson2.fromJson(jsonFavorites,int[].class);
        if(favoriteItems!=null) {
            listPositions = new ArrayList<>();
            for (int i : favoriteItems) {
                listPositions.add(i);
            }
        }

    }
}