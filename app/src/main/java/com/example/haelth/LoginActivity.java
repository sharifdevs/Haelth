package com.example.haelth;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import java.io.File;

public class LoginActivity extends AppCompatActivity {

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Sign-in Buttons Handling
        Button buttonGoogleSignIn = findViewById(R.id.buttonGoogleSignIn);
        Button buttonUsernameSignIn = findViewById(R.id.buttonUsernameSignIn); // Add Sign up button LATER!
        Button buttonOfflineMode = findViewById(R.id.buttonOfflineMode);

        // Making Google Sign in Client
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        App.mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // onClick Listeners for Buttons
        buttonGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonGoogleSignIn:
                        showGoogleSignIn();
                        break;
                }
            }
        });

        buttonUsernameSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonUsernameSignIn:

                        break;
                }
            }
        });

        buttonOfflineMode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonOfflineMode:
                        showGetInfo();
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(App.isLoggedIn) showMain();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
        }
        finish();
        moveTaskToBack(true);
    }

    private void showGetInfo() {
        Intent intent = new Intent(this, GetInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showGoogleSignIn() {
        Intent intent = App.mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }

    private boolean isUserExist(String uid) {
        try {
            App.getPatientJSONObj("https://api.myjson.com/bins/" + uid);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            App.googleSignInAccount = account;

            if(isUserExist("8uo1o")) {
                //App.jsonParser.saveJsonToCache();
            } else { showGetInfo(); }

            App.accountType = App.AccountType.GOOGLE;
            App.isLoggedIn = true;
            showMain();
        } catch (ApiException e) {
            Log.w("Login Failure!", "signInResult:failed code=" + e.getStatusCode());

        }
    }
}
