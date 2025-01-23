package com.esteco.gitinsight.dto;


import java.time.LocalDateTime;

public record Comment(String id, String body, LocalDateTime createdAt) {
}
