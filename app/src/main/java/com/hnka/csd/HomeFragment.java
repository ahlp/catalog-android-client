package com.hnka.csd;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

public class HomeFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private HomeCustomAdapter adapter;

    public HomeFragment() {};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new HomeCustomAdapter(this.getContext());

        adapter.addSectionHeaderItem(new HomeObject("Histórico"));
        adapter.addItem(new HomeObject("How to get away with murder", "Assisti o episódio 5 da 1a temporada", "https://source.unsplash.com/300x300/?movies"));
        adapter.addItem(new HomeObject("Supernatural", "Assisti o episódio 9 da 3a temporada", "https://source.unsplash.com/300x300/?movies"));
        adapter.addItem(new HomeObject("Cheese in the trap", "Assisti o episódio 3", "https://source.unsplash.com/400x300/?movies"));

        adapter.addSectionHeaderItem(new HomeObject("O que você acompanha"));
        adapter.addItem(new HomeObject("Dirk Gently", "3 temporada - 91 episódios", "https://source.unsplash.com/300x300/?movies"));
        adapter.addItem(new HomeObject("I am Not a Robot", "16 episódios", "https://source.unsplash.com/300x300/?movies"));
        adapter.addItem(new HomeObject("Agents of Shield", "4 tempoaradas - 106 episódios", "https://source.unsplash.com/300x300/?movies"));
        adapter.addItem(new HomeObject("It's okay, That's Love", "20 episódios", "https://source.unsplash.com/300x300/?movies"));

        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }
}
