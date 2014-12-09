package nimbus.ec.nap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.TextView;

public class ProfileEActivity extends Activity {
    private TextView uname;
    private TextView couse;
    private TextView prld;

    private static String unames = "";
    private static String courses = "";
    private static String pls = "";
    private  String prls = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Show the Up button in the action bar.
        setupActionBar();
        uname = (TextView) findViewById(R.id.namep);
        couse = (TextView) findViewById(R.id.cip);
        prld = (TextView) findViewById(R.id.prldp);


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ProfileEActivity.this);
        unames = sp.getString("uname", "anon");
        courses = sp.getString("course","anon");
        pls = sp.getString("pl","anon");
        uname.setText(unames);
        couse.setText(courses);
        prld.setText(prl());
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public String prl(){
        int n = Integer.parseInt(pls);
        switch (n) {
            case 0:
                prls = "A";
                return prls;
            case 1:
                prls = "A";
                return prls;
            case 2:
                prls = "B";
                return prls;
            case 3:
                prls = "C";
                return prls;
            case 4:
                prls = "D";
                return prls;
            case 5:
                prls = "R";
                return prls;
        }
        return null;
    }
}
