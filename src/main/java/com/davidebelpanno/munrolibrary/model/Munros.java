package com.davidebelpanno.munrolibrary.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

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

}
