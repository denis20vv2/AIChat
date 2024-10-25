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

@RestController
@RequestMapping("/group")
@Tag(name="Group")
@RequiredArgsConstructor
@Validated
public class GroupController {

    private  final GroupService groupService;
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Operation(
            summary = "Создание новой группы",
            description = "Ожидаем на вход объект типа group и сохраняем его в БД"
    )
    @PostMapping("CreateGroup")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Group createGroup (@Valid @RequestBody PostGroupReq postGroupReq) {
        logger.info("Получен запрос на создание Новой группы");
        return groupService.createGroup(postGroupReq);
    }

    @Operation(
            summary = "Получение группы",
            description = "Получение всех групп с пагинацией"
    )
    @GetMapping("GetChats/All")
    //@ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<Group> GetAllGroup (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        logger.info("Получен запрос на получение всех групп");
        return groupService.getAllGroups(page, size);
    }

}
