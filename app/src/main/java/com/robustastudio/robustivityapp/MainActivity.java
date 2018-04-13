package com.robustastudio.robustivityapp;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.onesignal.OneSignal;
import com.robustastudio.robustivityapp.CreateUserProfile.CreateUserProfActivity;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MainActivity";;
    public static  FirebaseAuth mAuth;
    public static FirebaseUser user ;
GoogleSignInClient mGoogleSignInClient;
    public static String  Logged_user;

   public static  GoogleSignInAccount account;
   boolean checked =false;
    List<UserProfile> userprofiles;




    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        mAuth = FirebaseAuth.getInstance();
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        userprofiles = db.userDao().getAllprofiles();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override

            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if(firebaseAuth.getCurrentUser()!=null){
                Intent myIntent = new Intent(MainActivity.this, HomeActivity.class);
                MainActivity.this.startActivity(myIntent);
                Logged_user =mAuth.getCurrentUser().getEmail();
                OneSignal.sendTag("User_ID",Logged_user);
            }
            }
        };

        user=mAuth.getCurrentUser();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
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

//        Logged_user = user.getEmail();
//        OneSignal.sendTag("User_ID",Logged_in_user_email);

    }
    int RC_SIGN_IN = 10;
    private void signIn() {
        @SuppressLint("RestrictedApi") Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);}
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }



//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//             account = completedTask.getResult(ApiException.class);
//
//            // Signed in successfully, show authenticated UI.
//            Toast.makeText(this,"Email :"+account.getEmail()+"SUCCESS",Toast.LENGTH_LONG).show();
//            CheckNewUser();
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//           Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show();
//        }
//    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(MainActivity.this,"Signed in successfully",Toast.LENGTH_SHORT).show();

                            user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this,"failed ",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

}
