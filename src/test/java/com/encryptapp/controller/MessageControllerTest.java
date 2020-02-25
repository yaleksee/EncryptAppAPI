package com.encryptapp.controller;

import com.encryptapp.Application;
import com.encryptapp.model.Message;
import com.encryptapp.repository.MessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/v1/cryptedMessages";
    }

    @Test
    public void testGetAllMessages() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl(), HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetMessageById() {
        Message message = restTemplate.getForObject(getRootUrl() + "/1", Message.class);
        assertNotNull(message);
    }

    @Test
    public void testCreateMessage() {
        Message message = new Message("message");
        ResponseEntity<Message> postResponse = restTemplate.postForEntity(getRootUrl(), message, Message.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateMessage() {
        int id = 0;
        Message message = restTemplate.getForObject(getRootUrl() + "/" + id , Message.class);
        message.setEncryptedMessage("messageUpdate");
        restTemplate.put(getRootUrl(), message);
        Message updatedMessage = restTemplate.getForObject(getRootUrl() + "/" + id, Message.class);
        assertNotNull(updatedMessage);
    }
}

