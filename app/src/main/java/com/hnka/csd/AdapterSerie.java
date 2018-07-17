package com.hnka.csd;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterSerie extends BaseAdapter {

    private final List<Serie> series;
    private final Activity activity;

    public AdapterSerie(Activity activity, List<Serie> series) {
        this.activity = activity;
        this.series = series;
    }

    @Override
    public int getCount() {
        return series.size();
    }

    @Override
    public Object getItem(int position) {
        return series.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView = activity.getLayoutInflater().inflate(R.layout.content_card_serie, parent, false);
        Serie serie = series.get(position);

        //Recupera os elementos do layout
        TextView title = (TextView) newView.findViewById(R.id.textView);
        TextView about = (TextView) newView.findViewById(R.id.textView3);

        //Atribui as informações do itemRSS
        title.setText(serie.getTitle());
        about.setText(serie.getAbout());

        return newView;
    }
}
