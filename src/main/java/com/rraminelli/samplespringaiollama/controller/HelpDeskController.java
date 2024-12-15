package com.rraminelli.samplespringaiollama.controller;

import com.rraminelli.samplespringaiollama.dto.HelpDeskRequest;
import com.rraminelli.samplespringaiollama.service.HelpDeskChatbotAgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/helpdesk")
public class HelpDeskController {

    private final HelpDeskChatbotAgentService helpDeskChatbotAgentService;

    @PostMapping("/chat")
    public Flux<String> chat(@RequestBody HelpDeskRequest helpDeskRequest) {
        return helpDeskChatbotAgentService.call(helpDeskRequest.getPromptMessage(), helpDeskRequest.getHistoryId());
    }
}