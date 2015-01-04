package nimbus.ec.napdemo;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nimbus.ec.napdemo.library.UserFunctions;

/**
 * Created by gbern_000 on 22/12/2014.
 */
public class News_fragment extends Fragment {
    ListView newer;
    ProgressBar newsloader;
    UserFunctions userFunctions;
    private ArrayList<Object> itemList;
    private MrItem bean;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RelativeLayout main_content = (RelativeLayout) inflater.inflate(R.layout.activity_main,container,false);
        newer = (ListView) main_content.findViewById(R.id.nlistView);
        newsloader = (ProgressBar) main_content.findViewById(R.id.progresnews);
        newsloader.setVisibility(View.VISIBLE);
        newer.setVisibility(View.INVISIBLE);
        prepareNews();
        return main_content;

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
        itemList = new ArrayList<Object>();
        new getnews().execute();

    }


    private class getnews extends AsyncTask<String,Integer,Boolean> {

        UserFunctions userFunctionsasd;
        JSONObject news;

        protected Boolean doInBackground(String... params){
            userFunctionsasd = new UserFunctions();
            news = userFunctionsasd.getnews();
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
        Listor adapter = new Listor(this.getActivity(),itemList);
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
