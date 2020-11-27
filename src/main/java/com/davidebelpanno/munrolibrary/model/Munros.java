package com.davidebelpanno.munrolibrary.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Munros {

    private Collection<Munro> munros;

    public Munros() {
        munros = new ArrayList<>();
    }

    public void addMunro(Munro munro) {
        munros.add(munro);
    }

    public Collection<Munro> getMunros() {
        return munros;
    }

    public Collection<Munro> getFilteredMunros(Optional<String> category, Optional<Double> maxHeight,
            Optional<Double> minHeight, Optional<String> sortingCriteria,
            Optional<String> sortingOrder, Optional<Integer> maxResults) {
        List<Munro> filteredMunros = filterByCategory(munros, category);
        filteredMunros = filterByHeight(filteredMunros, minHeight, maxHeight);
        sort(filteredMunros, sortingCriteria, sortingOrder);
        return limitResults(filteredMunros, maxResults);
    }

    private List<Munro> filterByCategory(Collection<Munro> munrosList, Optional<String> category) {
        List<Munro> filteredMunros = (List<Munro>)munrosList;
        if (category.isPresent() && ("MUN".equalsIgnoreCase(category.get()) || "TOP".equalsIgnoreCase(category.get()))) {
            filteredMunros = munrosList.stream().filter(munro -> munro.getCategory().equalsIgnoreCase(category.get()))
                    .collect(Collectors.toList());
        }
        return filteredMunros.stream().filter(munro -> !munro.getCategory().isEmpty()).collect(Collectors.toList());
    }

    private List<Munro> filterByHeight(List<Munro> munrosList, Optional<Double> minHeigth,
            Optional<Double> maxHeight) {
        if (minHeigth.isPresent() && maxHeight.isPresent() && maxHeight.get() < minHeigth.get()) {
            throw new IllegalArgumentException("Minimum height can't be higher than maximum height");
        }
        if (minHeigth.isPresent()) {
            munrosList = munrosList.stream().filter(munro -> munro.getHeight() >= minHeigth.get()).collect(Collectors.toList());
        }
        if (maxHeight.isPresent()) {
            munrosList = munrosList.stream().filter(munro -> munro.getHeight() <= maxHeight.get()).collect(Collectors.toList());
        }
        return munrosList;
    }

    private void sort(List<Munro> munrosList, Optional<String> sortingCriteria, Optional<String> sortingOrder) {
        if (sortingCriteria.isPresent() && "name".equalsIgnoreCase(sortingCriteria.get())) {
            if (sortingOrder.isPresent() && "desc".equalsIgnoreCase(sortingOrder.get())) {
                munrosList.sort(Comparator.comparing(Munro::getName).reversed());
            } else {
                munrosList.sort(Comparator.comparing(Munro::getName));
            }
        }
        if (sortingCriteria.isPresent() && "height".equalsIgnoreCase(sortingCriteria.get())) {
            if (sortingOrder.isPresent() && "desc".equalsIgnoreCase(sortingOrder.get())) {
                munrosList.sort(Comparator.comparing(Munro::getHeight).reversed());
            } else {
                munrosList.sort(Comparator.comparing(Munro::getHeight));
            }
        }
    }

    private Collection<Munro> limitResults(Collection<Munro> munrosList, Optional<Integer> maxResults) {
        if (maxResults.isPresent()) {
            munrosList = munrosList.stream().limit(maxResults.get()).collect(Collectors.toList());
        }
        return munrosList;
    }
}
