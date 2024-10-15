package com.secsystem.emr.shared.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class GenericService<T> {

    @Autowired
    private JpaSpecificationExecutor<T> repository;

    public Page<T> getFilteredData(GenericFilter filter, Pageable pageable, Class<T> clazz) {
        GenericSpecification<T> specification = new GenericSpecification<>();
        return repository.findAll(specification.getSpecification(filter), pageable);
    }
}