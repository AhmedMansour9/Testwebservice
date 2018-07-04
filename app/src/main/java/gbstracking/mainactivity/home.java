package gbstracking.mainactivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.RoutingListener;
import com.facebook.FacebookSdk;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.gbstracking.R;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import gbstracking.GetAndSethomeFriends;
import gbstracking.GetandSetFriendOnlineHome;
import gbstracking.Nvigation;
import gbstracking.SharedPrefManager;
import gbstracking.Userlogin.MainActivity;
import gbstracking.Userlogin.loginmain;
import gbstracking.friends.ActivityFriend;
import gbstracking.friends.Common;
import gbstracking.friends.IGoogleApi;
import gbstracking.friends.bolleaanuser;
import gbstracking.recycleviewfriends.Friendsetandget;
import gbstracking.searchplaces.InfoWindowData;
import gbstracking.searchplaces.windowinfofriend;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class home extends Fragment implements itemClickListener, RoutingListener, OnMapReadyCallback, com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    DatabaseReference dates;
    DatabaseReference dat;
    FirebaseUser userR;
    String IDd;
    FirebaseAuth mAuth;
    bolleaanuser online;
    Boolean Online;
    HashMap<String, Marker> markerlist = new HashMap<>();
    Boolean Privacy;
    GoogleMap googleMap;
    GoogleApiClient mGoogleApiClient;
    private static final String URL_REGISTER_DEVICE = "https://zamaleksongs.000webhostapp.com/RegisterDevice.php";
    LocationRequest locationReques;
    double lat, lon;
    boolean firstTime = true;
    AutoCompleteTextView auto;
    public static LatLngBounds latLngBounds;
    Context context;
    ArrayList<String> listid;
    String ID;
    String  KEY;
    com.getbase.floatingactionbutton.FloatingActionButton Share;
     com.getbase.floatingactionbutton.FloatingActionButton btngetlocation;
    IGoogleApi mService;
    LatLng latyy;
    private static final int[] COLORS = new int[]{R.color.colorPrimaryDark, R.color.colorPrimary, R.color.cardview_light_background, R.color.colorAccent, R.color.primary_dark_material_light};
    Button startmove;
    List<Address> addresses;
    String addres;
    DatabaseReference data;
    GetAndSethomeFriends y;
    DatabaseReference datalocation;
    double lati, longe;
    List<Marker> lismarket;
     public static  ValueEventListener value;
    ArrayList<GetandSetFriendOnlineHome> lsst;
    Marker m;
    List<Polyline> polylines;
    GetandSetFriendOnlineHome u;
    Friendsetandget t;
    List<LatLng> polinlist;
    recycleviewimage adpter;
    List<GetandSetFriendOnlineHome> lstfriends = new ArrayList<>();
    RecyclerView recyc;
    View v;
    String date;
    DatabaseReference datausers;
    int MY_PERMISSIONS_REQUEST_LOCATION=99;
    GetandSetFriendOnlineHome home;
    final public static int REQUEST_LOCATION_CODE = 99;
    CoordinatorLayout framehome;
    SharedPreferences sharedPreferences;

    public home() {
    }

    DatabaseReference connectedRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_home, container, false);


        datausers = FirebaseDatabase.getInstance().getReference("Users");
        Share=v.findViewById(R.id.share);
        framehome=v.findViewById(R.id.framehome);
        context = this.getContext();
        home = new GetandSetFriendOnlineHome();
        listid = new ArrayList<>();
        polylines = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        userR = mAuth.getCurrentUser();
        lismarket = new ArrayList<Marker>();
        lsst = new ArrayList<>();
        IDd = userR.getUid();
//        y = new GetAndSethomeFriends();
        polinlist = new ArrayList<>();
        startmove = v.findViewById(R.id.dirstart);
        datalocation = FirebaseDatabase.getInstance().getReference("Location");
        data = FirebaseDatabase.getInstance().getReference("Friends");
        dates = FirebaseDatabase.getInstance().getReference("Friends");
        auto = v.findViewById(R.id.autoComp);
        btngetlocation=v.findViewById(R.id.LOCATION);
        Toolbar toolbar = v.findViewById(R.id.toolbarauto);
        mService = Common.iGoogleApi();
        u = new GetandSetFriendOnlineHome();
        t = new Friendsetandget();
        sharedPreferences=getApplicationContext().getSharedPreferences("Phone",MODE_PRIVATE);
        String phone=sharedPreferences.getString("phone","");
        if(!phone.isEmpty()){
            DatabaseReference data=FirebaseDatabase.getInstance().getReference().child("Users");
            data.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("phone").setValue(phone);
        }
        Recyclview();
        checkLocationPermission();


        Nvigation.toggle = new ActionBarDrawerToggle(
                getActivity(), Nvigation.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        Nvigation.drawer.addDrawerListener(Nvigation.toggle);
        Nvigation.toggle.syncState();

        Nvigation.toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.navigat);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Nvigation.drawer.isDrawerOpen(GravityCompat.START)) {
                    Nvigation.drawer.closeDrawer(GravityCompat.START);
                } else {
                    Nvigation.drawer.openDrawer(GravityCompat.START);
                }
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (userR == null) {
            logoutuser();
        }
        online();

        sendTokenToServer();


        return v;
    }

    @Override
    public void onMapReady(final GoogleMap googleMaps) {

        googleMap = googleMaps;


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        buildGoogleapiclint();
        if (m != null) {
            m.remove();
        }
        googleMaps.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);


            }
        });
        SendatatoAdapter();
        callback();


    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationReques = new LocationRequest();
        locationReques.setSmallestDisplacement(1);
        locationReques.setInterval(1000);
        locationReques.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationReques, this);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationReques);


        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(getActivity(),
                                REQUEST_LOCATION_CODE);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        buildGoogleapiclint();
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        googleMap.setMyLocationEnabled(true);

                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;
                    default:
                        break;
                }
                break;
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        if (firstTime) {
            firstTime = false;
            Camerapoistion(location.getLatitude(), location.getLongitude());
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setIndoorEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setTrafficEnabled(false);
        googleMap.setBuildingsEnabled(false);
        lat = location.getLatitude();
        lon = location.getLongitude();
        latyy = new LatLng(lat, lon);
        polinlist.add(latyy);

        btngetlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camerapoistion(lat, lon);
            }
        });

        try {
            Geocoder geocoder = new Geocoder(context);
            addresses = geocoder.getFromLocation(lat, lon, 1);
            addres = addresses.get(0).getAddressLine(0);
            Sendlocationtofirebase(addres);
        } catch (IOException d) {
            d.printStackTrace();
        }


        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Geocoder geocoder = new Geocoder(context);
                    addresses = geocoder.getFromLocation(lat, lon, 1);
                    addres = addresses.get(0).getAddressLine(0);
                } catch (IOException d) {
                    d.printStackTrace();
                }
                String uri = "http://maps.google.com/maps?q=" + lat + "," + lon + "&iwloc=A&hl=es";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String ShareSub = "Here is my location";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ShareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, uri);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });


        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latyy);
        circleOptions.radius(800);
        circleOptions.fillColor(0X66b7cce5);
        circleOptions.strokeColor(0X66FF0000);
        circleOptions.strokeWidth(0);
        googleMap.addCircle(circleOptions);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getUid() != null) {
            String IDe = user.getUid();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Location");
            GeoFire geoFire = new GeoFire(reference);
            geoFire.setLocation(IDe, new GeoLocation(location.getLatitude(), location.getLongitude()), new GeoFire.CompletionListener() {
                @Override
                public void onComplete(String key, DatabaseError error) {
                    if (error != null) {
                        System.err.println("There was an error saving the location to GeoFire: " + error);
                    } else {
                        System.out.println("Location saved on server successfully!");
                    }
                }
            });
        }


    }
    public void Sendlocationtofirebase(final String ADDRES) {


        ID = userR.getUid();
        datausers.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bolleaanuser u = dataSnapshot.getValue(bolleaanuser.class);
                if(u.getOnline()!=null)
                if (u.getOnline().equals(true)) {
                    final Time now = new Time();
                    now.setToNow();
                    date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    data = FirebaseDatabase.getInstance().getReference("Locationstreet").child(ID);
                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String key=dataSnapshot.getKey();
                            HashMap<Object, String> ha = new HashMap<>();
                            ha.put("day", date);
                            ha.put("time", now.hour+":"+now.minute);
                            ha.put("street", ADDRES);
                            data.setValue(ha);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
//        if (ContextCompat.checkSelfPermission(getApplicationContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//           buildGoogleapiclint();
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationReques, this);        }
    }

    private synchronized void buildGoogleapiclint() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void Camerapoistion(double latiI, double longeE) {

        CameraPosition currentPlace = new CameraPosition.Builder()
                .target(new LatLng(latiI, longeE))
                .bearing(240).tilt(45).zoom(15f).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
    }

    public void logoutuser() {
        Intent inty = new Intent(getActivity(), loginmain.class);
        inty.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(inty);
    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.app.AlertDialog.Builder(getActivity())
                        .setTitle(R.string.info)
                        .setMessage(R.string.gbsmessage)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public void Chaneeuseronline() {
        IDd = userR.getUid();
        DatabaseReference data = FirebaseDatabase.getInstance().getReference("Users").child(IDd);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                online = dataSnapshot.getValue(bolleaanuser.class);
                dat = FirebaseDatabase.getInstance().getReference("Friends");
                dat.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            KEY = dataSnapshot1.getKey();
                            dates = FirebaseDatabase.getInstance().getReference("Friends").child(KEY);
                            dates.orderByChild("id").equalTo(IDd).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataa : dataSnapshot.getChildren()) {
                                            String email = dataa.child("email").getValue().toString();
                                            if (email != null) {
                                                dataa.getRef().child("online").setValue(online.getOnline());
                                                dataa.getRef().child("online").onDisconnect().setValue(false);
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

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void sendTokenToServer() {
        final String token = SharedPrefManager.getInstance(getActivity()).getDeviceToken();
        final String email = userR.getEmail();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                params.put("email", email);
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 99: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        buildGoogleapiclint();
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationReques, this);                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    public void online(){
        IDd=userR.getUid();
        DatabaseReference data = FirebaseDatabase.getInstance().getReference("Users");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(IDd)) {
                    online=new bolleaanuser();
                    connectedRef = FirebaseDatabase.getInstance().getReference("Users").child(IDd);
                    connectedRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            connectedRef.child("online").onDisconnect().setValue(false);
                            connectedRef.child("online").setValue(true);
                            Chaneeuseronline();
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                            System.err.println("Listener was cancelled");
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void hidekeyboard(){
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }


    public void GetFriendsOnline(final Firebasecallback firebasecallback){
        String Id=userR.getUid();
        data.child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                googleMap.clear();
                lstfriends.clear();
                adpter.notifyDataSetChanged();
                for (DataSnapshot datasnap : dataSnapshot.getChildren()) {
                    if(datasnap.hasChild("privacy")) {
                        y = datasnap.getValue(GetAndSethomeFriends.class);
                        Online = y.getOnline();
                        Privacy = y.getPrivacy();
                        if(Online!=null&&Privacy!=null)
                        if (Online && Privacy) {
                            final String Id = y.getId();
                            if(Id!=null) {
                                final String useer=y.getUsername();
                                final String photto=y.getPhoto();
                                u.setUsername(useer);
                                u.setPhoto(photto);
                                u.setId(Id);
                                firebasecallback.Callback(u);

                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int r) {
        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }
        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {
            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;
            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(Color.GRAY);
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = googleMap.addPolyline(polyOptions);
            polylines.add(polyline);
        }

    }

    @Override
    public void onRoutingCancelled() {

    }


    @Override
    public void onClick(View view, int position) {
        datalocation = FirebaseDatabase.getInstance().getReference("Location").child(lstfriends.get(position).getId());
        datalocation.child("l").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Object> lis = (List<Object>) dataSnapshot.getValue();
                    if (lis.get(0) != null) {
                        lati = Double.parseDouble(lis.get(0).toString());
                    }
                    if (lis.get(1) != null) {
                        longe = Double.parseDouble(lis.get(1).toString());
                    }
                    LatLng a = new LatLng(lati, longe);
                    CameraPosition currentPlace = new CameraPosition.Builder()
                            .target(a)
                            .bearing(240).tilt(30).zoom(16f).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            currentPlace));

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


    public void SendatatoAdapter() {

        GetFriendsOnline(new Firebasecallback() {
            @Override
            public void Callback(final GetandSetFriendOnlineHome e) {

                GetandSetFriendOnlineHome t=new GetandSetFriendOnlineHome();
                t.setPhoto(e.getPhoto());
                t.setId(e.getId());
                if (t != null && !hasId(t.getPhoto())) {
                    lstfriends.add(0, t);
                    adpter.notifyDataSetChanged();
                }

            }
        });
    }


    private interface Firebasecallback {
        void Callback(GetandSetFriendOnlineHome e);
    }
    private boolean hasId(String idc){
        if(!TextUtils.isEmpty(idc)) {
            for (GetandSetFriendOnlineHome fr : lstfriends) {
                if (fr.getPhoto().equals(idc)) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    public void Recyclview(){
        recyc = v.findViewById(R.id.recyclehome);
        recyc.setHasFixedSize(true);
        adpter = new recycleviewimage(lstfriends);
        adpter.clicklisten(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyc.setLayoutManager(linearLayoutManager);
        recyc.setItemAnimator(new DefaultItemAnimator());
        recyc.setAdapter(adpter);
    }

    public void friendsmaponline (final Firebasecall fire){
        String Id=userR.getUid();

        data.child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listid.clear();
                for (final DataSnapshot datasnap : dataSnapshot.getChildren()) {
                    if(datasnap.hasChild("privacy")) {
                        y = datasnap.getValue(GetAndSethomeFriends.class);
                        Online = y.getOnline();
                        Privacy = y.getPrivacy();
                        if(Online!=null&&Privacy!=null)
                        if (Online && Privacy) {
                            final String Id = y.getId();
                            final String useer=y.getUsername();
                            final String photto=y.getPhoto();
                            datalocation = FirebaseDatabase.getInstance().getReference("Location").child(Id);
                            datalocation.child("l").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                        List<Object> lis = (List<Object>) dataSnapshot.getValue();
                                        if (lis.get(0) != null) {
                                            lati = Double.parseDouble(lis.get(0).toString());
                                        }
                                        if (lis.get(1) != null) {
                                            longe = Double.parseDouble(lis.get(1).toString());
                                        }
                                        t.setUsername(useer);
                                        t.setPhoto(photto);
                                        t.setLat(lati);
                                        t.setLon(longe);
                                        t.setId(Id);
                                        fire.Callba(t);

                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                        }

                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    private interface Firebasecall {
        void Callba(Friendsetandget o);
    }
    public void  callback (){

        friendsmaponline(new Firebasecall() {
            @Override
            public void Callba(final Friendsetandget o) {

                listid.add(o.getId());
                markerlist.get(o.getId());//get marker from list

                if(markerlist.containsKey(o.getId())) {
                    Marker marker = markerlist.get(o.getId());
                    marker.remove();
                }

                LatLng l = new LatLng(o.getLat(), o.getLon());
                IconGenerator generator = new IconGenerator(context);
                generator.setBackground(getApplicationContext().getDrawable(R.drawable.mark));
                Bitmap icon = generator.makeIcon();

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(l)
                        .title(o.getUsername())
                        .icon(BitmapDescriptorFactory.fromBitmap(icon));


                InfoWindowData info = new InfoWindowData();
                info.setImage(o.getPhoto());
                windowinfofriend customInfoWindow = new windowinfofriend(getApplicationContext());
                googleMap.setInfoWindowAdapter(customInfoWindow);

                m = googleMap.addMarker(markerOptions);
                markerlist.put(o.getId(),m);//add marker to list
                m.setTag(info);
                m.showInfoWindow();



                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        LatLng l=marker.getPosition();
                        try {
                            Locale loc = new Locale("en");
                            Geocoder geocoder = new Geocoder(getContext(),loc);
                            addresses = geocoder.getFromLocation(lati, longe, 1);
                            addres = addresses.get(0).getAddressLine(0);
                        } catch (IOException d) {
                            d.printStackTrace();
                        }

                        Snackbar.make(framehome,addres,2500).show();

                        return false;

                    }
                });
            }
        });

    }
}


