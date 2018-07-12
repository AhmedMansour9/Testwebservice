package gbstracking.transportiation;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.RoutingListener;
import com.facebook.FacebookSdk;
import com.gbstracking.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gbstracking.CheckgbsAndNetwork;
import gbstracking.Nvigation;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */

public class nearesttransportion extends Fragment  implements AdapterView.OnItemSelectedListener,RoutingListener,OnMapReadyCallback, com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
    {
        GoogleMap googleMap;
        LocationRequest locationReques;
        Context context;
        GoogleApiClient mGoogleApiClient;
        Boolean firstTime=true;
        double latitude,longitude;
        double lati,longe;
        private static final int[] COLORS = new int[]{R.color.colorPrimaryDark, R.color.colorPrimary, R.color.cardview_light_background, R.color.colorAccent, R.color.primary_dark_material_light};
        int MY_PERMISSIONS_REQUEST_LOCATION=99;
        List<Polyline> polylines;
        ImageView btndoc,hospital,train,pharmcy,police,cofe,spinnerimg;
        BottomSheetBehavior sheetBehavior;
        LinearLayout sheet;
        RadioGroup radioGroup;
        List<String> categories;
        List<String> listreduis;
        Boolean chechfriend=false;
        GetNearbyPlacesData getNearbyPlacesData;
        RadioButton rad1,rad2;
        Spinner spinner,spinnerreduis;
        double la,lo;
       Marker marker2;
        ArrayAdapter<String> dataAdapter;
        ArrayAdapter<String> dataAd;
       final int REQUEST_LOCATION_CODE =99;
        String user="";
        int reduis;
        String id;
        double LAT,LON;
        TextView details;
        CheckgbsAndNetwork checkInfo;
        CoordinatorLayout layoutnearest;
        public nearesttransportion() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.nearesttransportion, container, false);
        btndoc=v.findViewById(R.id.doctor);
        spinnerimg=v.findViewById(R.id.imgsminner);
        train=v.findViewById(R.id.subway);
        details=v.findViewById(R.id.details);
        hospital=v.findViewById(R.id.hospital);
        pharmcy=v.findViewById(R.id.pharmcy);
        police=v.findViewById(R.id.police);
        sheet=v.findViewById(R.id.sheetplaces);
        cofe=v.findViewById(R.id.cofe);
        context=this.getContext();
        layoutnearest=v.findViewById(R.id.layoutnearest);
       categories = new ArrayList<String>();
        polylines=new ArrayList<>();
         spinner = v.findViewById(R.id.spinner);
        spinnerreduis= v.findViewById(R.id.spinnerreduis);
        checkInfo=new CheckgbsAndNetwork(getApplicationContext());
         listreduis=new ArrayList<>();
        checkLocationPermission();
        sheetBehavior = BottomSheetBehavior.from(sheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        ButtonSheet();
        moredetails();
        rad1=v.findViewById(R.id.radio1);
        rad2=v.findViewById(R.id.radio2);
        radioGroup = v.findViewById(R.id.myRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radio1) {
                    rad2.setChecked(false);
                   chechfriend=false;
                    spinner.setVisibility(View.GONE);
                    spinnerimg.setVisibility(View.GONE);
                } else if(i == R.id.radio2) {
                  rad1.setChecked(false);
                    chechfriend=true;
                    spinner.setVisibility(View.VISIBLE);
                    spinnerimg.setVisibility(View.VISIBLE);
                }

            }
        });

        Toolbar toolbar =v.findViewById(R.id.toolbatrans);
        Nvigation.toggle = new ActionBarDrawerToggle(
                getActivity(), Nvigation.drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        Nvigation.drawer.addDrawerListener(Nvigation.toggle);
        Nvigation.toggle.syncState();

        Nvigation.toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable. navigat);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
//            checkLocationPermission();

        }
        Retriveusersname();
        btntrain();
        btndoctor();
        btnhospital();
        btncafe();
        btnpharmcy();
        btnpolice();

        listreduis.add("500");
        listreduis.add("1000");
        listreduis.add("1500");
        listreduis.add("2000");
        listreduis.add("2500");
        listreduis.add("3000");
        listreduis.add("3500");
        listreduis.add("4000");
        dataAd = new ArrayAdapter<String>(getApplicationContext(), R.layout.textcolorspinner, listreduis) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setTextColor(Color.BLACK);
                return textView;
            }
        };
        dataAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerreduis.setOnItemSelectedListener(this);
        // attaching data adapter to spinner
        spinnerreduis.setAdapter(dataAd);
        spinnerreduis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reduis=Integer.parseInt(spinnerreduis.getSelectedItem().toString());

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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


            dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.textcolorspinner, categories) {
               @Override
               public View getDropDownView(int position, View convertView, ViewGroup parent) {
                   TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                   textView.setTextColor(Color.BLACK);
                   return textView;
               }
           };
           // Drop down layout style - list view with radio button
           dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           spinner.setOnItemSelectedListener(this);
           // attaching data adapter to spinner
           spinner.setAdapter(dataAdapter);


          spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                   user=spinner.getSelectedItem().toString();
                  if(user!=null) {
                      Getfriendslocation(new firebae() {
                          @Override
                          public void Call(double lati, double longe) {
                              LAT=lati;
                              LON=longe;
                          }
                      });
                  }
              }
              @Override
              public void onNothingSelected(AdapterView<?> adapterView) {

              }
          });


        return v;
    }
        public void snackbarinternet(){
            Snackbar.make(layoutnearest,getResources().getString(R.string.Nointernet),1500).show();

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


        public void moredetails(){
            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            });
    }

    public void btntrain(){
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                 if(chechfriend&&user!=null){
                     la = LAT;
                     lo = LON;
                 }else {

                     la=latitude;
                     lo=longitude;
                 }
                Object dataTransfer[] = new Object[2];
                getNearbyPlacesData = new GetNearbyPlacesData();
                googleMap.clear();
                if(chechfriend&&user!=null){
                    LatLng laty=new LatLng(LAT,LON);
                    marker2 = googleMap.addMarker(new MarkerOptions().position(laty).title(user).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                   Camerapoistion(LAT,LON);
                    marker2.showInfoWindow();
                }
                String school ="subway_station";
                String urle = getUrl(la,lo, school,reduis);
                Camerapoistion(la,lo);
                dataTransfer[0] = googleMap;
                dataTransfer[1] = urle;

                getNearbyPlacesData.execute(dataTransfer);

            }
        });
    }
    public void btndoctor(){
        btndoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInfo.isNetworkAvailable(getApplicationContext())) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    if (chechfriend && user != null) {
                        la = LAT;
                        lo = LON;
                    } else {
                        la = latitude;
                        lo = longitude;
                    }
                    Object dataTransfer[] = new Object[2];
                    getNearbyPlacesData = new GetNearbyPlacesData();
                    googleMap.clear();
                    if (chechfriend && user != null) {
                        Camerapoistion(LAT,LON);
                        LatLng laty = new LatLng(LAT, LON);
                        marker2 = googleMap.addMarker(new MarkerOptions().position(laty).title(user).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                        marker2.showInfoWindow();
                    }
                    String doctor = "doctor";
                    String urle = getUrl(la, lo, doctor, reduis);
                    Camerapoistion(la,lo);
                    dataTransfer[0] = googleMap;
                    dataTransfer[1] = urle;

                    getNearbyPlacesData.execute(dataTransfer);
                }else {
                    snackbarinternet();
                }

            }
        });
    }
   public void btnhospital(){
       hospital.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(checkInfo.isNetworkAvailable(getApplicationContext())) {
                   sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                   if (chechfriend && user != null) {
                       la = LAT;
                       lo = LON;
                   } else {
                       la = latitude;
                       lo = longitude;
                   }
                   Object dataTransfer[] = new Object[2];
                   getNearbyPlacesData = new GetNearbyPlacesData();
                   googleMap.clear();
                   if (chechfriend && user != null) {
                       LatLng laty = new LatLng(LAT, LON);
                       Camerapoistion(LAT,LON);
                       marker2 = googleMap.addMarker(new MarkerOptions().position(laty).title(user).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                       marker2.showInfoWindow();
                   }
                   String hospital = "hospital";
                   String url = getUrl(la, lo, hospital, reduis);
                   Camerapoistion(la,lo);
                   dataTransfer[0] = googleMap;
                   dataTransfer[1] = url;
                   getNearbyPlacesData.execute(dataTransfer);
               }else {
                   snackbarinternet();
               }
           }
       });
   }
   public void btnpharmcy(){
       pharmcy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(checkInfo.isNetworkAvailable(getApplicationContext())) {
                   sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                   if (chechfriend && user != null) {
                       la = LAT;
                       lo = LON;
                   } else {
                       la = latitude;
                       lo = longitude;
                   }
                   Object dataTransfer[] = new Object[2];
                   getNearbyPlacesData = new GetNearbyPlacesData();
                   googleMap.clear();
                   if (chechfriend && user != null) {
                       LatLng laty = new LatLng(LAT, LON);
                       Camerapoistion(LAT,LON);
                       marker2 = googleMap.addMarker(new MarkerOptions().position(laty).title(user).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                       marker2.showInfoWindow();
                   }
                   String hospital = "pharmcy";
                   String url = getUrl(la, lo, hospital, reduis);
                   Camerapoistion(la,lo);
                   dataTransfer[0] = googleMap;
                   dataTransfer[1] = url;
                   getNearbyPlacesData.execute(dataTransfer);
               }else{
                   snackbarinternet();
               }
           }
       });
   }
   public void btnpolice(){
       police.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(checkInfo.isNetworkAvailable(getApplicationContext())) {
                   sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                   if (chechfriend && user != null) {
                       la = LAT;
                       lo = LON;
                   } else {
                       la = latitude;
                       lo = longitude;
                   }
                   Object dataTransfer[] = new Object[2];
                   getNearbyPlacesData = new GetNearbyPlacesData();
                   googleMap.clear();
                   if (chechfriend && user != null) {
                       LatLng laty = new LatLng(LAT, LON);
                       Camerapoistion(LAT,LON);
                       marker2 = googleMap.addMarker(new MarkerOptions().position(laty).title(user).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                       marker2.showInfoWindow();
                   }
                   String hospital = "police";
                   String url = getUrl(la, lo, hospital, reduis);
                   Camerapoistion(la,lo);
                   dataTransfer[0] = googleMap;
                   dataTransfer[1] = url;
                   getNearbyPlacesData.execute(dataTransfer);
               }else {
                   snackbarinternet();
               }
           }
       });

   }
   public void btncafe(){
       cofe.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(checkInfo.isNetworkAvailable(getApplicationContext())) {
                   sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                   if (chechfriend && user != null) {
                       la = LAT;
                       lo = LON;
                   } else {
                       la = latitude;
                       lo = longitude;
                   }
                   Object dataTransfer[] = new Object[2];
                   getNearbyPlacesData = new GetNearbyPlacesData();
                   googleMap.clear();
                   if (chechfriend && user != null) {
                       LatLng laty = new LatLng(LAT, LON);
                       Camerapoistion(LAT,LON);
                       marker2 = googleMap.addMarker(new MarkerOptions().position(laty).title(user).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                       marker2.showInfoWindow();
                   }
                   String hospital = "cafe";
                   String url = getUrl(la, lo, hospital, reduis);
                   Camerapoistion(la,lo);
                   dataTransfer[0] = googleMap;
                   dataTransfer[1] = url;
                   getNearbyPlacesData.execute(dataTransfer);
               }else {
                   snackbarinternet();
               }
           }
       });
   }
    public void ButtonSheet(){
            sheetBehavior = BottomSheetBehavior.from(sheet);
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


            });


        }
    public void Retriveusersname(){
          DatabaseReference data= FirebaseDatabase.getInstance().getReference().child("Friends")
                  .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
          data.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                  for(DataSnapshot data:dataSnapshot.getChildren()){
                      String name=data.child("username").getValue().toString();
                      categories.add(name);
                      dataAdapter.notifyDataSetChanged();
                  }

              }

              @Override
              public void onCancelled(DatabaseError databaseError) {

              }
          });

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

                    }
                    return;
                }

            }
        }

        public void Getfriendslocation(final firebae f){

        DatabaseReference datas=FirebaseDatabase.getInstance().getReference().child("Users");
        Query query=datas.orderByChild("username").equalTo(user);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                     id=data.child("id").getValue().toString();
                }

                DatabaseReference datas=FirebaseDatabase.getInstance().getReference().child("Location");
                datas.child(id).child("l").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Object> lis = (List<Object>) dataSnapshot.getValue();
                        if (lis.get(0) != null) {
                            lati = Double.parseDouble(lis.get(0).toString());
                        }
                        if (lis.get(1) != null) {
                            longe = Double.parseDouble(lis.get(1).toString());
                        }
                        f.Call(lati,longe);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
//
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
     public interface firebae{
        void Call(double lati,double longe);
    }

        //spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



        @Override
        public void onMapReady(GoogleMap googleMaps) {
            googleMap = googleMaps;
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            buildGoogleapiclint();
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
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
            latitude = location.getLatitude();
            longitude=location.getLongitude();
            googleMap.setMyLocationEnabled(true);
            googleMap.setIndoorEnabled(false);
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.setTrafficEnabled(false);
            googleMap.setBuildingsEnabled(false);

        }
        @Override
        public void onRoutingFailure(RouteException e) {

        }

        @Override
        public void onRoutingStart() {
        }

        @Override
        public void onRoutingSuccess(ArrayList<Route> route, int y) {
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

        private String getUrl(double latitude , double longitude , String nearbyPlace,int reduis)
        {

            StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            googlePlaceUrl.append("location="+latitude+","+longitude);
            googlePlaceUrl.append("&radius="+reduis);
            googlePlaceUrl.append("&type="+nearbyPlace);
            googlePlaceUrl.append("&sensor=true");
            googlePlaceUrl.append("&key="+"AIzaSyA8-QREWpUiQP3UuUBsH6umO6Xu5BuIyQw");

            Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

            return googlePlaceUrl.toString();
        }

        public void Camerapoistion(double latiI,double longeE){

            CameraPosition currentPlace = new CameraPosition.Builder()
                    .target(new LatLng(latiI,longeE))
                    .bearing(240).tilt(45).zoom(13f).build();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
        }
        private synchronized void buildGoogleapiclint(){
            mGoogleApiClient=new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }

    }
