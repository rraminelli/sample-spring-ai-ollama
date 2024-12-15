package com.rraminelli.samplespringaiollama.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HelpDeskRequest {
    String promptMessage;

    String historyId;

}