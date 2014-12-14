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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nimbus.ec.napdemo.library.UserFunctions;

public class DashboardActivity extends Drawer_man {
	UserFunctions userFunctions;
	Button btnLogout;
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
        	btnLogout = (Button) findViewById(R.id.logout);
        	
        	btnLogout.setOnClickListener(new View.OnClickListener() {
    			
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				userFunctions.logoutUser(getApplicationContext());
    				Intent login = new Intent(getApplicationContext(), LoginActivity.class);
    	        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	        	startActivity(login);
    	        	// Closing dashboard screen
    	        	finish();
    			}
    		});
            newer = (ListView) findViewById(R.id.nlistView);
            newsloader = (ProgressBar) findViewById(R.id.progresnews);

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

    // Add one item into the Array List
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
        Listor adapter = new Listor(this,itemList);
        newer.setAdapter(adapter);
        newer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    /*String url = (String) view.getTag();
                    DetailActivity.launch(HomeActivity.this, view.findViewById(R.id.image), url);*/
            }
        });
        newsloader.setVisibility(View.INVISIBLE);
        newer.setVisibility(View.VISIBLE);
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

                    int l = news.length() - 1;
                    for (int i = 0; i <= l; i++) {
                        JSONObject noti;
                            noti = noticias.getJSONObject(i);
                        AddObjectToList(noti.getString("noti_date"),noti.getString("noti_title"),noti.getString("noti_content"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}