package com.esteco.gitinsight.response;

import com.esteco.gitinsight.github.dto.*;
import com.esteco.gitinsight.model.entity.PullRequest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
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
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());

        Map<String, List<CodeLanguage>> codeLanguages = responseJson.getLanguages(response);

        assertFalse(codeLanguages.isEmpty());
        assertEquals(1, codeLanguages.size());
        List<CodeLanguage> languageList = codeLanguages.get("MDEwOlJlcG9zaXRvcnkxMzk5MTQ5MzI=");
        CodeLanguage first = languageList.getFirst();
        assertEquals("Java", first.name());
        assertEquals("#b07219", first.color());
        assertEquals("MDg6TGFuZ3VhZ2UxNTg=", first.id());
    }

    @Test
    void testGetLabels() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());
        Map<String, List<IssueLabel>> issueLabels = responseJson.getLabels(response);

        assertFalse(issueLabels.isEmpty());
        assertEquals(1, issueLabels.size());
        List<IssueLabel> first = issueLabels.get("MDU6SXNzdWUzNTc3ODA3Njc=");
        IssueLabel firstLabel = first.getFirst();
        assertEquals("kind/enhancement", firstLabel.name());
        assertEquals("a2eeef", firstLabel.color());
        assertEquals("MDU6TGFiZWw5ODUzNDY2MjI=", firstLabel.id());

    }

    @Test
    void testGetIssueAuthor() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());
        Map<String, GitUser> gitUsers = responseJson.getIssueAuthor(response);
        assertFalse(gitUsers.isEmpty());
        assertEquals(10, gitUsers.size());
        GitUser first = gitUsers.get("MDU6SXNzdWUzNTc3ODA3Njc=");
        assertEquals("Sanne", first.login());
        assertEquals("https://github.com/Sanne", first.url());
    }

    @Test
    void testGetIssueCommentAuthor() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());
        Map<String, GitUser> comments = responseJson.getIssueCommentAuthor(response);
        assertFalse(comments.isEmpty());
        assertEquals(8, comments.size());
        GitUser first = comments.get("MDEyOklzc3VlQ29tbWVudDQxOTIxNzk3NQ==");
        assertEquals("dmlloyd", first.login());
        assertEquals("https://github.com/dmlloyd", first.url());
    }

    @Test
    void testGetPRAuthor() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        File response = new File(queryUrl.toURI());

        Map<String, GitUser> gitUsers = responseJson.getPullRequestAuthor(response);

        assertFalse(gitUsers.isEmpty());
        assertEquals(6, gitUsers.size());
        GitUser first = gitUsers.get("MDExOlB1bGxSZXF1ZXN0MjE3MjM0NjU0");
        assertEquals("mkouba", first.login());
        assertEquals("https://github.com/mkouba", first.url());
    }

    @Test
    void testGetPRCommits() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());

        Map<String, List<PRCommit>> PRCommits = responseJson.getIssueCommits(response);

        assertFalse(PRCommits.isEmpty());
        assertEquals(6, PRCommits.size());
        List<PRCommit> first = PRCommits.get("MDExOlB1bGxSZXF1ZXN0MjE3MjM0NjU0");
        PRCommit firstCommit = first.getFirst();
        assertEquals("MDE3OlB1bGxSZXF1ZXN0Q29tbWl0MjE3MjM0NjU0OjQ4ZGY2Y2Y5NTJlZTZjYmY0YzhiYzdjMTUyZDMyYTQxNzhjMjcxNGQ=", firstCommit.id());
        assertEquals("https://github.com/quarkusio/quarkus/pull/30/commits/48df6cf952ee6cbf4c8bc7c152d32a4178c2714d", firstCommit.url());
    }

    @Test
    void testGetRepo() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());
        GitRepositoryData gitRepositoryData = responseJson.getRepositoryData(response);
        assertEquals("MDEwOlJlcG9zaXRvcnkxMzk5MTQ5MzI=", gitRepositoryData.id());
        assertEquals("https://github.com/quarkusio/quarkus", gitRepositoryData.url());
        assertEquals("2018-07-06T00:44:20", gitRepositoryData.createdAt().toString());
        assertFalse(gitRepositoryData.isPrivate());
        assertNotNull(gitRepositoryData.updatedAt());

    }

    @Test
    void testGetIssueComment() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());

        Map<String, List<Comment>> issueComments = responseJson.getCommentData(response);

        assertEquals(6, issueComments.size());
        List<Comment> firstIssueComments = issueComments.get("MDU6SXNzdWUzNTc3ODA3Njc=");
        Comment firstIssueComment = firstIssueComments.getFirst();
        assertEquals("MDEyOklzc3VlQ29tbWVudDQxOTIxNzk3NQ==", firstIssueComment.id());
        assertEquals("I believe you could adapt it by returning a Function which returns a subclass of ClassWriter, which calls your transform at visitEnd and then uses a ClassReader initialized with the resultant class bytes to accept the passed-in ClassVisitor.", firstIssueComment.body());
        assertEquals("2018-09-06T19:44:48", firstIssueComment.createdAt().toString());

    }

    @Test
    void testGetIssueAssignees() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());

        Map<String, List<GitUser>> issueAssignees = responseJson.getIssueAssignees(response);

        assertFalse(issueAssignees.isEmpty());
        assertEquals(2, issueAssignees.size());
        List<GitUser> first = issueAssignees.get("MDU6SXNzdWUzNTc3ODA3Njc=");
        assertEquals("dmlloyd", first.getFirst().login());
        assertEquals("https://github.com/dmlloyd", first.getFirst().url());

    }

    @Test
    void testGetPullRequest() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());

        Map<String, List<GitPullRequest>> closedPullRequests = responseJson.getClosedPullRequests(response);

        List<GitPullRequest> gitPullRequests = closedPullRequests.get("MDU6SXNzdWUzNTc3ODA3Njc=");
        GitPullRequest firstGitPullRequest = gitPullRequests.getFirst();
        assertEquals("MDExOlB1bGxSZXF1ZXN0MjE3MjM0NjU0", firstGitPullRequest.id());
        assertEquals("Arc - make it possible to get interceptor bindings from", firstGitPullRequest.title());
        assertEquals("InvocationContext\r\n\r\n" +
                "- resolves #29 \r\n\r\n" +
                "NOTE: Currently we only support array members with `Class` component type. It should be straightforward to support other component types.", firstGitPullRequest.body());
        assertEquals("https://github.com/quarkusio/quarkus/pull/30", firstGitPullRequest.url());
        assertTrue(firstGitPullRequest.closed());
        assertEquals("2018-09-21T10:07:35", firstGitPullRequest.createdAt().toString());
        assertEquals("2018-09-21T11:42:55", firstGitPullRequest.closedAt().toString());

    }

    @Test
    void testGetIssues() throws Exception {
        ResponseJson responseJson = new ResponseJson();
        URL queryUrl = ResponseJsonTest.class.getResource("GraphQLResponse.json");
        assert queryUrl != null;
        File response = new File(queryUrl.toURI());

        Map<String, List<GitIssue>> repoIssues = responseJson.getIssues(response);

        List<GitIssue> gitIssues = repoIssues.get("MDEwOlJlcG9zaXRvcnkxMzk5MTQ5MzI=");
        GitIssue firstGitIssue = gitIssues.getFirst();
        assertEquals("MDU6SXNzdWUzNTc3ODA3Njc=", firstGitIssue.id());
        assertEquals("Allow application bytecode transformation over plain byte arrays", firstGitIssue.title());
        assertThat(firstGitIssue.body(), containsString("We need Hibernate ORM to apply its own bytecode transformation logic on the user's JPA model."));
        assertEquals("https://github.com/quarkusio/quarkus/issues/12", firstGitIssue.url());
        assertTrue(firstGitIssue.closed());
        assertEquals("2018-09-06T18:50:35", firstGitIssue.createdAt().toString());
        assertEquals("2018-09-06T22:49:04", firstGitIssue.closedAt().toString());

    }


}
