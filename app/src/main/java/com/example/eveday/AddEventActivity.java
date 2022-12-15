package com.example.eveday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEventActivity extends AppCompatActivity {
    private EditText editTextEventName;
    private EditText editTextEventDescription;
    private EditText editTextEventLocation;
    private EditText editTextEventSize;
    private Button buttonAddEvent;

    private AddEventViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        initViews();

        viewModel = new ViewModelProvider(this).get(AddEventViewModel.class);

        setupClickListeners();
        observeViewModel();
    }

    private void initViews() {
        editTextEventName = findViewById(R.id.editTextEventName);
        editTextEventDescription = findViewById(R.id.editTextEventDescription);
        editTextEventLocation = findViewById(R.id.editTextEventLocation);
        editTextEventSize = findViewById(R.id.editTextEventSize);
        buttonAddEvent = findViewById(R.id.buttonAddEvent);
    }

    private void setupClickListeners() {
        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventName = getTrimmedValue(editTextEventName);
                String eventDescription = getTrimmedValue(editTextEventDescription);
                String eventLocation = getTrimmedValue(editTextEventLocation);
                int eventSize = Integer.parseInt(getTrimmedValue(editTextEventSize));
                viewModel.createEvent(eventName, eventDescription, eventLocation, eventSize);
                Intent intent = EventsActivity.newIntent(AddEventActivity.this);
                startActivity(intent);
            }
        });
    }

    private String getTrimmedValue(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddEventActivity.class);
    }

    private void observeViewModel() {
        viewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldCloseScreen) {
                finish();
            }
        });
    }
}