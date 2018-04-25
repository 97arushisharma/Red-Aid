package com.DIC.RedAid.Application;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BloodOrgan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_organ);



        Button buttonDonate = (Button)findViewById(R.id.buttonblood);

        buttonDonate.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the donate category is clicked on.
            @Override
            public void onClick(View view) {


                // Create a new intent to open the {@link MainActivity1}
                Intent a= new Intent(BloodOrgan.this, MainActivity.class);

                // Start the new activity
                startActivity(a);


            }
        });

        Button buttonNeed = (Button)findViewById(R.id.buttonorgan);

        buttonNeed.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the need category is clicked on.
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("http://www.lampoflife.in/users/register"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
    }
}
