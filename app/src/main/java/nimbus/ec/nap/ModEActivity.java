package nimbus.ec.nap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ModEActivity extends Activity {

    private ProgressDialog pDialog;
    private TextView uname;
    private TextView usere;
    private TextView couse;
    private TextView prld;

    private static String unames = "";
    private static String usernames = "";
    private static String courses = "";
    private static String pls = "";
    private  String prls = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        // Show the Up button in the action bar.
        setupActionBar();
        uname = (TextView) findViewById(R.id.name);
        couse = (TextView) findViewById(R.id.ci);
        usere = (TextView) findViewById(R.id.usr);
        prld = (TextView) findViewById(R.id.prldr);


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ModEActivity.this);
        unames = sp.getString("uname", "anon");
        usernames = sp.getString("username", "anon");
        courses = sp.getString("course","anon");
        pls = sp.getString("pl","anon");
        uname.setText(unames);
        usere.setText(usernames);
        couse.setText(courses);
        prld.setText(prl());
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.loged, menu);
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


    public void news (View view){
        Intent newsi = new Intent(this,NewsActivity.class);
        startActivity(newsi);
    }
    public void about (View view){
        Intent abut = new Intent(this,AboutActivity.class);
        startActivity(abut);
    }
    public void grades (View view){
        Intent gradeac = new Intent(this,GradesActivity.class);
        startActivity(gradeac);
    }
    public void profile (View view){
        Intent prof = new Intent(this,ProfileEActivity.class);
        startActivity(prof);
    }
    public void crono (View view){
        Intent calendari = new Intent(this,CalendarActivity.class);
        startActivity(calendari);
    }
    public void readcm (View view){
        Intent calendari = new Intent(this,Hola.class);
        startActivity(calendari);
    }
    public void rankac (View view){
        Intent rank = new Intent(this,RankActivity.class);
        startActivity(rank);
    }
}
