package nimbus.ec.napdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;

import com.astuetz.PagerSlidingTabStrip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nimbus.ec.napdemo.library.DatabaseHandler;
import nimbus.ec.napdemo.library.UserFunctions;


public class GradesActivity extends Drawer_man {
    DatabaseHandler dbhan;
    HashMap<String,String> user_details;
    UserFunctions userfnc;
    JSONObject notas;
    private final Handler handler = new Handler();
    private ArrayList<String> TIsTLES;
    private ViewPager pager;
    PagerSlidingTabStrip tabs;
    private MyPagerAdapter adapter;
    String[] terms = new String[]{
            "Q1-P1",
            "Q1-P2",
            "Q1-P3",
            "Q2-P1",
            "Q2-P2",
            "Q2-P3",
            "Q2-EX"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        dbhan = new DatabaseHandler(getApplicationContext());
        user_details = dbhan.getUserDetails();
        Log.d("Info","Obetniendo notas..");
        getmygrades();
        Log.d("Info", "Fin de obtención de notas..");
        tabs= (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);


    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {



        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TIsTLES.get(position);
        }

        @Override
        public int getCount() {
            return TIsTLES.size();
        }

        @Override
        public Fragment getItem(int position) {
            JSONArray nota_termino = null;
            Double promedio = 0.0;
            try{
                nota_termino = notas.getJSONArray(terms[position]);
                for (int i=0;i<nota_termino.length();i++){
                    JSONObject grade_term = nota_termino.getJSONObject(i);
                    promedio+= Double.parseDouble(grade_term.getString("pr"));
                }
                promedio= promedio/nota_termino.length();
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return TermFragment_grades.newInstance(position,nota_termino, promedio);
        }

    }


    private void getmygrades(){
        TIsTLES = new ArrayList<String>();
        new getgrades().execute();
    }
    private class getgrades extends AsyncTask<String,Integer,Boolean> {

        UserFunctions userFunction;
        JSONObject news;
        protected void onPreExecute(){
        }
        protected Boolean doInBackground(String... params){
            userfnc = new UserFunctions();
            news = userfnc.getgrades(user_details.get("uid"));
            return news.length() != 0;
        }
        protected void onProgressUpdate(Integer... progress){

        }
        protected void onPostExecute(Boolean gotit){
            if(gotit){
                try {
                    notas = news.getJSONObject("grades");
                    JSONArray names = notas.names();
                    for (int asds=0;asds<names.length();asds++) {
                       addTitleToTitles(terms[asds]);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new MyPagerAdapter(getSupportFragmentManager());

                pager.setAdapter(adapter);

                final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                        .getDisplayMetrics());
                pager.setPageMargin(pageMargin);
                tabs.setViewPager(pager);
            }
        }
    }

    private void addTitleToTitles(String title){
        TIsTLES.add(title);
    }

}
