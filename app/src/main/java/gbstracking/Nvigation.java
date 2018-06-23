package gbstracking;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccountKit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.gbstracking.R;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.squareup.picasso.Picasso;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import gbstracking.Userlogin.loginmain;
import gbstracking.Warning.warnimgernecy;
import gbstracking.Warning.welcomewarning;
import gbstracking.contact.invitefriends;
import gbstracking.friends.TabsFriends;
import gbstracking.mainactivity.home;
import gbstracking.searchplaces.Searchplaces;
import gbstracking.transportiation.nearesttransportion;


public class Nvigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    CircleImageView c;
    TextView texName;
    FirebaseAuth authh;
    private Uri filePath;
    FirebaseUser user;
    private final int PICK_IMAGE_REQUEST = 71;
    private FirebaseAuth mAuth;
    NavigationView navigationView;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private int mCurrentSelectedPosition = 0;
    Fragment fr;
    public static DrawerLayout drawer;
    private Handler mHandler;
    ImageView i;
    String me="";
    FirebaseStorage storage;
    StorageReference storageReference;
    String IDd = "";
    Toolbar toolbar;
    public static ActionBarDrawerToggle toggle;
    SharedPreferences sharedPreferences;
    Boolean warn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvigation);
        drawer = findViewById(R.id.drawer_layout);
        AccountKit.initialize(getApplicationContext());
        user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        mHandler = new Handler();
         toolbar = findViewById(R.id.toolbar);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        IDd=user.getUid();
        setSupportActionBar(toolbar);



        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            home u=new home();
            u.logoutuser();
        }
       sharedPreferences=getSharedPreferences("Phone",MODE_PRIVATE);
        String phone=sharedPreferences.getString("phone","");
        if(!phone.isEmpty()){
            DatabaseReference data=FirebaseDatabase.getInstance().getReference().child("Users");
            data.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("phone").setValue(phone);
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    View headerView = navigationView.getHeaderView(0);
                    texName =  headerView.findViewById(R.id.texkName);
                    texName.setText(user.getDisplayName());

                    if(user.getPhotoUrl()!=null){
                      View headerVi = navigationView.getHeaderView(0);
                       i=headerVi.findViewById(R.id.imagecom);
                      CircleImageView c=headerView.findViewById(R.id.person_image);
                      Picasso.with(getApplicationContext())
                              .load(user.getPhotoUrl())
                              .placeholder(R.drawable.emptyprofile)
                              .into(c);
                      i.setVisibility(View.INVISIBLE);
                  }else if(user.getPhotoUrl()==null) {
                      View headerVie = navigationView.getHeaderView(0);
                      i=headerVie.findViewById(R.id.imagecom);
                      i.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                      MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                              startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
                          }
                      });
                  }

                }
            }
        };

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.flContent);
        fragment.onActivityResult(requestCode, resultCode, data);


        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            if(filePath != null)
            {
                final ProgressDialog progressDialog = new ProgressDialog(Nvigation.this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                Uri u=taskSnapshot.getDownloadUrl();


                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setPhotoUri(Uri.parse(u.toString()))
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("", "User profile updated.");
                                                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(IDd);
                                                    DatabaseReference data=databaseReference.child("photo");
                                                    data.setValue(user.getPhotoUrl().toString());
                                                    View headerView = navigationView.getHeaderView(0);
                                                    texName =  headerView.findViewById(R.id.texkName);
                                                    i=headerView.findViewById(R.id.imagecom);
                                                    c=headerView.findViewById(R.id.person_image);
                                                    Picasso.with(getApplicationContext())
                                                            .load(user.getPhotoUrl())
                                                            .into(c);
                                                    i.setVisibility(View.INVISIBLE);

                                                }
                                            }
                                        });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            }
                        });
            }
        }



    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        switch (item.getItemId()) {
            case R.id.home:
                mCurrentSelectedPosition = 0;
                toolbar.setVisibility(View.GONE);
                fr = new home();
                break;
            case R.id.addfriend:
                mCurrentSelectedPosition = 1;
                toolbar.setVisibility(View.VISIBLE);
                fr = new TabsFriends();
                break;
            case R.id.wrn:
                mCurrentSelectedPosition=2;
                toolbar.setVisibility(View.GONE);
                SharedPreferences share=getSharedPreferences("Warr",MODE_PRIVATE);
                warn=share.getBoolean("warnmessa",false);

//                if(warn){
//                        fr= new warnimgernecy();
//                    }else {
//                    fr = new welcomewarning();
//                    }
                fr = new welcomewarning();
                break;

            case R.id.transport:
                mCurrentSelectedPosition = 3;
                toolbar.setVisibility(View.GONE);
                fr = new nearesttransportion();
                break;
            case R.id.Search:
                mCurrentSelectedPosition = 4;
                toolbar.setVisibility(View.GONE);
                fr = new Searchplaces();
                break;
            case R.id.invite:
                mCurrentSelectedPosition=5;
                toolbar.setVisibility(View.VISIBLE);
                fr= new invitefriends();
                break;


            case R.id.logoutt:
                mCurrentSelectedPosition=5;
                View headerView = navigationView.getHeaderView(0);
                texName =headerView.findViewById(R.id.texkName);
                texName.setText("");
                authh.getInstance().signOut();
                AccountKit.logOut();
                startActivity(new Intent(Nvigation.this,loginmain.class));
                finish();
                break;

            default:
                mCurrentSelectedPosition = 0;

        }
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        item.setChecked(true);

        FragmentManager fragmentManager=getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flContent,fr);
        transaction.commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }





    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package","gbstracking" , null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }

}