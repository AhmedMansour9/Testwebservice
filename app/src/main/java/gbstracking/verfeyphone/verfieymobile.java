package gbstracking.verfeyphone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.gbstracking.R;

import gbstracking.Nvigation;

public class verfieymobile extends AppCompatActivity {
    public static int APP_REQUEST_CODE = 41;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verfieymobile);
        SharedPreferences prefs = getSharedPreferences("Phone", MODE_PRIVATE);
        String phone=prefs.getString("phone","");
          if(!phone.isEmpty()){
              startActivity(new Intent(verfieymobile.this, Nvigation.class));
              finish();
          }else {
        initAccountKitSmsFlow();
    }}

    public void initAccountKitSmsFlow() {
        final Intent intent = new Intent(getApplicationContext(), AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN

        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage= "";
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                }
                getAccount();
                startActivity(new Intent(verfieymobile.this, Nvigation.class));
                finish();
            }

            Toast.makeText(
                    getApplicationContext(),
                    toastMessage,
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
    private void getAccount() {
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                // Get Account Kit ID
                String accountKitId = account.getId();
                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                Log.e("verf", "onSuccess: "+phoneNumber.toString());
                String phoneNumberString = phoneNumber.toString();

                Toast.makeText(verfieymobile.this, ""+phoneNumberString, Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getSharedPreferences("Phone", MODE_PRIVATE).edit();
                editor.putString("phone",phoneNumberString);
                editor.commit();
                // Surface the result to your user in an appropriate way.
                Toast.makeText(
                        getApplicationContext(),
                        phoneNumberString,
                        Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onError(final AccountKitError error) {
                Log.e("AccountKit", error.toString());
                // Handle Error
            }
        });
    }

}
