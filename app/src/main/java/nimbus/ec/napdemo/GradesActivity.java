package nimbus.ec.napdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nimbus.ec.napdemo.library.DatabaseHandler;
import nimbus.ec.napdemo.library.UserFunctions;


public class GradesActivity extends Drawer_man {
    ListView grades_container;
    ProgressBar loader;
    MrItem bean;
    DatabaseHandler dbhan;
    HashMap<String,String> user_details;
    private ArrayList<Object> itemList;
    UserFunctions userfnc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        grades_container = (ListView) findViewById(R.id.gradeslistView);
        loader = (ProgressBar) findViewById(R.id.progressgrades);
        dbhan = new DatabaseHandler(getApplicationContext());
        user_details = dbhan.getUserDetails();
        getmygrades();
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
    private void getmygrades(){
        itemList = new ArrayList<Object>();
        new getgrades().execute();
        Listor adapter = new Listor(this,itemList);
        grades_container.setAdapter(adapter);
        grades_container.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    /*String url = (String) view.getTag();
                    DetailActivity.launch(HomeActivity.this, view.findViewById(R.id.image), url);*/
            }
        });
        loader.setVisibility(View.INVISIBLE);
        grades_container.setVisibility(View.VISIBLE);
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
            Log.d("GOTIT", gotit.toString());
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
