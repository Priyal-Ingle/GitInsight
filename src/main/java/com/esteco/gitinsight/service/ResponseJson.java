package com.esteco.gitinsight.service;

import com.esteco.gitinsight.dto.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class ResponseJson {

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public Map<String, List<CodeLanguage>> getLanguages(File file) throws IOException {
        Map<String, List<CodeLanguage>> languages = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(file);
        JsonNode responseLanguages = jsonNode.findPath("languages");
        String repo_id = jsonNode.findPath("id").asText();
        JsonNode edges = responseLanguages.findPath("edges");
        List<CodeLanguage> languagesList = new ArrayList<>();
        edges.iterator().forEachRemaining(node -> {
            JsonNode id = node.findValue("id");
            JsonNode color = node.findValue("color");
            JsonNode name = node.findValue("name");
            CodeLanguage codeLanguage = new CodeLanguage(id.asText(), color.asText(), name.asText());
            languagesList.add(codeLanguage);
        });
        languages.put(repo_id, languagesList);
        return languages;
    }

    public Map<String, List<IssueLabel>> getLabels(File file) throws IOException {
        Map<String, List<IssueLabel>> labels = new HashMap<>();
        JsonNode issues = getIssueJsonNode(file).findPath("edges");
        issues.iterator().forEachRemaining(issueNode -> {
            String issueId = issueNode.findValue("id").asText();
            JsonNode labelNode = issueNode.findPath("labels").findPath("edges");
            List<IssueLabel> issueLabels = new ArrayList<>();
            if (!labelNode.isEmpty()) {
                labelNode.iterator().forEachRemaining(label -> {
                    String labelId = label.findValue("id").asText();
                    String color = label.findValue("color").asText();
                    String name = label.findValue("name").asText();
                    IssueLabel issueLabel = new IssueLabel(labelId, color, name);
                    issueLabels.add(issueLabel);
                });
                labels.put(issueId, issueLabels);
            }
        });
        return labels;
    }

    public Map<String, List<PRCommit>> getIssueCommits(File file) throws IOException {
        Map<String, List<PRCommit>> PRCommits = new HashMap<>();
        JsonNode issuesEdges = getIssueJsonNode(file).findPath("edges");
        issuesEdges.iterator().forEachRemaining(edges -> {
            JsonNode prEdges = edges.findPath("closedByPullRequestsReferences").findPath("edges");
            prEdges.iterator().forEachRemaining(edge -> {
                List<PRCommit> prCommits = new ArrayList<>();
                String prId = edge.findValue("id").asText();
                JsonNode commitEdges = edge.findPath("commits").findPath("edges");
                commitEdges.iterator().forEachRemaining(node -> {
                    JsonNode id = node.findValue("id");
                    JsonNode url = node.findValue("commit").findValue("url");
                    PRCommit PRCommit = new PRCommit(id.asText(), url.asText());
                    prCommits.add(PRCommit);
                });
                PRCommits.put(prId, prCommits);
            });
        });
        return PRCommits;
    }

    public GitRepositoryData getRepositoryData(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(file);
        JsonNode id = jsonNode.findPath("id");
        JsonNode url = jsonNode.findPath("url");
        JsonNode createdAt = jsonNode.findPath("createdAt");
        JsonNode updatedAt = jsonNode.findPath("updatedAt");
        JsonNode isPrivate = jsonNode.findPath("isPrivate");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return new GitRepositoryData(id.asText(), url.asText(), LocalDateTime.parse(createdAt.asText(), formatter), LocalDateTime.parse(updatedAt.asText(), formatter), isPrivate.asBoolean());
    }

    private JsonNode getIssueJsonNode(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(file);
        return jsonNode.findPath("issues");
    }

    public Map<String, GitUser> getIssueAuthor(File file) throws IOException {
        Map<String, GitUser> gitUsers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(file);
        JsonNode issueEdges = jsonNode.findPath("issues").findPath("edges");
        issueEdges.iterator().forEachRemaining(new Consumer<JsonNode>() {
            @Override
            public void accept(JsonNode node) {
                String issueId = node.findValue("id").asText();
                JsonNode issueAuthor = node.findValue("author");
                JsonNode login = issueAuthor.findValue("login");
                String url = issueAuthor.findValue("url") == null ? null : issueAuthor.findValue("url").asText();
                GitUser issueCreator = new GitUser(login == null ? null : login.asText(), url);
                gitUsers.put(issueId, issueCreator);
            }
        });
        return gitUsers;
    }

    public Map<String, GitUser> getIssueCommentAuthor(File file) throws IOException {
        Map<String, GitUser> gitUsers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(file);
        JsonNode issues = jsonNode.findPath("issues");
        JsonNode issueEdge = issues.findPath("edges");
        issueEdge.iterator().forEachRemaining(node -> {
            JsonNode issueCommentsEdge = node.findPath("comments").findPath("edges");
            if (!issueCommentsEdge.isEmpty()) {
                getAuthor(gitUsers, issueCommentsEdge);
            }
        });
        return gitUsers;
    }

    public Map<String, GitUser> getPullRequestAuthor(File file) throws IOException {
        Map<String, GitUser> gitUsers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(file);
        JsonNode issues = jsonNode.findPath("issues");
        JsonNode issueEdge = issues.findPath("edges");
        issueEdge.iterator().forEachRemaining(node -> {
            JsonNode PREdge = node.findPath("closedByPullRequestsReferences").findPath("edges");
            PREdge.iterator().forEachRemaining(PRNode -> {
                String PRId = PRNode.findValue("id").asText();
                JsonNode PRAuthor = PRNode.findValue("author");
                JsonNode login = PRAuthor.findValue("login");
                JsonNode url = PRAuthor.findValue("url");
                GitUser PRCreator = new GitUser(login == null ? null : login.asText(), url.asText());
                gitUsers.put(PRId, PRCreator);
            });
        });
        return gitUsers;
    }


    public Map<String, List<Comment>> getCommentData(File file) throws IOException {
        Map<String, List<Comment>> gitIssueComments = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(file);
        JsonNode issues = jsonNode.findPath("issues");
        JsonNode issueEdge = issues.findPath("edges");
        issueEdge.iterator().forEachRemaining(node -> {
            String issueId = node.findValue("id").asText();
            List<Comment> comments = new ArrayList<>();
            JsonNode issueCommentsEdge = node.findPath("comments").findPath("edges");
            if (!issueCommentsEdge.isEmpty()) {
                issueCommentsEdge.iterator().forEachRemaining(commentNode -> {
                    String issueCommentId = commentNode.findValue("id").asText();
                    String commentBody = commentNode.findValue("body").asText();
                    String commentCreatedAt = commentNode.findValue("createdAt").asText();
                    Comment comment = new Comment(issueCommentId, commentBody, LocalDateTime.parse(commentCreatedAt, formatter));
                    comments.add(comment);
                });
                gitIssueComments.put(issueId, comments);
            }
        });
        return gitIssueComments;
    }


    public Map<String, List<GitUser>> getIssueAssignees(File response) throws IOException {
        Map<String, List<GitUser>> gitUsers = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response);
        JsonNode issues = jsonNode.findPath("issues");
        JsonNode issueEdge = issues.findPath("edges");

        issueEdge.iterator().forEachRemaining(node -> {
            String issueId = node.findValue("id").asText();
            List<GitUser> assignees = new ArrayList<>();
            JsonNode issueAssigneesEdge = node.findPath("assignees").findPath("edges");
            if (!issueAssigneesEdge.isEmpty()) {
                issueAssigneesEdge.iterator().forEachRemaining(assigneesNode -> {
                    JsonNode login = assigneesNode.findValue("login");
                    String url = assigneesNode.findValue("url") == null ? null : assigneesNode.findValue("url").asText();
                    GitUser issueAssignee = new GitUser(login == null ? null : login.asText(), url);
                    assignees.add(issueAssignee);
                });
                gitUsers.put(issueId, assignees);
            }
        });

        return gitUsers;
    }

    private void getAuthor(Map<String, GitUser> gitUsers, JsonNode issues) {
        issues.iterator().forEachRemaining(node -> {
            String issueId = node.findValue("id").asText();
            JsonNode issueAuthor = node.findValue("author");
            JsonNode login = issueAuthor.findValue("login");
            String url = issueAuthor.findValue("url") == null ? null : issueAuthor.findValue("url").asText();
            GitUser issueCreator = new GitUser(login == null ? null : login.asText(), url);
            gitUsers.put(issueId, issueCreator);
        });
    }

    public Map<String, List<GitPullRequest>> getClosedPullRequests(File response) throws IOException {
        Map<String, List<GitPullRequest>> closedPullRequests = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response);
        JsonNode issues = jsonNode.findPath("issues");
        JsonNode issueEdge = issues.findPath("edges");
        issueEdge.iterator().forEachRemaining(node -> {
            String issueId = node.findValue("id").asText();
            List<GitPullRequest> pullRequests = new ArrayList<>();
            JsonNode PREdge = node.findPath("closedByPullRequestsReferences").findPath("edges");
            if(!PREdge.isEmpty()) {

                PREdge.iterator().forEachRemaining(PRNode -> {
                    String pullRequestId = PRNode.findValue("id").asText();
                    String pullRequestTitle = PRNode.findValue("title").asText();
                    String pullRequestBody = PRNode.findValue("body").asText();
                    String pullRequestUrl = PRNode.findValue("url").asText();
                    boolean closed = PRNode.findValue("closed").asBoolean();
                    LocalDateTime createdAt = LocalDateTime.parse(PRNode.findValue("createdAt").asText(), formatter);
                    LocalDateTime closedAt = PRNode.findValue("closedAt") == null || "null".equals(PRNode.findValue("closedAt").asText())
                            ? null
                            : LocalDateTime.parse(PRNode.findValue("closedAt").asText(), formatter);
                    //GitPullRequest(String id, String title, String body, String url, LocalDateTime createdAt,
                    // LocalDateTime closedAt, boolean closed)
                    GitPullRequest gitPullRequest = new GitPullRequest(pullRequestId, pullRequestTitle, pullRequestBody, pullRequestUrl, createdAt, closedAt, closed);
                    pullRequests.add(gitPullRequest);
                });
                closedPullRequests.put(issueId, pullRequests);
            }
        });
        return closedPullRequests;
    }

    public Map<String, List<GitIssue>> getIssues(File response) throws IOException {
        Map<String, List<GitIssue>> repoIssues = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response);
        String repoId = jsonNode.findValue("id").asText();
        JsonNode issues = jsonNode.findPath("issues");
        JsonNode issueEdge = issues.findPath("edges");
        List<GitIssue> gitIssues = new ArrayList<>();
        issueEdge.iterator().forEachRemaining(node -> {
            String issueId = node.findValue("id").asText();
            String issueTitle = node.findValue("title").asText();
            String issueBody = node.findValue("body").asText();
            String issueUrl = node.findValue("url").asText();
            boolean closed = node.findValue("closed").asBoolean();
            LocalDateTime createdAt = LocalDateTime.parse(node.findValue("createdAt").asText(), formatter);
            LocalDateTime closedAt = LocalDateTime.parse(node.findValue("closedAt").asText(), formatter);
            GitIssue gitIssue = new GitIssue(issueId, issueTitle, issueBody, issueUrl, createdAt, closedAt, closed);
            gitIssues.add(gitIssue);

        });
        repoIssues.put(repoId, gitIssues);
        return repoIssues;
    }
}
