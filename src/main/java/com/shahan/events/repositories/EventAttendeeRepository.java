package com.shahan.events.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shahan.events.models.Event;
import com.shahan.events.models.EventsAttendees;
import com.shahan.events.models.User;

@Repository
public interface EventAttendeeRepository extends CrudRepository<EventsAttendees, Long>{

	@Transactional
	@Modifying
	@Query(value="delete EventsAttendees e WHERE e.attendee = ?1 AND e.event = ?2")
	void deleteRelationship(User user, Event event);

}
