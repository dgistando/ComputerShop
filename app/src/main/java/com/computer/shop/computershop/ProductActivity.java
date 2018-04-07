package com.computer.shop.computershop;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ProductActivity extends AppCompatActivity{

    TextView pv_title, pv_price, pv_desc;
    RatingBar pv_rating;
    ImageView imageView3;
    Button pv_add_to_cart;

    int clickCount=0;

    String[] arr = {
            "We actually just ran out",
            "Just go to NewEgg. We already made our money",
            "mmmm,mmmmm,mmmm. No, no, no",
            "try again",
            "Click me 100 times!!"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_description);

        pv_title = (TextView) findViewById(R.id.pv_title);
        pv_price = (TextView) findViewById(R.id.pv_price);
        pv_rating = (RatingBar) findViewById(R.id.pv_rating);
        pv_desc = (TextView) findViewById(R.id.pv_desc);

        imageView3 = (ImageView) findViewById(R.id.imageView3);

        pv_add_to_cart = (Button) findViewById(R.id.pv_add_to_cart);

        Intent intent = getIntent();

        pv_title.setText(intent.getStringExtra("Title"));
        pv_price.setText("\u0243 "+String.valueOf(intent.getDoubleExtra("Price",2000.0)));
        pv_rating.setRating((float)intent.getDoubleExtra("Rating", 0.0));
        pv_desc.setText(intent.getStringExtra("Desc"));

        //TODO GET ALL THE IMAGES FOR EACH PRODUCT TO DISPLAY HERE
        imageView3.setImageDrawable(this.getResources().getDrawable(R.drawable.gtx1080));

        pv_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;

                if(clickCount == 20)Toast.makeText(getBaseContext(), "20 CLICKS!!!!!", Toast.LENGTH_SHORT).show();
                if(clickCount == 50)Toast.makeText(getBaseContext(), "50 CLICKS!!!!!", Toast.LENGTH_SHORT).show();
                else
                Toast.makeText(getBaseContext(), arr[new Random().nextInt(arr.length)], Toast.LENGTH_SHORT).show();

                //TODO PLAY BITCONNEEEEEEEEEEECT!!!!! ON HIGH VOLUME
                /*if(clickCount == 100){
                    MediaPlayer mp = new MediaPlayer();

                    try{
                        //GET FILE AND FIND FILE PATH
                        mp.setDataSource();
                        mp.prepare();
                        mp.start();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    clickCount = 0;
                }*/
            }
        });

    }
}
