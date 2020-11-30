package com.davidebelpanno.munrolibrary.loader;

import static com.davidebelpanno.munrolibrary.utils.Constants.MUNRO_CATEGORY;
import static com.davidebelpanno.munrolibrary.utils.Constants.TOP_CATEGORY;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MunrosLoaderTest {

    MunrosLoader testee;

    private String[] validMunro = { "name", TOP_CATEGORY, "1000", "gridRef" };
    private String[] invalidMunro = { "name", "", null };
    private String[] validMunroWithCategory = new String[28];
    private String[] validMunroTopWithCategory = new String[28];
    private String[] munroWithInvalidCategory = new String[28];

    @BeforeEach
    void setup() {
        validMunroWithCategory[27] = MUNRO_CATEGORY;
        validMunroTopWithCategory[27] = TOP_CATEGORY;
        munroWithInvalidCategory[27] = "invalidCategory";
        testee = new MunrosLoader();
    }

    @Test
    void getMunroInfoShouldReturnValueForValidData() {
        String info = testee.getMunroInfo(validMunro, 2);
        assertEquals("1000", info);
    }

    @Test
    void getMunroInfoShouldThrowExceptionForBlankValue() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> testee.getMunroInfo(invalidMunro, 1));
        assertEquals("CSV file is malformed", ex.getMessage());
    }

    @Test
    void getMunroInfoShouldThrowExceptionForNullData() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> testee.getMunroInfo(invalidMunro, 2));
        assertEquals("CSV file is malformed", ex.getMessage());
    }

    @Test
    void getMunroCategoryShouldReturnValueIfTop() {
        String category = testee.getMunroCategory(validMunroTopWithCategory);
        assertEquals(TOP_CATEGORY, category);
    }

    @Test
    void getMunroCategoryShouldReturnValueIfMunro() {
        String category = testee.getMunroCategory(validMunroWithCategory);
        assertEquals(MUNRO_CATEGORY, category);
    }

    @Test
    void getMunroCategoryShouldReturnEmptyStringIfIndexOutOfBoundException() {
        String category = testee.getMunroCategory(invalidMunro);
        assertEquals("", category);
    }

    @Test
    void getMunroCategoryShouldReturnEmptyStringIfInvalidValue() {
        String category = testee.getMunroCategory(munroWithInvalidCategory);
        assertEquals("", category);
    }
}