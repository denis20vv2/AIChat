package com.example.AIChat.User.Service;

import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Group.Rep.GroupRep;
import com.example.AIChat.Group.Service.GroupService;
import com.example.AIChat.Message.Rep.MessageRep;
import com.example.AIChat.User.Domain.User;
import com.example.AIChat.User.Rep.AutorizteRep;
import com.example.AIChat.User.Rep.UserRep;
import com.example.AIChat.User.Web.*;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import java.util.*;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);
    private final GroupRep groupRep;
    private final UserRep userRep;
    private final MessageRep messageRep;
    private  final AutorizteRep autorizteRep;


    public List<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return userRep.findAll(pageable).getContent();
    }

    public List<User> getUsersByGroupId(String groupId, int page, int size) {

        if("0".equals(groupId)){

            logger.info("groupId = 0");

            return getAllUsers(page, size);

        }

        else
        {

            logger.info("groupId != 0");

        Group group = groupRep.findByGroupId(groupId);
        if (group == null) {
            throw new EntityNotFoundException("Group not found");
        }

        List<User> users = new ArrayList<>(group.getUsers());
        if (users.isEmpty()) {
            throw new IllegalArgumentException("Users is Emty");
        }


        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        int start = Math.min((int) pageable.getOffset(), users.size());
        int end = Math.min((start + pageable.getPageSize()), users.size());


        return users.subList(start, end);}
    }


    public ResponseAutorizate getUsersAutorizate(AutorizateReq autorizateReq) {

        ResponseAutorizate responseBody = new ResponseAutorizate();

        if(autorizteRep.findByLogin(autorizateReq.getLogin()) == null){
            responseBody.setUserId(null);
            responseBody.setStatus(false);
            responseBody.setMessageStatus("Неверный логин или пароль");

            return responseBody;
        }


        RequestAutorizate UserUZ = autorizteRep.findByLogin(autorizateReq.getLogin());

        User user = userRep.findByUserId(UserUZ.getUserId());

        if(!UserUZ.getPassword().equals(autorizateReq.getPassword())){
            responseBody.setUserId(null);
            responseBody.setStatus(false);
            responseBody.setMessageStatus("Неверный логин или пароль");
            return responseBody;
        }

        //ResponseAutorizate responseBody = new ResponseAutorizate();
        responseBody.setUserId(UserUZ.getUserId());
        responseBody.setStatus(true);
        responseBody.setMessageStatus("Авторизация прошла успешно");
        responseBody.setName(user.getName());
        responseBody.setAvatar(user.getAvatar());

        return responseBody;
    }

    public ResponseRegistration registration (RegistrationReq autorizateReq) {

        if (autorizateReq.getLogin().isEmpty() || autorizateReq.getName().isEmpty() || autorizateReq.getPassword().isEmpty() || autorizateReq.getAvatar().isEmpty() ){
            throw new IllegalArgumentException("Не все поля заполнены");
        }

        if (autorizateReq.getLogin() == null || autorizateReq.getName() == null || autorizateReq.getPassword() == null || autorizateReq.getAvatar() == null ){
            throw new IllegalArgumentException("Не все поля заполнены. Одно из полей = null");
        }

        ResponseRegistration responseBody = new ResponseRegistration();

        if(autorizteRep.findByLogin(autorizateReq.getLogin()) != null){
            //responseBody.setUserId(null);
            responseBody.setStatus(false);
            responseBody.setMessageStatus("Такой логин уже существует");

            return responseBody;
        }

        User user = new User();
        user.setName(autorizateReq.getName());
        user.setAvatar(autorizateReq.getAvatar());
        String userId = (userRep.save(user)).getUserId();

        RequestAutorizate userReg = new RequestAutorizate();
        userReg.setLogin(autorizateReq.getLogin());
        userReg.setPassword(autorizateReq.getPassword());
        userReg.setUserId(userId);
        autorizteRep.save(userReg);
        responseBody.setMessageStatus("Пользователь создан");

        return responseBody;
    }

}
