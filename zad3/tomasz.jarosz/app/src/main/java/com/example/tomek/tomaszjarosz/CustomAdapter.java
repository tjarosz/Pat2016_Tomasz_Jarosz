package com.example.tomek.tomaszjarosz;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private List<Item> items;
    private static LayoutInflater inflater=null;
    private List<Bitmap> bitmaps;
    private final View footer;
    private final View footer2;

    @SuppressLint("InflateParams")
    public CustomAdapter(Activity activity, List<Item> itemList, List<Bitmap> bitmapList) {

        this.items=itemList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footer = inflater.inflate(R.layout.view_footer, null, false);
        footer2 = inflater.inflate(R.layout.view_footer2, null, false);
        this.bitmaps = bitmapList;

    }
    @Override
    public int getCount() {
        return items.size()+1;
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHandler holder;
        if (!MainActivity.isLastPage){
            if (position == (getCount() - 1)) {
                return footer;
            }
        }
        else {
            if (position == (getCount() - 1)) {
                return footer2;
            }
        }
        if (convertView == null | vi == footer | vi == footer2){
            vi = inflater.inflate(R.layout.list_item,null,false);
            holder = new ViewHandler(vi);
            vi.setTag(holder);
        }else{
            holder=(ViewHandler)convertView.getTag();
        }
        setDownloadedContentOnGridElements(position, holder);
        return vi;
    }


    private void setDownloadedContentOnGridElements(int position, ViewHandler holder) {
        Item item=getItem(position);

        holder.titleView.setText(item.getTitle());
        holder.descView.setText(item.getDesc());
        setDownloadedBitmapsOnGridElements(position, holder);

    }
    private void setDownloadedBitmapsOnGridElements(int position, ViewHandler holder) {
        if (bitmaps.size() >= position + 1) {
            if (bitmaps.get(position) != null)
                holder.imageView.setImageBitmap(bitmaps.get(position));
            else
                holder.imageView.setImageResource(R.drawable.not_available);
        }
        else {
            holder.imageView.setImageResource(R.drawable.not_available);
        }
    }

    public void addBitmaps(List<Bitmap> bitmapList){

        this.bitmaps = bitmapList;
        notifyDataSetChanged();
    }
    public void addItems(List<Item> list){

        this.items = list;
        notifyDataSetChanged();
    }

    class ViewHandler {
        final TextView titleView;
        final TextView descView;
        final ImageView imageView;
        public ViewHandler(View view)
        {
            this.titleView = (TextView)view.findViewById(R.id.firstLine);
            this.descView = (TextView)view.findViewById(R.id.secondLine);
            this.imageView = (ImageView)view.findViewById(R.id.imageView);
        }
    }
}
