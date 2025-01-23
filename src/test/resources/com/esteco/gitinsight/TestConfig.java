package com.esteco.gitinsight.response;

public class TestConfig {
    public static final String GITHUB_URL = "https://api.github.com/graphql";
    public static final String GITHUB_TOKEN = "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";
    public static final String GITHUB_TEST_QUERY= """
                query {
                    repository(owner: "carbon-name", name: "carbon-lang") {
                        id
                    }
                }
            """;
    public static final String EXPECTED_TEST_RESPONSE = "Repository[id=MDEwOlJlcG9zaXRvcnkyNTk0NjM2ODU=, url=null, updatedAt=null, createdAt=null, forkCount=0, isPrivate=false, isLocked=false, labels=null, codeLanguage=null, issues=null]";
    public static final String GITHUB_QUERY = """
            query {
                repository(owner: "carbon-name", name: "carbon-lang") {
                  id
                  url
                  updatedAt
                  createdAt
                  forkCount
                  isPrivate
                  isLocked
                  labels(first: 1) {
                    edges {
                      node {
                        name
                      }
                    }
                  }
                  codeLanguage(first: 1) {
                    edges {
                      node {
                        name
                      }
                    }
                  }
                  issues(first: 1, states: CLOSED) {
                    edges {
                      node {
                        id
                        title
                        comments(first: 1) {
                          edges {
                            node {
                              id
                              body
                            }
                          }
                        }
                        url
                        body
                        closed
                        closedAt
                        closedByPullRequestsReferences(first: 1) {
                          edges {\s
                            node {
                              id
                              title
                              body
                              url
                              changedFiles
                              commits(first: 1) {
                                edges {
                                  node {
                                    id
                                    url
                                  }
                                }
                              }
                            }
                          }
                        }
                        createdAt
                        labels(first: 1) {
                          edges {
                            node {
                              id
                              color
                              name
                            }
                          }
                        }
                        user {
                          login
                        }
                        assignees(first: 1) {
                          edges {
                            node {
                              login
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            """;
    public static final String EXPECTED_RESPONSE = "Repository[id=MDEwOlJlcG9zaXRvcnkyNTk0NjM2ODU=, url=https://github.com/carbon-language/carbon-lang, updatedAt=2025-01-08T10:28:10Z, createdAt=2020-04-27T21:45:16Z, forkCount=1480, isPrivate=false, isLocked=false, labels=Labels[edges=[LabelEdge[node=Label[name=proposal]]]], codeLanguage=Languages[edges=[LanguageEdge[node=Language[name=GDB]]]], issues=Issues[edges=[IssueEdge[node=Issue[id=MDU6SXNzdWU2MjA2OTgwOTc=, title=[proposal] Lexical conventions, url=https://github.com/carbon-language/carbon-lang/issues/16, body=, closed=true, closedAt=2020-07-30T20:24:05Z, createdAt=2020-05-19T05:52:12Z, authors=null, assignees=Assignees[edges=[]], closedByPullRequestsReferences=ClosedByPullRequests[edges=[]]]]]]]";

}
