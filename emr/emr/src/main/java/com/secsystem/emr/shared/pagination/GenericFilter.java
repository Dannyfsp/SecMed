package com.secsystem.emr.shared.pagination;

import java.util.HashMap;
import java.util.Map;

public class GenericFilter {

    private Map<String, Object> filters = new HashMap<>();

    public void addFilter(String field, Object value) {
        filters.put(field, value);
    }

    public Map<String, Object> getFilters() {
        return filters;
    }
}