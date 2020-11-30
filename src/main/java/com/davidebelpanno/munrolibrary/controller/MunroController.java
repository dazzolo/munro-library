package com.davidebelpanno.munrolibrary.controller;

import com.davidebelpanno.munrolibrary.exceptions.NoMunrosFoundException;
import com.davidebelpanno.munrolibrary.model.Munro;
import com.davidebelpanno.munrolibrary.model.MunroDaoImpl;
import com.davidebelpanno.munrolibrary.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;
import javax.validation.constraints.Min;

@RestController
public class MunroController {

    Logger logger = LoggerFactory.getLogger(MunroController.class);

    @Autowired
    private Validator validator;

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
            @RequestParam(defaultValue = "1000")
            @Min(value = 1, message = "maxResults can't be less than 1") Optional<Integer> maxResults) {

        validate(category, sortingCriteria.get(), sortingOrder.get(), maxHeight.get(), minHeight.get());

        return getMunros(category, maxHeight.get(), minHeight.get(), sortingCriteria.get().trim().toUpperCase(),
                sortingOrder.get().trim().toUpperCase(), maxResults.get());
    }

    Collection<Munro> getMunros(Optional<String> category, double maxHeight, double minHeight, String sortingCriteria,
            String sortingOrder, int maxResults) {
        Collection<Munro> munros;
        if (category.isPresent()) {
            munros = repository
                    .findByCategoryAndHeight(category.get().trim().toUpperCase(), maxHeight, minHeight, sortingCriteria,
                            sortingOrder, maxResults);
        } else {
            munros = repository.findByHeight(maxHeight, minHeight, sortingCriteria, sortingOrder, maxResults);
        }
        if (munros.size() == 0) {
            logger.debug("No results found, returning 204");
            throw new NoMunrosFoundException();
        }
        logger.debug("Found results, sending 200 response");
        return munros;
    }

    private void validate(Optional<String> category, String sortingCriteria, String sortingOrder, double maxHeight,
            double minHeight) {
        category.ifPresent(validator::isValidCategory);
        validator.isValidSortingCriteria(sortingCriteria.trim().toUpperCase());
        validator.isValidSortingOrder(sortingOrder.trim().toUpperCase());
        validator.isValidMaxAndMinHeight(maxHeight, minHeight);
    }
}

