package nimbus.ec.napdemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nimbus.ec.napdemo.library.DatabaseHandler;
import nimbus.ec.napdemo.library.Rounder;
import nimbus.ec.napdemo.library.UserFunctions;


public class Drawer_man extends ActionBarActivity {

    protected RelativeLayout fullLayout;
    protected RelativeLayout actContent;
    ActionBarDrawerToggle mDrawerToggle;
    protected RelativeLayout profile_container;
    DrawerLayout mDrawerLayout;
    Bitmap p_image_drawer;
    DatabaseHandler dbhan;
    HashMap<String,String> user_details;
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
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView menu = (ListView) findViewById(R.id.menu_lview);
        dbhan = new DatabaseHandler(getApplicationContext());
        user_details = dbhan.getUserDetails();
        ImageView p_image = (ImageView) findViewById(R.id.drawer_p_image);
        TextView name = (TextView) findViewById(R.id.drawer_name);
        TextView curso = (TextView) findViewById(R.id.drawer_curso);
        TextView nick = (TextView) findViewById(R.id.drawer_user);
        Picasso.with(getApplicationContext()).load("http://attx.nimbus.ec/nap_demo/"+user_details.get("p_image")).transform(new Rounder(4, 0)).fit().centerCrop().into(p_image);
        profile_container = (RelativeLayout) findViewById(R.id.drawer_prof_container);
        profile_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(getPackageManager().getActivityInfo(getComponentName(),0).name.equals("nimbus.ec.nap.ProfileActivity")){
                        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                            mDrawerLayout.closeDrawers();
                        }
                    }else{
                        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                            mDrawerLayout.closeDrawers();
                        }
                        Intent go = new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(go);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        name.setText(user_details.get("name"));
        nick.setText(user_details.get("nicename"));
        curso.setText(user_details.get("curso")+ " \"" + user_details.get("paralelo").toUpperCase()+"\"");
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
                        try {
                            if(getPackageManager().getActivityInfo(getComponentName(),0).name.equals("nimbus.ec.napdemo.MainActivity")){
                                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                                    mDrawerLayout.closeDrawers();
                                }
                                break;
                            }else{
                                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                                    mDrawerLayout.closeDrawers();
                                }
                                Intent go = new Intent(getApplicationContext(),MainActivity.class);
                                go.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(go);
                                finish();
                                break;
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    case 1:

                        try {
                            if(getPackageManager().getActivityInfo(getComponentName(),0).name.equals("nimbus.ec.napdemo.GradesActivity")){
                                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                                    mDrawerLayout.closeDrawers();
                                }
                                break;
                            }else{
                                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                                    mDrawerLayout.closeDrawers();
                                }
                                Intent go = new Intent(getApplicationContext(),GradesActivity.class);
                                go.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(go);
                                finish();
                                break;
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    default:
                        break;
                }
            }

        });
       mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, mtoolbar,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                try {
                    getSupportActionBar().setTitle((getPackageManager().getActivityInfo(getComponentName(),0).loadLabel(getPackageManager()).toString()));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("NAP");
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.setFocusableInTouchMode(false);
       mDrawerToggle.syncState();
    }
  /*  @Override
    public void init(Bundle savedInstanceState) {
        someMethod("http://attx.nimbus.ec/nap_demo/"+user_details.get("p_image"));
        // add first account
        MaterialAccount account = new MaterialAccount(user_details.get("name"),user_details.get("mail"),p_image_drawer,this.getResources().getDrawable(R.drawable.background_tab));
        this.addAccount(account);

        // set listener
        this.setAccountListener(this);

        // add your sections

        this.addDivisor();
        // add custom colored section with only text

        Intent i = new Intent(this,SettingsActivity.class);
        this.addSection(this.newSection("Settings",this.getResources().getDrawable(R.drawable.grade_blue_bg),i));

    }
    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            p_image_drawer = bitmap;
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    private void someMethod( String url) {
        Picasso.with(this).load(url).into(target);
    }

    @Override
    public void onDestroy() {  // could be in onPause or onStop
        Picasso.with(this).cancelRequest(target);
        super.onDestroy();
    }*/
    @Override
    public void onBackPressed()
    {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawers();
        else
            super.onBackPressed();
    }

    /*@Override
    public void onAccountOpening(MaterialAccount materialAccount) {

    }

    @Override
    public void onChangeAccount(MaterialAccount materialAccount) {

    }*/

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
        //
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                mDrawerLayout.closeDrawers();}
            else{
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }

            switch (item.getItemId()) {
                case R.id.action_logouts:
                    Log.d("Tango","tingo");
                    UserFunctions userFunctions = new UserFunctions();
                    userFunctions.logoutUser(getApplicationContext());
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    // Closing dashboard screen
                    finish();
                    break;
                default:
                    return super.onOptionsItemSelected(item);
            }
            // Handle our other action bar items...

            return true;
            // Handle your other action bar items...

    }
}
