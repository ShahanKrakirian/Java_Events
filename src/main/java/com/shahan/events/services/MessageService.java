package com.shahan.events.services;

import org.springframework.stereotype.Service;

import com.shahan.events.models.Message;
import com.shahan.events.repositories.MessageRepository;

@Service
public class MessageService {
	private final MessageRepository messageRepository;
	
	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	public Message createMessage(Message message){
		return this.messageRepository.save(message);
	}

}
