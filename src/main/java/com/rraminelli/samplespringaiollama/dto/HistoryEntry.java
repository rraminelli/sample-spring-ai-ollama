package com.rraminelli.samplespringaiollama.dto;


public record HistoryEntry(String prompt, String response) {

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