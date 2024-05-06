package com.example.a71;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ItemDetailsActivity extends AppCompatActivity {

    private TextView itemNameTextView;
    private TextView dateTextView;
    private TextView locationTextView;
    private Button removeButton;
    private LostItemsDbHandler lostItemsDbHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        long itemId = getIntent().getLongExtra("item_id", 0);

        lostItemsDbHandler = new LostItemsDbHandler(this);
        LostItem lostItem = lostItemsDbHandler.getLostItemById(itemId);

        itemNameTextView = findViewById(R.id.itemNameTextView);
        dateTextView = findViewById(R.id.dateTextView);
        locationTextView = findViewById(R.id.locationTextView);

        String lOrF = lostItem.getFound() == 0 ? "Lost " : "Found ";
        itemNameTextView.setText(lOrF + lostItem.getLostItemName());
        String date = getFormattedDate(lostItem.getDate());
        dateTextView.setText(lOrF + date);
        locationTextView.setText("At " + lostItem.getLocation());

        removeButton = findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lostItemsDbHandler.deleteLostItem(lostItem.getId());
                Intent intent = new Intent(ItemDetailsActivity.this, LANDFListActivity.class);
                startActivity(intent);
            }
        });


    }
    private String getFormattedDate(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date lostDate = null;
        try {
            lostDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new Date());

        long diffInMillis = currentDate.getTimeInMillis() - lostDate.getTime();
        long daysDiff = TimeUnit.MILLISECONDS.toDays(diffInMillis);

        if (daysDiff == 0) {
            return "Today";
        } else if (daysDiff == 1) {
            return "Yesterday";
        } else {
            return daysDiff + " days ago";
        }
    }
}
