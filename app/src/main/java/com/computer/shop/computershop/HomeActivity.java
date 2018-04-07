package com.computer.shop.computershop;

import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String tag = HomeActivity.class.getSimpleName();
    RecyclerView recyclerView;
    ProductAdapter adapter;
    FloatingSearchView mSearchView;
    TextView getting;

    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;

    public List<Product> productList;

    private ProgressBar progressBar;


    //private static String URL = "http://api.openweathermap.org/data/2.5/forecast?appid=4f2b6b91dbd0fbce7c5ffa1680b750cb&q=Merced,us";


    private static String URL = "http://10.0.0.23:5000/check";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        getting = (TextView)findViewById(R.id.loadingEventText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new GetProducts().execute();


        /////////////////////////////////////////////////////////////////////
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                //get suggestions based on newQuery
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {

                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    mSearchView.showProgress();

                    //simulates a query call to a data source
                    //with a new query.
                    DataHelper.findSuggestions(HomeActivity.this, newQuery, 5,
                            FIND_SUGGESTION_SIMULATED_DELAY, new DataHelper.OnFindSuggestionsListener() {

                                @Override
                                public void onResults(List<Product> results) {

                                    //this will swap the data and
                                    //render the collapse/expand animations as necessary
                                    mSearchView.swapSuggestions(results);

                                    //let the users know that the background
                                    //process has completed
                                    mSearchView.hideProgress();
                                }
                            });
                }

                Log.d(tag, "onSearchTextChanged()");
            }

        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.categories) {
            // Handle the camera action
        } else if (id == R.id.graphicsCard) {

        } else if (id == R.id.cpu) {

        } else if (id == R.id.cooler) {

        } else if (id == R.id.cayse) {

        } else if (id == R.id.motherboard) {

        } else if (id == R.id.memory) {

        } else if (id == R.id.cooler) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     *
     * The job of this class is to use the HTTP Handler to
     * populate a list of products
     */
    private class GetProducts extends AsyncTask<Void,Void,Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar = (ProgressBar) findViewById(R.id.progressbar);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();

            String jsonResponse = httpHandler.makeServiceCall(URL);
            //String jsonResponse = makeRequest(URL);

            Log.e(tag, "Response Request: " + jsonResponse);

            if(jsonResponse != null)
                try{
                    //Should probably be an array. Thats first level
                    //JSONObject jsonObject = new JSONObject(jsonResponse);
                    JSONArray products = new JSONArray(jsonResponse);

                    //JSONArray products = jsonObject.getJSONArray(jsonResponse);
                    for(int i=0 ; i<products.length(); i++){
                        JSONObject p = products.getJSONObject(i);

                        //Actually adding things to list
                        productList.add(new Product(
                                p.getInt("id"),
                                p.getString("title"),
                                p.getString("desc"),
                                p.getDouble("rating"),
                                p.getDouble("price"),
                                //p.getInt("image")
                                ""
                        ));
                        Log.d("JSONLoop", "First Product title: " +productList.get(0).getTitle());

                    }
                }catch (final JSONException e){
                    Log.e(tag, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }

            else{
                Log.e(tag, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //TODO get rid of progress bar if still shown
            if(progressBar.isShown()){
                progressBar.setVisibility(View.INVISIBLE);
                getting.setVisibility(View.INVISIBLE);
            }

            adapter = new ProductAdapter(HomeActivity.this, productList);
            recyclerView.setAdapter(adapter);

        }
    }

    public String makeRequest(String URLRequest){
        StringBuilder sb;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("RESPONSE", response);
                        //sb.append(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_LONG);
                Log.e(tag, "message request: "+ error.getMessage());
            }
        });

        Volley.newRequestQueue(HomeActivity.this).add(stringRequest);
        return "";
    }


}
