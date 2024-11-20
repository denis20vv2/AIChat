package com.example.AIChat.Group.Web;

import com.example.AIChat.Group.DTO.PostGroupReq;
import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Group.Service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name="Group")
@RequiredArgsConstructor
@Validated
public class GroupController {

    private  final GroupService groupService;
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Operation(
            summary = "Создание нового чата",
            description = "Ожидаем на вход объект типа group и сохраняем его в БД"
    )
    @PostMapping("createGroup")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Group createGroup (@Valid @RequestBody PostGroupReq postGroupReq) {
        logger.info("getting request on creating new group");
        return groupService.createGroup(postGroupReq);
    }

    @Operation(
            summary = "Получение чатов по userId",
            description = "Получение userid, всех связаных с userи вывод groups"
    )
    @GetMapping("chats/{userId}")
    //@ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<Group> GetAllGroupsByUserId (@PathVariable String userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        logger.info("get chats by userid");
        return groupService.getAllGroupsByUserId(userId, page, size);
    }

    @Operation(
            summary = "Получение чата по groupId",
            description = "Получение чата по groupId"
    )
    @GetMapping("chatsById/{groupId}")
    //@ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Group GetGroupByGroupId (@PathVariable String groupId) {
        logger.info("get chats by groupId");
        return groupService.GetGroupByGroupId(groupId);
    }


   /* @Operation(
            summary = "Получение пользователей ",
            description = "Получение всех групп с пагинацией"
    )
    @GetMapping("GetChats/All")
    @ResponseBody
    public List<Group> GetAllGroups (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        logger.info("Получен запрос на получение всех групп");
        return groupService.getAllGroups(page, size);
    }*/

}
