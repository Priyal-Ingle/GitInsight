package com.esteco.gitinsight.dto;

import java.time.LocalDateTime;

public record GitRepositoryData(String id, String url, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isPrivate) {
}
