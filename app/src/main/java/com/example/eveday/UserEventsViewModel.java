package com.example.eveday;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserEventsViewModel extends ViewModel {
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference eventsInfoReference;
    private DatabaseReference userEventsReference;
    private FirebaseUser currentUser;

    private MutableLiveData<List<Event>> createdEvents = new MutableLiveData<>();
    private MutableLiveData<List<Event>> subscribedEvents = new MutableLiveData<>();

    public UserEventsViewModel() {
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        eventsInfoReference = firebaseDatabase.getReference(DB.DB_EVENTS_INFO);
        userEventsReference = firebaseDatabase.getReference(DB.DB_USER_EVENTS);

        eventsInfoReference.child(currentUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Event> createdEventsDb = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Event newEvent = dataSnapshot.getValue(Event.class);
                            createdEventsDb.add(newEvent);
                        }
                        createdEvents.setValue(createdEventsDb);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        userEventsReference.child(currentUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Event> subscribedEventsDb = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Event newEvent = dataSnapshot.getValue(Event.class);
                            subscribedEventsDb.add(newEvent);
                        }
                        subscribedEvents.setValue(subscribedEventsDb);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public LiveData<List<Event>> getCreatedEvents() {
        return createdEvents;
    }

    public LiveData<List<Event>> getSubscribedEvents() {
        return subscribedEvents;
    }
}
