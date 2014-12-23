package nimbus.ec.napdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by gbern_000 on 19/12/2014.
 */
public class TermFragment_grades extends Fragment{

    private static final String ARG_POSITION = "position";
    private static JSONArray grades;
    private static Double promedio;
    private int position;
    ArrayList<Object> itemList;
    MrItem bean;
    public static TermFragment_grades newInstance(int position, JSONArray term, Double promedio) {
        setGrades(term);
        setPromedio(promedio);
        TermFragment_grades f = new TermFragment_grades();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    public static JSONArray getGrades() {
        return grades;
    }

    public static void setGrades(JSONArray grades) {
        TermFragment_grades.grades = grades;
    }

    public static Double getPromedio() {
        return promedio;
    }

    public static void setPromedio(Double promedio) {
        TermFragment_grades.promedio = promedio;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView grades_container;
        TextView promedio_txt;
        ProgressBar loader;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        FrameLayout fl = (FrameLayout) inflater.inflate(R.layout.layout_grades_tabcontent,container,false) ;
        fl.setLayoutParams(params);
        promedio_txt = (TextView) fl.findViewById(R.id.promedio_term);
        grades_container = (ListView) fl.findViewById(R.id.gradeslv);
        DecimalFormat df = new DecimalFormat("###.##");
        promedio_txt.setText(String.valueOf(df.format(promedio)));
        loader = (ProgressBar) fl.findViewById(R.id.progressgrades);
        itemList = new ArrayList<Object>();
        int l = getGrades().length() ;
        for (int i = 0; i < l; i++) {
            JSONObject termino;
            try {
                termino = getGrades().getJSONObject(i);
                AddObjectToList(termino.getString("materia"), termino.getString("pr"), termino.getString("ta"), termino.getString("ti"), termino.getString("tg"), termino.getString("l"), termino.getString("pe"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Listor_grades adapter = new Listor_grades(getActivity(),itemList);
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

        return fl;
    }
    // Add one item into the Array List
    public void AddObjectToList(String subject, String grade, String ta, String ti, String tg, String le, String pe)
    {
        bean = new MrItem();
        bean.setSubject(subject);
        bean.setGrade(grade);
        bean.setTa(ta);
        bean.setTi(ti);
        bean.setTg(tg);
        bean.setLe(le);
        bean.setPe(pe);
        itemList.add(bean);
    }
}