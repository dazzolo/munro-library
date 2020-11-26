package com.davidebelpanno.munrolibrary.controller;

import com.davidebelpanno.munrolibrary.model.Munro;
import com.davidebelpanno.munrolibrary.model.MunroRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;
import javax.validation.constraints.Min;

@RestController
public class MunroController {

    private final MunroRepository repository;

    MunroController(MunroRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/munros")
    Collection<Munro> get(@RequestParam Optional<String> category, @RequestParam Optional<Double> maxHeight,
            @RequestParam Optional<Double> minHeight, @RequestParam Optional<String> sortingCriteria,
            @RequestParam Optional<String> sortingOrder, @RequestParam @Min(1) Optional<Integer> maxResults) {
        Collection<Munro> munros = repository.find(category, maxHeight, minHeight, sortingCriteria, sortingOrder, maxResults);
        if (munros.size() == 0) {
            return null; // return 204
        }
        return munros;
    }
}

