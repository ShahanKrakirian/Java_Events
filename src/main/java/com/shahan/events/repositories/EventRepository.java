package com.shahan.events.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shahan.events.models.Event;
import com.shahan.events.models.User;

public interface EventRepository extends CrudRepository<Event, Long>{
	
	@Query(value="SELECT e FROM Event e WHERE e.state = ?1")
	List<Event> eventsInState(String state);
	
	@Query(value="SELECT e FROM Event e WHERE e.state <> ?1")
	List<Event> eventsOutOfState(String state);
	
	
	

}