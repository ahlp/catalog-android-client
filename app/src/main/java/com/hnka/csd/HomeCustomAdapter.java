package com.hnka.csd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.TreeSet;

public class HomeCustomAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;

    private ArrayList<HomeObject> data = new ArrayList<>();
    private TreeSet<Integer> sectionHeader = new TreeSet<>();

    private LayoutInflater inflater;

    public HomeCustomAdapter(Context context) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final HomeObject item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final HomeObject item) {
        data.add(item);
        sectionHeader.add(data.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public HomeObject getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = inflater.inflate(R.layout.home_row, null);
                    holder.titleText = (TextView) convertView.findViewById(R.id.rowTitle);
                    holder.subtitleText = (TextView) convertView.findViewById(R.id.rowSubtitle);
                    holder.imageThumb = (ImageView) convertView.findViewById(R.id.rowImage);
                    break;
                case TYPE_HEADER:
                    convertView = inflater.inflate(R.layout.home_header, null);
                    holder.titleText = (TextView) convertView.findViewById(R.id.sectionTitle);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(rowType == TYPE_ITEM){
            holder.titleText.setText(data.get(position).getTitle());
            holder.subtitleText.setText(data.get(position).getSubtitle());

            Picasso.get().load(data.get(position).getImage()).resize(120,120).into(holder.imageThumb);

        }else if(rowType == TYPE_HEADER){
            holder.titleText.setText(data.get(position).getTitle());
        }

        return convertView;
    }

    public static class ViewHolder {
        public TextView titleText;
        public TextView subtitleText;
        public ImageView imageThumb;
    }
}
