package com.example.AIChat.Sockets.Web;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.example.AIChat.Message.DTO.MessageDTO;
import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.Message.domain.MessageType;
import com.example.AIChat.Sockets.DTO.Answer;
import com.example.AIChat.Sockets.Service.MessageSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class SocketIoService {

    //private final SocketIOServer server;

    private final MessageSocketService messageSocketService;

    private final Map<UUID, SocketIOClient> connectedClients = new ConcurrentHashMap<>();

    @OnConnect
    public void onConnect(SocketIOClient client) {
        System.out.println("Client connected: " + client.getSessionId());
        connectedClients.put(client.getSessionId(), client);
        client.sendEvent("pong");
        //client.sendEvent("ai_send");
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        System.out.println("Client disconnected: " + client.getSessionId());
        connectedClients.remove(client.getSessionId());
    }

    @OnEvent("message")
    public void onMessage(SocketIOClient client, MessageDTO messageDTO) {
        System.out.println("A message of the type was received message");
        MessageType flag = messageSocketService.GetFlag(messageDTO);
        if(flag == MessageType.AI){
            Message msg = messageSocketService.MessageDTOToMessage(messageDTO);
            messageSocketService.saveMessage(msg);

            SocketIOClient recipientClient = null;

            if (!connectedClients.isEmpty()) {
                // Получаем первого клиента (с индексом 0) из коллекции connectedClients
                recipientClient = connectedClients.values().iterator().next();
            }

            if (recipientClient != null) {
                recipientClient.sendEvent("ai_send", msg);
            }

            client.sendEvent("ai_send", msg);
            System.out.println(msg);
            System.out.println("AI");

        }

        if(flag == MessageType.user){
            messageSocketService.saveMessage(messageSocketService.MessageDTOToMessage(messageDTO));
            client.sendEvent("message", messageSocketService.MessageDTOToMessage(messageDTO));
            System.out.println("user");
        }

    }
    @OnEvent("ping")
    public void onPing(SocketIOClient client) {
        client.sendEvent("pong", "gdshghesggueguhuw");

        System.out.println("получен пинг от клиента ");
    }
    @OnEvent("new_answer")
    public void onNewAnswer(SocketIOClient client, Answer answer ) {
        //client.sendEvent("message", "Message received:");
        System.out.println("рассылаем клиентам ответ от нейронки" + answer.getAnswer());

        messageSocketService.saveMessage(messageSocketService.AnswerToMessage(answer));
        client.sendEvent("message", messageSocketService.AnswerToMessageAnswer(answer));
    }

}


