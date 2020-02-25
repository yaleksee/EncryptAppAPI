package com.encryptapp.controller;

import java.util.List;

import javax.validation.Valid;

import com.encryptapp.exception.ResourceNotFoundException;
import com.encryptapp.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encryptapp.model.Message;

@RestController
@RequestMapping("/api/v1/cryptedMessages")
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;

	@GetMapping
	public List<Message> getAllMessages() {
		return messageService.getAllMessages();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Message> getMessagesById(@PathVariable(value = "id") Long messagesId)
			throws ResourceNotFoundException {
		Message decryptedMessage = messageService.getById(messagesId);
		return ResponseEntity.ok().body(decryptedMessage);
	}

	@PostMapping
	public Message createMessages(@Valid @RequestBody Message message) {
		return messageService.createMessage(message);
	}

	@PutMapping
	public ResponseEntity<Message> updateMessages(
			@Valid @RequestBody Message messageDetails
	) throws ResourceNotFoundException {
		Message updatedMessage = messageService.updateMessage(messageDetails.getId(), messageDetails);
		return ResponseEntity.ok(updatedMessage);
	}

}

