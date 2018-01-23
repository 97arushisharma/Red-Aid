package com.example.charul.webservicedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by charul on 18/4/17.
 */
public class BloodGroup extends AppCompatActivity {
    public RadioButton A_pos ;
    public RadioButton A_neg ;
    public RadioButton B_pos ;
    public RadioButton B_neg;
    public RadioButton AB_pos;
    public RadioButton AB_neg;
    public RadioButton O_pos ;
    public RadioButton O_neg;




    public Firebase mref;
    public String blood;
    public String f,f1;
    public String num;
    public String n;

    public String s;

    private double lat1;
    private double lat2;
    private double long1;
    private double long2;
    private double dist,dist2;
    private String address;
    private ProgressDialog progress;

    private String[] list = new String[10];
    private  String[] numbr = new String[10];






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_group);
        Button buttonDonate = (Button) findViewById(R.id.proceed);
       // Button search = (Button) findViewById(R.id.proceed2);




        A_pos = (RadioButton) findViewById(R.id.A_positive);
        A_neg = (RadioButton) findViewById(R.id.A_negative);
        B_pos = (RadioButton) findViewById(R.id.B_positive);
        B_neg = (RadioButton) findViewById(R.id.B_negative);
        AB_pos = (RadioButton) findViewById(R.id.AB_positive);
        AB_neg = (RadioButton) findViewById(R.id.AB_negative);
        O_pos = (RadioButton) findViewById(R.id.O_positive);
        O_neg = (RadioButton) findViewById(R.id.O_negative);
        progress = new ProgressDialog(this);





        Firebase.setAndroidContext(this);

        Bundle bundle=getIntent().getExtras();
        lat1 = bundle.getDouble("lat");
        long1 = bundle.getDouble("long");

      /*  search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(), search_near.class);
                startActivity(p);
            }
        }); */


        buttonDonate.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the donate category is clicked on.
            @Override
            public void onClick(View view) {
                bloodGroup();

                progress.setMessage("Fetching details");
                progress.show();

                mref = new Firebase("https://red-aid.firebaseio.com/");



                Query info= mref.orderByChild("blood").equalTo(blood);

                info.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        int count=0;

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            n = (String) postSnapshot.child("name").getValue();
                            num = (String) postSnapshot.child("number").getValue();

                            address = (String) postSnapshot.child("address").getValue();


                            lat2 = (Double) postSnapshot.child("lat").getValue();
                            long2 = (Double) postSnapshot.child("long").getValue();
                            Location locationA = new Location("point A");
                            locationA.setLatitude(lat1);
                            locationA.setLongitude(long1);
                            Location locationB = new Location("point B");
                            locationB.setLatitude(lat2);
                            locationB.setLongitude(long2);
                            dist = locationA.distanceTo(locationB) ;


                            if (dist < 10000) {

                                dist2 = dist/1000;
                                f = "Name: "+n+"\n"+"At a distance of "+String.format("%.2f", dist2)+" Kms from you";

                                list[count]=f;
                                numbr[count]=num;

                                count++;


                            }


                        }


                          if(count==0){
                              Toast.makeText(BloodGroup.this, "No donor of this blood group is available nearby ! Try with a different location", Toast.LENGTH_LONG).show();
                              progress.dismiss();
                          }
                        else {
                              Intent a = new Intent(getApplicationContext(), data.class);

                              a.putExtra("blood", blood);
                              a.putExtra("county", count);
                              a.putExtra("lists", list);
                              a.putExtra("contacts", numbr);
                              if (address != null) {
                                  progress.dismiss();


                                  startActivity(a);

                              }
                          }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }
        });


    }


    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }



    public void bloodGroup() {

        if (A_pos.isChecked()){
            blood="A+";
        }
        else if(A_neg.isChecked()){
            blood="A-";
        }
        else if(B_pos.isChecked()){
            blood="B+";
        }
        else if(B_neg.isChecked()){
            blood="B-";
        }
        else if(AB_pos.isChecked()){
            blood="AB+";
        }
        else if(AB_neg.isChecked()){
            blood="AB-";
        }
        else if(O_pos.isChecked()){
            blood="O+";
        }
        else if(O_neg.isChecked()){
            blood="O-";
        }
    }


}