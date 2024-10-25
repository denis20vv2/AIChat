package com.example.AIChat.User.Web;

import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Group.Service.GroupService;
import com.example.AIChat.Group.Web.GroupController;
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
@RequestMapping("/user")
@Tag(name="User")
@RequiredArgsConstructor
@Validated
public class UserController {

    private  final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Operation(
            summary = "Получение группы",
            description = "Получение всех групп с пагинацией"
    )
    @GetMapping("GetUsers/All")
    //@ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<User> GetAllUsers (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        logger.info("Получен запрос на получение всех пользователей");
        return userService.getAllUsers(page, size);
    }
}
