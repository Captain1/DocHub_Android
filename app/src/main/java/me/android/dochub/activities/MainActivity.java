package me.android.dochub.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import me.android.dochub.R;
import me.android.dochub.adapter.CustomDrawerAdapter;
import me.android.dochub.components.DrawerFragment;
import me.android.dochub.models.DrawerItem;

public class MainActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    CustomDrawerAdapter adapter;

    List<DrawerItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Initializing
        dataList = new ArrayList<DrawerItem>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        // Add Drawer Item to dataList
        dataList.add(new DrawerItem("Search", R.drawable.ic_search));
        dataList.add(new DrawerItem("Document", R.drawable.ic_book));
        dataList.add(new DrawerItem("About", R.drawable.ic_info));
        dataList.add(new DrawerItem("Settings", R.drawable.ic_setting));
        dataList.add(new DrawerItem("Help", R.drawable.ic_question));
        dataList.add(new DrawerItem("Exit", R.drawable.ic_exit));

        adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
                dataList);

        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            SelectItem(0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    public void SelectItem(int position) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (position) {
            case 0:
                fragment = new DrawerFragment();
                args.putString(DrawerFragment.ITEM_NAME, dataList.get(position)
                        .getItemName());
                args.putInt(DrawerFragment.IMAGE_RESOURCE_ID, dataList.get(position)
                        .getImgResID());
                break;
            case 1:
                fragment = new DrawerFragment();
                args.putString(DrawerFragment.ITEM_NAME, dataList.get(position)
                        .getItemName());
                args.putInt(DrawerFragment.IMAGE_RESOURCE_ID, dataList.get(position)
                        .getImgResID());
                startActivity(new Intent(this, DocList.class));
                break;
            case 2:
                fragment = new DrawerFragment();
                args.putString(DrawerFragment.ITEM_NAME, dataList.get(position)
                        .getItemName());
                args.putInt(DrawerFragment.IMAGE_RESOURCE_ID, dataList.get(position)
                        .getImgResID());
                break;
            case 3:
                fragment = new DrawerFragment();
                args.putString(DrawerFragment.ITEM_NAME, dataList.get(position)
                        .getItemName());
                args.putInt(DrawerFragment.IMAGE_RESOURCE_ID, dataList.get(position)
                        .getImgResID());
                break;
            case 4:
                fragment = new DrawerFragment();
                args.putString(DrawerFragment.ITEM_NAME, dataList.get(position)
                        .getItemName());
                args.putInt(DrawerFragment.IMAGE_RESOURCE_ID, dataList.get(position)
                        .getImgResID());
                break;
            case 5:
                fragment = new DrawerFragment();
                args.putString(DrawerFragment.ITEM_NAME, dataList.get(position)
                        .getItemName());
                args.putInt(DrawerFragment.IMAGE_RESOURCE_ID, dataList.get(position)
                        .getImgResID());
                finish();
                break;
            default:
                break;
        }

        fragment.setArguments(args);
        FragmentManager frgManager = getFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                .commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(dataList.get(position).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return false;
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            SelectItem(position);

        }
    }

}