package com.example.AIChat.Reaction.Web;

import com.example.AIChat.Group.DTO.PostGroupReq;
import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Group.Web.GroupController;
import com.example.AIChat.Message.DTO.MessageReaction;
import com.example.AIChat.Message.Service.MessageService;
import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.Reaction.DTO.NewReactionDTO;
import com.example.AIChat.Reaction.DTO.ReactionDTO;
import com.example.AIChat.Reaction.Domain.ReactionBlock;
import com.example.AIChat.Reaction.Service.ReactionService;
import com.example.AIChat.Sockets.Service.MessageSocketService;
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
@Tag(name="reaction")
@RequiredArgsConstructor
@Validated
public class ReactionController {

        private  final ReactionService reactionService;
        private  final MessageSocketService messageSocketService;

        private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
        @Operation(
                summary = "Получение реакций",
                description = "Получение реакций из бд"
        )
        @GetMapping("reaction/all")
        @ResponseBody
        public List<ReactionDTO> GetAllReactions() {
            logger.info("Get reactions all");
            return reactionService.getAllReactions();
        }

    @Operation(
            summary = "Получение реакций по id",
            description = "Получение реакций по id из бд"
    )
    @GetMapping("reaction/{reactionId}")
    @ResponseBody
    public ReactionDTO GetReactionsById(@PathVariable String reactionId) {
        logger.info("Get reactions all");
        return reactionService.GetReactionsById(reactionId);
    }

    @Operation(
            summary = "Не используй это, чисто для теста сделал!!!",
            description = "Не используй это, чисто для теста сделал"
    )
    @PostMapping("createReaction")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public MessageReaction SaveReaction(@Valid @RequestBody NewReactionDTO newReactionDTO) {
        logger.info("Save reaction");
        return messageSocketService.saveReaction(newReactionDTO);
    }

}
