package com.robustastudio.robustivityapp.ViewProfile;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.robustastudio.robustivityapp.CreateUserProfile.CreateUserProf;
import com.robustastudio.robustivityapp.CreateUserProfile.CreateUserProfActivity;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Constants.Constants;
import com.robustastudio.robustivityapp.FirebaseApp;
import com.robustastudio.robustivityapp.HomeActivity;
import com.robustastudio.robustivityapp.MainActivity;
import com.robustastudio.robustivityapp.Models.UserProfile;
import com.robustastudio.robustivityapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewProfileActivity extends AppCompatActivity implements  ViewProfile {

    private ViewProfilePresenter mViewProfilePresenter;
    String downloadedImage;
    TextView emailtv ;
    TextView nametv;
    List<UserProfile> userprofiles;
    String UserEmail;
    TextView userphone;
    TextView userstatus;
    Button Edit;
    private StorageTask mUploadTask;
    String DbName = Constants.AppdatabaseName;
    private  static final int PICK_IMAGE_REQUEST =1;
    Button MyImage ;
    Button Upload;
    ImageView Image ;
    Uri mImageUri;
    ProgressBar mprogressBar;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    public static FirebaseAuth mAuth;
    private List<Upload> mUploads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    mViewProfilePresenter = new ViewProfilePresenterImpl(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprofile);
        emailtv = findViewById(R.id.viewEmail);
        nametv = findViewById(R.id.viewName);
        userphone=findViewById(R.id.viewPhone);
        userstatus=findViewById(R.id.viewStatus);
        MyImage = findViewById(R.id.MyImage);
        Image =findViewById(R.id.Image);
        Upload = findViewById(R.id.upload);
        Edit = findViewById(R.id.edit);
        mAuth =FirebaseAuth.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,DbName).allowMainThreadQueries().build();
        userprofiles  = db.userDao().getAllprofiles();
        mprogressBar = findViewById(R.id.progress);
        mprogressBar.setVisibility(View.INVISIBLE);
    mViewProfilePresenter.ShowProfile(userprofiles);



MyImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try {
        if (ActivityCompat.checkSelfPermission(ViewProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ViewProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
        }
        else {
            Toast.makeText(getApplicationContext(),"ACCESS",Toast.LENGTH_LONG).show();
            OpenFileChooser();

        }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
});


Edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent myIntent = new Intent(ViewProfileActivity.this, CreateUserProfActivity.class);
        myIntent.putExtra("name",nametv.getText().toString() );
        myIntent.putExtra("email",emailtv.getText().toString());
        myIntent.putExtra("phone",userphone.getText().toString());
        myIntent.putExtra("status",userstatus.getText().toString());
        ViewProfileActivity.this.startActivity(myIntent);
    }
});

Upload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (mUploadTask != null && mUploadTask.isInProgress()) {

            Toast.makeText(ViewProfileActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
        } else {
            mprogressBar.setVisibility(View.VISIBLE);
            uploadFile();
        }
    }
});
        DatabaseReference ref = mDatabaseRef.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("image");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                downloadedImage=dataSnapshot.getValue(String.class);
                if(!downloadedImage.isEmpty())
                Picasso.get().load(downloadedImage).centerCrop().fit().into(Image);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mprogressBar.setProgress(0);
                                }
                            }, 500);
                            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/taskmanagement-1522006863027.appspot.com/o/Uploads%2F1523667879875.jpeg?alt=media&token=7c3effa9-4035-42ea-8bde-f83fcaf16fea").centerCrop().fit().into(Image);
                            Toast.makeText(ViewProfileActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            mprogressBar.setVisibility(View.INVISIBLE);


                            mDatabaseRef.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("image").setValue(taskSnapshot.getDownloadUrl().toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ViewProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mprogressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void OpenFileChooser() {
    Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(Image);
        }
    }



    public void SetTextViews(String name,String Email,String phone,String status) {


        emailtv.setText(Email);
        nametv.setText(name);
        userphone.setText(phone);
        userstatus.setText(status);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    OpenFileChooser();
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }




    }
