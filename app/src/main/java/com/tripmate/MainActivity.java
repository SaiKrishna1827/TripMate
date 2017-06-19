package com.tripmate;

import android.content.Context;
import android.database.Cursor;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GridView trip_grid_view;
    public static  DataBaseHelper AppBase = null;


    ArrayList<TripModel> trip_array_list = new ArrayList<>();

    TripAdapter grid_view_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addTripFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddTrip.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AppBase = new DataBaseHelper(this);

        trip_array_list = AppBase.getTripsData();
        //grid_view_adapter.notifyDataSetChanged();

        grid_view_adapter  = new TripAdapter(this, trip_array_list);

        trip_grid_view = (GridView) findViewById(R.id.trip_grid_view);

        trip_grid_view.setAdapter(grid_view_adapter);

        trip_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(MainActivity.this,TripDesk.class);
                intent.putExtra("trip_id",trip_array_list.get(position).getTrip_id());
                startActivity(intent);

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(AppBase!=null){

            trip_array_list = AppBase.getTripsData();
            grid_view_adapter.notifyDataSetChanged();

        }

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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public class TripAdapter extends BaseAdapter {
        private Context context;
        ArrayList<TripModel> trip_array_list;

        public TripAdapter(Context context, ArrayList<TripModel> trip_array_list) {
            this.context = context;
            this.trip_array_list = trip_array_list;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View v;
            if(convertView==null)
            {
                LayoutInflater li = getLayoutInflater();
                v = li.inflate(R.layout.trip_item_grid_row, null);
            }else{
                v = convertView;
            }

            TripModel model = trip_array_list.get(position);

            TextView trip_name_tv = (TextView) v.findViewById(R.id.trip_name_tv);
            TextView trip_date_tv = (TextView) v.findViewById(R.id.trip_date_tv);
            TextView trip_amount_tv = (TextView) v.findViewById(R.id.trip_amount_tv);

            trip_name_tv.setText(model.getTrip_name());
            trip_date_tv.setText(model.getTrip_date());
            trip_amount_tv.setText(model.getTrip_amount());

            return v;
        }

        @Override
        public int getCount() {
            return trip_array_list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }



}
