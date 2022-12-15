package com.example.eveday;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;

public class AddEventViewModel extends ViewModel {
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference eventsInfoReference;

    private MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();

    public MutableLiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }

    public AddEventViewModel() {
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        eventsInfoReference = firebaseDatabase.getReference(DB.DB_EVENTS_INFO);
    }

    public void createEvent(
            String eventName,
            String eventDescription,
            String eventLocation,
            int eventSize
    ) {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null)
            return;
        String eventId = eventsInfoReference.child(firebaseUser.getUid()).push().getKey();
        Event newEvent = new Event(
                eventName,
                eventLocation,
                eventDescription,
                firebaseUser.getUid(),
                eventId,
                eventSize
        );
        eventsInfoReference.child(firebaseUser.getUid()).child(eventId).setValue(newEvent);
        shouldCloseScreen.setValue(true);
    }
}