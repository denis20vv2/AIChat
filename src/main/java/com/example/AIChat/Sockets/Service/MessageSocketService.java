package com.example.AIChat.Sockets.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Group.Rep.GroupRep;
import com.example.AIChat.Group.Web.GroupController;
import com.example.AIChat.Message.Converter.MessageToReactionDTOConverter;
import com.example.AIChat.Message.DTO.MessageDTO;
import com.example.AIChat.Message.DTO.MessageReaction;
import com.example.AIChat.Message.Rep.MessageRep;
import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.Message.domain.MessageType;
import com.example.AIChat.Reaction.Converter.NewReactionToReactionBlock;
import com.example.AIChat.Reaction.Converter.ReactionToreactionDTOConverter;
import com.example.AIChat.Reaction.DTO.NewReactionDTO;
import com.example.AIChat.Reaction.DTO.ReactionDTO;
import com.example.AIChat.Reaction.Domain.ReactionBlock;
import com.example.AIChat.Reaction.Rep.ReactionRep;
import com.example.AIChat.Sockets.DTO.Answer;
import com.example.AIChat.Sockets.DTO.MessageAddedUser;
import com.example.AIChat.User.Domain.User;
import com.example.AIChat.User.Rep.UserRep;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageSocketService {

    private final MessageRep messageRep;
    private final UserRep userRep;
    private final GroupRep groupRep;
    private final ReactionRep reactionRep;

    private final ReactionToreactionDTOConverter reactionToreactionDTOConverter;

    private final NewReactionToReactionBlock newReactionToReactionBlock;

    private final MessageToReactionDTOConverter messageToReactionDTOConverter;
    private static final Logger logger = LoggerFactory.getLogger(MessageSocketService.class);

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

        //message.setMessageNested(messageRep.findByMessageId(answer.getMessageId()));

        Message messageNested = messageRep.findByMessageId(answer.getMessageId());
        messageNested.setReactionBlocks(null);
        message.setMessageNested(messageNested);

        message.setMessage(answer.getAnswer());

        User user = (userRep.findByUserId("AI"));

        message.setUser(user);

        message.setGroupId((messageRep.findByMessageId(oldMessageId)).getGroupId());

        message.setMessageType((messageRep.findByMessageId(oldMessageId)).getMessageType());

        message.setCreated(answer.getCreated());

       // logger.info(" G");

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

    public List<ReactionDTO> getAllReactionByMessageId(String messageId){

        Set<ReactionBlock> reactionBlocks = messageRep.findByMessageId(messageId).getReactionBlocks();
        logger.info(" G");
        List<ReactionDTO> reactionDTOList = new ArrayList<>();
        logger.info(" G");

       for(ReactionBlock reactionBlock : reactionBlocks){
           ReactionDTO reactionDTO = reactionToreactionDTOConverter.convert(reactionBlock);
           logger.info(" G");
           reactionDTOList.add(reactionDTO);
       }

        return reactionDTOList;
    }

    @Transactional
        public ReactionBlock getReaction(String messageId, String emoji){


        Set<ReactionBlock> ReactionBlocks = messageRep.findByMessageId(messageId).getReactionBlocks();

        for(ReactionBlock reactionBlock: ReactionBlocks ){

            if(reactionBlock.getEmoji().equals(emoji)){

                return reactionBlock;

            }

        }

        return null;
        //return reactionRep.findAllByMessageIdAndEmoji(messageId, emoji);
    }

    public MessageReaction saveReaction(NewReactionDTO newReactionDTO){

        Message message = messageRep.findByMessageId(newReactionDTO.getMessageId());

        logger.info(" G");

        logger.info(" Getting method saveReaction");

        ReactionBlock newReactionBlock =  getReaction(newReactionDTO.getMessageId(), newReactionDTO.getEmoji());
        if(newReactionBlock == null){

            logger.info("newReactionBlock == null");

            chekUser(newReactionDTO.getUserId(), newReactionDTO.getMessageId());

            logger.info("newReactionBlock delete old reaction");

            newReactionBlock = newReactionToReactionBlock.convert(newReactionDTO);

            logger.info(" Вызов do ");

            message = messageRep.findByMessageId(newReactionDTO.getMessageId());

            reactionRep.save(newReactionBlock);

            Set<ReactionBlock> reactionBlocks = message.getReactionBlocks();

            logger.info(" Вызов do ");

            reactionBlocks.add(reactionRep.save(newReactionBlock));

            message.setReactionBlocks(reactionBlocks);


            logger.info("newReactionBlock saved");

            //logger.info(" Вызов do ");

            return messageToReactionDTOConverter.convert(message);

        }else {

            //logger.info("newReactionBlock != null");

            chekUser(newReactionDTO.getUserId(), newReactionDTO.getMessageId());

            //logger.info(" Вызов do ");

            Set<User> users = newReactionBlock.getUser();

            //logger.info(" Вызов do ");

            users.add(userRep.findByUserId(newReactionDTO.getUserId()));

            //logger.info(" Вызов do ");

            newReactionBlock.setUser(users);

            message = messageRep.findByMessageId(newReactionDTO.getMessageId());

            //reactionRep.save(newReactionBlock);

            Set<ReactionBlock> reactionBlocks = message.getReactionBlocks();

            reactionBlocks.add(reactionRep.save(newReactionBlock));

            message.setReactionBlocks(reactionBlocks);


            logger.info("newReactionBlock saved");
            return messageToReactionDTOConverter.convert(message);

        }

        //return null;
    }


    public void chekUser(String userId, String messageId){

        logger.info("Вызов  chekUser");
        ////////////////////////////////////////////////////////////////////ошибка тут
        Message message = messageRep.findByMessageId(messageId);

        Set<ReactionBlock> reactionBlocks = message.getReactionBlocks();

        //List<ReactionBlock> reactionBlocks = reactionRep.findByMessageId(messageId);
        logger.info("получен  reactionBlocks");
        boolean found = false;
        logger.info(" Вызов do ");
        String reactionId = null;

        logger.info(" Вызов do ");

        //do {
            //logger.info(" Вызвали do ");
            for (ReactionBlock reactionBlock : reactionBlocks) {
                // Ищем userId в списке пользователей в текущем ReactionBlock
                logger.info("reactionBlock = " + reactionBlock.getReactionId());
                for (User user : reactionBlock.getUser()) {
                    logger.info("userId = " + reactionBlock.getUser());
                    if (user.getUserId().equals(userId)) {
                        found = true;
                        reactionId = reactionBlock.getReactionId();
                        break;
                    }
                }
                if (found) break;
            }

            //if (found) break;

       // } while (!found);

        if (found) {
            System.out.println("User ID найден: " + userId);

            ReactionBlock reactionBlock = reactionRep.findByReactionId(reactionId);

          //  logger.info(" Вызов do ");

            Set<User> users = reactionBlock.getUser();

           // logger.info(" Вызов do ");

            users.removeIf(user -> user.getUserId().equals(userId));

           // logger.info(" Вызов do ");

            if(users.isEmpty() || users == null){

              //  logger.info(" Вызов do ");

                Set<ReactionBlock> newReactionBlocks = message.getReactionBlocks();
              //  logger.info(" Вызов do ");
                String reactionIdNew = reactionId;
               // logger.info(" Вызов do ");
                newReactionBlocks.removeIf(newReactionBlock -> reactionBlock.getReactionId().equals(reactionIdNew));
               // logger.info(" Вызов do ");
                message.setReactionBlocks(newReactionBlocks);
               // logger.info(" Вызов do ");
                messageRep.save(message);
               // logger.info(" Вызов do ");
                //logger.info(" Вызов do ");

                reactionRep.delete(reactionBlock);

            }else {

                //logger.info(" Вызов do ");

                reactionBlock.setUser(users);

                //logger.info(" Вызов do ");

                System.out.println("User удален из старой реакции с id: " + userId);

                reactionRep.save(reactionBlock);
            }

        } else {
            System.out.println("User с ID не найден." + userId);

        }


    }

    public ReactionBlock deleteReaction(NewReactionDTO newReactionDTO){



        return null;
    }


}
