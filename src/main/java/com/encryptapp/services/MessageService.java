package com.encryptapp.services;

import com.encryptapp.exception.MessageEncryptException;
import com.encryptapp.exception.ResourceNotFoundException;
import com.encryptapp.utils.cryptography.asymmetric.AsymmetricCryptography;
import lombok.RequiredArgsConstructor;
import com.encryptapp.model.Message;
import com.encryptapp.repository.MessageRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private static final Logger log = Logger.getLogger(MessageService.class);

    private final MessageRepository messageRepository;
    private final AsymmetricCryptography asymmetricCryptography;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getById(long messageId) throws ResourceNotFoundException {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Messages not found for this id : " + messageId));
        final String decryptedMessage;
        try {
            decryptedMessage = asymmetricCryptography.decryptText(message.getEncryptedMessage());
        } catch (Exception e) {
            log.error("failed to decrypt message", e);
            throw new MessageEncryptException("Error during decrypting message. Message: " + message.getEncryptedMessage());
        }
        message.setEncryptedMessage(decryptedMessage);
        return message;
    }

    public Message createMessage(Message message) {
        final String encriptMessage;
        try {
            encriptMessage = asymmetricCryptography.encryptText(message.getEncryptedMessage());
        } catch (Exception e) {
            log.error("failed to encrypt message", e);
            throw new MessageEncryptException("Error during encrypting message. Message: " + message.getEncryptedMessage());
        }
        final Message updatedMessage = messageRepository.save(new Message(encriptMessage));
        return messageRepository.save(updatedMessage);

    }

    public Message updateMessage(Long messageId, Message externalMsg) throws ResourceNotFoundException {
        final Message internalMsg = getById(messageId);
        final String updatedMsg;
        try {
            updatedMsg = asymmetricCryptography.encryptText(externalMsg.getEncryptedMessage());
        } catch (Exception e) {
            log.error("failed to encrypt message", e);
            throw new MessageEncryptException("Error during encrypting message. Message: " + externalMsg.getEncryptedMessage());
        }
        internalMsg.setEncryptedMessage(updatedMsg);
        return messageRepository.save(internalMsg);
    }


}
