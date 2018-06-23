package gbstracking.friends;


import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gbstracking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import gbstracking.Userlogin.UserloginMain;
import gbstracking.recycleviewfriends.UserEmail;

/**
 * Created by HP on 19/04/2018.
 */

public class FragmentRequests extends Fragment implements btnclickRequests, SwipeRefreshLayout.OnRefreshListener {
        View v;
    UserEmail a;
    String id;
    UserEmail userr;
    private RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    FirebaseAuth auth;
    String IDd;
    private RecycleViewRequests mAdapter;
    public ArrayList<String> lst = new ArrayList<String>(); // Result will be holded Here
    public ArrayList<emailsetandget> moviesList;
    DatabaseReference mDatabaseRef;
    DatabaseReference data;
    DatabaseReference currnetuser;
    FirebaseUser user;
    FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.requests, container, false);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
         mDatabaseRef = FirebaseDatabase.getInstance().getReference("FriendsRequests");
        data = FirebaseDatabase.getInstance().getReference("Users");

        auth=FirebaseAuth.getInstance();
         user=auth.getCurrentUser();
         IDd=user.getUid();
        UserEmail a;
        moviesList=new ArrayList<>();
        recyclerView = v.findViewById(R.id.recycler_friendRequest);
        recyclerView.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);
        mAdapter = new RecycleViewRequests(moviesList);
        mAdapter.setClickButton(FragmentRequests.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout =  v.findViewById(R.id.swipe_contain);
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
    return v;
    }

    @Override
    public void onClickCallback(View view, final int adapterPosition) {
       String email=moviesList.get(adapterPosition).getEmail();
         mDatabaseRef.child(IDd).orderByChild("email").equalTo(email)
          .addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                  moviesList.remove(adapterPosition);
                  dataSnapshot1.getRef().removeValue();
                  mAdapter.notifyDataSetChanged();
              }

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });
        Sendhisemailtome(email);
        Sendmyemailtohim(email);
    }

    @Override
    public void onClickCallb(View view, int adapterPosition) {
        String email=moviesList.get(adapterPosition).getEmail();
        DeleteRequest(email,adapterPosition);
    }
    private boolean hasId(String idc){
        if(!TextUtils.isEmpty(idc)) {
            for (emailsetandget fr : moviesList) {
                if (fr.getEmail().equals(idc)) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
    public void SendatatoAdapter() {
        lst.clear();
        moviesList.clear();
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(true);
        mDatabaseRef.child(IDd).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                emailsetandget email= dataSnapshot.getValue(emailsetandget.class);
                if (email != null && !hasId(email.getEmail())) {
                    moviesList.add(0, email);
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
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onRefresh() {
        SendatatoAdapter();
    }
    public void Sendhisemailtome(String Email){
        data.orderByChild("email").equalTo(Email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                     userr=dataSnapshot1.getValue(UserEmail.class);
                }
                auth=FirebaseAuth.getInstance();
                user=auth.getCurrentUser();
                IDd=user.getUid();
                currnetuser = FirebaseDatabase.getInstance().getReference("Friends").child(IDd);
                DatabaseReference dataa = currnetuser.push();
                userr.setPrivacy(true);
                dataa.setValue(userr);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void Sendmyemailtohim(final String EMail){
        data.orderByChild("email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                     a=dataSnapshot1.getValue(UserEmail.class);
                 data.orderByChild("email").equalTo(EMail).addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         for(DataSnapshot dataSnapshot11:dataSnapshot.getChildren()){
                              id=dataSnapshot11.child("id").getValue().toString();
                         }
                         currnetuser = FirebaseDatabase.getInstance().getReference("Friends").child(id);
                         DatabaseReference dataa = currnetuser.push();
                         a.setPrivacy(true);
                         dataa.setValue(a);

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
    public void DeleteRequest(String Email, final int adapterPosition){
        mDatabaseRef.child(IDd).orderByChild("email").equalTo(Email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot da:dataSnapshot.getChildren()){
                    moviesList.remove(adapterPosition);
                    da.getRef().removeValue();
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
