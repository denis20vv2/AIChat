package com.example.AIChat.Group.Web;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
@Tag(name="Author")
@RequiredArgsConstructor
@Validated
public class GroupController {
}
