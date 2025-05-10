package com.example.AIChat.Group.Service;

import com.example.AIChat.Group.Converter.GroupPostReqToGroupConverter;
import com.example.AIChat.Group.Converter.GroupToGroupDTOConverter;
import com.example.AIChat.Group.DTO.GroupDTO;
import com.example.AIChat.Group.DTO.PostGroupReq;
import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Message.Rep.MessageRep;
import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.User.Domain.User;
import com.example.AIChat.User.Rep.UserRep;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.AIChat.Group.Rep.GroupRep;
import org.webjars.NotFoundException;

import java.util.*;

@AllArgsConstructor
@Service
public class GroupService {

    private final GroupRep groupRep;
    private final UserRep userRep;
    private final MessageRep messageRep;
    private final GroupPostReqToGroupConverter groupPostReqToGroupConverter;

    private final GroupToGroupDTOConverter groupToGroupDTOConverter;

    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    public Group createGroup(PostGroupReq postGroupReq) {
        logger.info("Создание группы");

        Group group = groupPostReqToGroupConverter.convert(postGroupReq);
        if (group == null) {
            throw new IllegalArgumentException("Ошибка конвертации группы");
        }



        List<String> userIds = postGroupReq.getUserIds();
        if (userIds == null || userIds.isEmpty()) {
            throw new IllegalArgumentException("Список пользователей пуст");
        }



        Set<User> users = new HashSet<>();
        for (String userId : userIds) {
            User user = userRep.findByUserId(userId);
            if (user == null) {
                logger.error("Пользователь с ID {} не найден", userId);
                throw new NotFoundException("Пользователь с ID " + userId + " не найден");
            }
            users.add(user);
        }
        group.setUsers(users);


        //Message lastMessage = messageRep.findByMessageId(postGroupReq.getLastMessageId());
//        if (lastMessage == null) {
//            throw new NotFoundException("Сообщение не найдено");
//        }
        //group.setLastMessage(lastMessage);

        logger.info("Группа создана успешно");

        return groupRep.save(group);
    }

    public List<GroupDTO> getAllGroupsByUserId(String userId, int page, int size) {

        logger.info("Г");
        // Параметры пагинации: текущая страница, размер страницы и сортировка
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        logger.info("Г");
        // Получаем группы пользователя с учетом пагинации
        Page<Group> groupPage = groupRep.findByUsersUserId(userId, pageable);
        logger.info("Г");
        // Конвертируем группы в GroupDTO с помощью конвертера
        List<GroupDTO> groupDTOs = groupPage.getContent().stream()
                .map(groupToGroupDTOConverter::convert) // Применяем конвертер к каждой группе
                .toList(); // Собираем результат в список

        logger.info("Г");

        return groupDTOs; // Возвращаем список GroupDTO
    }


    public Group GetGroupByGroupId(String groupId) {

        return groupRep.findByGroupId(groupId);
    }



}
