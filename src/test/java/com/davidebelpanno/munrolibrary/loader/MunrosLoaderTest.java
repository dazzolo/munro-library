package com.davidebelpanno.munrolibrary.loader;

import static com.davidebelpanno.munrolibrary.utils.Constants.MUNRO_CATEGORY;
import static com.davidebelpanno.munrolibrary.utils.Constants.TOP_CATEGORY;
import static org.junit.jupiter.api.Assertions.*;

import com.davidebelpanno.munrolibrary.model.MunroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

class MunrosLoaderTest {

    @InjectMocks
    MunrosLoader testee;

    @Mock
    private MunroRepository repository;

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
        MockitoAnnotations.initMocks(this);
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

    @Test
    void shouldLoadDatabase() throws Exception {
        ReflectionTestUtils.setField(testee, "dataFile", "/test_munros.csv");
        testee.run();
    }

    @Test
    void shouldHandleEmptyDataFile() throws Exception {
        ReflectionTestUtils.setField(testee, "dataFile", "/empty-data-file.csv");
        testee.run();
    }

    @Test
    void shouldIgnoreFileFirstLine() throws Exception {
        ReflectionTestUtils.setField(testee, "dataFile", "/invalid-first-line-file.csv");
        testee.run();
    }

    @Test
    void shouldThrowExceptionWhenNoDataFile() {
        ReflectionTestUtils.setField(testee, "dataFile", "/fileNotFound.csv");
        assertThrows(Exception.class, () -> testee.run());
    }

    @Test
    void shouldThrowExceptionWhenInvalidDataFile() {
        ReflectionTestUtils.setField(testee, "dataFile", "/invalid-data-file.csv");
        assertThrows(Exception.class, () -> testee.run());
    }
}