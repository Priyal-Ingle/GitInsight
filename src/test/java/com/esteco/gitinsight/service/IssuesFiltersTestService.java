package com.esteco.gitinsight.service;

import com.esteco.gitinsight.model.entity.Issue;
import com.esteco.gitinsight.model.repository.IssueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class IssuesFiltersTestService {

    @Autowired
    private IssueRepository issueRepository;


    private IssuesFilterService issuesFilterService;


    @BeforeEach
    public void setup() {
        this.issuesFilterService = new IssuesFilterService(issueRepository);
    }

    @Test
    void getIssuePaginationTest() {
        List<Issue> getIssues= issuesFilterService.getIssuePagination();

        assertEquals(10, getIssues.size());
    }

    @Test
    void getFilteredIssueByAuthorTest(){
        String id= "605d7ed2-82bc-44ce-a43e-bdfa89e4b9e3";

        List<Issue> getIssues = issuesFilterService.getFilteredIssueByAuthor(id);

        assertEquals(20, getIssues.size());

    }

    @Test
    void getFilteredIssuesByLabelIdTest(){
        String id = "LA_kwDOD3caBc8AAAABByA18g";
        int pageNo=1;
        List<Issue> getIssuesByLabelId = issuesFilterService.getFilteredIssuesByLabelId(id, pageNo);

        assertEquals(20, getIssuesByLabelId.size());
    }

    @Test
    void getFilteredIssuesBYMultipleParamsTest(){
        Specification<Issue> spec=null;
//        spec = Specification.where(UserSp)
        List<Issue> getIssuesByMultipleParams = issuesFilterService.getFilteredIssuesBYMultipleParams(spec);

        assertNull(getIssuesByMultipleParams);
    }
}
