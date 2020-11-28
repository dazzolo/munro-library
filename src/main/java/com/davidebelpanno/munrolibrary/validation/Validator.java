package com.davidebelpanno.munrolibrary.validation;

import java.util.Arrays;
import java.util.List;

public class Validator {

        private static final List<String> VALID_CATEGORIES = Arrays.asList("TOP", "MUN");
        private static final List<String> VALID_SORTING_ORDERS = Arrays.asList("ASC", "DESC");
        private static final List<String> VALID_SORTING_CRITERIA = Arrays.asList("NAME", "HEIGHT");
        public static final String INVALID_CATEGORY_ERROR_MESSAGE = "Invalid category";
        public static final String INVALID_SORTING_CRITERIA_ERROR_MESSAGE = "Invalid sorting criteria";
        public static final String INVALID_SORTING_ORDER_ERROR_MESSAGE = "Invalid sorting order";
        public static final String INVALID_MAX_MIN_HEIGHT_ERROR_MESSAGE = "maxHeight must be higher than minHeight";

    public static void isValidCategory(String category) {
        if (category != null && !category.trim().isEmpty() && !VALID_CATEGORIES.contains(category.trim().toUpperCase())) {
            throw new IllegalArgumentException(INVALID_CATEGORY_ERROR_MESSAGE);
        }
    }

    public static void isValidSortingCriteria(String sortingCriteria) {
        if (!VALID_SORTING_CRITERIA.contains(sortingCriteria.trim().toUpperCase())) {
            throw new IllegalArgumentException(INVALID_SORTING_CRITERIA_ERROR_MESSAGE);
        }
    }

    public static void isValidSortingOrder(String sortingOrder) {
        if (!VALID_SORTING_ORDERS.contains(sortingOrder.trim().toUpperCase())) {
            throw new IllegalArgumentException(INVALID_SORTING_ORDER_ERROR_MESSAGE);
        }
    }

    public static void isValidMaxAndMinHeight(Double maxHeight, Double minHeight) {
        if (maxHeight < minHeight) {
            throw new IllegalArgumentException(INVALID_MAX_MIN_HEIGHT_ERROR_MESSAGE);
        }
    }
}
