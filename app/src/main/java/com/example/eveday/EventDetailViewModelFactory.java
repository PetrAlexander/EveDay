package com.example.eveday;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class EventDetailViewModelFactory implements ViewModelProvider.Factory {
    private Event event;
    public EventDetailViewModelFactory(Event event) {
        this.event = event;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EventDetailViewModel(event);
    }
}
