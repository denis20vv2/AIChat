package com.example.AIChat.Reaction.Web;

import com.example.AIChat.Group.Web.GroupController;
import com.example.AIChat.Message.Service.MessageService;
import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.Reaction.DTO.ReactionDTO;
import com.example.AIChat.Reaction.Domain.ReactionBlock;
import com.example.AIChat.Reaction.Service.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

}
