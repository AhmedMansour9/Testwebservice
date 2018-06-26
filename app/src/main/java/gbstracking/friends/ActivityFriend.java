package gbstracking.friends;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.directions.route.AbstractRouting;
import com.directions.route.Routing;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.MapStyleOptions;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;

import com.gbstracking.R;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
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

import de.hdodenhof.circleimageview.CircleImageView;
import gbstracking.GetandSetFriendOnlineHome;
import gbstracking.MyVolley;
import retrofit2.Call;

import retrofit2.Callback;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ActivityFriend extends FragmentActivity implements RoutingListener,OnMapReadyCallback, com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    double lon, lat;
    ValueEventListener authh;
    private GoogleMap googleMap;
    ProgressDialog progressDialog;
    GoogleApiClient mGoogleApiClient;
    Location lastlocation;
    LocationRequest locationReques;
    List<LatLng> listlatlong;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String email;
    ImageView btnSen;
    EditText getmessage;
    public String massege;
    Handler hn;
    Dialog dialog;
    String id;
    DatabaseReference databaseRefere;
    double lati;
    double longe;
    Marker marker2;
    TextView textstreet;
    DatabaseReference data;
    TextView texttime;
    TextView textday;
    List<Address> addresses;
    TextView textdistance;
    TextView textdistancewalk;
    List<Polyline> polylines;
    ImageView btndistance;
    ImageView draw;
    LatLng laty;
    TextView texttimeCar;
    PolylineOptions polyOptions, blacklineoption;
    private static final int[] COLORS = new int[]{R.color.colorPrimaryDark, R.color.colorPrimary, R.color.cardview_light_background, R.color.colorAccent, R.color.primary_dark_material_light};
    Polyline green, black;
    Marker marker;
    LatLng latyy;
    int index, next;
    public LatLng starrtpoistion, endpoistion;
    List<LatLng> polinlist;
    private float v;
    public double lattt, lngg;
    IGoogleApi mService;
    CoordinatorLayout Scrollfrie;
    String username;
    String date;
    DatabaseReference datausers;
    DatabaseReference databaseReference;
    ImageView imagefullscreen, Rot90, Rot180;
    FloatingActionButton FAB;
    GeoDataClient mGeoDataClient;
    public boolean firstTime = true;
    FloatingActionButton floatbtn;
    String ID;
    String addres;
    DatabaseReference databaseRefer;
    ImageView Draw2;
    ImageView phone;
    final int REQUEST_LOCATION_CODE = 99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityfriend);
        polylines = new ArrayList<>();
        listlatlong = new ArrayList<>();
        mGeoDataClient = Places.getGeoDataClient(this, null);
        phone = findViewById(R.id.phonecall);
        Draw2 = findViewById(R.id.DRAW);
        Rot90 = findViewById(R.id.rot90);
        Rot180 = findViewById(R.id.rot180);
        floatbtn = findViewById(R.id.myLocationButton2);
        imagefullscreen = findViewById(R.id.fullscreen);
        FAB = findViewById(R.id.myLocationButton);
        databaseRefere = FirebaseDatabase.getInstance().getReference("Location");
        databaseReference = FirebaseDatabase.getInstance().getReference("Friends");
        datausers = FirebaseDatabase.getInstance().getReference("Users");
        Scrollfrie = findViewById(R.id.Scrollfriend);
        polinlist = new ArrayList<>();
        texttimeCar = findViewById(R.id.textdistanceCar);
        draw = findViewById(R.id.draw);
        btndistance = findViewById(R.id.distanceCar);

        texttime = findViewById(R.id.edittime);
        textday = findViewById(R.id.editday);
        textdistance = findViewById(R.id.textdistanceCar);
        textdistancewalk = findViewById(R.id.textDistancWalk);
        mService = Common.iGoogleApi();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        textstreet = findViewById(R.id.idstreet);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getintentData();
        CheckAllowUser();
        getstreetlocation();
        SendingMassege();
        Drawline();
        getDistanceCar();
        CallPhone();


        getFriendlocation(new Firebasecallback() {
            @Override
            public void Callback(final GetandSetFriendOnlineHome e) {
                lati = e.getLat();
                longe = e.getLon();

            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMaps) {
        googleMap = googleMaps;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        buildGoogleapiclint();

        googleMaps.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                GoneAllvisabilty();
                imagefullscreen.setVisibility(View.VISIBLE);
                FAB.setVisibility(View.VISIBLE);
                floatbtn.setVisibility(View.VISIBLE);
                Rot90.setVisibility(View.VISIBLE);
                Rot180.setVisibility(View.VISIBLE);
            }
        });
        Rot90.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraPosition currentPlace = new CameraPosition.Builder()
                        .target(new LatLng(lastlocation.getLatitude(), lastlocation.getLongitude()))
                        .bearing(240).tilt(30).zoom(17f).build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));

            }
        });

        Rot180.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraPosition currentPlace = new CameraPosition.Builder()
                        .target(new LatLng(lastlocation.getLatitude(), lastlocation.getLongitude()))
                        .bearing(240).tilt(90).zoom(18f).build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));

            }
        });
        Draw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoneAllvisabilty();
                if(latyy!=null||laty!=null) {
                    try {
                        boolean success = googleMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        getApplicationContext(), R.raw.map_style));
                        if (!success) {
                        }
                    } catch (Resources.NotFoundException e) {
                    }
                    float bear = getBearing(latyy, laty);

                    CameraPosition currentPlace = new CameraPosition.Builder()
                            .target(latyy)
                            .bearing(bear).tilt(30).zoom(16f).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            currentPlace));
                    Routing routing = new Routing.Builder()
                            .withListener(ActivityFriend.this)
                            .alternativeRoutes(true)
                            .waypoints(latyy, laty)
                            .build();
                    routing.execute();

                }
            }
        });

        getFriendlocation(new Firebasecallback() {
            @Override
            public void Callback(final GetandSetFriendOnlineHome e) {
                if (marker2 != null) {
                    marker2.remove();
                }

                lati = e.getLat();
                longe = e.getLon();
                try {
                    Locale loc = new Locale("en");
                    Geocoder geocoder = new Geocoder(ActivityFriend.this, loc);
                    addresses = geocoder.getFromLocation(lati, longe, 1);
                    addres = addresses.get(0).getAddressLine(0);
                } catch (IOException d) {
                    d.printStackTrace();
                }
                laty = new LatLng(lati, longe);
                marker2 = googleMap.addMarker(new MarkerOptions().position(laty).title(username).snippet(addres).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                marker2.showInfoWindow();
                if (firstTime) {
                    firstTime = false;
                    Camerapoistion(lati, longe);
                }

            }
        });


        userLocationFAB(240);
        Getroutedirection();


    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationReques = new LocationRequest();
//        locationReques.setSmallestDisplacement(100);
        locationReques.setInterval(30 * 1000);
        locationReques.setFastestInterval(30 * 1000);

        locationReques.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationReques, this);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationReques);


        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(ActivityFriend.this,
                                REQUEST_LOCATION_CODE);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        buildGoogleapiclint();
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        googleMap.setMyLocationEnabled(true);
                        break;
                    case Activity.RESULT_CANCELED:

                        break;
                    default:
                        break;
                }
                break;
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(final Location location) {

        try {
            Locale loc = new Locale("en");
            Geocoder geocoder = new Geocoder(ActivityFriend.this, loc);
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String addres = addresses.get(0).getAddressLine(0);
            Sendlocationtofirebase(addres);
        } catch (IOException e) {
            e.printStackTrace();
        }

        latyy = new LatLng(location.getLatitude(), location.getLongitude());
        this.lastlocation = location;
//        polinlist.add(latyy);

        googleMap.setIndoorEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setTrafficEnabled(false);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings();

        //        zoomRoute(googleMap,polinlist);

        googleMap.getUiSettings();
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        googleMap.setMyLocationEnabled(true);

        lat = location.getLatitude();
        lon = location.getLongitude();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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

    private synchronized void buildGoogleapiclint() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    protected void onStop() {
        super.onStop();
        textdistance.setText("");
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        finish();
    }

    private interface Firebasecallback {
        void Callback(GetandSetFriendOnlineHome e);
    }

    public void getFriendlocation(final Firebasecallback firebasecallback) {
        databaseRefere.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(id)) {
                    authh = databaseRefere.child(id).child("l").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                List<Object> lis = (List<Object>) dataSnapshot.getValue();
                                if (lis.get(0) != null) {
                                    lati = Double.parseDouble(lis.get(0).toString());
                                }
                                if (lis.get(1) != null) {
                                    longe = Double.parseDouble(lis.get(1).toString());
                                }
                                GetandSetFriendOnlineHome u = new GetandSetFriendOnlineHome();
                                u.setLat(lati);
                                u.setLon(longe);
                                firebasecallback.Callback(u);

                            } else if (!dataSnapshot.exists()) {
                            }
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

    public void getstreetlocation() {
        databaseRefer = FirebaseDatabase.getInstance().getReference("Locationstreet");
        databaseRefer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(id)) {
                    databaseRefer.child(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                HashMap<Object, String> hash = (HashMap<Object, String>) dataSnapshot.getValue();
                                texttime.setText(hash.get("time"));
                                textday.setText(hash.get("day"));
                                textstreet.setText(hash.get("street"));
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

    private float getBearing(LatLng startpostio, LatLng Endpostion) {
        double lath = Math.abs(startpostio.latitude - Endpostion.latitude);
        double lngh = Math.abs(startpostio.longitude - Endpostion.longitude);
        if (startpostio.latitude < Endpostion.latitude && startpostio.longitude < Endpostion.longitude) {
            return (float) (Math.toDegrees(Math.atan(lngh / lath)));
        } else if (startpostio.latitude >= Endpostion.latitude && startpostio.longitude < Endpostion.longitude) {
            return (float) ((90 - Math.toDegrees(Math.atan(lngh / lath))) + 90);
        } else if (startpostio.latitude >= Endpostion.latitude && startpostio.longitude >= Endpostion.longitude) {
            return (float) (Math.toDegrees(Math.atan(lngh / lath)) + 180);
        } else if (startpostio.latitude < Endpostion.latitude && startpostio.longitude >= Endpostion.longitude) {
            return (float) ((90 - Math.toDegrees(Math.atan(lngh / lath))) + 270);
        }
        return -1;
    }

    private List decodePoly(String encoded) {
        List poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public void AllowUser() {
        Scrollfrie.setVisibility(View.INVISIBLE);
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(ActivityFriend.this);
        // Setting Dialog Title
        alertDialog.setTitle("Sorry");
        // Setting Dialog Message
        alertDialog.setMessage(username + "  Turn off locatiohn social"+"\n"+"Do You Want To Request him/her To Open social location ?");
        // On pressing Settings button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int which) {
               dialog.cancel();
                Sendlocationshare();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();

    }

    public void CheckAllowUser() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Friends");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String ID = user.getUid();
        Query q = databaseReference.child(ID).orderByChild("id").equalTo(id);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.hasChild("privacy")) {
                        bolleaanuser b = dataSnapshot1.getValue(bolleaanuser.class);
                        Log.e("ee", "onDataChange: "+b );
                        Boolean t = b.getPrivacy();
                        if (t) {
                            Scrollfrie.setVisibility(View.VISIBLE);
                        } else {
                            AllowUser();
                        }
                    } else {
                        AllowUser();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getintentData() {
        Intent i = getIntent();
        email = i.getStringExtra("Email2");
        String photo = i.getStringExtra("Photo2");
        id = i.getStringExtra("id");
        username = i.getStringExtra("username");
        CircleImageView c = findViewById(R.id.ImaGeFriend);
        Picasso.with(getApplicationContext())
                .load(photo)
                .placeholder(R.drawable.emptyprofile)
                .into(c);
        TextView t = findViewById(R.id.textemail);
        if (!username.isEmpty()) {
            t.setText(username);
        } else {
            t.setText(email);
        }
    }

    public void SendingMassege() {
        btnSen = findViewById(R.id.messag);
        btnSen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(ActivityFriend.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.messagetodevice);

                getmessage = dialog.findViewById(R.id.Messagete);
                massege = getmessage.getText().toString();
                Button btn = dialog.findViewById(R.id.btnSend);


                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        massege = getmessage.getText().toString();
                        if (!massege.isEmpty()) {
                            progressDialog = new ProgressDialog(ActivityFriend.this);
                            progressDialog.setMessage("Sending ...");
                            progressDialog.show();
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://zamaleksongs.000webhostapp.com/pushem.php",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            progressDialog.dismiss();
                                            dialog.cancel();
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
                                    massege = getmessage.getText().toString();
                                    params.put("message", massege);
                                    mAuth = FirebaseAuth.getInstance();
                                    user = mAuth.getCurrentUser();
                                    params.put("email", email);
                                    return params;
                                }
                            };

                            MyVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

                        } else if (massege.isEmpty() || massege.length() > 5) {
                            getmessage.setError("Massege Should Contain At least 5 letter");
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    public void Drawline() {
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetDirection();
            }
        });
    }

    public void getDistanceCar() {
        btndistance.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                DistanceAndtimeCar();
            }
        });
    }


    public void GoneAllvisabilty() {
        RelativeLayout relativeLayout = findViewById(R.id.undermap);
        relativeLayout.setVisibility(View.INVISIBLE);

        RelativeLayout f = findViewById(R.id.framemap);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                       /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
               /*height*/ ViewGroup.LayoutParams.MATCH_PARENT
        );
        f.setLayoutParams(param);

    }

    private void userLocationFAB(final float bearing) {

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Camerapoistion(lati, longe);
            }
        });
    }



    public void Camerapoistion(double latiI, double longeE) {
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        CameraPosition currentPlace = new CameraPosition.Builder()
                .target(new LatLng(latiI, longeE))
                .bearing(240).tilt(30).zoom(15f).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        if (e != null) {
        } else {
        }
    }

    @Override
    public void onRoutingStart() {
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int l) {
        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }
        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i < route.size(); i++) {
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

    public void Getroutedirection() {
        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    boolean success = googleMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    getApplicationContext(), R.raw.map_style));
                    if (!success) {
                    }
                } catch (Resources.NotFoundException e) {
                }
                float bear = getBearing(latyy, laty);

                CameraPosition currentPlace = new CameraPosition.Builder()
                        .target(latyy)
                        .bearing(bear).tilt(30).zoom(16f).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        currentPlace));

//                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));


                Routing routing = new Routing.Builder()
                        .travelMode(AbstractRouting.TravelMode.DRIVING)
                        .withListener(ActivityFriend.this)
                        .alternativeRoutes(true)
                        .waypoints(latyy, laty)
                        .build();
                routing.execute();
            }
        });
    }

    public void DistanceAndtimeCar() {

        String url = "https://maps.googleapis.com/maps/api/directions/json?" + "mode=driving&" + "transit_routing_preference=less_driving&"
                + "origin=" + lat + "," + lon + "&destination=" + lati + "," + longe + "&key=" + "AIzaSyA8-QREWpUiQP3UuUBsH6umO6Xu5BuIyQw";
        Log.d("URL", url);
        mService.getPath(url).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("routes");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject route = jsonArray.getJSONObject(i);
                        JSONArray json = route.getJSONArray("legs");
                        JSONObject jsonObject1 = json.getJSONObject(0);
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("distance");
                        JSONObject jsonObject3 = jsonObject1.getJSONObject("duration");
                        textdistance.setText(jsonObject2.getString("text") + " " + jsonObject3.getString("text"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });

        String urle = "https://maps.googleapis.com/maps/api/directions/json?" + "mode=walking&" + "transit_routing_preference=less_driving&"
                + "origin=" + lat + "," + lon + "&destination=" + lati + "," + longe + "&key=" + "AIzaSyA8-QREWpUiQP3UuUBsH6umO6Xu5BuIyQw";
        Log.d("URL", urle);
        mService.getPath(urle).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("routes");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject route = jsonArray.getJSONObject(i);
                        JSONArray json = route.getJSONArray("legs");
                        JSONObject jsonObject1 = json.getJSONObject(0);
                        JSONObject jsonObject3 = jsonObject1.getJSONObject("duration");
                        textdistancewalk.setText(jsonObject3.getString("text"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });

    }

    public void GetDirection() {
        GoneAllvisabilty();
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));
            if (!success) {
            }
        } catch (Resources.NotFoundException e) {
        }
        try {
            String url = "https://maps.googleapis.com/maps/api/directions/json?" + "mode=driving&" + "transit_routing_preference=less_driving&"
                    + "origin=" + lat + "," + lon + "&destination=" + lati + "," + longe + "&key=" + "AIzaSyA8-QREWpUiQP3UuUBsH6umO6Xu5BuIyQw";
            Log.d("URL", url);
            mService.getPath(url).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("routes");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject route = jsonArray.getJSONObject(i);
                            JSONObject pouly = route.getJSONObject("overview_polyline");
                            String poly = pouly.getString("points");
                            polinlist = decodePoly(poly);
                            if (polinlist == null) {
                                return;
                            } else {
                                break;
                            }
                        }
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (LatLng la : polinlist)
                            builder.include(la);
                        LatLngBounds bui = builder.build();
                        CameraUpdate comera = CameraUpdateFactory.newLatLngBounds(bui, 2);
                        googleMap.animateCamera(comera);
                        polyOptions = new PolylineOptions();
                        polyOptions.color(COLORS[1]);
                        polyOptions.width(5 * 3);
                        polyOptions.startCap(new SquareCap());
                        polyOptions.endCap(new SquareCap());
                        polyOptions.jointType(JointType.ROUND);
                        polyOptions.addAll(polinlist);
                        green = googleMap.addPolyline(polyOptions);
                        blacklineoption = new PolylineOptions();
                        blacklineoption.color(Color.GRAY);
                        blacklineoption.width(5);
                        blacklineoption.startCap(new SquareCap());
                        blacklineoption.endCap(new SquareCap());
                        blacklineoption.jointType(JointType.ROUND);
                        blacklineoption.addAll(polinlist);
                        black = googleMap.addPolyline(blacklineoption);
//                        googleMap.addMarker(new MarkerOptions().position(polinlist.get(polinlist.size() - 1)));
                        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
                        valueAnimator.setDuration(2000);
                        valueAnimator.setInterpolator(new LinearInterpolator());
                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                List<LatLng> point = green.getPoints();
                                int precentvalue = (int) valueAnimator.getAnimatedValue();
                                int size = point.size();
                                int newpoints = (int) (size * (precentvalue / 100.0f));
                                List<LatLng> p = point.subList(0, newpoints);
                                black.setPoints(p);
                            }
                        });
                        valueAnimator.start();

                        marker = googleMap.addMarker(new MarkerOptions().position(latyy)
                                .flat(true)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.texticonmap)));
                        hn = new Handler();
                        index = -1;
                        next = 1;
                        hn.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (index < polinlist.size() - 1) {
                                    index++;
                                    next = index + 1;
                                }
                                if (index < polinlist.size() - 1) {
                                    starrtpoistion = polinlist.get(index);
                                    endpoistion = polinlist.get(next);
                                }
                                ValueAnimator valueAnimat = ValueAnimator.ofFloat(0, 1);
                                valueAnimat.setDuration(3000);
                                valueAnimat.setInterpolator(new LinearInterpolator());
                                valueAnimat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        v = valueAnimator.getAnimatedFraction();
                                        lngg = v * endpoistion.longitude + (1 - v)
                                                * starrtpoistion.longitude;
                                        lattt = v * endpoistion.latitude + (1 - v)
                                                * starrtpoistion.latitude;
                                        LatLng l = new LatLng(lattt, lngg);
                                        marker.setPosition(l);
                                        marker.setAnchor(0.5f, 0.5f);
                                        marker.setRotation(getBearing(starrtpoistion, l));
                                        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                                .target(l)
                                                .bearing(240)
                                                .tilt(30)
                                                .zoom(15.5f)
                                                .build()
                                        ));
                                    }
                                });
                                valueAnimat.start();
                                hn.postDelayed(this, 3000);
                            }
                        }, 3000);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Sendlocationtofirebase(final String ADDRES) {


        ID = user.getUid();
        datausers.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bolleaanuser u = dataSnapshot.getValue(bolleaanuser.class);
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

    public void CallPhone() {

        if (id != null) {
            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("phone")){
                                String phonn=dataSnapshot.child("phone").getValue(String.class);
                                Intent callIntent = new Intent(Intent.ACTION_CALL);

                                callIntent.setData(Uri.parse("tel:"+phonn));
                                if (ActivityCompat.checkSelfPermission(ActivityFriend.this,
                                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    return;
                                }
                                startActivity(callIntent);
                            }else {
                                Toast.makeText(ActivityFriend.this, "That's Number is Unavailable Now", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            });
        }
    }
    public void Sendlocationshare() {
        progressDialog = new ProgressDialog(ActivityFriend.this);

        progressDialog.setMessage("Sending ...");

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://zamaleksongs.000webhostapp.com/pushem.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> para = new HashMap<>();
                if (user.getDisplayName() != null) {
                    para.put("title", user.getDisplayName());
                } else {
                    para.put("title", user.getEmail());
                }
                para.put("message",getResources().getString(R.string.locationshre));
                para.put("email", email);
                return para;
            }
        };
        MyVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }



}