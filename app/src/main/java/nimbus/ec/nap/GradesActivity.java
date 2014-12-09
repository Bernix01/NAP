package nimbus.ec.nap;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class GradesActivity extends Activity {
    private ProgressDialog pDialog;
    private TextView name1;
    private TextView cid1;
    private static final String TAG_MATERIA = "materia";
    private static final String TAG_TA = "ta";
    private static final String TAG_TI = "ti";
    private static final String TAG_TG = "tg";
    private static final String TAG_EL = "el";
    private static final String TAG_PE = "pe";
    private static final String TAG_PR = "pr";
    private JSONArray mComments = null;
    private static final String TAG_POSTS = "posts";
    // manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> mCommentList;

    private static final String GRADES_URL = "http://andrapi.p.ht/login.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        // Show the Up button in the action bar.
        name1 = (TextView) findViewById(R.id.unames);
        cid1 = (TextView) findViewById(R.id.cid);

    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.grades, menu);
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


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }




   /* private BaseAdapter2 mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return dataObjects.length;
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject, null);
            return retval;
        }
    };
*/



    private void updateList() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(GradesActivity.this);

        String post_name = sp.getString("uname","unam");
        String post_cid = sp.getString("cid","ci");

        name1.setText(post_name);
        cid1.setText(post_cid);



    }



    public void updateJSONdata() {

        // Instantiate the arraylist to contain all the JSON data.
        // we are going to use a bunch of key-value pairs, referring
        // to the json element name, and the content, for example,
        // message it the tag, and "I'm awesome" as the content..

        mCommentList = new ArrayList<HashMap<String, String>>();

        // Bro, it's time to power up the J parser
        JSONParser jParser = new JSONParser();
        // Feed the beast our comments url, and it spits us
        // back a JSON object. Boo-yeah Jerome.
        JSONObject json = jParser.getJSONFromUrl(GRADES_URL);

        // when parsing JSON stuff, we should probably
        // try to catch any exceptions:
        try {

            // I know I said we would check if "Posts were Avail." (success==1)
            // before we tried to read the individual posts, but I lied...
            // mComments will tell us how many "posts" or comments are
            // available
            mComments = json.getJSONArray(TAG_POSTS);

            // looping through all posts according to the json object returned
            for (int i = 0; i < mComments.length(); i++) {
                JSONObject c = mComments.getJSONObject(i);

                // gets the content of each tag
                String materia = c.getString(TAG_MATERIA);
                String ta = c.getString(TAG_TA);
                String ti = c.getString(TAG_TI);
                String tg = c.getString(TAG_TG);
                String el = c.getString(TAG_EL);
                String pe = c.getString(TAG_PE);
                String pr = c.getString(TAG_PR);
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                map.put(TAG_MATERIA, materia);
                map.put(TAG_TA, ta);
                map.put(TAG_TI, ti);
                map.put(TAG_TG, tg);
                map.put(TAG_EL, el);
                map.put(TAG_PE, pe);
                map.put(TAG_PR, pr);

                // adding HashList to ArrayList
                mCommentList.add(map);

                // annndddd, our JSON data is up to date same with our array
                // list
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public class Loadgrades extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(GradesActivity.this);
            pDialog.setMessage("Updating grades if you are a bad student it will take a time...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            updateJSONdata();
            return null;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            updateList();
        }
    }


}
