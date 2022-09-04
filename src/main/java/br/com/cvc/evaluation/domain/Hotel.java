package br.com.cvc.evaluation.domain;

import java.util.List;

import lombok.Builder;

@Builder
public record Hotel(Integer id, String cityName, String name, List<Room> rooms) { };
