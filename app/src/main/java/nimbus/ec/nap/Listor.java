package nimbus.ec.nap;

/**
 * Created by gbern_000 on 10/12/2014.
 */
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Listor extends BaseAdapter{

    ArrayList<Object> itemList;

    public Activity context;
    public LayoutInflater inflater;

    public Listor(Activity context,ArrayList<Object> itemList) {
        super();

        this.context = context;
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
        TextView txtViewDate;
        TextView txtViewTitle;
        TextView txtViewDescription;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder;
        if(convertView==null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_new_itemlist, null);

            holder.txtViewDate = (TextView) convertView.findViewById(R.id.ndate);
            holder.txtViewTitle = (TextView) convertView.findViewById(R.id.ntitle);
            holder.txtViewDescription = (TextView) convertView.findViewById(R.id.ncontent);

            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();

        MrItem bean = (MrItem) itemList.get(position);

        holder.txtViewDate.setText(bean.getDate());
        holder.txtViewTitle.setText(bean.getTitle());
        holder.txtViewDescription.setText(bean.getDescription());

        return convertView;
    }

}
