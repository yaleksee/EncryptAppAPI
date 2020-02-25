package com.encryptapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.encryptapp.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

}
