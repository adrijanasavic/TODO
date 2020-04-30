package com.example.todo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<String> drawerItems;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    private RelativeLayout drawerPane;
    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        fillData();
        setupToolbar();
        setupDrawer();
    }

    private void fillData() {
        drawerItems = new ArrayList<>();
        drawerItems.add( "Grupe" );
        drawerItems.add( "Settings" );
        drawerItems.add( "About" );

    }

    private void setupDrawer() {
        drawerList = findViewById( R.id.left_drawer );
        drawerLayout = findViewById( R.id.drawer_layout );
        drawerPane = findViewById( R.id.drawerPane );

        drawerList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = "Unknown";
                switch (i) {
                    case 0:
                        title = "Grupe";
                        break;
                    case 1:
                        title = "Settings";
                        break;
                    case 2:
                        title = "About";
                        break;

                }
                drawerList.setItemChecked( i, true );
                setTitle( title );
                drawerLayout.closeDrawer( drawerPane );
            }
        } );
        drawerList.setAdapter( new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, drawerItems ) );


        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
    }

    public void setupToolbar() {
        toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled( true );
            actionBar.setHomeAsUpIndicator( R.drawable.menu );
            actionBar.setHomeButtonEnabled( true );
            actionBar.show();
        }
    }
}
