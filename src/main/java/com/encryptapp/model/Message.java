package com.encryptapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
public class Message {

    public Message(String encryptedMessage) {
        this.encryptedMessage = encryptedMessage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "encrypted_message", nullable = false)
    private String encryptedMessage;
}
