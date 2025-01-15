package com.esteco.gitinsight.response;

import com.esteco.gitinsight.github.dto.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseJsonTest {
    @Test
    void testGetLanguage() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        File response = new File(queryUrl.toURI());

        List<CodeLanguage> codeLanguages = responseJson.getLanguages(response);

        assertFalse(codeLanguages.isEmpty());
        assertEquals(2, codeLanguages.size());
        CodeLanguage first = codeLanguages.getFirst();
        assertEquals("Java", first.name());
        assertEquals("#b07219", first.color());
        assertEquals("MDg6TGFuZ3VhZ2UxNTg=", first.id());
    }

    @Test
    void testGetLabels() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        File response = new File(queryUrl.toURI());
        Map<String, List<IssueLabel>> issueLabels = responseJson.getLabels(response);

        assertFalse(issueLabels.isEmpty());
        assertEquals(1, issueLabels.size());
        List<IssueLabel> first = issueLabels.get("MDU6SXNzdWUzNTc3ODA3Njc=");
        IssueLabel firstLabel = first.get(0);
        assertEquals("kind/enhancement", firstLabel.name());
        assertEquals("a2eeef", firstLabel.color());
        assertEquals("MDU6TGFiZWw5ODUzNDY2MjI=", firstLabel.id());

    }

    @Test
    void testGetGitAuthor() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        File response=new File(queryUrl.toURI());


        List<GitUser> gitUsers=responseJson.getGitAuthor(response);
        assertFalse(gitUsers.isEmpty());
        assertEquals(1, gitUsers.size());
        GitUser first = gitUsers.getFirst();
        assertEquals("Sanne", first.login());
        assertEquals("https://github.com/Sanne", first.url());
    }

    @Test
    void testGetPRCommits() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        File response = new File(queryUrl.toURI());

        Map<String, List<PRCommit>> PRCommits = responseJson.getIssueCommits(response);

        assertFalse(PRCommits.isEmpty());
        assertEquals(1, PRCommits.size());
        List<PRCommit> first = PRCommits.get("MDExOlB1bGxSZXF1ZXN0MjE3MjM0NjU0");
        PRCommit firstCommit = first.getFirst();
        assertEquals("MDE3OlB1bGxSZXF1ZXN0Q29tbWl0MjE3MjM0NjU0OjQ4ZGY2Y2Y5NTJlZTZjYmY0YzhiYzdjMTUyZDMyYTQxNzhjMjcxNGQ=", firstCommit.id());
        assertEquals("https://github.com/quarkusio/quarkus/pull/30/commits/48df6cf952ee6cbf4c8bc7c152d32a4178c2714d", firstCommit.url());
    }

    @Test
    void testGetRepo() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        File response = new File(queryUrl.toURI());
        GitRepositoryData gitRepositoryData = responseJson.getRepositoryData(response);
        assertEquals("MDEwOlJlcG9zaXRvcnkxMzk5MTQ5MzI=", gitRepositoryData.id());
        assertEquals("https://github.com/quarkusio/quarkus", gitRepositoryData.url());
        assertEquals("2018-07-06T00:44:20", gitRepositoryData.createdAt().toString());
        assertFalse(gitRepositoryData.isPrivate());
        assertNotNull(gitRepositoryData.updatedAt());

    }



}
