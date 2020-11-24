package com.davidebelpanno.munrolibrary;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class MunroLibraryIT {

    @Test
    void shouldFilterMunros() throws IOException, InterruptedException {
    }

    @Test
    void shouldFilterMunroTops() {

    }

    @Test
    void shouldIgnoreFilteringIfNoCategory() {

    }

    @Test
    void shouldReturn422IfInvalidFilter() {
    }

    @Test
    void shouldSortByAscendingName() {

    }

    @Test
    void shouldSortByDescendingName() {

    }

    @Test
    void shouldSortByAscendingHeight() {

    }

    @Test
    void shouldSortByDescendingHeight() {

    }

    @Test
    void shouldIgnoreSortingIfNoCriteriaAndOrderSpecified() {

    }

    @Test
    void shouldIgnoreSortingIfNoCriteriaButOrderSpecified() {

    }

    @Test
    void shouldDefaultToAscendingIfNoSortingOrderSpecified() {

    }

    @Test
    void shouldReturn422IfInvalidSortingCriteria() {}

    @Test
    void shouldReturn422IfInvalidSortingOrder() {}

    @Test
    void shouldReturnLessThanSpecifiedMaxResults() {
    }

    @Test
    void shouldReturn200IfNoSpecifiedMaxResults() {
    }

    @Test
    void shouldFilterByMaxiHeight() {
    }

    @Test
    void shouldFilterByMinHeight() {
    }

    @Test
    void shouldFilterByMinAndMaxHeight() {
    }

    @Test
    void shouldReturn200WithNoHeightFilters() {
    }

    @Test
    void shouldReturn422IfInvalidMaxHeight() {
    }

    @Test
    void shouldReturn422IfInvalidMinHeight() {
    }

    @Test
    void shouldReturn422WhenMinHeightHigherThanMaxHeight() {

    }

    @Test
    void shouldReturn404WhenPathInvalid() {

    }


}
