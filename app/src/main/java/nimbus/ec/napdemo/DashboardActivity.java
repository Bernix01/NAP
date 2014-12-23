/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package nimbus.ec.napdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nimbus.ec.napdemo.library.UserFunctions;

public class DashboardActivity extends Drawer_man {
	UserFunctions userFunctions;
    ListView newer;
    ProgressBar newsloader;
    private ArrayList<Object> itemList;
    private MrItem bean;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /**
         * Dashboard Screen for the application
         * */        
        // Check login status in database
        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())){
        	setContentView(R.layout.activity_main);
            newer = (ListView) findViewById(R.id.nlistView);
            newsloader = (ProgressBar) findViewById(R.id.progresnews);
            newsloader.setVisibility(View.VISIBLE);
            newer.setVisibility(View.INVISIBLE);
        	prepareNews();
        }else{
        	// user is not logged in show login screen
        	Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(login);
        	// Closing dashboard screen
        	finish();
        }
    }
    public void AddObjectToList(String date, String title, String desc)
    {
        bean = new MrItem();
        bean.setDescription(desc);
        bean.setDate(date);
        bean.setTitle(title);
        itemList.add(bean);
    }

    private void prepareNews(){
        itemList = new ArrayList <Object>();
        new getnews().execute();

    }

    private class getnews extends AsyncTask<String,Integer,Boolean> {

        UserFunctions userFunction;
        JSONObject news;
        protected void onPreExecute(){

        }

        protected Boolean doInBackground(String... params){
            userFunction = new UserFunctions();
            news = userFunctions.getnews();
            return news.length() != 0;
        }
        protected void onProgressUpdate(Integer... progress){

        }
        protected void onPostExecute(Boolean gotit){
            Log.d("GOTIT",gotit.toString());
            if(gotit){
                JSONArray noticias = null;
                try {
                    noticias = news.getJSONArray("notic");
                    Log.d("Length", String.valueOf(noticias.length()));
                    int l = noticias.length() - 1;
                    for (int i = 0; i <= l; i++) {
                        JSONObject noti;
                            noti = noticias.getJSONObject(i);
                        AddObjectToList(noti.getString("noti_date"),noti.getString("noti_title"),noti.getString("noti_content"));
                        Log.d("Adding #",String.valueOf(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                charge_it();
            }
        }
    }

    private void charge_it (){
        newsloader.setVisibility(View.INVISIBLE);
        newer.setVisibility(View.VISIBLE);
        Log.d("Length", String.valueOf(itemList.size()));
        Listor adapter = new Listor(this,itemList);
        SwingLeftInAnimationAdapter animationAdapter = new SwingLeftInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(newer);
        newer.setAdapter(animationAdapter);
        newer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    /*String url = (String) view.getTag();
                    DetailActivity.launch(HomeActivity.this, view.findViewById(R.id.image), url);*/
            }
        });
    }


}