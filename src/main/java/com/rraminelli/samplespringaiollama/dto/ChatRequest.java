package com.rraminelli.samplespringaiollama.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record ChatRequest(String promptMessage) {


}