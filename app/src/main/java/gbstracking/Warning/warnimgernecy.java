package gbstracking.Warning;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gbstracking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gbstracking.MyVolley;
import gbstracking.Nvigation;
import gbstracking.friends.ActivityFriend;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class warnimgernecy extends Fragment {
    View v;
     List<String> listname=new ArrayList<>();
    String number,Emaail;
    ImageView truee,falsee;
    EditText pin;
    Button btnsendmessage;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ProgressDialog progressDialog;
    public warnimgernecy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.warnimgernecy, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        pin=v.findViewById(R.id.pin);
        truee=v.findViewById(R.id.truee);
        falsee=v.findViewById(R.id.falsee);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        btnsendmessage=v.findViewById(R.id.btnsendmessage);
        Toolbar tool=v.findViewById(R.id.tool);
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


        SharedPreferences share=getActivity().getSharedPreferences("Number", Context.MODE_PRIVATE);
         number=share.getString("Num","");
        Toast.makeText(getApplicationContext(), ""+number, Toast.LENGTH_SHORT).show();
        SharedPreferences sha= getApplicationContext().getSharedPreferences("Usersname", MODE_PRIVATE);
        String jsonFavorites = sha.getString("users", null);
        Gson gson2 = new Gson();
        String[] favoriteItems = gson2.fromJson(jsonFavorites,String[].class);
        if(favoriteItems!=null) {
            listname = new ArrayList<>();
            for (String i : favoriteItems) {
                listname.add(i);
            }
        }
       editpin();



        return v;
    }

    private Boolean getPin(){

        String Pin=pin.getText().toString();
        if(Pin.equals(number)){
            return true;
        }else {
            return false;
        }
    }
    public void editpin() {
        pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                Boolean num=getPin();
                if(num){
                    truee.setVisibility(View.VISIBLE);
                    falsee.setVisibility(View.GONE);
                    btnsendmessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getAddresslocation(new firebasecallback() {
                                @Override
                                public void Callback(final String day, final String street,final String time) {


                                    for(int i=0;i<listname.size();i++){
                                        final int finalI = i;
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://zamaleksongs.000webhostapp.com/pushem.php",
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                    }
                                                }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<>();
                                                if (user.getDisplayName() != null) {
                                                    params.put("title", user.getDisplayName());
                                                } else {
                                                    params.put("title", user.getEmail());
                                                }
                                                params.put("message",getResources().getString(R.string.mesge));
                                                params.put("address",street);
                                                params.put("time",time);
                                                params.put("Day",day);
                                                params.put("email",listname.get(finalI));
                                                return params;
                                            }
                                        };
                                        MyVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                                    }
                                    Dialog dialog=new Dialog(getContext());
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setContentView(R.layout.dialogalert);
                                    dialog.show();

                                }

                            });
                            btnsendmessage.setEnabled(false);

                        }
                    });
                }else {
                    falsee.setVisibility(View.VISIBLE);
                    truee.setVisibility(View.GONE);
                }
            }
        });
    }
    public void getAddresslocation( final firebasecallback fire){
        DatabaseReference data= FirebaseDatabase.getInstance().getReference().child("Locationstreet")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    String day=dataSnapshot.child("day").getValue().toString();
                    String street=dataSnapshot.child("street").getValue().toString();
                    String time=dataSnapshot.child("time").getValue().toString();
                fire.Callback(day,street,time);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public interface firebasecallback{
        void Callback(String day,String street,String time);
    }
    public void SendingMassege() {

    }


}
