package com.example.AIChat.Message.Web;

import com.example.AIChat.Group.Web.GroupController;
import com.example.AIChat.Message.Service.MessageService;
import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.User.Domain.User;
import com.example.AIChat.User.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@Tag(name="message")
@RequiredArgsConstructor
@Validated
public class MessageController {

    private  final MessageService messageService;

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Operation(
            summary = "Получение всех сообщений",
            description = "Получение всех сообщений с пагинацией"
    )
    @GetMapping("GetMessages/All")
    @ResponseBody
    public List<Message> GetAllMessages (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        logger.info("Получен запрос на получение всех сообщений");
        return messageService.getAllMessages(page, size);
    }


    @Operation(
            summary = "Получение всех сообщений группы с groupId",
            description = "По полученому groupId выводим все сообщения этой группы"
    )
    @GetMapping("GetMessage/Id/{groupId}")
    @ResponseBody
    public List<Message> getAllMessagesByGroupId (@PathVariable String groupId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return messageService.getAllMessagesByGroupId(groupId, page, size);
    }


}
