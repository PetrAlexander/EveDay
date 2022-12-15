package com.example.eveday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CreatedEventsAdapter extends RecyclerView
        .Adapter<CreatedEventsAdapter.CreatedEventsViewHolder> {
    private static final String COUNTER_SEPARATOR = "/";
    List<Event> events = new ArrayList<>();
    private CreatedEventsAdapter.OnEventClickListener onEventClickListener;

    public void setOnEventClickListener(CreatedEventsAdapter.OnEventClickListener onEventClickListener) {
        this.onEventClickListener = onEventClickListener;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CreatedEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.event_item,
                parent,
                false);
        return new CreatedEventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreatedEventsViewHolder holder, int position) {
        Event event = events.get(position);

        holder.textViewEventName.setText(event.getEventName());
        holder.textViewEventLocation.setText(event.getEventLocation());
        String eventSize = String.valueOf(event.getEventSize());
        String subsNow = String.valueOf(event.getSubscribersNow());
        String peopleCounter = String.format(
                "%s%s%s",
                subsNow,
                COUNTER_SEPARATOR,
                eventSize
        );
        holder.textViewPeopleCounter.setText(peopleCounter);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onEventClickListener != null) {
                    onEventClickListener.onEventClick(event);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    interface OnEventClickListener {
        void onEventClick(Event event);
    }

    static class CreatedEventsViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewEventName;
        private TextView textViewEventLocation;
        private TextView textViewPeopleCounter;

        public CreatedEventsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEventName = itemView.findViewById(R.id.textViewEventName);
            textViewEventLocation = itemView.findViewById(R.id.textViewEventLocation);
            textViewPeopleCounter = itemView.findViewById(R.id.textViewPeopleCounter);
        }
    }
}
