package com.davidebelpanno.munrolibrary.validation;

import com.davidebelpanno.munrolibrary.controller.MunroController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Validator {

    org.slf4j.Logger logger = LoggerFactory.getLogger(MunroController.class);

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
        if (category == null || !validCategories.contains(category.trim().toUpperCase())) {
            logger.error("ERROR: " + invalidCategoryErrorMessage);
            throw new IllegalArgumentException(invalidCategoryErrorMessage);
        }
    }

    public void isValidSortingCriteria(String sortingCriteria) {
        if (!validSortingCriteria.contains(sortingCriteria.trim().toUpperCase())) {
            logger.error("ERROR: " + invalidSortingCriteriaErrorMessage);
            throw new IllegalArgumentException(invalidSortingCriteriaErrorMessage);
        }
    }

    public void isValidSortingOrder(String sortingOrder) {
        if (!validSortingOrders.contains(sortingOrder.trim().toUpperCase())) {
            logger.error("ERROR: " + invalidSortingOrderErrorMessage);
            throw new IllegalArgumentException(invalidSortingOrderErrorMessage);
        }
    }

    public void isValidMaxAndMinHeight(Double maxHeight, Double minHeight) {
        if (maxHeight < minHeight) {
            logger.error("ERROR: " + invalidMaxMinHeightErrorMessage);
            throw new IllegalArgumentException(invalidMaxMinHeightErrorMessage);
        }
    }
}
