package com.shahan.events.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shahan.events.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>{

}
