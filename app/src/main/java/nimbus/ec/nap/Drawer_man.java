package nimbus.ec.nap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.callback.PasswordCallback;


public class Drawer_man extends ActionBarActivity {

    protected RelativeLayout fullLayout;
    protected RelativeLayout actContent;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void setContentView(final int layoutResID) {
        fullLayout= (RelativeLayout) getLayoutInflater().inflate(R.layout.layout_drawer_man, null); // Your base layout here
        actContent= (RelativeLayout) fullLayout.findViewById(R.id.main_content);
        getLayoutInflater().inflate(layoutResID, actContent, true); // Setting the content of layout your provided to the act_content frame
        super.setContentView(fullLayout);
        // here you can get your drawer buttons and define how they should behave and what must they do, so you won't be needing to repeat it in every activity class
        Toolbar mtoolbar = (Toolbar) findViewById(R.id.Toolbar_nap);

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < getResources().getStringArray(R.array.drawer_menu_items).length; ++i) {
            list.add(getResources().getStringArray(R.array.drawer_menu_items)[i]);
        }
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView menu = (ListView) findViewById(R.id.menu_lview);
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        menu.setAdapter(adapter);

        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                switch (position)
                {
                    case 0:
                        break;
                    default:
                        break;
                }
            }

        });


        mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, mtoolbar,
                R.string.drawer_open, R.string.drawer_close) {
            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                try {
                    getSupportActionBar().setTitle((getPackageManager().getActivityInfo(getComponentName(),0).loadLabel(getPackageManager()).toString()));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("NAP");
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
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
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
}
