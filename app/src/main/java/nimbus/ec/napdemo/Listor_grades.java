package nimbus.ec.napdemo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gbern_000 on 20/12/2014.
 */
public class Listor_grades extends BaseAdapter {

    ArrayList<Object> itemList;

    public Context context;
    public LayoutInflater inflater;

    public Listor_grades(Context context,ArrayList<Object> itemList) {
        super();
        this.itemList = itemList;

        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder
    {
        TextView txtViewindex;
        TextView txtViewsubject;
        TextView txtViewgade;
        TextView txtViewsubgr;
        RelativeLayout rltgradecontainer;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder;
        if(convertView==null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_grade_itemlist,parent,false);
            holder.txtViewindex = (TextView) convertView.findViewById(R.id.indexL_txtv);
            holder.txtViewsubgr = (TextView) convertView.findViewById(R.id.subgrades);
            holder.rltgradecontainer = (RelativeLayout) convertView.findViewById(R.id.grade_container);
            holder.txtViewsubject = (TextView) convertView.findViewById(R.id.subject_txtv);
            holder.txtViewgade = (TextView) convertView.findViewById(R.id.grade_txtv);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        MrItem bean = (MrItem) itemList.get(position);

        holder.txtViewindex.setText((bean.getSubject()).substring(0,1));
        holder.txtViewsubject.setText(bean.getSubject());
        holder.txtViewsubgr.setText("TA: "+bean.getTa()+"   TI: "+bean.getTi()+"   TG: "+bean.getTg()+"   LE: "+bean.getle()+"   PE: "+bean.getPe());
        holder.txtViewgade.setText(String.valueOf(bean.getGrade()));
        if (bean.getGrade() < 7.0){
            holder.rltgradecontainer.setBackgroundResource(R.drawable.grade_red_bg);
        }else{
            holder.rltgradecontainer.setBackgroundResource(R.drawable.grade_blue_bg);
        }
        return convertView;
    }

}
