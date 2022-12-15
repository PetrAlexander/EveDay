package com.example.eveday;

import java.io.Serializable;

public class Event implements Serializable {
    private String eventName;
    private String eventLocation;
    private String eventDescription;
    private String eventCreatorId;
    private String eventId;
    private int subscribersNow;
    private int eventSize;

    public Event(
            String eventName,
            String eventLocation,
            String eventDescription,
            String eventCreatorId,
            String eventId,
            int eventSize,
            int subscribersNow
    ) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDescription = eventDescription;
        this.eventCreatorId = eventCreatorId;
        this.eventId = eventId;
        this.eventSize = eventSize;
        this.subscribersNow = subscribersNow;
    }

    public Event (
            String eventName,
            String eventLocation,
            String eventDescription,
            String eventCreatorId,
            String eventId,
            int eventSize
    ) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDescription = eventDescription;
        this.eventCreatorId = eventCreatorId;
        this.eventId = eventId;
        this.eventSize = eventSize;
        this.subscribersNow = 0;
    }

    public Event() {

    }

    public String getEventName() {
        return eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventCreatorId() {
        return eventCreatorId;
    }

    public String getEventId() {
        return eventId;
    }

    public int getEventSize() {
        return eventSize;
    }

    public int getSubscribersNow() {
        return subscribersNow;
    }

}
