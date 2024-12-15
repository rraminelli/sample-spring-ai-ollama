package com.rraminelli.samplespringaiollama.controller;

import com.rraminelli.samplespringaiollama.dto.ChatRequest;
import com.rraminelli.samplespringaiollama.service.Llama2AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
@RestController
public class ChatController {

    private final Llama2AiService llama2AiService;

    @GetMapping("/ai/generate")
    public String generateResponse(@RequestParam(value = "prompt", defaultValue = "what is java") String prompt) {
        log.info("Sending prompt:: {} to Llama2 model ", prompt);
        return llama2AiService.generateResponse(prompt);
    }


    @PostMapping("/ai/generateStream")
    public Flux<String> generateStreamResponse(@RequestBody ChatRequest prompt) {
        log.info("Sending prompt message:: {} to Llama2 model ", prompt.getPromptMessage());
        return llama2AiService.generateStreamResponse(prompt.getPromptMessage());
    }

}
