package com.hnka.csd;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

public class HomeFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private HomeCustomAdapter mAdapter;

    public HomeFragment() {};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new HomeCustomAdapter(this.getContext());

        int y = 1;
        mAdapter.addSectionHeaderItem(new HomeObject(("Header Item #" + y), 0));
        y = 2;

        for (int i = 1; i < 30; i++) {
            mAdapter.addItem(new HomeObject(("Row Item #" + i), i*5));
            if (i % 4 == 0) {
                mAdapter.addSectionHeaderItem(new HomeObject(("Header Item #" + y), 0));
                y++;
            }
        }

        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }
}
