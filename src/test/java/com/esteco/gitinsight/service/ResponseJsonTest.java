package com.esteco.gitinsight.service;

import com.esteco.gitinsight.dto.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

public class ResponseJsonTest {
    @Test
    void testGetLanguage() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("../GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());

        Map<String, List<CodeLanguage>> codeLanguages = responseJson.getLanguages(response);

        assertFalse(codeLanguages.isEmpty());
        assertEquals(1, codeLanguages.size());
        List<CodeLanguage> languageList = codeLanguages.get("R_kgDONXxB9w");
        CodeLanguage first = languageList.getFirst();
        assertEquals("Python", first.name());
        assertEquals("#3572A5", first.color());
        assertEquals("MDg6TGFuZ3VhZ2UxNDU=", first.id());
    }

    @Test
    void testGetLabels() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("../GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());
        Map<String, List<IssueLabel>> issueLabels = responseJson.getLabels(response);

        assertFalse(issueLabels.isEmpty());
        assertEquals(5, issueLabels.size());
        List<IssueLabel> first = issueLabels.get("I_kwDONXxB986m5_bU");
        IssueLabel firstLabel = first.getFirst();
        assertEquals("invalid", firstLabel.name());
        assertEquals("e4e669", firstLabel.color());
        assertEquals("LA_kwDONXxB988AAAAB0pbE-w", firstLabel.id());

    }

    @Test
    void testGetIssueAuthor() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("../GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());
        Map<String, GitUser> gitUsers = responseJson.getIssueAuthor(response);
        assertFalse(gitUsers.isEmpty());
        assertEquals(12, gitUsers.size());
//        GitUser first = gitUsers.get("MDU6SXNzdWUzNTc3ODA3Njc=");
//        assertEquals("Sanne", first.login());
//        assertEquals("https://github.com/Sanne", first.url());
    }

    @Test
    void testGetIssueCommentAuthor() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("../GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());
        Map<String, GitUser> comments = responseJson.getIssueCommentAuthor(response);
        assertFalse(comments.isEmpty());
        assertEquals(15, comments.size());
//        GitUser first = comments.get("MDEyOklzc3VlQ29tbWVudDQxOTIxNzk3NQ==");
//        assertEquals("dmlloyd", first.login());
//        assertEquals("https://github.com/dmlloyd", first.url());
    }

    @Test
    void testGetPRAuthor() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("../GraphQLResponse.json");
        File response = new File(queryUrl.toURI());

        Map<String, GitUser> gitUsers = responseJson.getPullRequestAuthor(response);

        assertFalse(gitUsers.isEmpty());
        assertEquals(2, gitUsers.size());
//        GitUser first = gitUsers.get("MDExOlB1bGxSZXF1ZXN0MjE3MjM0NjU0");
//        assertEquals("mkouba", first.login());
//        assertEquals("https://github.com/mkouba", first.url());
    }

    @Test
    void testGetPRCommits() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("../GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());

        Map<String, List<PRCommit>> PRCommits = responseJson.getIssueCommits(response);

        assertFalse(PRCommits.isEmpty());
        assertEquals(2, PRCommits.size());
        List<PRCommit> first = PRCommits.get("PR_kwDONXxB986IaKyi");
        PRCommit firstCommit = first.getFirst();
        assertEquals("https://github.com/PathOfBuildingCommunity/PathOfBuilding-PoE2/commit/4edf5f8be77d4ea569d8d2f2a4fd1f26e9febfc2", firstCommit.url());
    }

    @Test
    void testGetRepo() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("../GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());
        GitRepositoryData gitRepositoryData = responseJson.getRepositoryData(response);
        assertEquals("R_kgDONXxB9w", gitRepositoryData.id());
        assertEquals("https://github.com/PathOfBuildingCommunity/PathOfBuilding-PoE2", gitRepositoryData.url());
//        assertEquals("2018-07-06T00:44:20", gitRepositoryData.createdAt().toString());
        assertFalse(gitRepositoryData.isPrivate());
        assertNotNull(gitRepositoryData.updatedAt());

    }

    @Test
    void testGetIssueComment() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("../GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());

        Map<String, List<Comment>> issueComments = responseJson.getCommentData(response);

        assertEquals(10, issueComments.size());
        List<Comment> firstIssueComments = issueComments.get("I_kwDONXxB986m5_bU");
        Comment firstIssueComment = firstIssueComments.getFirst();
        assertEquals("IC_kwDONXxB986bKdES", firstIssueComment.id());
//        assertEquals("I believe you could adapt it by returning a Function which returns a subclass of ClassWriter, which calls your transform at visitEnd and then uses a ClassReader initialized with the resultant class bytes to accept the passed-in ClassVisitor.", firstIssueComment.body());
//        assertEquals("2018-09-06T19:44:48", firstIssueComment.createdAt().toString());

    }

    @Test
    void testGetIssueAssignees() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("../GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());

        Map<String, List<GitUser>> issueAssignees = responseJson.getIssueAssignees(response);

        assertFalse(issueAssignees.isEmpty());
        assertEquals(1, issueAssignees.size());
        List<GitUser> first = issueAssignees.get("I_kwDONXxB986m50lz");
        assertEquals("HeavyGravy", first.getFirst().login());
        assertEquals("https://github.com/HeavyGravy", first.getFirst().url());

    }

    @Test
    void testGetPullRequest() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("../GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());

        Map<String, List<GitPullRequest>> closedPullRequests = responseJson.getClosedPullRequests(response);

        List<GitPullRequest> gitPullRequests = closedPullRequests.get("I_kwDONXxB986m50lz");
        GitPullRequest firstGitPullRequest = gitPullRequests.getFirst();
        assertEquals("PR_kwDONXxB986IaKyi", firstGitPullRequest.id());
        assertEquals("Fix projectile scaling for Bonestorm and Gas Arrow", firstGitPullRequest.title());


    }

    @Test
    void testGetIssues() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("../GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());

        Map<String, List<GitIssue>> repoIssues = responseJson.getIssues(response);

        List<GitIssue> gitIssues = repoIssues.get("R_kgDONXxB9w");
        GitIssue firstGitIssue = gitIssues.getFirst();
        assertEquals("I_kwDONXxB986m50lz", firstGitIssue.id());
        assertEquals("Projectile Damage modifiers do not apply to Bonestorm", firstGitIssue.title());
        assertEquals("https://github.com/PathOfBuildingCommunity/PathOfBuilding-PoE2/issues/470", firstGitIssue.url());
        assertTrue(firstGitIssue.closed());
    }


}
