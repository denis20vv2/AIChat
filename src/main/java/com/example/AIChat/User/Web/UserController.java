package com.example.AIChat.User.Web;

import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Group.Service.GroupService;
import com.example.AIChat.Group.Web.GroupController;
import com.example.AIChat.User.Domain.User;
import com.example.AIChat.User.Service.UserService;
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
@RequestMapping("/api")
@Tag(name="User")
@RequiredArgsConstructor
@Validated
public class UserController {

    private  final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Operation(
            summary = "Получение всех пользователей",
            description = "Получение всех групп с пагинацией"
    )
    @GetMapping("GetUsers/All")
    //@ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<User> GetAllUsers (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        logger.info("Получен запрос на получение всех пользователей");
        return userService.getAllUsers(page, size);
    }

    @Operation(
            summary = "Получение всех юзеров чата по groupId",
            description = "Получение юзеров с groupId. Если groupId = 0, то получаем всех пользователей "
    )
    @GetMapping("userList/{groupId}")
    //@ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<User> GetUsersByUserId (@PathVariable String groupId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        logger.info("get userList by groupId");
        return userService.getUsersByGroupId(groupId, page, size);
    }

    @Operation(
            summary = "авторизация"
    )
    @PostMapping("authorization")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseAutorizate getUsersAutorizate(@Valid @RequestBody AutorizateReq autorizateReq) {
        logger.info("request on authorization");
        return userService.getUsersAutorizate(autorizateReq);
    }

    @Operation(
            summary = "Регистрация"
    )
    @PostMapping("registration")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseRegistration registration (@Valid @RequestBody RegistrationReq registrationReq) {
        logger.info("request on registration");
        return userService.registration(registrationReq);
    }

}
