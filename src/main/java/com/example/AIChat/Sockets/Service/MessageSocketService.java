package com.example.AIChat.Sockets.Service;

import com.example.AIChat.Message.Rep.MessageRep;
import com.example.AIChat.Message.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSocketService {
    @Autowired
    MessageRep messageRep;

   public Message saveMessage(Message message){

       return messageRep.save(message);
   }
}
