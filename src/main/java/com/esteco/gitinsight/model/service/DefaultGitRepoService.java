package com.esteco.gitinsight.model.service;

import com.esteco.gitinsight.model.repository.GitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultGitRepoService implements GitRepoService {

    @Autowired
    private GitRepository gitRepository;

}
