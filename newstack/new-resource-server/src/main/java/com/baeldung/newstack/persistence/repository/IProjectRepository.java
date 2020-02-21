package com.baeldung.newstack.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.baeldung.newstack.persistence.model.Project;

public interface IProjectRepository extends PagingAndSortingRepository<Project, Long> {
}
