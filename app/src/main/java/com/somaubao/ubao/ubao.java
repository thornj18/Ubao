package com.somaubao.ubao;


import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ubao extends Activity {
    private String[] drawerTitles;
    private DrawerLayout drawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubao);

        mTitle = mDrawerTitle = getTitle();
        drawerTitles = getResources().getStringArray(R.array.drawer_items);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navDrawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.drawable.ic_navigation_drawer, R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActionBar().setTitle(mTitle);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.drawer_info_item, mDrawerList,false);
        mDrawerList.addHeaderView(header);


        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawerTitles));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        if (savedInstanceState == null){
            selectedItem(1);
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ubao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    public class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            selectedItem(i);
        }


    }

    private void selectedItem(int i) {
        Fragment fragment = null;
        switch(i){
            case 1:
                fragment = new Explore();
                break;
            case 2:
                fragment = new Stand();
                break;
            case  3:
                fragment = new Settings();
                break;
            case  4:
                fragment = new About();
                break;
            default:
                break;
        }
        if (fragment != null){
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.container, fragment).commit();
            mDrawerList.setItemChecked(i,true);
            setTitle(drawerTitles[i-1]);
            drawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("Main", "Error in creating fragment");
        }

    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
}
