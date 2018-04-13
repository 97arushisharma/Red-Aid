package com.DIC.RedAid.Application;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class data extends AppCompatActivity {


    private TextView head1;
    private LinearLayout mainLayout;
    private String message1 = "Your Blood is Needed";

    private String blood;
    private int count1,i;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);


        head1 = (TextView)findViewById(R.id.head) ;
        mainLayout = (LinearLayout) findViewById(R.id.activity_data);


        Bundle bundle=getIntent().getExtras();

        blood=bundle.getString("blood");
         count1=bundle.getInt("county");
        final String[] listy =bundle.getStringArray("lists");
        final String[] conty = bundle.getStringArray("contacts");

       head1.setText("DONORS WITH BLOOD GROUP "+blood+" ARE:\n");

        final Button details = (Button)findViewById(R.id.button1) ;

       details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(data.this, new String[]{android.Manifest.permission.SEND_SMS}, 1);
                details.setEnabled(false);
               if(count1!=0)
               {
                for (i = 0; i < count1; i++) {
                    TextView textView = new TextView(data.this);

                    textView.setText(listy[i] + "\n\n");
                    textView.setTextSize(20);
                    textView.setTextColor(Color.BLACK);
                    final String thenum = conty[i];


                    LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(textViewLayoutParams);
                    mainLayout.addView(textView);
                    textView.setId(i);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(data.this);
                            alertDialog.setTitle("Contact to the donor");
                            alertDialog.setMessage("Enter your details, It will be sent as a message to donor ( Normal messaging charges will be applied )");
                            final TextView input = new EditText(data.this);
                            alertDialog.setView(input);

                            alertDialog.setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                          final String  message  = "RED-AID :  " + input.getText().toString();
                                            PendingIntent pi = PendingIntent.getActivity(data.this, 0, new Intent(data.this, ignore.class), 0);
                                            SmsManager sms = SmsManager.getDefault();
                                            sms.sendTextMessage(thenum, null, message, pi, null);


                                            Toast.makeText(data.this, "Message sent", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            alertDialog.setNegativeButton("NO",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Write your code here to execute after dialog
                                            Toast.makeText(getApplicationContext(), "Contact to donor denied", Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                        }
                                    });


                            alertDialog.show();

                        }
                    });

                }
                   Toast.makeText(data.this, "TAP ON THE DONOR TO CONTACT", Toast.LENGTH_SHORT).show();
            }


            }


        });




    }


}
