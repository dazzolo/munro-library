package com.davidebelpanno.munrolibrary.controller;

import static com.davidebelpanno.munrolibrary.utils.Constants.MUNRO_CATEGORY;
import static com.davidebelpanno.munrolibrary.utils.Constants.TOP_CATEGORY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import com.davidebelpanno.munrolibrary.exceptions.NoMunrosFoundException;
import com.davidebelpanno.munrolibrary.model.Munro;
import com.davidebelpanno.munrolibrary.model.MunroDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MunroControllerTest {

    MunroController testee;
    private final Munro munroTop = new Munro(TOP_CATEGORY, "test-munro-top", 150, "test-grid-ref");
    private final Munro munro = new Munro(MUNRO_CATEGORY, "test-munro", 100, "test-grid-ref");

    @Mock
    private MunroDaoImpl mockRepository;

    @BeforeEach
    void setup() {
        testee = new MunroController(mockRepository);
    }

    List<Munro> findByHeightData() {
        List<Munro> munros = new ArrayList<>();
        munros.add(munro);
        return munros;
    }

    List<Munro> findByCategoryAndHeightData() {
        List<Munro> munros = new ArrayList<>();
        munros.add(munroTop);
        return munros;
    }

    @Test
    void getMunrosShouldThrowExceptionIfNoResults() {
        when(mockRepository.findByHeight(anyDouble(), anyDouble(), anyString(), anyString(), anyInt()))
                .thenReturn(new ArrayList<>());
        assertThrows(NoMunrosFoundException.class,
                () -> testee.getMunros(Optional.empty(), anyDouble(), anyDouble(), anyString(), anyString(), anyInt()));
    }

    @Test
    void shouldReturnMunrosByCategoryWhenCategoryNonEmpty() {
        when(mockRepository.findByCategoryAndHeight(anyString(), anyDouble(), anyDouble(), anyString(), anyString(), anyInt()))
                .thenReturn(findByCategoryAndHeightData());
        Collection<Munro> munros =
                testee.getMunros(Optional.of(anyString()), anyDouble(), anyDouble(), anyString(), anyString(), anyInt());
        assertFalse(munros.isEmpty());
        assertEquals(munroTop, munros.iterator().next());
    }

    @Test
    void shouldReturnMunrosByHeightWhenCategoryEmpty() {
        when(mockRepository.findByHeight(anyDouble(), anyDouble(), anyString(), anyString(), anyInt()))
                .thenReturn(findByHeightData());
        Collection<Munro> munros =
                testee.getMunros(Optional.empty(), anyDouble(), anyDouble(), anyString(), anyString(), anyInt());
        assertFalse(munros.isEmpty());
        assertEquals(munro, munros.iterator().next());
    }
}