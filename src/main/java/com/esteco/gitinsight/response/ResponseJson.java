package com.esteco.gitinsight.response;

import com.esteco.gitinsight.github.dto.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseJson {
    public List<CodeLanguage> getLanguages(File file) throws IOException {
        List<CodeLanguage> languages = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(file);
        JsonNode responseLanguages = jsonNode.findPath("languages");
        JsonNode edges = responseLanguages.findPath("edges");
        edges.iterator().forEachRemaining(edge -> {
            JsonNode node = edges.findPath("node");
            JsonNode id = node.findValue("id");
            JsonNode color = node.findValue("color");
            JsonNode name = node.findValue("name");
            CodeLanguage codeLanguage = new CodeLanguage(id.asText(), color.asText(), name.asText());
            languages.add(codeLanguage);
        });
        return languages;
    }

    public Map<String, List<IssueLabel>> getLabels(File file) throws IOException {
        Map<String, List<IssueLabel>> labels = new HashMap<>();
        JsonNode issues = getIssueJsonNode(file);
        issues.iterator().forEachRemaining(edges -> {
            String issueId = edges.findValue("id").asText();
            JsonNode labelNode = edges.findPath("labels").findPath("edges");
            List<IssueLabel> issueLabels = new ArrayList<>();
            labelNode.iterator().forEachRemaining(label -> {
                String labelId = label.findValue("id").asText();
                String color = label.findValue("color").asText();
                String name = label.findValue("name").asText();
                IssueLabel issueLabel = new IssueLabel(labelId, color, name);
                issueLabels.add(issueLabel);
            });
            labels.put(issueId, issueLabels);
        });
        return labels;
    }

    public List<GitUser> getGitAuthor(File file) throws IOException {
        List<GitUser> gitUsers = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(file);
        JsonNode responseLabels = jsonNode.findPath("author");
        JsonNode login = responseLabels.findValue("login");
        JsonNode url = responseLabels.findValue("url");
        GitUser gitUser = new GitUser(login.asText(), url.asText());
        gitUsers.add(gitUser);

        return gitUsers;
    }

    public Map<String, List<PRCommit>> getIssueCommits(File file) throws IOException {
        Map<String, List<PRCommit>> PRCommits = new HashMap<>();
        JsonNode issues = getIssueJsonNode(file);
        issues.iterator().forEachRemaining(edges -> {
            JsonNode prEdges = edges.findPath("closedByPullRequestsReferences").findPath("edges");
            prEdges.iterator().forEachRemaining(edge -> {
                List<PRCommit> prCommits = new ArrayList<>();
                String prId = edge.findValue("id").asText();
                JsonNode commits = edge.findPath("commits").findPath("edges");
                commits.iterator().forEachRemaining(commit -> {
                    JsonNode node = commit.findPath("node");
                    JsonNode id = node.findValue("id");
                    JsonNode url = node.findValue("url");
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
}
