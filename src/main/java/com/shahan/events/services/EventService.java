package com.shahan.events.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shahan.events.models.Event;
import com.shahan.events.models.User;
import com.shahan.events.repositories.EventAttendeeRepository;
import com.shahan.events.repositories.EventRepository;

@Service
public class EventService {
	private final EventRepository eventRepository;
	private final EventAttendeeRepository eventAttendeeRepository;
	
	public EventService(EventRepository eventRepository, EventAttendeeRepository eventAttendeeRepository) {
		this.eventRepository = eventRepository;
		this.eventAttendeeRepository = eventAttendeeRepository;
	}
	
	public Event createEvent(Event event) {
		return this.eventRepository.save(event);
	}
	
	public void deleteRelationship(User user, Event event){
		this.eventAttendeeRepository.deleteRelationship(user, event);
	}

	public Event findEventById(Long id) {
		Optional<Event> optionalEvent = this.eventRepository.findById(id);
		if(optionalEvent.isPresent()) {
            return optionalEvent.get();
        } else {
            return null;
        }
	}
	
	public List<Object[]> test(){
		return this.test();
	}
	
	public void deleteEvent(Long id) {
		this.eventRepository.deleteById(id);
	}
	
	public List<Event> eventsInState(String state){
		return this.eventRepository.eventsInState(state);
	}
	
	public List<Event> eventsOutOfState(String state){
		return this.eventRepository.eventsOutOfState(state);
	}
	
	public void deleteById(Long id) {
		this.eventRepository.deleteById(id);
	}

}