package com.example.daniel.geolocation;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


// Result set class
public class ResultsList extends ActionBarActivity {

    ArrayList<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list);

        Bundle bundel = getIntent().getExtras();
        try {
            restaurants = (ArrayList<Restaurant>) bundel.get("Data");
            if (restaurants == null) {return;}else{Toast.makeText(getApplicationContext(), "Results Obtained", Toast.LENGTH_SHORT).show();}

        } catch (Exception e) {
            Log.i(" Error at bundle ", e.toString());
        }

        for (int i = 0; i < restaurants.size(); i++) {

            // Space declarations
            TextView space = new TextView(getApplicationContext());
            space.setText(" - ");
            space.setTextColor(Color.BLACK);

            TextView space2 = new TextView(getApplicationContext());
            space2.setText(" - ");
            space2.setTextColor(Color.BLUE);

            // Information views declaration
            TextView name = new TextView(this.getApplicationContext());
            name.setText(restaurants.get(i).getBusinessName());
            name.setTextColor(Color.BLACK);

            TextView address = new TextView(this.getApplicationContext());
            address.setText(restaurants.get(i).getAddressLine1());
            address.setTextColor(Color.BLUE);
            // address.setPadding(5,0,0,0);

            LinearLayout ln = new LinearLayout(this.getApplicationContext());
            ln.setOrientation(LinearLayout.HORIZONTAL);
            ln.addView(name);
            ln.addView(space);
            ln.addView(address);
            ln.addView(space2);

            TextView rating = new TextView(this.getApplicationContext());

            if (restaurants.get(i).getRatingValue() == -1) {
                //rating.setText("Exempt");
                ImageView iv = new ImageView(getApplicationContext());
                iv.setImageDrawable(getResources().getDrawable(R.drawable.i0));
                //ln.addView(rating);
                ln.addView(iv);

            }else if (restaurants.get(i).getRatingValue() == 1) {
                ImageView iv = new ImageView(getApplicationContext());
                iv.setImageDrawable(getResources().getDrawable(R.drawable.i1));
                //ln.addView(rating);
                ln.addView(iv);

            }else if (restaurants.get(i).getRatingValue() == 2) {
                ImageView iv = new ImageView(getApplicationContext());
                iv.setImageDrawable(getResources().getDrawable(R.drawable.i2));
                //ln.addView(rating);
                ln.addView(iv);

            }else if (restaurants.get(i).getRatingValue() == 3) {
                ImageView iv = new ImageView(getApplicationContext());
                iv.setImageDrawable(getResources().getDrawable(R.drawable.i3));
                //ln.addView(rating);
                ln.addView(iv);

            }else if (restaurants.get(i).getRatingValue() == 4) {
                ImageView iv = new ImageView(getApplicationContext());
                iv.setImageDrawable(getResources().getDrawable(R.drawable.i4));
                //ln.addView(rating);
                ln.addView(iv);

            }else if (restaurants.get(i).getRatingValue() == 5) {
                ImageView iv = new ImageView(getApplicationContext());
                iv.setImageDrawable(getResources().getDrawable(R.drawable.i5));
                //ln.addView(rating);
                ln.addView(iv);

            }

            else {
               // rating.setText(""+ restaurants.get(i).getRatingValue());
            }

            rating.setTextColor(Color.RED);
            ln.addView(rating);

            // Information views added to Main layout
            LinearLayout theList = (LinearLayout) this.findViewById(R.id.theList);
            theList.addView(ln);
        }

        findViewById(R.id.button10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultsList.this, MainActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
