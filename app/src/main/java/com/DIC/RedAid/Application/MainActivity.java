package com.DIC.RedAid.Application;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by charul on 18/4/17.
 */
public class MainActivity extends AppCompatActivity {

    public String string = "Need";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Button buttonDonate = (Button)findViewById(R.id.button2);

        buttonDonate.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the donate category is clicked on.
            @Override
            public void onClick(View view) {


                // Create a new intent to open the {@link MainActivity1}
                Intent  a= new Intent(MainActivity.this, LoginActivity.class);

                // Start the new activity
                startActivity(a);


            }
        });

       Button buttonNeed = (Button)findViewById(R.id.button3);

        buttonNeed.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the need category is clicked on.
            @Override
            public void onClick(View view) {



                Intent b = new Intent(MainActivity.this, MapsActivity.class);
               // b.putExtra("var",string);
                // Start the new activity
                startActivity(b);


            }
        });

        TextView feedback = (TextView)findViewById(R.id.textView7);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://goo.gl/forms/1RdkwYb1Q3g7txHr1"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


    }

}