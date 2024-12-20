package com.example.AIChat.Message.Service;

import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Group.Rep.GroupRep;
import com.example.AIChat.Message.Converter.MessageToReactionDTOConverter;
import com.example.AIChat.Message.DTO.MessageReaction;
import com.example.AIChat.Message.Rep.MessageRep;
import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.User.Domain.User;
import com.example.AIChat.User.Rep.UserRep;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class MessageService {

    private final GroupRep groupRep;
    private final UserRep userRep;
    private final MessageRep messageRep;
    private final MessageToReactionDTOConverter messageToReactionDTOConverter;

    public List<Message> getAllMessages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("message").ascending());
        return messageRep.findAll(pageable).getContent();
    }

    public List<MessageReaction> getAllMessagesByGroupId(String groupId, int page, int size) {

        //User user = userRep.findByUserId(userId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("created").descending());

        List<Message> messages = messageRep.findByGroupId(groupId, pageable).getContent();

        return messages.stream()
                .map(messageToReactionDTOConverter::convert) // Применяем конвертер
                .toList(); // Сбор результата в список
    }
}




