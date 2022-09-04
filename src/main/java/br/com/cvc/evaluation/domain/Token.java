package br.com.cvc.evaluation.domain;

import lombok.Builder;

@Builder
public record Token (String type, String token) { }
