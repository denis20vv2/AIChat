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

    private static final UUID RESERVED_CLIENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");


    @OnConnect
    public void onConnect(SocketIOClient client) {
        // Получаем userId из параметров URL
        String groupId = client.getHandshakeData().getSingleUrlParam("groupId");

        if (groupId != null) {
            System.out.println("User connected with groupId: " + groupId);

            // Проверяем, является ли userId равным "0", чтобы сохранить его в зарезервированном элементе
            if ("00000000-0000-0000-0000-000000000000".equals(groupId)) {
                connectedClients.put(UUID.fromString("00000000-0000-0000-0000-000000000000"), client);
                System.out.println("Client(AI) with groupId 0 assigned to reserved slot.");
            } else {
                connectedClients.put(UUID.fromString(groupId), client); // Сохранение клиента с использованием userId
                System.out.println("Client conected whith groupId " + groupId);
            }
        } else {
            System.out.println("User connected without groupId");
        }
    }



    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        System.out.println("Client disconnected: " + client.getSessionId());
        connectedClients.remove(client.getSessionId());
    }

    @OnEvent("message")
    public void onMessage(SocketIOClient client, MessageDTO messageDTO) {
        String groupId = client.getHandshakeData().getSingleUrlParam("groupId");
        System.out.println("Getting message from client with groupId" + groupId);
        MessageType flag = messageSocketService.GetFlag(messageDTO);
        if(flag == MessageType.AI){
            System.out.println("message from client == AI");
            Message msg = messageSocketService.MessageDTOToMessage(messageDTO);
            msg = messageSocketService.saveMessage(msg);

            SocketIOClient recipientClient = null;

            if (!connectedClients.isEmpty()) {
                // Получаем первого клиента (с индексом 0) из коллекции connectedClients
                recipientClient = connectedClients.values().iterator().next();
                System.out.println("Getting message for AI   (groupId: " + groupId + " )");
            }

            System.out.println("A message of the type was received message");

            if (recipientClient != null) {
                recipientClient.sendEvent("ai_send", msg);
                System.out.println("Getting message for AI   (groupId: " + groupId + " )   2");
            }

            //client.sendEvent("ai_send", msg);
            System.out.println(msg);
            System.out.println("AI");

        }

        if(flag == MessageType.user){
            System.out.println("Getting message for user with groupId: " + groupId + " ) ");
            Message msg = messageSocketService.MessageDTOToMessage(messageDTO);
            //SocketIOClient recipientClient = null;
            //recipientClient = connectedClients.values().iterator().next();
            //recipientClient.sendEvent("ai_send", msg);
            msg = messageSocketService.saveMessage(msg);
            for (SocketIOClient recipientClient : connectedClients.values()) {
                // Проверяем, является ли клиент частью данной группы
                String clientGroupId = recipientClient.getHandshakeData().getSingleUrlParam("groupId");
                if (groupId.equals(clientGroupId)) {
                    recipientClient.sendEvent("message", msg);
                    System.out.println("Send message for client with groupId: " + groupId);
                }
            }
            System.out.println("user");
        }

    }
    @OnEvent("ping")
    public void onPing(SocketIOClient client) {
        client.sendEvent("pong");

        System.out.println("получен пинг от клиента ");
    }
    @OnEvent("new_answer")
    public void onNewAnswer(SocketIOClient client, Answer answer ) {
        String groupId = client.getHandshakeData().getSingleUrlParam("groupId");
        //client.sendEvent("message", "Message received:");
        Message msg = messageSocketService.AnswerToMessage(answer);
        System.out.println("рассылаем клиентам ответ от нейронки" + answer.getAnswer());

        msg = messageSocketService.saveMessage(msg);
        System.out.println("Сохранение прошло");
        for (SocketIOClient recipientClient : connectedClients.values()) {
            // Проверяем, является ли клиент частью данной группы
            String clientGroupId = recipientClient.getHandshakeData().getSingleUrlParam("groupId");
            if (groupId.equals(clientGroupId)) {
                recipientClient.sendEvent("message", msg);
                System.out.println("Send message for client with groupId: " + groupId);
            }
        }
        client.sendEvent("message", msg);
        System.out.println("Выполнено");
    }

}


