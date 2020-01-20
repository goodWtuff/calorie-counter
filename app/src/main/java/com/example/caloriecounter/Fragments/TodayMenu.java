package com.example.caloriecounter.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecounter.R;
import com.example.caloriecounter.ui.Adapters.IngestionListAdapter;


public class TodayMenu extends Fragment {
    private RecyclerView ingestionList;
    private IngestionListAdapter ingestionListAdapter;

    public static TodayMenu newInstance() {
        TodayMenu fragment = new TodayMenu();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View elements initialization
        View v = inflater.inflate(R.layout.fragment_today_menu, container, false);
        ingestionList = v.findViewById(R.id.ingestion_list);

        //Ingestion list initialization
        initIngestionList();
        return v;
    }

    //Ingestion list initialization
    private void initIngestionList(){
        ingestionListAdapter = new IngestionListAdapter(getContext());
        ingestionList.setAdapter(ingestionListAdapter);
        ingestionList.setLayoutManager(new LinearLayoutManager(getContext()));
    }


}
