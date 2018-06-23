package gbstracking.searchplaces;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.gbstracking.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import gbstracking.Nvigation;
import gbstracking.friends.Common;
import gbstracking.friends.IGoogleApi;
import gbstracking.mainactivity.CustomWinfoView;
import gbstracking.mainactivity.itemClickListener;

import static gbstracking.mainactivity.home.latLngBounds;


/**
 * A simple {@link Fragment} subclass.
 */
public class Searchplaces extends Fragment implements itemClickListener,RoutingListener,OnMapReadyCallback, com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{


    public Searchplaces() {
        // Required empty public constructor
    }
    GoogleMap googleMap;
    IGoogleApi mService;
    Button startmove;
    Button btnBottomdirect;
    BottomSheetBehavior sheetBehavior;
    LinearLayout layoutBottomSheet;
    ImageView window;
    Context context;
    Button btnexit;
    Marker marker;
    PlaceInfo placeT;
    LocationRequest locationReques;
    GoogleApiClient mGoogleApiClient;
    FloatingActionButton btngetlocation;
    AutoCompleteTextView auto;
    PlaceAutocompleteAdapter placeAutocompleteAdapter;
    View v;
    LatLng latyy;
    LatLng latlongplace;
    double lat, lon;
    LatLng latlaang;
    List<LatLng> polinlist;
    boolean firstTime = true;
    List<Polyline> polylines;
    final int REQUEST_LOCATION_CODE =99;
    private static final int[] COLORS = new int[]{R.color.colorPrimaryDark, R.color.colorPrimary, R.color.cardview_light_background, R.color.colorAccent, R.color.primary_dark_material_light};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v=inflater.inflate(R.layout.fragment_searchplaces, container, false);
        context=this.getActivity();
        Toolbar toolbar =v.findViewById(R.id.toolbarauto);
        btnBottomdirect = v.findViewById(R.id.direc);
        layoutBottomSheet = v.findViewById(R.id.sheetdirec);
        window=v.findViewById(R.id.windoinfo);
        btnexit=v.findViewById(R.id.exit);
        polylines=new ArrayList<>();
        btngetlocation=v.findViewById(R.id.myLocationBtn);
        auto=v.findViewById(R.id.autoComp);
        polinlist=new ArrayList<>();
        mService = Common.iGoogleApi();
        startmove=v.findViewById(R.id.dirstart);
        ButtonSheet();
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        Nvigation.toggle = new ActionBarDrawerToggle(
                getActivity(), Nvigation.drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        Nvigation.drawer.addDrawerListener(Nvigation.toggle);
        Nvigation.toggle.syncState();

        Nvigation.toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable. navigat);

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


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MAP);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


        return v;
    }
    @Override
    public void onMapReady(GoogleMap googleMaps) {

        googleMap = googleMaps;


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        buildGoogleapiclint();

        googleMaps.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);


            }
        });
        userLocationFAB(240);
        window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "onClick: clicked place info");
                try {
                    if (marker.isInfoWindowShown()) {
                        marker.hideInfoWindow();
                    } else {
                        Log.d("", "onClick: place info: " + placeT.toString());
                        marker.showInfoWindow();
                        window.setVisibility(View.INVISIBLE);
                    }
                } catch (NullPointerException e) {
                    Log.e("", "onClick: NullPointerException: " + e.getMessage());
                }
            }
        });

        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleMap.clear();
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

    }
    private synchronized void buildGoogleapiclint(){
        mGoogleApiClient=new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .enableAutoManage(getActivity(),this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
        placeAutocompleteAdapter=new PlaceAutocompleteAdapter(context,mGoogleApiClient,latLngBounds,null);
        auto.setAdapter(placeAutocompleteAdapter);
        auto.setOnItemClickListener(mAutocomplete);
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
//                        result.Call(1);
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
        lat=location.getLatitude();
        lon=location.getLongitude();



        googleMap.setMyLocationEnabled(true);
        googleMap.setIndoorEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setTrafficEnabled(false);
        googleMap.setBuildingsEnabled(false);
        latyy=new LatLng(lat,lon);
        polinlist.add(latyy);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(mGoogleApiClient == null)
                        {
                            buildGoogleapiclint();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }
                }
                else
                {

                }
        }
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
        }
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onClick(View view, int position) {


    }
    public void ButtonSheet() {
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

    }
    private void hidekeyboard(){
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
    private void  userLocationFAB(final float bearing){

        btngetlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Camerapoistion(lat,lon);
            }
        });
    }
    private AdapterView.OnItemClickListener mAutocomplete=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hidekeyboard();
            btnBottomdirect.setVisibility(View.VISIBLE);
            startmove.setVisibility(View.INVISIBLE);
            final AutocompletePrediction autocompletePrediction=placeAutocompleteAdapter.getItem(i);
            final String id=autocompletePrediction.getPlaceId();
            PendingResult<PlaceBuffer> place= Places.GeoDataApi.getPlaceById(mGoogleApiClient,id);
            place.setResultCallback(updateplaceCallback);
        }
    };
    public void Camerapoistion(double latiI,double longeE){

        CameraPosition currentPlace = new CameraPosition.Builder()
                .target(new LatLng(latiI,longeE))
                .bearing(240).tilt(45).zoom(15f).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(currentPlace));
    }
    public void StartMove() {
        startmove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                marker.remove();
                Routing routing = new Routing.Builder()
                        .alternativeRoutes(true)
                        .waypoints(latyy, latlaang)
                        .build();
                routing.execute();
                float bear = getBearing(latyy,latlongplace);
                CameraPosition currentPlace = new CameraPosition.Builder()
                        .target(latyy)
                        .bearing(bear).tilt(45).zoom(18f).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        currentPlace));

            }
        });
    }
    private  float getBearing(LatLng startpostio,LatLng Endpostion){
        double lath=Math.abs(startpostio.latitude-Endpostion.latitude);
        double lngh=Math.abs(startpostio.longitude-Endpostion.longitude);
        if(startpostio.latitude<Endpostion.latitude&&startpostio.longitude<Endpostion.longitude){
            return (float)(Math.toDegrees(Math.atan(lngh/lath)));
        }
        else if(startpostio.latitude>=Endpostion.latitude&&startpostio.longitude<Endpostion.longitude){
            return (float)((90-Math.toDegrees(Math.atan(lngh/lath)))+90);
        }
        else if(startpostio.latitude>=Endpostion.latitude&&startpostio.longitude>=Endpostion.longitude){
            return (float)(Math.toDegrees(Math.atan(lngh/lath))+180);
        }
        else if(startpostio.latitude<Endpostion.latitude&&startpostio.longitude>=Endpostion.longitude){
            return (float)((90-Math.toDegrees(Math.atan(lngh/lath)))+270);
        }
        return -1;
    }
    private void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo){
        Log.d("", "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        googleMap.clear();
        googleMap.setInfoWindowAdapter(new CustomWinfoView(context));
        if(placeInfo!=null){
            try {
                String snippet = "Address " + placeInfo.getAddress() + "\n" +
                        "Phone Number " + placeInfo.getPhoneNumber() + "\n" +
                        "Name " + placeInfo.getWebsite() + "\n" +
                        "Rating " + placeInfo.getRating() + "\n";
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng)
                        .title(placeInfo.getName())
                        .snippet(snippet);
                marker=googleMap.addMarker(markerOptions);

            }catch (NullPointerException e){

            }
        }else {
            marker=googleMap.addMarker(new MarkerOptions().position(latLng));
        }

        hidekeyboard();
    }
    private ResultCallback<PlaceBuffer> updateplaceCallback=new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            if(!places.getStatus().isSuccess()){
                places.release();
                return;
            }
            final Place place=places.get(0);
            placeT=new PlaceInfo();
            try {
                placeT.setName(place.getName().toString());
                placeT.setAddress(place.getAddress().toString());
                placeT.setPhoneNumber(place.getPhoneNumber().toString());
                placeT.setId(place.getId());
                placeT.setWebsite(place.getWebsiteUri());
                placeT.setLatlong(place.getLatLng());
                placeT.setRating(place.getRating());
            }catch (NullPointerException e){
            }
            moveCamera(new LatLng(place.getViewport().getCenter().latitude,place.getViewport().getCenter().longitude),18,placeT);
            places.release();
            latlaang=placeT.getLatlong();
            latlongplace=placeT.getLatlong();
            polinlist.add(latlaang);
            window.setVisibility(View.VISIBLE);

            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            btnBottomdirect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnBottomdirect.setVisibility(View.INVISIBLE);
                    GetDirection();
                    startmove.setVisibility(View.VISIBLE);
                }
            });
            StartMove();


//           if (sheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
//
//           }
        }
    };

    public void GetDirection() {

        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.map_style));
            if (!success) {
            }
        } catch (Resources.NotFoundException e) {
        }
        float bear = getBearing(latyy, latlaang);

        Routing routing = new Routing.Builder()
                .withListener(Searchplaces.this)
                .alternativeRoutes(true)
                .waypoints(latyy, latlaang)
                .build();
        routing.execute();
        zoomRoute(googleMap,polinlist);
    }
    public void zoomRoute(GoogleMap googleMap, List<LatLng> lstLatLngRoute) {

        if (googleMap == null || lstLatLngRoute == null || lstLatLngRoute.isEmpty()) return;

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (LatLng latLngPoint : lstLatLngRoute)
            boundsBuilder.include(latLngPoint);

        int routePadding = 100;
        LatLngBounds latLngBounds = boundsBuilder.build();

        try {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, routePadding));
        } catch (Exception e) {
        }
    }

    }
