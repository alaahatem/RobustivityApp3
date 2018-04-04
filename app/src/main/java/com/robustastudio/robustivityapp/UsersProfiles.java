package com.robustastudio.robustivityapp;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class UsersProfiles extends AppCompatActivity {
    TextView viewname ;
    TextView viewemail;
    TextView viewphone;
    TextView viewstatus;
    Button ping ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profiles);
        getIncomingIntent();
        Button ping = findViewById(R.id.ping);
        ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });

    }

    public void getIncomingIntent() {
        if (getIntent().hasExtra("Username") && getIntent().hasExtra("user_email")&&getIntent().hasExtra("phone")&&getIntent().hasExtra("Status")) {
            String  username = getIntent().getStringExtra("Username");

            String  Email = getIntent().getStringExtra("user_email");
            String  phone = getIntent().getStringExtra("phone");
            String  status = getIntent().getStringExtra("Status");

             setTextViews(username,Email,phone,status);
        }
    }

    public void setTextViews(String username , String email ,String phone , String status){
        Toast.makeText(this,username,Toast.LENGTH_LONG);
        viewname = findViewById(R.id.viewName);
        viewemail = findViewById(R.id.viewEmail);
        viewphone=findViewById(R.id.viewphone);
        viewstatus=findViewById(R.id.viewStatus);
        viewname.setText(username);
        viewemail.setText(email);
        viewstatus.setText(status);

    }
    private void sendNotification() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;

                    //This is a Simple Logic to Send Notification different Device Programmatically....
                    if (MainActivity.Logged_user.equals("sanaahatem86@gmail.com")) {
                        send_email = "sanaahatem86@gmail.com";
                    } else {
                        send_email="sanaahatem86@gmail.com";
                    }

                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic MDRiZTRmN2MtNjE3Yi00NmI1LWFkODUtMTBhMzNkNzg1ZDFj");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"ad48fdd4-7c63-4dcd-bd26-b6941d648769\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"English Message\"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }


}