package com.esteco.gitinsight.dto;

import java.util.List;

public record Repository(
        String id,
        String url,
        String updatedAt,
        String createdAt,
        int forkCount,
        boolean isPrivate,
        boolean isLocked,
        Labels labels,
        Languages languages,
        Issues issues
) {
    public record Labels(List<LabelEdge> edges) {
        public record LabelEdge(Label node) {
            public record Label(String name) {}
        }
    }
    public record Languages(List<LanguageEdge> edges) {
        public record LanguageEdge(Language node) {
            public record Language(String name) {}
        }
    }
    public record Issues(List<IssueEdge> edges) {
        public record IssueEdge(Issue node) {
            public record Issue(
                    String id,
                    String title,
                    String url,
                    String body,
                    boolean closed,
                    String closedAt,
                    String createdAt,
                    Authors authors,
                    Assignees assignees,
                    ClosedByPullRequests closedByPullRequestsReferences
            ) {
                public record Authors(List<AuthorEdge> edges) {
                    public record AuthorEdge(Author node) {
                        public record Author(String login) {}
                    }
                }

                public record Assignees(List<AssigneeEdge> edges) {
                    public record AssigneeEdge(Assignee node) {
                        public record Assignee(String login) {}
                    }
                }

                public record ClosedByPullRequests(List<ClosedByPullRequestEdge> edges) {
                    public record ClosedByPullRequestEdge(ClosedByPullRequest node) {
                        public record ClosedByPullRequest(
                                String id,
                                String title,
                                String body,
                                String url,
                                int changedFiles,
                                Commits commits
                        ) {
                            public record Commits(List<CommitEdge> edges) {
                                public record CommitEdge(Commit node) {
                                    public record Commit(String id, String url) {}
                                }
                            }
                        }
                    }
                }

                public record Author(String login) {}
                public record Assignee(String login) {}
            }
        }
    }
}
