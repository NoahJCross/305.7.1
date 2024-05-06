package com.example.a71;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LANDFListActivity extends AppCompatActivity {

    private List<LostItem> lostItems;
    private RecyclerView lAndFRecyclerView;
    private LostItemsDbHandler lostItemsDbHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_and_found);

        lostItemsDbHandler = new LostItemsDbHandler(this);
        lostItems = lostItemsDbHandler.getLostItems();

        lAndFRecyclerView = findViewById(R.id.lAndFRecyclerView);
        LANDFViewAdapter adapter = new LANDFViewAdapter(lostItems, this);
        lAndFRecyclerView.setAdapter(adapter);
        lAndFRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
