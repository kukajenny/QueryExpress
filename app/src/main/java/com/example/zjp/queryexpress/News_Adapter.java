package com.example.zjp.queryexpress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zjp on 16-1-5.
 */
public class News_Adapter extends ArrayAdapter<Listitem>{
private int resourceId;

    public News_Adapter(Context context, int resource, List<Listitem> objects) {
        super(context, resource, objects);
        this.resourceId=resource;
    }

    @Override
    public View getView(int position,View converView,ViewGroup parent){
        Listitem listitem = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(converView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_listitem);
            viewHolder.text_context = (TextView)view.findViewById(R.id.text_context_listitem);
            viewHolder.text_time = (TextView)view.findViewById(R.id.text_time_listitem);

            view.setTag(viewHolder);
        }
        else {
            view=converView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.imageView.setImageResource(listitem.getImageId());
        viewHolder.text_context.setText(listitem.getComtext());
        viewHolder.text_context.setTextColor(listitem.getTextcolor());
        viewHolder.text_time.setText(listitem.getTime());
        viewHolder.text_time.setTextColor(listitem.getTextcolor());
        return view;
    }
    class ViewHolder{
        ImageView imageView;
        TextView text_context,text_time;
    }
}
