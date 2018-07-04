package gbstracking.Userlogin;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.gbstracking.R;

import gbstracking.friends.ActivityFriend;
import gbstracking.verfeyphone.verfieymobile;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Arrays;

import gbstracking.CheckgbsAndNetwork;
import gbstracking.Nvigation;

import static com.google.android.gms.auth.api.signin.GoogleSignIn.getClient;

/**
 * Created by HP on 02/03/2018.
 */

public class loginmain extends AppCompatActivity {

    Boolean checke=false;
    private SharedPreferences sharelod;
    private SharedPreferences.Editor shareeditor;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private Boolean saveusername;
    EditText loguser;
    SharedPreferences.Editor editorr;
    EditText logpass;
    ImageView img;
   private ProgressBar progressBar;
    String firstName;
    FirebaseUser user;
    String passwooord;
    Dialog dialog;
   CheckBox check;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseAuth mAuth;
    SharedPreferences sharee;
    public FirebaseAuth auth;
    String emaaail;
    GoogleSignInOptions googleSignInOptions;
    DatabaseReference currnetuser;
    CheckgbsAndNetwork checkInfo;
    public static final String TAG = "MainActivity";
    // Request sing in code. Could be anything as you required.
    public static final int RequestSignInCode = 7;
   CallbackManager mCallbackManager;
    String username;
    public GoogleApiClient googleApiClient;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkInfo=new CheckgbsAndNetwork(loginmain.this);
        setContentView(R.layout.activitylogin);

        loguser = findViewById(R.id.loginname);
        check = findViewById(R.id.checkbox);
        logpass = findViewById(R.id.loginpassword);
        progressBar=findViewById(R.id.progressBarlogin);

        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(loginmain.this, verfieymobile.class));
                    finish();
                }
            }
        };

        CheckedBox();
        GoogleSignOpition();
        SigninGoogleAndgetprofile();
        FaceBooklogin();
        ForgetPassword();
        textSignuptoRegister();
        Fonttybe();
        CheckisUserSavehisinformation();
        LoginHome();
    }



    //ValidaTeEmail
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void sendResetPasswordEmail() {
        mAuth.sendPasswordResetEmail(emaaail)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(loginmain.this,
                                    getResources().getString(R.string.reseet)
                                            + emaaail,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if(!task.isSuccessful()){
                            Toast.makeText(loginmain.this,
                                    getResources().getString(R.string.invalidemaail),
                                    Toast.LENGTH_SHORT).show();
                        }}
                    }
                });
    }
    //GoogleAuthintication
    // Sign In function Starts From Here.
    public void UserSignInMethod(){
        // Passing Google Api Client into Intent.
        if (checkInfo.isNetworkAvailable(loginmain.this)) {
            Intent AuthIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(AuthIntent, RequestSignInCode);

        }else {
            checkInfo.ShowNetworkdialoge();
        }
    }
    public void FirebaseUserAuth(GoogleSignInAccount googleSignInAccount) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(loginmain.this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task AuthResultTask) {
                        if (AuthResultTask.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            currnetuser= FirebaseDatabase.getInstance().getReference();

                            final String useer=user.getDisplayName();
                            final String emaail=user.getEmail();
                            final String id=user.getUid();
                            Uri photo=user.getPhotoUrl();
                            final String phtotooo=String.valueOf(photo);
                            UserloginMain a=new UserloginMain(useer,emaail,id,phtotooo);
                            currnetuser.child("Users").child(id).setValue(a);
                            Intent intent=new Intent(loginmain.this,Nvigation.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();



                        } else {
                            Toast.makeText(loginmain.this, "Turn on Internet", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
         @Override
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
             super.onActivityResult(requestCode, resultCode, data);
             mCallbackManager.onActivityResult(requestCode, resultCode, data);
           if (requestCode == RequestSignInCode) {
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (googleSignInResult.isSuccess()) {
                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
                FirebaseUserAuth(googleSignInAccount);
            }
        }
    }
    //KeyBoard
    public  void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(new View(this).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    //Authintication FaceeBook
    public void FaceBooklogin() {
        ImageView btn_fb_login = findViewById(R.id.img_face);
        btn_fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInfo.isNetworkAvailable(loginmain.this)) {
                    LoginManager.getInstance().logInWithReadPermissions(loginmain.this, Arrays.asList("email", "public_profile"));
                    LoginManager.getInstance().registerCallback(mCallbackManager,
                            new FacebookCallback<LoginResult>() {
                                @Override
                                public void onSuccess(LoginResult loginResult) {
                                    Log.d("Success", "Login");
                                    handleFacebookAccessToken(loginResult.getAccessToken());
                                }

                                @Override
                                public void onCancel() {
//                                Toast.makeText(loginmain.this, "Login Cancel", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onError(FacebookException exception) {
                                    Toast.makeText(loginmain.this, "Turn on Internet", Toast.LENGTH_LONG).show();
                                }
                            });
                }else {
                    checkInfo.ShowNetworkdialoge();
                }
            }
        });
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
       progressBar.setVisibility(View.VISIBLE);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            progressBar.setVisibility(View.GONE);
                            FirebaseUser user = mAuth.getCurrentUser();
                            currnetuser= FirebaseDatabase.getInstance().getReference();
                            final String useer=user.getDisplayName();
                            final String emaail=user.getEmail();
                            final String id=user.getUid();
                            Uri photo=user.getPhotoUrl();
                            final String phtotooo=String.valueOf(photo);

                            UserloginMain a=new UserloginMain(useer,emaail,id,phtotooo);
                            currnetuser.child("Users").child(id).setValue(a);
                            Intent intent=new Intent(loginmain.this,verfieymobile.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(loginmain.this, getResources().getString(R.string.alreadyuser),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void GoogleSignOpition(){
            googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            googleApiClient = new GoogleApiClient.Builder(loginmain.this)
                    .enableAutoManage(loginmain.this, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        }
                    } /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                    .build();

    }

  public void CheckedBox(){
      sharelod = getSharedPreferences("SHARE", MODE_PRIVATE);
      shareeditor = sharelod.edit();
      shareeditor.apply();
      checke = sharelod.getBoolean("test", false);
      if (checke==true){
          Intent intent=new Intent(loginmain.this,verfieymobile.class);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
          startActivity(intent);
      }
  }

  public void SigninGoogleAndgetprofile(){
      img = findViewById(R.id.img_google);
      img.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              UserSignInMethod();
          }
      });
  }

  public void ForgetPassword(){
      TextView texreset=findViewById(R.id.textforget);
      texreset.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              dialog = new Dialog(loginmain.this);
              dialog.setContentView(R.layout.customlayout);
              Button dialogButton = (Button) dialog.findViewById(R.id.btnreset);
              // if button is clicked, close the custom dialog
              dialogButton.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (checkInfo.isNetworkAvailable(loginmain.this)) {

                          EditText edit = dialog.findViewById(R.id.getppassword);

                      String EMAIL = edit.getText().toString().trim();
                      if (edit.getText().toString().isEmpty()) {
//                              Toast.makeText(getBaseContext(), "Check Null Values", Toast.LENGTH_SHORT).show();
                      } else if (EMAIL.isEmpty() || !isValidEmail(EMAIL)) {
                          edit.setError(getResources().getString(R.string.invalidemail));
                      } else {
                          emaaail = edit.getText().toString();
                          sendResetPasswordEmail();
                          dialog.dismiss();
                      }
                  }else {
                      checkInfo.ShowNetworkdialoge();
                  }

                  }
              });
              dialog.show();
          }
      });
  }

  public void textSignuptoRegister(){
      TextView texregis=findViewById(R.id.terms2);
      texregis.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(loginmain.this, MainActivity.class));
              finish();
          }
      });
  }

  public void Fonttybe(){
      TextView textview =findViewById(R.id.welcomee);
      Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/fune.otf");
      textview.setTypeface(typeface);
  }

  public void LoginHome(){
      Button btnlog = (Button) findViewById(R.id.btnlogin);
      btnlog.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              mAuth = FirebaseAuth.getInstance();
              if (checkInfo.isNetworkAvailable(loginmain.this)) {
                  user = mAuth.getCurrentUser();
                  if (loguser.getText().toString().isEmpty() || logpass.getText().toString().isEmpty())
                  {
                      Toast.makeText(getBaseContext(), getResources().getString(R.string.fullinform), Toast.LENGTH_SHORT).show();
                  } String EMAIL=loguser.getText().toString().trim();
                  if (EMAIL.isEmpty()||!isValidEmail(EMAIL)){
                      loguser.setError(getResources().getString(R.string.invalidemail));
                  }else if(!loguser.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                      loguser.setError(getResources().getString(R.string.invalidemail));
                  }
                  else{
                      progressBar.setVisibility(View.VISIBLE);
                      firstName = loguser.getText().toString();
                      passwooord = logpass.getText().toString();
                      mAuth.signInWithEmailAndPassword(firstName, passwooord).addOnCompleteListener(loginmain.this, new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                              progressBar.setVisibility(View.GONE);
                              if (!task.isSuccessful()) {
                                  // there was an error
                              } else {
                                  mAuth = FirebaseAuth.getInstance();
                                  user = mAuth.getCurrentUser();
                                  boolean emailVerified = user.isEmailVerified();
//                                  if (emailVerified) {
                                      if (check.isChecked()) {
                                          loginPrefsEditor.putBoolean("saveLogin", true);
                                          loginPrefsEditor.putString("username", firstName);
                                          loginPrefsEditor.putString("password", passwooord);
                                          loginPrefsEditor.commit();
                                      } else {
                                          loginPrefsEditor.clear();
                                          loginPrefsEditor.commit();
                                      }
                                      sharee=getSharedPreferences("Name",MODE_PRIVATE);
                                      editorr=sharee.edit();
                                      editorr.apply();
                                      saveusername=sharee.getBoolean("user",false);
                                      if(saveusername==true) {
                                          username = sharee.getString("name", "");
                                      }
                                      UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                              .setDisplayName(username)
                                              .build();
                                      final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                      user.updateProfile(profileUpdates)
                                              .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                  @Override
                                                  public void onComplete(@NonNull Task<Void> task) {
                                                      if (task.isSuccessful()) {
                                                          currnetuser= FirebaseDatabase.getInstance().getReference();
                                                          final String emaail=user.getEmail();
                                                          final String id=user.getUid();
                                                          Uri photo=user.getPhotoUrl();
                                                          username=user.getDisplayName();
                                                          final String phtotooo=String.valueOf(photo);

                                                          UserloginMain a=new UserloginMain(username,emaail,id,phtotooo);
                                                          currnetuser.child("Users").child(id).setValue(a);
                                                          Intent intent=new Intent(loginmain.this,verfieymobile.class);
                                                          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                          startActivity(intent);
                                                          finish();
                                                      }
                                                  }
                                              });



//                                  }else {
//                                      Toast.makeText(getApplicationContext(),"Verified Your Account",Toast.LENGTH_LONG).show();
//                                  }
                              }
                          }
                      });
                  }     }
              else{
                 checkInfo.ShowNetworkdialoge();
              }
          }

      });
  }
   public void CheckisUserSavehisinformation(){
       loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
       loginPrefsEditor = loginPreferences.edit();
       saveLogin = loginPreferences.getBoolean("saveLogin", false);
       if (saveLogin == true) {
           loguser.setText(loginPreferences.getString("username", ""));
           logpass.setText(loginPreferences.getString("password", ""));
           check.setChecked(true);
       }

   }
}
