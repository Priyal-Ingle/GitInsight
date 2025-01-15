package com.esteco.gitinsight.github.dto;

import java.time.LocalDateTime;

public record Comment(String id, String body, String createdAt) {
}
