package com.example.daniel.geolocation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.ArrayList;


/*
* Main class
*
* */
public class MainActivity extends ActionBarActivity {

    ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
    double lat = 0.0;
    double lon = 0.0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        // Get Location Manager Information

        LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Called when a new location is found

                lat = location.getLatitude();
                lon = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    public double getLatitude(){
        return lat;
    }

    public double getLongitude(){
        return lon;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void GetLocation(View view) {
        new GeoSearchTask().execute(" ");
    }

    public void searchRestaurant(View view) {

        String searchTerm = ((EditText) findViewById(R.id.textSearch)).getText().toString();
        RadioButton rb = (RadioButton) findViewById(R.id.rbName);
        RadioButton rp = (RadioButton) findViewById(R.id.rbPcode);
        RadioButton rr = (RadioButton) findViewById(R.id.rbRecent);

        if (rb.isChecked()) {
            String url = "http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=search_name&name=" + searchTerm;
            new SecondSearchTask().execute(url);
        } else if (rp.isChecked()) {
            String url = "http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=search_postcode&postcode=" + searchTerm;
            new SecondSearchTask().execute(url);
        }
        else if (rr.isChecked()) {
            String url = "http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=recent";
            new SecondSearchTask().execute(url);
        }
    } // END OF SEARCH RESTAURANT METHOD

    // Distance's format
    public static String truncate(String value, int length) {
        // Ensure String length is longer than requested size.
        if (value.length() > length) {
            return value.substring(0, length);
        } else {
            return value;
        }
    }

//>>>>>>>>>>>>>>>>>>////////////////////// MY ASYNCTASK ///////////////////////////////<<<<<<<<<<<<<<<<<<<<<<<<<<

    class GeoSearchTask extends AsyncTask<String, String, ArrayList<Restaurant>> {
        double lat = 0.0;
        double lon = 0.0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Restaurant> doInBackground(String... params) {

            // fetch data
            try {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                URL url = new URL("http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=nearest&lat=" + getLatitude() + "&long=" + getLongitude());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    InputStreamReader ins = new InputStreamReader(urlConnection.getInputStream());
                    BufferedReader in = new BufferedReader(ins);
                    // read the input stream as formal IO
                    String responseBody = "";
                    String line = "";
                    while ((line = in.readLine()) != null) {
                        responseBody = responseBody + line;
                    }

                    // we should know have one big string with the entire fetched resource
                    //Toast.makeText(getApplicationContext(), "Results Obtained", Toast.LENGTH_LONG).show();
                    restaurants = JSONParser.parseFeed(responseBody);

                } catch (IOException e) {
                    // handle errors
                    Log.d("", e + " " + e.getMessage());
                } finally {
                    urlConnection.disconnect();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return restaurants;

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<Restaurant> results) {

            super.onPostExecute(results);

                    TextView data1 = (TextView) findViewById(R.id.textView);
                    TextView data2 = (TextView) findViewById(R.id.textView2);
                    data1.setText("" + getLatitude());
                    data1.setTextColor(Color.BLUE);
                    data2.setText("" + getLongitude());
                    data2.setTextColor(Color.RED);

            // Main view
            LinearLayout resultsLayout = (LinearLayout) findViewById(R.id.resultsLayout);
            resultsLayout.removeAllViews();

            for (Restaurant result : results) {

                TextView name = new TextView(getApplicationContext());
                TextView address = new TextView(getApplicationContext());

                // Space views definitions
                TextView space = new TextView(getApplicationContext());
                space.setText(" - ");
                space.setTextColor(Color.BLACK);

                TextView space2 = new TextView(getApplicationContext());
                space2.setText(" - ");
                space2.setTextColor(Color.BLUE);

                TextView space3 = new TextView(getApplicationContext());
                space3.setText("");
                space3.setTextColor(Color.BLACK);

                TextView space4 = new TextView(getApplicationContext());
                space4.setText("- - - - - - - - - - - - - - - - -");
                space4.setTextColor(Color.BLACK);

                name.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
                name.setText(result.getBusinessName());
                name.setTextColor(Color.BLACK);

                address.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
                address.setText(result.getAddressLine1());
                address.setTextColor(Color.BLUE);

                // Middle layout declarations and composition
                LinearLayout middleLayout = new LinearLayout(getApplicationContext());
                middleLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout middleLayout2 = new LinearLayout(getApplicationContext());
                middleLayout2.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout middleLayout3 = new LinearLayout(getApplicationContext());
                middleLayout3.setOrientation(LinearLayout.HORIZONTAL);

                middleLayout3.addView(space4);
                middleLayout.addView(name);
                middleLayout.addView(space);
                middleLayout.addView(address);
                middleLayout.addView(space2);

                ImageView iv = new ImageView(getApplicationContext());

                // Image addition
                TextView rating = new TextView(getApplicationContext());
                if (result.getRatingValue() == -1) {
                    // rating.setText("Exempt");
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.i0));
                    middleLayout.addView(rating);
                    middleLayout.addView(iv);
                    middleLayout.addView(space3);

                }else if(result.getRatingValue() == 1) {
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.i1));
                    middleLayout.addView(rating);
                    middleLayout.addView(iv);
                    middleLayout.addView(space3);

                }else if(result.getRatingValue() == 2) {
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.i2));
                    middleLayout.addView(rating);
                    middleLayout.addView(iv);
                    middleLayout.addView(space3);

                }else if(result.getRatingValue() == 3) {
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.i3));
                    middleLayout.addView(rating);
                    middleLayout.addView(iv);
                    middleLayout.addView(space3);

                }else if(result.getRatingValue() == 4) {
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.i4));
                    middleLayout.addView(rating);
                    middleLayout.addView(iv);
                    middleLayout.addView(space3);

                }else if(result.getRatingValue() == 5) {
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.i5));
                    middleLayout.addView(rating);
                    middleLayout.addView(iv);
                    middleLayout.addView(space3);
                }

                // Distance addition
                if (result.getDistanceKM() != null) {
                    String dis = result.getDistanceKM();
                    String dis2 = truncate(dis, 5);
                    TextView distance = new TextView(getApplicationContext());
                    distance.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
                    distance.setText("Distance: " + dis2 + " Kms");
                    distance.setTextColor(Color.RED);
                    middleLayout2.addView(distance);
                }

                // Middle layouts added to Main Layout
                resultsLayout.addView(middleLayout);
                resultsLayout.addView(middleLayout2);
                resultsLayout.addView(middleLayout3);
            }
        }
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< SECOND ASYNCTASK <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    class SecondSearchTask extends AsyncTask<String, String, ArrayList<Restaurant>>{

        public ArrayList<Restaurant> doInBackground(String ...address){

            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo !=null && networkInfo.isConnected()){
                // fetch data

                try{

                    URL url = new URL(address[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    try {
                        InputStreamReader ins = new InputStreamReader(urlConnection.getInputStream());
                        BufferedReader in = new BufferedReader(ins);
                        // read the input stream as formal IO
                        String responseBody = "";
                        String line ="";
                        while ((line = in.readLine() ) !=null){
                            responseBody = responseBody + line;
                        }

                        // we should know have one big string with the entire fetched resource
                        // Toast.makeText(getApplicationContext(),"Results Obtained",Toast.LENGTH_LONG);
                        restaurants = JSONParser.parseFeed(responseBody);

                    }catch (IOException e){
                        // handle errors
                        e.printStackTrace();
                    }
                    finally {
                        urlConnection.disconnect();
                    }

                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }else {
                // display error
            }

            return restaurants;
        }

        public void onPostExecute(ArrayList<Restaurant> restaurants){

            // SEND MY OBJECT
            Intent intent = new Intent(getApplicationContext(), ResultsList.class);
            intent.putExtra("Data", restaurants);
            startActivity(intent);

        }
    }
} //end of class
