package com.shahan.events.services;

import org.springframework.stereotype.Service;

import com.shahan.events.models.EventsAttendees;
import com.shahan.events.repositories.EventAttendeeRepository;

@Service 
public class EventAttendeeService {
	private final EventAttendeeRepository eventAttendeeRepository;
	
	public EventAttendeeService(EventAttendeeRepository eventAttendeeRepository) {
		this.eventAttendeeRepository = eventAttendeeRepository;
	}

	public void createRelationship(EventsAttendees eventAttendee) {
		this.eventAttendeeRepository.save(eventAttendee);
	}

}
