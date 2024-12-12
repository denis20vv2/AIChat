package com.example.AIChat.Sockets.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Group.Rep.GroupRep;
import com.example.AIChat.Message.DTO.MessageDTO;
import com.example.AIChat.Message.Rep.MessageRep;
import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.Message.domain.MessageType;
import com.example.AIChat.Sockets.DTO.Answer;
import com.example.AIChat.Sockets.DTO.MessageAddedUser;
import com.example.AIChat.User.Domain.User;
import com.example.AIChat.User.Rep.UserRep;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageSocketService {

    private final MessageRep messageRep;
    private final UserRep userRep;
    private final GroupRep groupRep;

    //private final Map<String, SocketIOClient> connectedClients = new ConcurrentHashMap<>();

    public MessageType GetFlag(MessageDTO messageDTO){
        //MessageType flag = message.getMessageType();

        return messageDTO.getMessageType() ;
    }

    public Message saveMessage(Message message){
        return messageRep.save(message);
    }

    public Group saveGroup(Message message){

        Group newGroup = groupRep.findByGroupId(message.getGroupId());
        newGroup.setLastMessage(message);

        return groupRep.save(newGroup);
    }

   /* public Group saveGroup(Group group){
        return groupRep.save(group);
    }*/

    public List<SocketIOClient> getListByGroupId(List<String> userIds){

        return null;
    }

    public List<String> getUserByIds(Map<String, SocketIOClient> connectedClients, String groupId) {
        List<String> userIdsFromDb = getUserIdsFromDatabase(groupId);

        return userIdsFromDb.stream()
                .filter(connectedClients::containsKey) // Проверяем наличие userId в мапе
                .collect(Collectors.toList());
    }

    private List<String> getUserIdsFromDatabase(String groupId) {

        Set<User> users = (groupRep.findByGroupId(groupId)).getUsers();
        List<String> userIds;
        //Hibernate.initialize(users);
        // Преобразуем множество объектов User в список userId
        userIds = users.stream()
                .map(User::getUserId) // Извлекаем поле userId
                .toList(); // Собираем в список
        // Здесь вызываем репозиторий или SQL-запрос для получения userId
        // Пример: SELECT user_id FROM user_group WHERE group_id = :groupId
        return userIds; // Заглушка
    }

    public Group addUser(String groupId, List<String> userIds ){

        Group newGroup = groupRep.findByGroupId(groupId);
        Set<User> users = newGroup.getUsers();
        //Hibernate.initialize(users);
        for (String userId : userIds) {
            User user = userRep.findByUserId(userId);
            if (user == null) {
                throw new IllegalArgumentException("Пользователя с таким userId не существует в системе!");
            }
            users.add(user);
        }
        newGroup.setUsers(users);

        return groupRep.save(newGroup);
    }

    public int getUserCount(String groupId){

        Group newGroup = groupRep.findByGroupId(groupId);
        Set<User> users = newGroup.getUsers();

        return users.size();
    }

    public MessageAddedUser responeMessage(String groupId){

        MessageAddedUser messageAddedUser = new MessageAddedUser();
        messageAddedUser.setCount(getUserCount(groupId));

        return messageAddedUser;
    }


    public Message MessageDTOToMessage(MessageDTO messageDTO){

        Message message = new Message();
        //message.setMessageId(messageDTO.getMessageId());
        message.setMessage(messageDTO.getMessage());
        message.setUser(userRep.findByUserId(messageDTO.getUserId()));
        message.setGroupId(messageDTO.getGroupId());
        message.setMessageType(messageDTO.getMessageType());
        message.setCreated(messageDTO.getCreated());

        return message;
    }

    public Message AnswerToMessage(Answer answer){

        Message message = new Message();
        String oldMessageId = answer.getMessageId();

        message.setMessageNested(messageRep.findByMessageId(answer.getMessageId()));
        message.setMessage(answer.getAnswer());



        User user = (userRep.findByUserId("AI"));
        message.setUser(user);

        message.setGroupId((messageRep.findByMessageId(oldMessageId)).getGroupId());

        message.setMessageType((messageRep.findByMessageId(oldMessageId)).getMessageType());

        message.setCreated(answer.getCreated());

        return message;
    }


    public String getKeyByValue(SocketIOClient value, Map<String, SocketIOClient> connectedClients ) {

        for (Map.Entry<String, SocketIOClient> entry : connectedClients.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }


}
