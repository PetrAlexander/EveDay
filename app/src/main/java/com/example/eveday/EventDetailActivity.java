package com.example.eveday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EventDetailActivity extends AppCompatActivity {
    private static final String EXTRA_EVENT = "event";

    private TextView textViewEventName;
    private TextView textViewEventLocation;
    private TextView textViewEventDescription;
    private TextView textViewEventRemainingSeats;
    private Button buttonSubscribe;


    private Event currentEvent;
    private EventDetailViewModel viewModel;
    private EventDetailViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        initViews();
        currentEvent = (Event) getIntent().getSerializableExtra(EXTRA_EVENT);
        viewModelFactory = new EventDetailViewModelFactory(currentEvent);
        viewModel = new ViewModelProvider(this, viewModelFactory)
                .get(EventDetailViewModel.class);
        observeViewModel();

        textViewEventName.setText(currentEvent.getEventName());
        textViewEventLocation.setText(currentEvent.getEventLocation());
        textViewEventDescription.setText(currentEvent.getEventDescription());

        buttonSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.subscribe();
            }
        });
    }

    private void initViews() {
        textViewEventName = findViewById(R.id.textViewEventName);
        textViewEventLocation = findViewById(R.id.textViewEventLocation);
        textViewEventDescription = findViewById(R.id.textViewEventDescription);
        textViewEventRemainingSeats = findViewById(R.id.textViewEventRemainingSeats);
        buttonSubscribe = findViewById(R.id.buttonSubscribe);
    }

    private void observeViewModel() {

        viewModel.getRemainingSeats().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer remainingSeats) {
                textViewEventRemainingSeats.setText(String.valueOf(remainingSeats));
            }
        });

        viewModel.getIsClickable().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isClickable) {
                buttonSubscribe.setClickable(isClickable);
            }
        });

        viewModel.getStatus().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String status) {
                buttonSubscribe.setText(status);
            }
        });

    }

    public static Intent newIntent(Context context, Event event) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.putExtra(EXTRA_EVENT, event);
        return intent;
    }
}