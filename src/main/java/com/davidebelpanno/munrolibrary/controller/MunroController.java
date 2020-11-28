package com.davidebelpanno.munrolibrary.controller;

import com.davidebelpanno.munrolibrary.exceptions.NoMunrosFoundException;
import com.davidebelpanno.munrolibrary.model.Munro;
import com.davidebelpanno.munrolibrary.model.MunroDaoImpl;
import com.davidebelpanno.munrolibrary.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;
import javax.validation.constraints.Min;

@RestController
public class MunroController {

    private final MunroDaoImpl repository;

    MunroController(MunroDaoImpl repository) {
        this.repository = repository;
    }

    @GetMapping("/munros")
    Collection<Munro> get(@RequestParam Optional<String> category,
            @RequestParam(defaultValue = "1.7976931348623157E308") Optional<Double> maxHeight,
            @RequestParam(defaultValue = "4.9E-324") Optional<Double> minHeight,
            @RequestParam(defaultValue = "name") Optional<String> sortingCriteria,
            @RequestParam(defaultValue = "asc") Optional<String> sortingOrder,
            @RequestParam(defaultValue = "1000") @Min(value = 1, message = "maxResults can't be less than 1") Optional<Integer> maxResults) {

        validate(category, sortingCriteria.get(), sortingOrder.get(), maxHeight.get(), minHeight.get());

        Collection<Munro> munros;
        if (category.isPresent()) {
            munros = repository.findByCategoryAndHeight(category.get().trim().toUpperCase(), maxHeight.get(), minHeight.get(),
                    sortingCriteria.get().trim().toUpperCase(),
                    sortingOrder.get().trim().toUpperCase(), maxResults.get());
        } else {
            munros = repository.findByHeight(maxHeight.get(), minHeight.get(), sortingCriteria.get().trim().toUpperCase(),
                    sortingOrder.get().trim().toUpperCase(), maxResults.get());
        }

        if (munros.size() == 0) {
            throw new NoMunrosFoundException();
        }
        return munros;
    }

    private void validate(Optional<String> category, String sortingCriteria, String sortingOrder, Double maxHeight, Double minHeight) {
        category.ifPresent(Validator::isValidCategory);
        Validator.isValidSortingCriteria(sortingCriteria.trim().toUpperCase());
        Validator.isValidSortingOrder(sortingOrder.trim().toUpperCase());
        Validator.isValidMaxAndMinHeight(maxHeight, minHeight);
    }
}

