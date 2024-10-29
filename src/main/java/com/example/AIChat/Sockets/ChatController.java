package com.example.AIChat.Sockets;

import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.Sockets.Domain.ChatMessage;
import com.example.AIChat.Sockets.Service.MessageSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

//@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageSocketService messageSocketService;

    @MessageMapping("/getMessage")  // Путь для получения сообщений от клиента
    @SendTo("/sendMessage")       // Путь для отправки сообщений клиентам
    public Message sendMessage(Message message) {
        System.out.println("Received message: " + message);
        return messageSocketService.saveMessage(message); // Сохраняем и пересылаем сообщение
    }
}