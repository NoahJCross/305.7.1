package com.example.a71;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Locale;

public class CreateAdvertActivity extends AppCompatActivity {

    private CheckBox lostCheckBox;
    private CheckBox foundCheckBox;
    private TextView createItemName;
    private TextView createPhoneNumber;
    private TextView createDescription;
    private TextView createDate;
    private TextView createLocation;
    private Button saveButton;
    private int found = -1;
    private LostItemsDbHandler lostItemsDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_lost_item);

        lostItemsDbHandler = new LostItemsDbHandler(this);

        lostCheckBox = findViewById(R.id.lostCheckBox);
        foundCheckBox = findViewById(R.id.foundCheckBox);
        createItemName = findViewById(R.id.createItemName);
        createPhoneNumber = findViewById(R.id.createPhoneNumber);
        createDescription = findViewById(R.id.createDescription);
        createDate = findViewById(R.id.createDate);
        createLocation = findViewById(R.id.createLocation);
        saveButton = findViewById(R.id.saveButton);

        lostCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                found = 0;
                foundCheckBox.setChecked(false);
            }
        });

        foundCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                found = 1;
                lostCheckBox.setChecked(false);
            }
        });

        createDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateAdvertActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", selectedDayOfMonth, selectedMonth + 1, selectedYear);
                        createDate.setText(formattedDate);
                    }
                }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
    }

    private void saveItem() {
        String itemName = createItemName.getText().toString();
        String phoneNumber = createPhoneNumber.getText().toString();
        String description = createDescription.getText().toString();
        String date = createDate.getText().toString();
        String location = createLocation.getText().toString();

        if (itemName.isEmpty() || phoneNumber.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty() || found == -1) {
            Toast.makeText(this, "Please fill in all fields and select Lost or Found.", Toast.LENGTH_SHORT).show();
            return;
        }

        LostItem item = new LostItem(itemName, phoneNumber, description, date, location, found);
        lostItemsDbHandler.addLostItem(item);
        finish();
    }
}
