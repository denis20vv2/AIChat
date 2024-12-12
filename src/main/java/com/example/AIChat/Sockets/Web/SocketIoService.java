package com.example.AIChat.Sockets.Web;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.example.AIChat.Group.Service.GroupService;
import com.example.AIChat.Message.DTO.MessageDTO;
import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.Message.domain.MessageType;
import com.example.AIChat.Sockets.DTO.Answer;
import com.example.AIChat.Sockets.DTO.MessageAddedUser;
import com.example.AIChat.Sockets.Service.MessageSocketService;
import com.example.AIChat.User.Domain.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class SocketIoService {

    private final MessageSocketService messageSocketService;

    private final Map<String, SocketIOClient> connectedClients = new ConcurrentHashMap<>();
    //private final Map<String, List<Map<String, SocketIOClient>>> connectedClients = new ConcurrentHashMap<>();

    private static final UUID RESERVED_CLIENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    //private static final UUID RESERVED_CLIENT_ID_1 = UUID.fromString("00000000-0000-0000-0000-000000000001");

    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @OnConnect
    public void onConnect(SocketIOClient client) {
        // Получаем userId из параметров URL
        String userId = client.getHandshakeData().getSingleUrlParam("userId");

        if (userId != null) {
            // Проверяем, подключён ли уже клиент с таким userId
            if (connectedClients.containsKey(userId)) {
                //logger.info("Client with userId {} is already connected.", userId);
                return; // Не разрешаем повторное подключение для одного userId
            }

            // Проверка, является ли userId зарезервированным
            if ("00000000-0000-0000-0000-000000000000".equals(userId)) {
                // Если это зарезервированный AI-клиент, добавляем его в специальную ячейку
                connectedClients.put(userId, client);
                logger.info("Client(AI) with userId 0 assigned to reserved slot.");
            } else {
                // Для обычного клиента добавляем в мапу с userId
                connectedClients.put(userId, client);
                logger.info("Client with userId {} connected.", userId);
            }
        } else {
            logger.info("User connected without userId");
        }
    }


   /* public String getKeyByValue(SocketIOClient value) {
        for (Map.Entry<String, SocketIOClient> entry : connectedClients.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }*/

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        //logger.info("Client disconnected: " + client.getSessionId() + "userId = ");
        String key = messageSocketService.getKeyByValue(client, connectedClients);
        logger.info("Client disconnected: " + client.getSessionId() + "userId = " + key);
        connectedClients.remove(key);
    }

    @OnEvent("message")
    public void onMessage(SocketIOClient client, MessageDTO messageDTO) {
        // Получаем userId из параметров URL
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        String groupId = messageDTO.getGroupId();
        logger.info("Getting message from client with userId: " + userId);



        // Определяем тип сообщения (AI или USER)
        MessageType flag = messageSocketService.GetFlag(messageDTO);

        // Если сообщение от AI
        if (flag == MessageType.AI) {
            logger.info("Message from client == AI");

            // Преобразуем MessageDTO в Message и сохраняем
            Message msg = messageSocketService.MessageDTOToMessage(messageDTO);
            msg = messageSocketService.saveMessage(msg);
            messageSocketService.saveGroup(msg);

            // Ищем получателей по userId
            //List<SocketIOClient> recipients = getRecipientsByUserId(userId);
            List<String> userIds = messageSocketService.getUserByIds(connectedClients, groupId);

            //List<SocketIOClient> recipients = messageSocketService.getListByGroupId(userIds);
            for (String currentUserId : userIds) {
                client = connectedClients.get(currentUserId); // Получаем клиента из connectedClients
                if (client != null) { // Проверяем, что клиент существует
                    client.sendEvent("message", msg); // Отправляем сообщение
                    logger.info("Send message to client with userId: " + currentUserId);
                    client.sendEvent("not_available_ai"); // Отправляем сообщение
                    logger.info("Stoping AI: " + currentUserId);
                } else  logger.info("error!!!!! ");
            }

            // Ищем клиент с зарезервированным userId для AI (если есть)
            SocketIOClient recipientClient = connectedClients.get("00000000-0000-0000-0000-000000000000");
            if (recipientClient != null) {
                Map<String, Object> message = new HashMap<>();
                message.put("message", msg);
                recipientClient.sendEvent("ai_send", message);
                logger.info("Send AI message to reserved client with userId: ");
            }
        }

        // Если сообщение от обычного пользователя
        if (flag == MessageType.user) {
            logger.info("Message from user with userId: " + userId);

            // Преобразуем MessageDTO в Message и сохраняем
            Message msg = messageSocketService.MessageDTOToMessage(messageDTO);
            msg = messageSocketService.saveMessage(msg);
            messageSocketService.saveGroup(msg);

            // Отправляем сообщение всем подключенным пользователям
            List<String> userIds = messageSocketService.getUserByIds(connectedClients, groupId);

            //List<SocketIOClient> recipients = messageSocketService.getListByGroupId(userIds);
            for (String currentUserId : userIds) {
                client = connectedClients.get(currentUserId); // Получаем клиента из connectedClients
                if (client != null) { // Проверяем, что клиент существует
                    client.sendEvent("message", msg); // Отправляем сообщение
                    logger.info("Send message to client with userId: " + currentUserId);
                } else  logger.info("error!!!!! ");
            }

            logger.info("Message processed for user.");
        }
    }

    // Метод для получения получателей по userId
  /*  private List<SocketIOClient> getRecipientsByUserId(String userId) {
        // Можно использовать мапу connectedClients для поиска по userId
        List<SocketIOClient> recipients = new ArrayList<>();
        if (connectedClients.containsKey(userId)) {
            recipients.add(connectedClients.get(userId));
        }
        return recipients;
    }

    // Метод для получения зарезервированного клиента для AI
    private SocketIOClient getReservedClientForAI() {
        // Можно искать клиента по зарезервированному userId
        String reservedUserId = "00000000-0000-0000-0000-000000000000";
        return connectedClients.getOrDefault(reservedUserId, null);
    }*/

    @OnEvent("ping")
    public void onPing(SocketIOClient client) {
        client.sendEvent("pong");
        logger.info("получен пинг от клиента ");
    }
    @OnEvent("new_answer")
    public void onNewAnswer(SocketIOClient client, Answer answer ) {

        String groupId = answer.getGroupId();
        logger.info("рассылаем клиентам ответ от нейронки" + answer.getAnswer());

        Message msg = messageSocketService.saveMessage(messageSocketService.AnswerToMessage(answer));
        logger.info("Сохранение прошло");

        messageSocketService.saveGroup(msg);

        List<String> userIds = messageSocketService.getUserByIds(connectedClients, groupId);

        //List<SocketIOClient> recipients = messageSocketService.getListByGroupId(userIds);
        for (String currentUserId : userIds) {
            client = connectedClients.get(currentUserId); // Получаем клиента из connectedClients
            if (client != null) { // Проверяем, что клиент существует
                client.sendEvent("message", msg); // Отправляем сообщение
                logger.info("Send message to client with userId: " + currentUserId);
                client.sendEvent("available_ai"); // Отправляем сообщение
                logger.info("Stoping AI: " + currentUserId);
            } else  logger.info("error!!!!! ");
        }

        logger.info("Выполнена отправка msg пользователям от нейронки");
    }

    @OnEvent("add_users_to_group")
    public void addUserToGroup(SocketIOClient client, List<String> userIds ) {
        String groupId = client.getHandshakeData().getSingleUrlParam("groupId");
        //String userId = client.getHandshakeData().getSingleUrlParam("userId");
        messageSocketService.addUser(groupId, userIds);
        MessageAddedUser messageAddedUser = messageSocketService.responeMessage(groupId);


        List<String> userOldIds = messageSocketService.getUserByIds(connectedClients, groupId);

        //List<SocketIOClient> recipients = messageSocketService.getListByGroupId(userIds);
        for (String currentUserId : userOldIds) {
            client = connectedClients.get(currentUserId); // Получаем клиента из connectedClients
            if (client != null) { // Проверяем, что клиент существует
                client.sendEvent("added_users_to_group", messageAddedUser); // Отправляем сообщение
                logger.info("Send message added to client client with userId: " + currentUserId);
            } else  logger.info("error!!!!! ");
        }


        userOldIds = userIds;
        logger.info("error!!!!! ");
        //List<SocketIOClient> recipients = messageSocketService.getListByGroupId(userIds);
        for (String currentUserId : userOldIds) {
            client = connectedClients.get(currentUserId); // Получаем клиента из connectedClients
            logger.info("error!!!!! ");
            if (client != null) { // Проверяем, что клиент существует
                client.sendEvent("get_chat", groupId); // Отправляем сообщение
                logger.info("Client added to client client with userId: " + currentUserId);
            } else  logger.info("error!!!!! ");
        }

    }



}


