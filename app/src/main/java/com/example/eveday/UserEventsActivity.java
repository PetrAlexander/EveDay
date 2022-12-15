package com.example.eveday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UserEventsActivity extends AppCompatActivity {
    UserEventsViewModel viewModel;
    private RecyclerView recyclerViewCreatedEvents;
    private RecyclerView recyclerViewSubscribedEvents;

    private CreatedEventsAdapter createdEventsAdapter;
    private SubscribedEventsAdapter subscribedEventsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_events);
        initViews();
        viewModel = new ViewModelProvider(this).get(UserEventsViewModel.class);
        observeViewModel();
        setupClickListeners();
    }

    private void initViews() {
        recyclerViewCreatedEvents = findViewById(R.id.recyclerViewCreatedEvents);
        createdEventsAdapter = new CreatedEventsAdapter();
        recyclerViewCreatedEvents.setAdapter(createdEventsAdapter);

        recyclerViewSubscribedEvents = findViewById(R.id.recyclerViewSubscribedEvents);
        subscribedEventsAdapter = new SubscribedEventsAdapter();
        recyclerViewSubscribedEvents.setAdapter(subscribedEventsAdapter);
    }

    private void setupClickListeners() {
        createdEventsAdapter.setOnEventClickListener(
                new CreatedEventsAdapter.OnEventClickListener() {
                    @Override
                    public void onEventClick(Event event) {
                        Intent intent = EventDetailActivity.newIntent(
                                UserEventsActivity.this,
                                event
                        );
                        startActivity(intent);
                    }
                });

        subscribedEventsAdapter.setOnEventClickListener(
                new SubscribedEventsAdapter.OnEventClickListener() {
                    @Override
                    public void onEventClick(Event event) {
                        Intent intent = EventDetailActivity.newIntent(
                                UserEventsActivity.this,
                                event
                        );
                        startActivity(intent);
                    }
                });
    }

    private void observeViewModel() {
        viewModel.getCreatedEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                createdEventsAdapter.setEvents(events);
            }
        });

        viewModel.getSubscribedEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                subscribedEventsAdapter.setEvents(events);
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, UserEventsActivity.class);
    }
}