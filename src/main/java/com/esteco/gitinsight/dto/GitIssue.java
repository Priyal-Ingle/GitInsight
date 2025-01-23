package com.esteco.gitinsight.dto;

import java.time.LocalDateTime;

public record GitIssue(String id, String title, String body, String url, LocalDateTime createdAt,
                       LocalDateTime closedAt, boolean closed) {
}
