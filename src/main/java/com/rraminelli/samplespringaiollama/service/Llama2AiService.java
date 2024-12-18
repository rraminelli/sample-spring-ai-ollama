package com.rraminelli.samplespringaiollama.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class Llama2AiService {

    private final OllamaChatModel chatModel;

    public Llama2AiService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String generateResponse(String prompt) {

//        ChatResponse response = chatModel.call(
//                new Prompt(prompt, OllamaOptions.create()
//                        .withModel("llama3.1")
//                        .withTemperature(0.9)));

        ChatResponse response = chatModel.call(new Prompt(prompt));

        return response.getResult().getOutput().getContent();


//        ChatResponse response = chatModel.call(
//                new Prompt("Generate the names of 5 famous pirates."));



//        //////  OR with streaming responses
//        Flux<ChatResponse> response = chatModel.stream(
//                new Prompt("Generate the names of 5 famous pirates."));


        //////  OR by explicitly creating a Prompt object
        //  Prompt prompt = new Prompt(prompt);
        //  Flux<ChatResponse> response = chatModel.stream(prompt);
    }


       //////  OR Generating a streaming response of type Flux<ChatResponse>
//    public Flux<ChatResponse> generateStreamResponse(String prompt) {
//        Flux<ChatResponse> response = chatModel.stream(
//                new Prompt(prompt, OllamaOptions.create()
//                        //.withModel("llama3.1")
//                        .withModel(OllamaOptions.DEFAULT_MODEL)
//                        .withTemperature(0.9f)));
//
//        return response;
//
//    }



    public Flux<String> generateStreamResponse(String prompt) {
        Flux<String> response = chatModel.stream(prompt);
        return response;
    }

}