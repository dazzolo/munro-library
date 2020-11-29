package com.davidebelpanno.munrolibrary.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

class ValidatorTest {

    Validator testee;

    private List<String> validCategories = new ArrayList<>();
    private List<String> validSortingOrders = new ArrayList<>();
    private List<String> validSortingCriteria = new ArrayList<>();
    private String invalidCategoryErrorMessage = "Invalid category";

    @BeforeEach
    void setup() {
        validCategories.add("TOP");
        validCategories.add("MUN");
        validSortingOrders.add("ASC");
        validSortingOrders.add("DESC");
        validSortingCriteria.add("NAME");
        validSortingCriteria.add("HEIGHT");
        testee = new Validator();
    }

    @Test
    void shouldNotThrowExceptionForValidMunroCategory() {
        ReflectionTestUtils.setField(testee, "validCategories", validCategories);
        testee.isValidCategory("MUN");
    }

    @Test
    void shouldNotThrowExceptionForValidMunroTopCategory() {
        ReflectionTestUtils.setField(testee, "validCategories", validCategories);
        testee.isValidCategory("TOP");
    }

    @Test
    void shouldThrowExceptionForInvalidMunroTopCategory() {
        ReflectionTestUtils.setField(testee, "validCategories", validCategories);
        ReflectionTestUtils.setField(testee, "invalidCategoryErrorMessage", invalidCategoryErrorMessage);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> testee.isValidCategory("InvalidCategory"));
        assertEquals(invalidCategoryErrorMessage, ex.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullCategory() {
        ReflectionTestUtils.setField(testee, "validCategories", validCategories);
        ReflectionTestUtils.setField(testee, "invalidCategoryErrorMessage", invalidCategoryErrorMessage);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> testee.isValidCategory(null));
        assertEquals(invalidCategoryErrorMessage, ex.getMessage());
    }

    @Test
    void shouldNotThrowExceptionForValidNameSortingCriteria() {
        ReflectionTestUtils.setField(testee, "validSortingCriteria", validSortingCriteria);
        testee.isValidSortingCriteria("name");
    }

    @Test
    void shouldNotThrowExceptionForValidHeightSortingCriteria() {
        ReflectionTestUtils.setField(testee, "validSortingCriteria", validSortingCriteria);
        testee.isValidSortingCriteria("height");
    }

    @Test
    void shouldThrowExceptionForInvalidSortingCriteria() {
        ReflectionTestUtils.setField(testee, "validSortingCriteria", validSortingCriteria);
        final String invalidSortingCriteriaErrorMessage = "Invalid sorting criteria";
        ReflectionTestUtils.setField(testee, "invalidSortingCriteriaErrorMessage", invalidSortingCriteriaErrorMessage);
        Exception ex =
                assertThrows(IllegalArgumentException.class, () -> testee.isValidSortingCriteria("InvalidSortingCriteria"));
        assertEquals(invalidSortingCriteriaErrorMessage, ex.getMessage());
    }

    @Test
    void shouldNotThrowExceptionForValidAscSortingOrder() {
        ReflectionTestUtils.setField(testee, "validSortingOrders", validSortingOrders);
        testee.isValidSortingOrder("asc");
    }

    @Test
    void shouldNotThrowExceptionForValidDescSortingOrder() {
        ReflectionTestUtils.setField(testee, "validSortingOrders", validSortingOrders);
        testee.isValidSortingOrder("desc");
    }

    @Test
    void shouldThrowExceptionForInvalidSortingOrder() {
        ReflectionTestUtils.setField(testee, "validSortingOrders", validSortingOrders);
        final String invalidSortingOrderErrorMessage = "Invalid sorting order";
        ReflectionTestUtils.setField(testee, "invalidSortingOrderErrorMessage", invalidSortingOrderErrorMessage);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> testee.isValidSortingOrder("InvalidSortingOrder"));
        assertEquals(invalidSortingOrderErrorMessage, ex.getMessage());
    }

    @Test
    void shouldNotThrowExceptionIfMaxHeightGreaterThanMinHeight() {
        testee.isValidMaxAndMinHeight(1000.0, 900.0);
    }

    @Test
    void shouldNotThrowExceptionIfMaxHeightLowerThanMinHeight() {
        final String invalidMaxMinHeightErrorMessage = "maxHeight must be higher than minHeight";
        ReflectionTestUtils.setField(testee, "invalidMaxMinHeightErrorMessage", invalidMaxMinHeightErrorMessage);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> testee.isValidMaxAndMinHeight(900.0, 1000.0));
        assertEquals(invalidMaxMinHeightErrorMessage, ex.getMessage());
    }

}