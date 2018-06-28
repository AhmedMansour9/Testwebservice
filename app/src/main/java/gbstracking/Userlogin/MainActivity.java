package gbstracking.Userlogin;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.gbstracking.R;

import gbstracking.CheckgbsAndNetwork;

import static com.google.firebase.auth.FirebaseAuth.getInstance;


public class MainActivity extends Activity {
    EditText editusername;
    EditText editpasworrd;
    EditText editemail;
    String url;
   FirebaseAuth auth;
   private ProgressBar progressBar;
   String username;
    FirebaseAuth authh;
    SharedPreferences share;
    SharedPreferences.Editor edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editemail = findViewById(R.id.email);
        editpasworrd = findViewById(R.id.pass);
        editusername = findViewById(R.id.name);
        auth = getInstance();
        progressBar = findViewById(R.id.progressBarr);

        Registration();
        Font();
         Sendtologinmain();

    }


    private Boolean ValidateUsername(){
        if (editusername.getText().toString().trim().isEmpty()&&editusername.getText().toString().trim().length()>3){
            editusername.setError(getResources().getString(R.string.namee));
         return false;
        } else if(!editusername.getText().toString().matches(("^[a-zA-Z\\s]*$"))) {
            editusername.setError(getResources().getString(R.string.namee));
            return false;

        }
        return true;
    }
    private Boolean ValidateEmail(){
       String EMAIL=editemail.getText().toString().trim();
        if (EMAIL.isEmpty()||!isValidEmail(EMAIL)){
            editemail.setError(getResources().getString(R.string.invalidemail));

            return false;
        }else if(!editemail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            editemail.setError(getResources().getString(R.string.invalidemail));
           return false;
        }
        return true;
    }
    private Boolean ValidatePassword(){
    if(editpasworrd.getText().toString().trim().isEmpty()&&editpasworrd.getText().toString().trim().length()>3){
        editpasworrd.setError(getResources().getString(R.string.enterpas));
        return false;
    }else {
        return true;
    }}
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
      public void  Registration(){
          Button btnregisterr = findViewById(R.id.btnregister);
          btnregisterr.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  CheckgbsAndNetwork Check=new CheckgbsAndNetwork(MainActivity.this);

                  if (Check.isNetworkAvailable(getApplicationContext())) {
                      if (!ValidateEmail()||!ValidatePassword()||!ValidateUsername()){
                          Toast.makeText(getBaseContext(),getResources().getString(R.string.fullinform),Toast.LENGTH_LONG).show();

                      } else{
                          username = editusername.getText().toString();
                          String  pas = editpasworrd.getText().toString();
                          String Emaail = editemail.getText().toString();
                          progressBar.setVisibility(View.VISIBLE);
                          auth.createUserWithEmailAndPassword(Emaail,pas).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                              @Override
                              public void onComplete(@NonNull Task<AuthResult> task) {
                                  progressBar.setVisibility(View.GONE);
                                  if (!task.isSuccessful()) {
                                      Toast.makeText(MainActivity.this, getResources().getString(R.string.alreadyuser) ,
                                              Toast.LENGTH_SHORT).show();
                                  } else {
                                      share = getSharedPreferences("Name", MODE_PRIVATE);
                                      edit = share.edit();
                                      edit.putBoolean("user",true);
                                      edit.putString("name", username);
                                      edit.apply();

                                      FirebaseUser user = getInstance().getCurrentUser();
//                                      user.sendEmailVerification();
//                                      Toast.makeText(MainActivity.this,
//                                              getResources().getString(R.string.verfiymail) + user.getEmail(),
//                                              Toast.LENGTH_SHORT).show();

                                      authh.getInstance().signOut();
                                      startActivity(new Intent(MainActivity.this, loginmain.class));
                                      finish();
                                  }
                              }
                          });
                      }
                  } else {
                      Check.ShowNetworkdialoge();
                  }
              }
          });
      }
    public void Font(){
    TextView textView =findViewById(R.id.create);
    Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FONT1.ttf");
    textView.setTypeface(typeface);  }
    public void Sendtologinmain(){
        TextView textsign = findViewById(R.id.terms2);
        textsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inty = new Intent(getApplication(), loginmain.class);
                startActivity(inty);
                finish();
            }
        });

    }
}