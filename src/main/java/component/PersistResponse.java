package component;

import com.esteco.gitinsight.dto.*;
import com.esteco.gitinsight.dto.Comment;
import com.esteco.gitinsight.dto.GitUser;
import com.esteco.gitinsight.model.entity.*;
import com.esteco.gitinsight.model.repository.*;
import com.esteco.gitinsight.service.ResponseJson;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PersistResponse {

    private final LanguageRepository languageRepository;
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final PullRequestRepository pullRequestRepository;
    private final GitRepository gitRepository;
    private final AuthorRepository authorRepository;
    private final LabelRepository labelRepository;

    @Autowired
    public PersistResponse(GitRepository gitRepository, LanguageRepository languageRepository, CommentRepository commentRepository, IssueRepository issueRepository, PullRequestRepository pullRequestRepository, AuthorRepository authorRepository, LabelRepository labelRepository) {
        this.gitRepository = gitRepository;
        this.languageRepository = languageRepository;
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.pullRequestRepository = pullRequestRepository;
        this.authorRepository = authorRepository;
        this.labelRepository = labelRepository;
    }

    public void storeLanguages(File file) throws IOException {
        ResponseJson responseJson = new ResponseJson();
        GitRepositoryData repositoryData = responseJson.getRepositoryData(file);
        Optional<GitRepo> getGitRepo = gitRepository.findById(repositoryData.id());

        if(getGitRepo.isPresent()) {
            return;
        }
        GitRepo gitRepo = new GitRepo(repositoryData.id());

        System.out.println("Executed first time");
        gitRepo.setUrl(repositoryData.url());
        gitRepo.setCreatedAt(repositoryData.createdAt());
        gitRepo.setUpdatedAt(repositoryData.updatedAt());
        gitRepo.setPrivateRepository(false);

        Map<String, List<CodeLanguage>> codeLanguages = responseJson.getLanguages(file);
        List<CodeLanguage> languageList = codeLanguages.get(repositoryData.id());
        List<Language> storedLanguages = new ArrayList<>();
        for (CodeLanguage codeLanguage : languageList) {
            Optional<Language> fetchedLanguage = languageRepository.findById(codeLanguage.id());
            if (fetchedLanguage.isEmpty()) {
                Language language = storeLanguage(codeLanguage);
                storedLanguages.add(language);
            } else {
                storedLanguages.add(fetchedLanguage.get());
            }
        }
        gitRepo.setLanguages(storedLanguages);
        gitRepository.save(gitRepo);
    }

    private Language storeLanguage(CodeLanguage language) {
        Language entityLanguage = new Language(language.id());
        entityLanguage.setName(language.name());
        entityLanguage.setColor(language.color());
        languageRepository.save(entityLanguage);
        return entityLanguage;
    }

    @Transactional
    public void storeComments(File file) throws IOException {
        ResponseJson responseJson = new ResponseJson();
        Map<String, List<Comment>> commentData = responseJson.getCommentData(file);

        commentData.forEach((issueId, value) -> {
            List<com.esteco.gitinsight.model.entity.Comment> comments = new ArrayList<>();
            Optional<Issue> optionalIssue = issueRepository.findById(issueId);

            if (optionalIssue.isPresent()) {
                Issue issue = optionalIssue.get();
                value.forEach(comment -> {
                    Optional<com.esteco.gitinsight.model.entity.Comment> byId = commentRepository.findById(comment.id());
                    if (byId.isPresent()) {
                        com.esteco.gitinsight.model.entity.Comment entityComment = byId.get();
                        entityComment.setBody(comment.body());
                        entityComment.setCreatedAt(comment.createdAt());
                        commentRepository.save(entityComment);
                        comments.add(entityComment);
                    } else {
                        com.esteco.gitinsight.model.entity.Comment entityComment = new com.esteco.gitinsight.model.entity.Comment(comment.id());
                        entityComment.setBody(comment.body());
                        entityComment.setCreatedAt(comment.createdAt());
                        commentRepository.save(entityComment);
                        comments.add(entityComment);
                    }
                });
                issue.setComments(comments);
                issueRepository.save(issue);
            }
        });
    }


    public void storeIssueAuthor(File file) throws IOException {
        Map<String, GitUser> issueAuthor = new ResponseJson().getIssueAuthor(file);
        issueAuthor.forEach(this::storeAuthorAndIssue);
    }

    private void storeAuthorAndIssue(String issueId, GitUser value) {
        Issue issue = new Issue(issueId);
        Optional<com.esteco.gitinsight.model.entity.GitUser> byUsername = authorRepository.findByUsername(value.login());
        if (byUsername.isPresent()) {
            issue.setAuthor(byUsername.get());
        } else {
            com.esteco.gitinsight.model.entity.GitUser creator = new com.esteco.gitinsight.model.entity.GitUser();
            creator.setUsername(value.login());
            creator.setUrl(value.url());
            issue.setAuthor(creator);
        }
        issueRepository.save(issue);
    }

    public void storeCommentAuthor(File file) throws IOException {
        Map<String, GitUser> issueCommentAuthor = new ResponseJson().getIssueCommentAuthor(file);
        issueCommentAuthor.forEach((commentId, value) -> {
            Optional<com.esteco.gitinsight.model.entity.GitUser> byUsername = authorRepository.findByUsername(value.login());
            com.esteco.gitinsight.model.entity.Comment comment = commentRepository.findById(commentId).isPresent() ? commentRepository.findById(commentId).get() : new com.esteco.gitinsight.model.entity.Comment();
            comment.setId(commentId);
            if (byUsername.isEmpty()) {
                com.esteco.gitinsight.model.entity.GitUser creator = new com.esteco.gitinsight.model.entity.GitUser();
                creator.setUsername(value.login());
                creator.setUrl(value.url());
                comment.setCommentAuthor(creator);
            } else {
                comment.setCommentAuthor(byUsername.get());
            }
            commentRepository.save(comment);

        });

    }

    public void storePullRequestCommits(File file) throws IOException {
        Map<String, List<PRCommit>> pullRequestCommits = new ResponseJson().getIssueCommits(file);
        pullRequestCommits.forEach((prId, value) -> {
            PullRequest pullRequest = new PullRequest(prId);
            List<Commit> commits = new ArrayList<>();
            value.forEach(commit -> {
                Commit entityCommit = new Commit(commit.id());
//                entityCommit.setFileChanges(getFileChanges(commit.url()));
                entityCommit.setUrl(commit.url());
                commits.add(entityCommit);
            });
            pullRequest.setCommits(commits);
            pullRequestRepository.save(pullRequest);

        });
    }

    private List<FileChanges> getFileChanges(String url) {
        if (url == null || !url.contains("github.com")) {
            throw new IllegalArgumentException("Invalid GitHub URL.");
        }
        List<FileChanges> fileChanges = new ArrayList<>();
        String apiUrl = url.replace("https://github.com/", "https://api.github.com/repos/")
                .replace("/commit/", "/commits/");
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer "+"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");
            connection.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String jsonResponse = response.toString();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonResponse);
            JsonNode jsonFiles = jsonNode.findValue("files");
            jsonFiles.forEach(jsonFile -> {
                FileChanges fileChange = new FileChanges();
                fileChange.setFilePath(jsonFile.get("filename").asText());
                fileChange.setStatus(jsonFile.get("status").asText());
                fileChanges.add(fileChange);
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("JSON File Response from RESTAPI");
        return fileChanges;
    }
    @Transactional
    public void storePullRequests(File file) throws IOException {
        Map<String, List<GitPullRequest>> closedPullRequests = new ResponseJson().getClosedPullRequests(file);
        closedPullRequests.forEach((issueId, value) -> {
            issueRepository.findById(issueId).ifPresent(issue -> {
                List<PullRequest> pullRequests = new ArrayList<>();
                value.forEach(pullRequest -> {
                    Optional<PullRequest> byId = pullRequestRepository.findById(pullRequest.id());
                    PullRequest PR = byId.get();
                    PR.setUrl(pullRequest.url());
                    PR.setBody(pullRequest.body());
                    PR.setCreatedAt(pullRequest.createdAt());
                    PR.setClosedAt(pullRequest.closedAt());
                    PR.setTitle(pullRequest.title());
                    pullRequests.add(PR);

                });
                issue.setPullRequests(pullRequests);
                issueRepository.save(issue);
            });

        });
    }

    public void storeIssueAssignees(File file) throws IOException {
        Map<String, List<GitUser>> issueAssignees = new ResponseJson().getIssueAssignees(file);
        issueAssignees.forEach((issueId, value) -> {
            issueRepository.findById(issueId).ifPresent(issue -> {
                List<com.esteco.gitinsight.model.entity.GitUser> assignees = new ArrayList<>();
                value.forEach(assignee -> {
                    Optional<com.esteco.gitinsight.model.entity.GitUser> byUsername = authorRepository.findByUsername(assignee.login());
                    if (byUsername.isEmpty()) {
                        com.esteco.gitinsight.model.entity.GitUser assignee1 = new com.esteco.gitinsight.model.entity.GitUser();
                        assignee1.setUsername(assignee.login());
                        assignee1.setUrl(assignee.url());
                        assignees.add(assignee1);
                    } else {
                        assignees.add(byUsername.get());
                    }
                });
                issue.setAssignees(assignees);
                issueRepository.save(issue);
            });
        });
    }

    public void storeIssueLabels(File file) throws IOException {
        Map<String, List<IssueLabel>> labels = new ResponseJson().getLabels(file);
        labels.forEach((issueId, value) -> {
            issueRepository.findById(issueId).ifPresent(issue -> {
                List<Label> entityLabels = new ArrayList<>();
                value.forEach(entityLabel -> {
                    Optional<Label> byId = labelRepository.findById(entityLabel.id());
                    if (byId.isEmpty()) {
                        Label label = new Label(entityLabel.id());
                        label.setName(entityLabel.name());
                        label.setColor(entityLabel.color());
                        entityLabels.add(label);
                    } else {
                        entityLabels.add(byId.get());
                    }
                });
                issue.setLabels(entityLabels);
                issueRepository.save(issue);
            });
        });

    }

    @Transactional
    public void storeIssues(File file) throws IOException {
        Map<String, List<GitIssue>> issues = new ResponseJson().getIssues(file);
        issues.forEach((repoId, value) -> {
            GitRepo gitRepo = gitRepository.findById(repoId).isPresent() ? gitRepository.findById(repoId).get() : new GitRepo(repoId);
            List<Issue> entityIssues = new ArrayList<>();
            value.forEach(entityIssue -> {
                Issue issue = issueRepository.findById(entityIssue.id()).isPresent() ? issueRepository.findById(entityIssue.id()).get() : new Issue(entityIssue.id());
                issue.setUrl(entityIssue.url());
                issue.setCreatedAt(entityIssue.createdAt());
                issue.setBody(entityIssue.body());
                issue.setClosedAt(entityIssue.closedAt());
                issue.setTitle(entityIssue.title());
                if (!gitRepo.getIssues().contains(issue)) {
                    entityIssues.add(issue);
                }
            });
            gitRepo.setIssues(entityIssues);
            gitRepository.save(gitRepo);
        });
    }

//    public void storeIssues(File file) throws IOException {
//        Map<String, List<GitIssue>> issues = new ResponseJson().getIssues(file);
//
//        issues.forEach((repoId, value) -> {
//            // Fetch or create GitRepo
//            GitRepo gitRepo = gitRepository.findById(repoId).orElse(new GitRepo(repoId));
//            List<Issue> entityIssues = new ArrayList<>();
//
//            value.forEach(entityIssue -> {
//                // Fetch or create Issue
//                Issue issue = issueRepository.findById(entityIssue.id())
//                        .orElseGet(() -> {
//                            Issue newIssue = new Issue(entityIssue.id());
//                            newIssue.setUrl(entityIssue.url());
//                            newIssue.setCreatedAt(entityIssue.createdAt());
//                            newIssue.setBody(entityIssue.body());
//                            newIssue.setClosedAt(entityIssue.closedAt());
//                            newIssue.setTitle(entityIssue.title());
//                            return newIssue;
//                        });
//
//                // Check if the relationship already exists
//                if (!gitRepo.getIssues().contains(issue)) {
//                    entityIssues.add(issue);
//                }
//            });
//
//            // Add new issues to the GitRepo
//            gitRepo.getIssues().addAll(entityIssues);
//            gitRepository.save(gitRepo);
//        });
//    }


    public void storeGitRepo(File file) throws IOException {
        GitRepositoryData repositoryData = new ResponseJson().getRepositoryData(file);
        GitRepo gitRepo = gitRepository.findById(repositoryData.id()).isPresent() ? gitRepository.findById(repositoryData.id()).get() : new GitRepo(repositoryData.id());
        gitRepo.setUrl(repositoryData.url());
        String regex = "https://github\\.com/([^/]+)/([^/]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(repositoryData.url());

        if (matcher.matches()) {
            String owner = matcher.group(1);
            String repo = matcher.group(2);
            gitRepo.setRepoName(repo);
            gitRepo.setRepoOwner(owner);
            gitRepo.setCreatedAt(repositoryData.createdAt());
            gitRepo.setUpdatedAt(repositoryData.updatedAt());
            gitRepo.setPrivateRepository(repositoryData.isPrivate());
            gitRepository.save(gitRepo);
        }
    }
}
