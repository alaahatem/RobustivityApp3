package com.robustastudio.robustivityapp;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.List;

public class MainActivity extends AppCompatActivity {
GoogleSignInClient mGoogleSignInClient;
   public static  GoogleSignInAccount account;
   boolean checked =false;
    List<UserProfile> userprofiles;



    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
        userprofiles = db.userDao().getAllprofiles();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }
    int RC_SIGN_IN = 10;
    private void signIn() {
        @SuppressLint("RestrictedApi") Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void CheckNewUser() {

        if (!userprofiles.isEmpty()) {
            for (int i = 0; i < userprofiles.size(); i++) {
                if (userprofiles.get(i).getEmail().toString().equals(account.getEmail())) {

                    Intent myIntent = new Intent(MainActivity.this, HomeActivity.class);
                    MainActivity.this.startActivity(myIntent);
                    checked= true;

                }
            }
            if(!checked){
                Intent myIntent = new Intent(MainActivity.this, createuserprof.class);
                MainActivity.this.startActivity(myIntent);
            }

        }
        else{
            Intent myIntent = new Intent(MainActivity.this, createuserprof.class);
            MainActivity.this.startActivity(myIntent);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
             account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Toast.makeText(this,"Email :"+account.getEmail()+"SUCCESS",Toast.LENGTH_LONG).show();
            CheckNewUser();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
           Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show();
        }
    }

}
