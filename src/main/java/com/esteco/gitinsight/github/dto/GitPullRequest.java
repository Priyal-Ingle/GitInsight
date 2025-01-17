package com.esteco.gitinsight.github.dto;

import java.time.LocalDateTime;

public record GitPullRequest(String id, String title, String body, String url, LocalDateTime createdAt, LocalDateTime closedAt, boolean closed) {
}


//public record ClosedByPullRequest(
//        String id,
//        String title,
//        String body,
//        String url,
//        int changedFiles,
//        Repository.Issues.IssueEdge.Issue.ClosedByPullRequests.ClosedByPullRequestEdge.ClosedByPullRequest.Commits commits
//)