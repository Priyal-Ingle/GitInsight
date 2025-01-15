package com.esteco.gitinsight.github.dto;

import java.time.LocalDateTime;

public record GitRepositoryData(String id, String url, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isPrivate) {
}
