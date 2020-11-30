package com.davidebelpanno.munrolibrary;

import static com.davidebelpanno.munrolibrary.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MunroLibraryIT {

    @LocalServerPort
    private int port;

    @Value("${app.invalid.category.message}")
    private String invalidCategoryErrorMessage;

    @Value("${app.invalid.sorting.criteria.error.message}")
    private String invalidSortingCriteriaErrorMessage;

    @Value("${app.invalid.sorting.order.error.message}")
    private String invalidSortingOrderErrorMessage;

    @Value("${app.invalid.max.min.height.error.message}")
    private String invalidMaxMinHeightErrorMessage;

    private static final String SCHEME = "http";
    private static final String HOST = "localhost";
    private static final String PATH = "/munros";
    private static final String CATEGORY_PARAM_NAME = "category";
    private static final String SORTING_CRITERIA_PARAM_NAME = "sortingCriteria";
    private static final String SORTING_ORDER_PARAM_NAME = "sortingOrder";
    private static final String MAX_RESULTS_PARAM_NAME = "maxResults";
    private static final String MAX_HEIGHT_PARAM_NAME = "maxHeight";
    private static final String MIN_HEIGHT_PARAM_NAME = "minHeight";
    private static final String RESOURCES_PATH = "src/test/resources/";
    private static final String EXPECTED_RESULTS_ORDERED_BY_NAME_ASC_FILE = "expected-results-ordered-by-name-asc.json";
    private static final String EXPECTED_RESULTS_ORDERED_BY_NAME_DESC_FILE = "expected-results-ordered-by-name-desc.json";
    private static final String EXPECTED_RESULTS_ORDERED_BY_HEIGHT_ASC_FILE = "expected-results-ordered-by-height-asc.json";
    private static final String EXPECTED_RESULTS_ORDERED_BY_HEIGHT_DESC_FILE = "expected-results-ordered-by-height-desc.json";
    private static final String EXPECTED_RESULTS_BY_MIN_AND_MAX_HEIGHT = "expected-results-by-min-and-max-height.json";

    @Test
    void shouldAcceptRequestWithAllFilters() throws URISyntaxException, IOException, InterruptedException {
        final String queryString = queryParam(CATEGORY_PARAM_NAME, TOP_CATEGORY)
                + queryParam(SORTING_CRITERIA_PARAM_NAME, NAME_SORTING_CRITERIA)
                + queryParam(SORTING_ORDER_PARAM_NAME, DESCENDING_ORDER)
                + queryParam(MAX_RESULTS_PARAM_NAME, "15")
                + queryParam(MAX_HEIGHT_PARAM_NAME, "1100")
                + queryParam(MIN_HEIGHT_PARAM_NAME, "900");

        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldAcceptEmptyQueryString() throws IOException, InterruptedException, URISyntaxException {
        HttpResponse response = sendRequest(getURI(""));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldFilterMunros() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(CATEGORY_PARAM_NAME, MUNRO_CATEGORY);
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
        assertFalse(response.body().toString().contains(TOP_CATEGORY));
    }

    @Test
    void shouldFilterMunroTops() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(CATEGORY_PARAM_NAME, TOP_CATEGORY);
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
        assertFalse(response.body().toString().contains(MUNRO_CATEGORY));
    }

    @Test
    void shouldReturn400IfInvalidFilter() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(CATEGORY_PARAM_NAME, "invalidFilter");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(400, response.statusCode());
        assertEquals(invalidCategoryErrorMessage, response.body());
    }

    @Test
    void shouldSortByAscendingName() throws IOException, InterruptedException, URISyntaxException, JSONException {
        final String queryString = queryParam(SORTING_CRITERIA_PARAM_NAME, NAME_SORTING_CRITERIA)
                + queryParam(SORTING_ORDER_PARAM_NAME, ASCENDING_ORDER)
                + queryParam(MAX_RESULTS_PARAM_NAME, "2");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
        JSONArray jsonResponse = new JSONArray(response.body().toString());
        JSONArray expectedJsonResponse = getExpectedResult(EXPECTED_RESULTS_ORDERED_BY_NAME_ASC_FILE);
        assertEquals(expectedJsonResponse.toString(), jsonResponse.toString());
    }

    @Test
    void shouldSortByDescendingName() throws IOException, InterruptedException, URISyntaxException, JSONException {
        final String queryString = queryParam(SORTING_CRITERIA_PARAM_NAME, NAME_SORTING_CRITERIA)
                + queryParam(SORTING_ORDER_PARAM_NAME, DESCENDING_ORDER)
                + queryParam(MAX_RESULTS_PARAM_NAME, "10");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
        JSONArray jsonResponse = new JSONArray(response.body().toString());
        JSONArray expectedJsonResponse = getExpectedResult(EXPECTED_RESULTS_ORDERED_BY_NAME_DESC_FILE);
        assertEquals(expectedJsonResponse.toString(), jsonResponse.toString());
    }

    @Test
    void shouldSortByAscendingHeight() throws IOException, InterruptedException, URISyntaxException, JSONException {
        final String queryString = queryParam(SORTING_CRITERIA_PARAM_NAME, HEIGHT_SORTING_CRITERIA)
                + queryParam(SORTING_ORDER_PARAM_NAME, ASCENDING_ORDER)
                + queryParam(MAX_RESULTS_PARAM_NAME, "5");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
        JSONArray jsonResponse = new JSONArray(response.body().toString());
        JSONArray expectedJsonResponse = getExpectedResult(EXPECTED_RESULTS_ORDERED_BY_HEIGHT_ASC_FILE);
        assertEquals(expectedJsonResponse.toString(), jsonResponse.toString());
    }

    @Test
    void shouldSortByDescendingHeight() throws IOException, InterruptedException, URISyntaxException, JSONException {
        final String queryString = queryParam(SORTING_CRITERIA_PARAM_NAME, HEIGHT_SORTING_CRITERIA)
                + queryParam(SORTING_ORDER_PARAM_NAME, DESCENDING_ORDER)
                + queryParam(MAX_RESULTS_PARAM_NAME, "5");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
        JSONArray jsonResponse = new JSONArray(response.body().toString());
        JSONArray expectedJsonResponse = getExpectedResult(EXPECTED_RESULTS_ORDERED_BY_HEIGHT_DESC_FILE);
        assertEquals(expectedJsonResponse.toString(), jsonResponse.toString());
    }

    @Test
    void shouldDefaultToNameIfNoSortingCriteriaSpecified()
            throws IOException, InterruptedException, URISyntaxException, JSONException {
        final String queryString = queryParam(SORTING_ORDER_PARAM_NAME, DESCENDING_ORDER)
                + queryParam(MAX_RESULTS_PARAM_NAME, "10");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
        JSONArray jsonResponse = new JSONArray(response.body().toString());
        JSONArray expectedJsonResponse = getExpectedResult(EXPECTED_RESULTS_ORDERED_BY_NAME_DESC_FILE);
        assertEquals(expectedJsonResponse.toString(), jsonResponse.toString());
    }

    @Test
    void shouldDefaultToAscendingIfNoSortingOrderSpecified()
            throws IOException, InterruptedException, URISyntaxException, JSONException {
        final String queryString = queryParam(SORTING_CRITERIA_PARAM_NAME, HEIGHT_SORTING_CRITERIA)
                + queryParam(MAX_RESULTS_PARAM_NAME, "5");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
        JSONArray jsonResponse = new JSONArray(response.body().toString());
        JSONArray expectedJsonResponse = getExpectedResult(EXPECTED_RESULTS_ORDERED_BY_HEIGHT_ASC_FILE);
        assertEquals(expectedJsonResponse.toString(), jsonResponse.toString());
    }

    @Test
    void shouldReturn400IfInvalidSortingCriteria() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(SORTING_CRITERIA_PARAM_NAME, "invalidCriteria");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(400, response.statusCode());
        assertEquals(invalidSortingCriteriaErrorMessage, response.body());
    }

    @Test
    void shouldReturn400IfInvalidSortingOrder() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(SORTING_CRITERIA_PARAM_NAME, NAME_SORTING_CRITERIA)
                + queryParam(SORTING_ORDER_PARAM_NAME, "invalidOrder");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(400, response.statusCode());
        assertEquals(invalidSortingOrderErrorMessage, response.body());
    }

    @Test
    void shouldReturnLessThanSpecifiedMaxResults() throws IOException, InterruptedException, URISyntaxException, JSONException {
        final String queryString = queryParam(MAX_RESULTS_PARAM_NAME, "10");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
        JSONArray jsonArray = new JSONArray(response.body().toString());
        assertTrue(jsonArray.length() <= 10);
    }

    @Test
    void shouldFilterByMaxiHeight() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(MAX_HEIGHT_PARAM_NAME, "500");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(204, response.statusCode());
    }

    @Test
    void shouldFilterByMinHeight() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(MIN_HEIGHT_PARAM_NAME, "1500");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(204, response.statusCode());
    }

    @Test
    void shouldFilterByMinAndMaxHeight() throws IOException, InterruptedException, URISyntaxException, JSONException {
        final String queryString = queryParam(MIN_HEIGHT_PARAM_NAME, "1040")
                + queryParam(MAX_HEIGHT_PARAM_NAME, "1050");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
        JSONArray jsonResponse = new JSONArray(response.body().toString());
        JSONArray expectedJsonResponse = getExpectedResult(EXPECTED_RESULTS_BY_MIN_AND_MAX_HEIGHT);
        assertEquals(expectedJsonResponse.toString(), jsonResponse.toString());
    }

    @Test
    void shouldReturn400IfInvalidMaxHeight() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(MAX_HEIGHT_PARAM_NAME, "invalidHeight");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(400, response.statusCode());
    }

    @Test
    void shouldReturn400IfInvalidMinHeight() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(MIN_HEIGHT_PARAM_NAME, "invalidHeight");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(400, response.statusCode());
    }

    @Test
    void shouldReturn400WhenMinHeightHigherThanMaxHeight() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(MIN_HEIGHT_PARAM_NAME, "1000")
                + queryParam(MAX_HEIGHT_PARAM_NAME, "900");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(400, response.statusCode());
        assertEquals(invalidMaxMinHeightErrorMessage, response.body());
    }

    @Test
    void shouldReturn204IfNoResults() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(MIN_HEIGHT_PARAM_NAME, "3000");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(204, response.statusCode());
    }

    @Test
    void shouldReturn404WhenPathInvalid() throws IOException, InterruptedException, URISyntaxException {
        HttpResponse response = sendRequest(getURIWithPath("", "/invalidPath"));
        assertEquals(404, response.statusCode());
    }

    @Test
    void shouldNotReturnMunrosWithNoCategory() throws IOException, InterruptedException, URISyntaxException {
        HttpResponse response = sendRequest(getURI(""));
        assertEquals(200, response.statusCode());
        assertFalse(response.body().toString().contains("\"category\": \"\""));
    }

    private URI getURI(String queryString) throws URISyntaxException {
        return new URI(SCHEME, null, HOST, port, PATH, queryString, "");
    }

    private URI getURIWithPath(String queryString, String path) throws URISyntaxException {
        return new URI(SCHEME, null, HOST, port, path, queryString, "");
    }

    private String queryParam(String name, String value) {
        return name + "=" + value + "&";
    }

    private HttpResponse sendRequest(URI uri) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private JSONArray getExpectedResult(String expectedResultFile) throws IOException, JSONException {
        String expectedResultString = Files.readString(Path.of(RESOURCES_PATH + expectedResultFile));
        return new JSONArray(expectedResultString);
    }
}
