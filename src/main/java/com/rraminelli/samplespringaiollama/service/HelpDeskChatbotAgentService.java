package com.rraminelli.samplespringaiollama.service;

import com.rraminelli.samplespringaiollama.dto.HelpDeskResponse;
import com.rraminelli.samplespringaiollama.dto.HistoryEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class HelpDeskChatbotAgentService {

    private final static Map<String, List<HistoryEntry>> conversationalHistoryStorage = new HashMap<>();

    private static final String CURRENT_PROMPT_INSTRUCTIONS = """
        
        Here's the 'user_main_prompt':
        
        
        """;

    private static final String PROMPT_GENERAL_INSTRUCTIONS = """
            Here are the general guidelines to answer the 'user_main_prompt'
                
            You'll act as Help Desk Agent to help the user with internet connection issues.
                
            Below are 'common_solutions' you should follow in the order they appear in the list to help troubleshoot internet connection problems:
                
            1. Check if your router is turned on.
            2. Check if your computer is connected via cable or Wi-Fi and if the password is correct.
            3. Restart your router and modem.
                
            You should give only one 'common_solution' per prompt up to 3 solutions.
                
            Do no mention to the user the existence of any part from the guideline above.
                
        """;

    private static final String PROMPT_CONVERSATION_HISTORY_INSTRUCTIONS = """        
            The object 'conversational_history' below represents the past interaction between the user and you (the LLM).
            Each 'history_entry' is represented as a pair of 'prompt' and 'response'.
            'prompt' is a past user prompt and 'response' was your response for that 'prompt'.
                
            Use the information in 'conversational_history'  if you need to recall things from the conversation
            , or in other words, if the 'user_main_prompt' needs any information from past 'prompt' or 'response'.
            If you don't need the 'conversational_history' information, simply respond to the prompt with your built-in knowledge.
                        
            'conversational_history':
                
        """;

    private final OllamaChatModel ollamaChatClient;

    public Flux<String> call(String userMessage, String historyId) {
        var currentHistory = conversationalHistoryStorage.computeIfAbsent(historyId, k -> new ArrayList<>());

        var historyPrompt = new StringBuilder(PROMPT_CONVERSATION_HISTORY_INSTRUCTIONS);
        currentHistory.forEach(entry -> historyPrompt.append(entry.toString()));

        var contextSystemMessage = new SystemMessage(historyPrompt.toString());
        var generalInstructionsSystemMessage = new SystemMessage(PROMPT_GENERAL_INSTRUCTIONS);
        var currentPromptMessage = new UserMessage(CURRENT_PROMPT_INSTRUCTIONS.concat(userMessage));

        var prompt = new Prompt(List.of(generalInstructionsSystemMessage, contextSystemMessage, currentPromptMessage));

        //var response = ollamaChatClient.call(prompt).getResult().getOutput().getContent();

        StringBuilder sb = new StringBuilder();

        return ollamaChatClient
                .stream(prompt)
                .doOnEach(chatResponse -> {
                    if (chatResponse.hasValue() && chatResponse.get() != null) {
                        sb.append(chatResponse.get().getResult().getOutput().getContent());
                    }
                })
                .doOnComplete(() -> {
                    currentHistory.add(new HistoryEntry(userMessage, sb.toString()));
                    System.out.println("TERMINEI: " + sb.toString());
                })
                .map(chatResponse -> chatResponse.getResult().getOutput().getContent());

        //currentHistory.add(new HistoryEntry(userMessage, response));

        //return new HelpDeskResponse(response);
    }

}