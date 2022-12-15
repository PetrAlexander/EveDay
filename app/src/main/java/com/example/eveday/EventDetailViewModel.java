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


public class EventDetailViewModel extends ViewModel {

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersOnEventReference;
    private DatabaseReference eventInfoReference;
    private DatabaseReference userEventsReference;
    private FirebaseUser currentUser;

    private Event currentEvent;

    private MutableLiveData<Integer> remainingSeats = new MutableLiveData<>();
    private MutableLiveData<Boolean> isClickable = new MutableLiveData<>(true);
    private MutableLiveData<String> status = new MutableLiveData<>("Подписаться");

    public EventDetailViewModel(Event event) {
        auth = FirebaseAuth.getInstance();

        currentEvent = event;
        remainingSeats.setValue(event.getEventSize() - event.getSubscribersNow());
        currentUser = auth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        usersOnEventReference = firebaseDatabase.getReference(DB.DB_EVENT_USERS);
        eventInfoReference = firebaseDatabase.getReference(DB.DB_EVENTS_INFO);
        userEventsReference = firebaseDatabase.getReference(DB.DB_USER_EVENTS);

        checkStatus();
        usersOnEventReference.child(currentEvent.getEventId()).child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String onEventId = null;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    onEventId = dataSnapshot.toString();
                }
                if (onEventId != null) {
                    isClickable.setValue(false);
                    status.setValue("Вы подписаны");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        eventInfoReference.child(currentEvent.getEventCreatorId())
                .child(currentEvent.getEventId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Event eventFromDb = snapshot.getValue(Event.class);
                        int newRemainSeats =
                                eventFromDb.getEventSize() - eventFromDb.getSubscribersNow();
                        remainingSeats.setValue(newRemainSeats);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public LiveData<Integer> getRemainingSeats() {
        return remainingSeats;
    }

    public LiveData<Boolean> getIsClickable() {
        return isClickable;
    }

    public LiveData<String> getStatus() {
        return status;
    }

    private void checkStatus() {
        String creatorId = currentEvent.getEventCreatorId();
        if (creatorId.equals(currentUser.getUid())) {
            isClickable.setValue(false);
            status.setValue("Вы создатель");
        } else if (remainingSeats.getValue().equals(0)) {
            isClickable.setValue(false);
            status.setValue("Мест нет");
        }
    }

    public void subscribe() {
        if (isClickable.getValue().equals(true)) {
            usersOnEventReference.child(currentEvent.getEventId())
                    .child(currentUser.getUid())
                    .child("userId")
                    .setValue(currentUser.getUid());
            eventInfoReference.child(currentEvent.getEventCreatorId())
                    .child(currentEvent.getEventId())
                    .child("subscribersNow")
                    .setValue(currentEvent.getSubscribersNow() + 1);
            userEventsReference.child(currentUser.getUid())
                    .child(currentEvent.getEventId())
                    .setValue(currentEvent);
            userEventsReference.child(currentUser.getUid())
                    .child(currentEvent.getEventId())
                    .child("subscribersNow")
                    .setValue(currentEvent.getSubscribersNow() + 1);
        }
    }
}
