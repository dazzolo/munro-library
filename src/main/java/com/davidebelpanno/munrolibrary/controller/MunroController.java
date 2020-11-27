package com.davidebelpanno.munrolibrary.controller;

import com.davidebelpanno.munrolibrary.exceptions.NoMunrosFoundException;
import com.davidebelpanno.munrolibrary.model.Munro;
import com.davidebelpanno.munrolibrary.model.MunroDaoImpl;
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
            @RequestParam(defaultValue = "2000") Optional<Double> maxHeight,
            @RequestParam(defaultValue = "0") Optional<Double> minHeight,
            @RequestParam(defaultValue = "name") Optional<String> sortingCriteria,
            @RequestParam(defaultValue = "asc") Optional<String> sortingOrder,
            @RequestParam(defaultValue = "1000") @Min(1) Optional<Integer> maxResults) {

        //validate category
        //validate sorting criteria
        //validate sorting oder

        Collection<Munro> munros;
        if (category.isPresent()) {
            munros = repository.findByCategoryAndHeight(category.get(), maxHeight.get(), minHeight.get(), sortingCriteria.get(),
                    sortingOrder.get(), maxResults.get());
        } else {
            munros = repository.findByHeight(maxHeight.get(), minHeight.get(), sortingCriteria.get(),
                    sortingOrder.get(), maxResults.get());
        }

        if (munros.size() == 0) {
            throw new NoMunrosFoundException();
        }
        return munros;
    }
}

