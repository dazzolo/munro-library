package com.davidebelpanno.munrolibrary.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Validator {

    @Value("${app.valid.categories}")
    private List<String> validCategories;

    @Value("${app.valid.sorting.orders}")
    private List<String> validSortingOrders;

    @Value("${app.valid.sorting.criteria}")
    private List<String> validSortingCriteria;

    @Value("${app.invalid.category.message}")
    private String invalidCategoryErrorMessage;

    @Value("${app.invalid.sorting.criteria.error.message}")
    private String invalidSortingCriteriaErrorMessage;

    @Value("${app.invalid.sorting.order.error.message}")
    private String invalidSortingOrderErrorMessage;

    @Value("${app.invalid.max.min.height.error.message}")
    private String invalidMaxMinHeightErrorMessage;

    public void isValidCategory(String category) {
        if (category != null && !category.trim().isEmpty() && !validCategories.contains(category.trim().toUpperCase())) {
            throw new IllegalArgumentException(invalidCategoryErrorMessage);
        }
    }

    public void isValidSortingCriteria(String sortingCriteria) {
        if (!validSortingCriteria.contains(sortingCriteria.trim().toUpperCase())) {
            throw new IllegalArgumentException(invalidSortingCriteriaErrorMessage);
        }
    }

    public void isValidSortingOrder(String sortingOrder) {
        if (!validSortingOrders.contains(sortingOrder.trim().toUpperCase())) {
            throw new IllegalArgumentException(invalidSortingOrderErrorMessage);
        }
    }

    public void isValidMaxAndMinHeight(Double maxHeight, Double minHeight) {
        if (maxHeight < minHeight) {
            throw new IllegalArgumentException(invalidMaxMinHeightErrorMessage);
        }
    }
}
