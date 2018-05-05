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
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.onesignal.OneSignal;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MainActivity";;

    public static FirebaseAuth mAuth;
    public static FirebaseUser user ;
    GoogleSignInClient mGoogleSignInClient;
    public static String  Logged_user;
    public List<String> value = new ArrayList<String>();
    public static  GoogleSignInAccount account;
    static boolean checked;
    List<String> mails = new ArrayList<>();
    private DatabaseReference mDatabase;
    private DatabaseReference ref_sectors;
    private DatabaseReference ref_projects;
    List<UserProfile> userprofiles;
    public List<String> sector_names;
    public List<String> available;


    private String webClientId = "734558269858-a9m110bdaccgh81elqd4pfo5iv4f5lv6.apps.googleusercontent.com";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        mAuth = FirebaseAuth.getInstance();
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        userprofiles = db.userDao().getAllprofiles();

        super.onCreate(savedInstanceState);
        checked =false;
        sector_names = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ref_sectors = mDatabase.child("Sectors");

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Firebase Token: " + refreshedToken);



//        ref.addValueEventListener(new ValueEventListener() {
//
//            String temp;
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    temp = postSnapshot.getKey();
//                    mails.add(temp);
//                    Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override

            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {
                    DatabaseReference ref = mDatabase.child("user_profile");
                    ref.addValueEventListener(new ValueEventListener() {
                        //
                        String temp;

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                temp = postSnapshot.getKey();
                                mails.add(temp);


                            }
                            if (mails != null && !mails.isEmpty()) {
                                for (int i = 0; i < mails.size(); i++) {
                                    if (FirebaseApp.DecodeString(mails.get(i)).equals(firebaseAuth.getCurrentUser().getEmail())) {
                                        checked = true;
                                    }
                                }
                                if (checked) {
                                    Toast.makeText(getApplicationContext(), "already there", Toast.LENGTH_LONG).show();
                                    Intent myIntent = new Intent(MainActivity.this, HomeActivity.class);
                                    MainActivity.this.startActivity(myIntent);
                                    Logged_user = mAuth.getCurrentUser().getEmail();
                                    OneSignal.sendTag("User_ID", Logged_user);
                                } else {
                                    Toast.makeText(getApplicationContext(), "am new here", Toast.LENGTH_LONG).show();
                                    UserProfile userprofile = new UserProfile("",firebaseAuth.getCurrentUser().getDisplayName(),
                                            "", firebaseAuth.getCurrentUser().getEmail(),  "Off Premises");



                                    mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).setValue(userprofile);

                                    Intent myIntent = new Intent(MainActivity.this, HomeActivity.class);
                                    MainActivity.this.startActivity(myIntent);
                                    Logged_user = mAuth.getCurrentUser().getEmail();
                                    OneSignal.sendTag("User_ID", Logged_user);
                                }


                            }
                            else{
                                Toast.makeText(getApplicationContext(), "am the first here", Toast.LENGTH_LONG).show();
                                UserProfile userprofile = new UserProfile("",firebaseAuth.getCurrentUser().getDisplayName(),
                                        "", firebaseAuth.getCurrentUser().getEmail(), "Off Premises");


                                mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).setValue(userprofile);

                                Intent myIntent = new Intent(MainActivity.this, HomeActivity.class);
                                MainActivity.this.startActivity(myIntent);
                                Logged_user = mAuth.getCurrentUser().getEmail();
                                OneSignal.sendTag("User_ID", Logged_user);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        };


//
//                    }else{
//                        UserProfile userprofile = new UserProfile(firebaseAuth.getCurrentUser().getDisplayName(),
//                                firebaseAuth.getCurrentUser().getEmail(), firebaseAuth.getCurrentUser().getPhoneNumber(), temp, "Off Premises");
//
//
//
//                        mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).setValue(userprofile);
//                        Intent myIntent = new Intent(MainActivity.this, HomeActivity.class);
//                        MainActivity.this.startActivity(myIntent);
//                        Logged_user = mAuth.getCurrentUser().getEmail();
//                        OneSignal.sendTag("User_ID", Logged_user);
//
//                    }
//                }




        user=mAuth.getCurrentUser();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestEmail()
                .requestIdToken(webClientId)
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
                if(account.getEmail().endsWith("@gmail.com"))


                    firebaseAuthWithGoogle(account);
                else Toast.makeText(getApplicationContext(), "wrong domain sir", Toast.LENGTH_SHORT).show();

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
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
//            // Please refer to the GoogleSignInStatusCodes class ref_sectors for more information.
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
                            Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

}
