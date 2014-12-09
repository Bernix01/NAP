package nimbus.ec.nap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Hola extends FragmentActivity implements AdapterView.OnItemSelectedListener {



    JSONParser jsonParser = new JSONParser();

    private Spinner spinnert;
    private ProgressDialog pDialog;
    private TextView name1;
    private TextView cid1;
    private static final String TAG_MATERIA = "materia";
    private static final String TAG_TA = "ta";
    private static final String TAG_TI = "ti";
    private static final String TAG_TG = "tg";
    private static final String TAG_EL = "l";
    private static final String TAG_PE = "pe";
    private static final String TAG_PR = "pr";
    private int calc;
    private JSONArray mComments = null;
    private static final String TAG_GRADES = "grades";
    public ArrayList<HashMap<String, String>> mCommentList2;


    public int a;

    private String tera = "";
    private String p = "";
    private String message = "";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private int success;
    private static final String GRADES_URL = "http://nimbus.pusku.com/grades.php";

    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hola);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        name1 = (TextView) findViewById(R.id.unames);
        cid1 = (TextView) findViewById(R.id.cid);
        spinnert = (Spinner) findViewById(R.id.qpselector);
        ArrayAdapter<CharSequence> term = ArrayAdapter.createFromResource(this,R.array.qs_array, android.R.layout.simple_spinner_item);
        term.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnert.setAdapter(term);
        spinnert.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                          int pos, long id) {
        a =  parent.getSelectedItemPosition();
        p = termselected();
        new Loadgrades().execute();
    }


    public String termselected(){
        switch (a){
            case 0:
                String j = "Q1P1";
                return j;
            case 1:
                String k = "Q1P2";
                return k;
            case 2:
                String l = "Q1P3";
                return l;
            case 3:
                String m = "EX";
                return m;
            case 4:
                String n = "Q1";
                return n;
            case 5:
                String o = "Q2P1";
                return o;
            case 6:
                String p = "Q2P2";
                return p;
            case 7:
                String q = "Q2P3";
                return q;
            case 8:
                String r = "EXQ2";
                return r;
            case 9:
                String s = "FINAL";
                return s;

        }
        return null;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hola, menu);
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

    private void updateList() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Hola.this);

        String post_name = sp.getString("uname","unam");
        String post_cid = sp.getString("cid","ci");
        String curse = sp.getString("course", "anon");
        calc = Integer.parseInt(curse);

        name1.setText(post_name);
        cid1.setText(post_cid);


        if (success == 1) {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        tera = p;
        mViewPager.setAdapter(mSectionsPagerAdapter);
            spinnert.setSelection(a);
        }

        Toast.makeText(Hola.this, message,Toast.LENGTH_SHORT).show();
    }


    public void updateJSONdata() {
        // Instantiate the arraylist to contain all the JSON data.
        // we are going to use a bunch of key-value pairs, referring
        // to the json element name, and the content, for example,
        // message it the tag, and "I'm awesome" as the content..



        // Bro, it's time to power up the J parserList<NameValuePair> params = new ArrayList<NameValuePair>();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Hola.this);
        String id = sp.getString("id", "anon");


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("p", p));
        JSONObject json = jsonParser.makeHttpRequest(GRADES_URL, "POST", params);

        // when parsing JSON stuff, we should probably
        // try to catch any exceptions:
        try {

            // I know I said we would check if "Posts were Avail." (success==1)
            // before we tried to read the individual posts, but I lied...
            // mComments will tell us how many "posts" or comments are
            // available
            mComments = json.getJSONArray(TAG_GRADES);

            // looping through all posts according to the json object returned

            success = json.getInt(TAG_SUCCESS);
            message = json.getString(TAG_MESSAGE);
            if (success == 1) {
                mCommentList2 = new ArrayList<HashMap<String, String>>();
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

                    mCommentList2.add(map);
                }
                }else{
                finish();
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public class Loadgrades extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Hola.this);
            pDialog.setMessage("Actualizando notas...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
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



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }



        /*this, mCommentList, new String[] { TAG_TA }, new int[] { R.id.title, R.id.message, R.id.username };*/
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new pfrag();
            Bundle args = new Bundle();
            args.putInt(pfrag.ARG_SECTION_NUMBER, position );
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Hola.this);

            String curse = sp.getString("course", "anon");
            calc = Integer.parseInt(curse);
            switch (calc){
                case 0:
                    return 12;
                case 1:
                    return 14;
                case 2:
                    return 15;
            }
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    String a = mCommentList2.get(0).get("materia").toUpperCase(l);
                    return a;
                case 1:
                    String b = mCommentList2.get(1).get("materia").toUpperCase(l);
                    return b;
                case 2:
                    String c = mCommentList2.get(2).get("materia").toUpperCase(l);
                    return c;
                case 3:
                    String d = mCommentList2.get(3).get("materia").toUpperCase(l);
                    return d;
                case 4:
                    String e = mCommentList2.get(4).get("materia").toUpperCase(l);
                    return e;
                case 5:
                    String f = mCommentList2.get(5).get("materia").toUpperCase(l);
                    return f;
                case 6:
                    String g = mCommentList2.get(6).get("materia").toUpperCase(l);
                    return g;
                case 7:
                    String h = mCommentList2.get(7).get("materia").toUpperCase(l);
                    return h;
                case 8:
                    String i = mCommentList2.get(8).get("materia").toUpperCase(l);
                    return i;
                case 9:
                    String j = mCommentList2.get(9).get("materia").toUpperCase(l);
                    return j;
                case 10:
                    String k = mCommentList2.get(10).get("materia").toUpperCase(l);
                    return k;
                case 11:
                    String l1 = mCommentList2.get(11).get("materia").toUpperCase(l);
                    return l1;
                case 12:
                    String m = mCommentList2.get(12).get("materia").toUpperCase(l);
                    return m;
                case 13:
                    String n = mCommentList2.get(13).get("materia").toUpperCase(l);
                    return n;
                case 14:
                    String o = mCommentList2.get(14).get("materia").toUpperCase(l);
                    return o;
                case 15:
                    String p = mCommentList2.get(15).get("materia").toUpperCase(l);
                    return p;

            }
            return null;
        }
    }

    public class pfrag extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public pfrag() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.subject, container, false);
            int j = getArguments().getInt(ARG_SECTION_NUMBER);
            TextView tars = (TextView) rootView.findViewById(R.id.ta);
            tars.setText(mCommentList2.get(j).get("ta"));
            TextView tirs = (TextView) rootView.findViewById(R.id.ti);
            tirs.setText(mCommentList2.get(j).get("ti"));
            TextView tgrs = (TextView) rootView.findViewById(R.id.tg);
            tgrs.setText(mCommentList2.get(j).get("tg"));
            TextView lrs = (TextView) rootView.findViewById(R.id.el);
            lrs.setText(mCommentList2.get(j).get("l"));
            TextView pers = (TextView) rootView.findViewById(R.id.pe);
            pers.setText(mCommentList2.get(j).get("pe"));
            TextView prs = (TextView) rootView.findViewById(R.id.pr);
            prs.setText(mCommentList2.get(j).get("pr"));
            return rootView;
        }

    }


    public static class qfrag extends Fragment {
        public qfrag(){

        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
                View rootView2 = inflater.inflate(R.layout.activity_calendar, container, false);


                return rootView2;
        }

    }

}
