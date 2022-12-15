package com.example.eveday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity {
    private EventsViewModel viewModel;

    private RecyclerView recyclerViewEvents;
    private EventsAdapter eventsAdapter;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        initViews();
        viewModel = new ViewModelProvider(this).get(EventsViewModel.class);
        observeViewModel();
        setupClickListeners();
    }

    private void observeViewModel() {
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    Intent intent = LoginActivity.newIntent(EventsActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });

        viewModel.getEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                eventsAdapter.setEvents(events);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_logout) {
            viewModel.logout();
        }
        if (item.getItemId() == R.id.item_events_info) {
            Intent intent = UserEventsActivity.newIntent(EventsActivity.this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        recyclerViewEvents = findViewById(R.id.recyclerViewEvents);
        eventsAdapter = new EventsAdapter();
        recyclerViewEvents.setAdapter(eventsAdapter);

        floatingActionButton = findViewById(R.id.buttonAddEvent);
    }

    private void setupClickListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddEventActivity.newIntent(EventsActivity.this);
                startActivity(intent);
            }
        });

        eventsAdapter.setOnEventClickListener(new EventsAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(Event event) {
                Intent intent = EventDetailActivity.newIntent(EventsActivity.this, event);
                startActivity(intent);
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, EventsActivity.class);
    }
}