package com.baeldung.newstack.service;

import java.util.Optional;

import com.baeldung.newstack.persistence.model.Project;

public interface IProjectService {
    Optional<Project> findById(Long id);

    Project save(Project project);
    
    Iterable<Project> findAll();

}
