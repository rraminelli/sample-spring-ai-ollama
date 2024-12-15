package com.rraminelli.samplespringaiollama.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HistoryEntry {

    private String prompt;

    private String response;

    @Override
    public String toString() {
        return String.format("""
                        'history_entry':
                            'prompt': %s
                        
                            'response': %s
                        -----------------
                       \n
            """, prompt, response);
    }
}