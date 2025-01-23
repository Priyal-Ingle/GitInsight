package com.esteco.gitinsight.service;

import com.esteco.gitinsight.model.repository.*;
import com.esteco.gitinsight.utils.GraphqlUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

@Service
public class GithubAPIService {

    private final StoreJSONInDatabase storeJSONInDatabase;

    @Autowired
    public GithubAPIService(GitRepository gitRepository, LanguageRepository languageRepository, CommentRepository commentRepository,
                            IssueRepository issueRepository, PullRequestRepository pullRequestRepository,
                            AuthorRepository authorRepository, LabelRepository labelRepository) {

         this.storeJSONInDatabase = new StoreJSONInDatabase(gitRepository, languageRepository, commentRepository, issueRepository, pullRequestRepository, authorRepository, labelRepository);

    }

    public void getRepoData(String owner, String repoName) throws IOException, URISyntaxException {

        String endCursor ="";
        URL queryUrl = GithubAPIService.class.getResource("../query.resources");
        File query = new File(queryUrl.toURI());
        String queryString = Files.readString(query.toPath());

        queryString = queryString.replace("@owner-name", owner);
        queryString = queryString.replace("@repo-name", repoName);
        String queryTemplate = queryString;
        queryString = queryString.replace("@end-cursor", endCursor);

        int i=1;

        boolean hasNextPage = true;
        do{

//            write in file
            GraphqlUtils graphqlUtils = new GraphqlUtils();
            graphqlUtils.executeGraphQLQuery(queryString,"./src/main/resources/com/esteco/gitinsight/graphqlResponse.json");



//            reading file to extract hasNextPage and nextCursor
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("/home/pingle/IdeaProjects/GitInsight/src/main/resources/com/esteco/gitinsight/graphqlResponse.json");


//            writing in database from json file
            storeJSONInDatabase.persistFileInDB(file);
            System.out.println(i+" JSON data saved to DB");
            i++;

//            updating query
            JsonNode jsonNode = mapper.readTree(file);
            JsonNode issuePageInfo = jsonNode.findPath("issues").findPath("pageInfo");
            endCursor = issuePageInfo.findPath("endCursor").asText();
            hasNextPage = issuePageInfo.findPath("hasNextPage").asBoolean();
            queryString = queryTemplate.replace("@end-cursor", endCursor);

        }while(hasNextPage);

    }
}
