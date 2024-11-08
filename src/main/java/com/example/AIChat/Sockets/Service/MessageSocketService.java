package com.example.AIChat.Sockets.Service;

import com.example.AIChat.Message.DTO.MessageDTO;
import com.example.AIChat.Message.Rep.MessageRep;
import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.Message.domain.MessageAnswer;
import com.example.AIChat.Message.domain.MessageType;
import com.example.AIChat.Sockets.DTO.Answer;
import com.example.AIChat.User.Domain.User;
import com.example.AIChat.User.Rep.UserRep;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@AllArgsConstructor
@Service
public class MessageSocketService {

    private final MessageRep messageRep;
    private final UserRep userRep;

    public MessageType GetFlag(MessageDTO messageDTO){
        //MessageType flag = message.getMessageType();

        return messageDTO.getMessageType() ;
    }

    public Message saveMessage(Message message){
        return messageRep.save(message);
    }

    public Message MessageDTOToMessage(MessageDTO messageDTO){

        Message message = new Message();
        message.setMessageId(messageDTO.getMessageId());
        message.setMessage(messageDTO.getMessage());
        message.setUser(userRep.findByUserId(messageDTO.getUserId()));
        message.setGroupId(messageDTO.getGroupId());
        message.setMessageType(messageDTO.getMessageType());
        message.setAiRepliedid(messageDTO.getAiRepliedid());
        message.setCreated(messageDTO.getCreated());
        return message;

    }

    public Message AnswerToMessage(Answer answer){

        Message message = new Message();
        String oldMessageId = answer.getMessageId();

        message.setAiRepliedid(oldMessageId);
        message.setMessage(answer.getAnswer());
        //messageAnswer.setMessageId(answer.getMessageId());


        User user = (messageRep.findByMessageId(oldMessageId)).getUser();
        message.setUser(user);
        //message.setUser(userRep.findByUserId(messageDTO.getUserId()));
        message.setGroupId((messageRep.findByMessageId(oldMessageId)).getGroupId());

        message.setMessageType((messageRep.findByMessageId(oldMessageId)).getMessageType());

        int createdInt = answer.getCreated();
        long createdMillis = (long) createdInt * 1000;
        Timestamp createdTimestamp = new Timestamp(createdMillis);
        message.setCreated(createdTimestamp);

        return message;
    }

    public MessageAnswer AnswerToMessageAnswer(Answer answer){

        String oldMessageId = answer.getMessageId();

        MessageAnswer messageAnswer = new MessageAnswer();
        messageAnswer.setAiRepliedid(oldMessageId);
        messageAnswer.setMessage(answer.getAnswer());
        //messageAnswer.setMessageId(answer.getMessageId());

        User user = (messageRep.findByMessageId(oldMessageId)).getUser();;
        messageAnswer.setUser(user);

        //messageAnswer.setUser(userRep.findByUserId(messageDTO.getUserId()));
        messageAnswer.setGroupId((messageRep.findByMessageId(oldMessageId)).getGroupId());

        messageAnswer.setMessageType((messageRep.findByMessageId(oldMessageId)).getMessageType());

        int createdInt = answer.getCreated();
        long createdMillis = (long) createdInt * 1000;
        Timestamp createdTimestamp = new Timestamp(createdMillis);
        messageAnswer.setCreated(createdTimestamp);

        messageAnswer.setMessageAnswer(messageRep.findByMessageId(oldMessageId));

        return messageAnswer;
    }

}
