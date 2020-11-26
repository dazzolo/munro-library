package com.davidebelpanno.munrolibrary.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MunroRepository {

    @Autowired
    private Munros munros;

    public Collection<Munro> find(final Map<String, String> params) {
        return filter(params);
    }

    public Collection<Munro> find(Optional<String> category, Optional<Double> maxHeight,
            Optional<Double> minHeight, Optional<String> sortingCriteria,
            Optional<String> sortingOrder, Optional<Integer> maxResults) {
        return munros.getFilteredMunros(category, maxHeight, minHeight, sortingCriteria, sortingOrder, maxResults);
    }

    public Collection<Munro> findAll() {
        return munros.getMunros();
    }

    private Collection<Munro> filter(Map<String, String> filters) {
        return munros.getMunros();
    }

}
