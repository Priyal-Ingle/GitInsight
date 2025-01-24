package com.esteco.gitinsight.service;

import com.esteco.gitinsight.model.entity.Issue;
import com.esteco.gitinsight.model.repository.IssueRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class IssuesFilterService {

    private final IssueRepository issueRepository;

    public IssuesFilterService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public List<Issue> getIssuePagination() {
        Pageable pageable = PageRequest.of(0, 10);
        return issueRepository.findAll(pageable).toList();
    }

    public List<Issue> getFilteredIssueByAuthor(String authorId) {
        return issueRepository.findByAuthor_Id(authorId);
    }

    public List<Issue> getFilteredIssuesByLabelId(String id, int pageNumber ){   //starts from 1
        Pageable pageable = PageRequest.of(pageNumber-1, 20);
        return issueRepository.findIssueByLabelId(id,pageable);
    }

    public List<Issue> getFilteredIssuesBYMultipleParams(Specification<Issue> spec) {
        List<Issue> getFilteredIssues = issueRepository.findAll(spec);

        return null;
    }
}
