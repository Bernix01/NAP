package nimbus.ec.napdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nimbus.ec.napdemo.library.DatabaseHandler;
import nimbus.ec.napdemo.library.UserFunctions;


public class GradesActivity extends Fragment {
    DatabaseHandler dbhan;
    HashMap<String,String> user_details;
    UserFunctions userfnc;
    JSONObject notas;
    private final Handler handler = new Handler();
    private ArrayList<String> TIsTLES;
    private ViewPager pager;
    PagerSlidingTabStrip tabs;
    private MyPagerAdapter frag_grade_adapter;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout main_content = (RelativeLayout) inflater.inflate(R.layout.activity_grades,container,false);
        dbhan = new DatabaseHandler(this.getActivity().getApplicationContext());
        user_details = dbhan.getUserDetails();
        Log.d("Info","Obetniendo notas..");
        getmygrades();
        Log.d("Info", "Fin de obtención de notas..");
        tabs= (PagerSlidingTabStrip) main_content.findViewById(R.id.tabs);
        pager = (ViewPager) main_content.findViewById(R.id.pager);

        return main_content;
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
            Log.d("Position_grade", String.valueOf(position));
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
            Log.d("frag_pos",String.valueOf(position));
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
                frag_grade_adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());

                pager.setAdapter(frag_grade_adapter);

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
