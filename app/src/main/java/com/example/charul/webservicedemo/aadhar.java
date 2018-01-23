package com.example.charul.webservicedemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import static android.R.attr.name;

public class aadhar extends AppCompatActivity  {
    private TextView info;
    private Button save, nu;
    private String scanContent;
    private String name1;
    private String uid, name, gender, yearOfBirth, careOf, villageTehsil,loc, postOffice, district, state, postCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar);


        name1 = getIntent().getStringExtra("name");


        info = (TextView)findViewById(R.id.tv);
        save = (Button) findViewById(R.id.savebtn);
        save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(aadhar.this);
                                        alertDialog.setTitle("Confirm Your Authentication");
                                        alertDialog.setMessage("Your Aadhar Card Details");
                                        final TextView input = new TextView(aadhar.this);
                                        // input.setText("\nName : "+name+"\nUID : "+uid+"\nGender : "+gender+"\nYear of Birth : "+yearOfBirth+"\nCare of : "+careOf+"\nLocation : "+loc+"\nVillage/Tehsil : "+villageTehsil+"\nPost Office : "+postOffice+"\nDistrict : "+district+"\nState : "+state+"Post Code : "+postCode);

                                        input.setText(processScannedData(scanContent));
                                        alertDialog.setView(input);
                                        alertDialog.setPositiveButton("YES",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Write your code here to execute after dialog
                                                        Toast.makeText(getApplicationContext(), "Information Saved", Toast.LENGTH_SHORT).show();
                                                        Intent a = new Intent(aadhar.this, ProfileActivity.class);
                                                        a.putExtra("name11", name1);
                                                        // Start the new activity
                                                        startActivity(a);
                                                        finish();
                                                    }
                                                });

                                        alertDialog.setNegativeButton("NO",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Write your code here to execute after dialog
                                                        Toast.makeText(getApplicationContext(), "Information not saved! Try Again", Toast.LENGTH_SHORT).show();
                                                        dialog.cancel();
                                                    }
                                                });
                                        alertDialog.show();
                                    }
                                });

                nu = (Button) findViewById(R.id.nobtn);
        nu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent a = new Intent(aadhar.this, ProfileActivity.class);
                a.putExtra("name11", name1);
                finish();
                // Start the new activity
                startActivity(a);
            }
        });


    }

    public void scanNow(View view) {
        ActivityCompat.requestPermissions(aadhar.this, new String[]{Manifest.permission.CAMERA}, 1);

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan a Aadharcard QR Code");
        integrator.setResultDisplayDuration(500);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            // process received data
            if (scanContent != null && !scanContent.isEmpty()) {
                //    processScannedData(scanContent);
                Toast.makeText(aadhar.this, scanContent + scanFormat, Toast.LENGTH_LONG).show();
                //  info.setText(scanContent);

                    processScannedData(scanContent);



            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Scan Cancelled", Toast.LENGTH_SHORT);
                toast.show();
            }

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }





    protected String processScannedData(String scanData) {
        Log.d("Information", scanData);

            // init the parserfactory
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();

            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(new StringReader(scanData));

            // parse the XML
            int event = parser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                if (event == XmlPullParser.TEXT) {
                    Toast toast = Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_LONG);
                    toast.show();
                uid = parser.getText();
                }

                event = parser.next();
            }
        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Toast toast = Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_LONG);
        toast.show();
        return uid;
    }
}
