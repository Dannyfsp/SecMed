package com.secsystem.emr.shared.pagination;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericSpecification<T> {

    public Specification<T> getSpecification(GenericFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : filter.getFilters().entrySet()) {
                String field = entry.getKey();
                Object value = entry.getValue();

                if (value instanceof String) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(field)),
                            "%" + value.toString().toLowerCase() + "%"
                    ));
                } else {
                    predicates.add(criteriaBuilder.equal(root.get(field), value));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
