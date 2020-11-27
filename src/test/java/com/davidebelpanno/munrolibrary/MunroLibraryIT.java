package com.davidebelpanno.munrolibrary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MunroLibraryIT {

    @LocalServerPort
    private int port;

    private static final String SCHEME = "http";
    private static final String HOST = "localhost";
    private static final String PATH = "/munros";
    private static final String CATEGORY_PARAM_NAME = "category";
    private static final String SORTING_CRITERIA_PARAM_NAME = "sortingCriteria";
    private static final String SORTING_ORDER_PARAM_NAME = "sortingOrder";
    private static final String MAX_RESULTS_PARAM_NAME = "maxResults";
    private static final String MAX_HEIGHT_PARAM_NAME = "maxHeight";
    private static final String MIN_HEIGHT_PARAM_NAME = "minHeight";

    @Test
    void shouldAcceptRequestWithAllFilters() throws URISyntaxException, IOException, InterruptedException {
        final String queryString = queryParam(CATEGORY_PARAM_NAME, "top")
                + queryParam(SORTING_CRITERIA_PARAM_NAME, "name")
                + queryParam(SORTING_ORDER_PARAM_NAME, "desc")
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
        final String queryString = queryParam(CATEGORY_PARAM_NAME, "MUN");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldFilterMunroTops() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(CATEGORY_PARAM_NAME, "TOP");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldReturn422IfInvalidFilter() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(CATEGORY_PARAM_NAME, "invalidFilter");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(422, response.statusCode());
    }

    @Test
    void shouldSortByAscendingName() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(SORTING_CRITERIA_PARAM_NAME, "name")
                + queryParam(SORTING_ORDER_PARAM_NAME, "asc");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldSortByDescendingName() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(SORTING_CRITERIA_PARAM_NAME, "name")
                + queryParam(SORTING_ORDER_PARAM_NAME, "desc");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldSortByAscendingHeight() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(SORTING_CRITERIA_PARAM_NAME, "height")
                + queryParam(SORTING_ORDER_PARAM_NAME, "asc");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldSortByDescendingHeight() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(SORTING_CRITERIA_PARAM_NAME, "height")
                + queryParam(SORTING_ORDER_PARAM_NAME, "desc");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldIgnoreSortingIfNoCriteriaButOrderSpecified() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(SORTING_ORDER_PARAM_NAME, "asc");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldDefaultToAscendingIfNoSortingOrderSpecified() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(SORTING_CRITERIA_PARAM_NAME, "name");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldReturn422IfInvalidSortingCriteria() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(SORTING_CRITERIA_PARAM_NAME, "invalidCriteria");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(422, response.statusCode());
    }

    @Test
    void shouldReturn422IfInvalidSortingOrder() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(SORTING_CRITERIA_PARAM_NAME, "name")
                + queryParam(SORTING_ORDER_PARAM_NAME, "invalidOrder");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(422, response.statusCode());
    }

    @Test
    void shouldIgnoreInvalidSortingOrderIfNoCriteria() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(SORTING_ORDER_PARAM_NAME, "invalidOrder");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldReturnLessThanSpecifiedMaxResults() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(MAX_RESULTS_PARAM_NAME, "10");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldFilterByMaxiHeight() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(MAX_HEIGHT_PARAM_NAME, "1050");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldFilterByMinHeight() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(MIN_HEIGHT_PARAM_NAME, "1000");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldFilterByMinAndMaxHeight() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(MIN_HEIGHT_PARAM_NAME, "1000")
                + queryParam(MAX_HEIGHT_PARAM_NAME, "1050");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldReturn422IfInvalidMaxHeight() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(MAX_HEIGHT_PARAM_NAME, "invalidHeight");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(422, response.statusCode());
    }

    @Test
    void shouldReturn422IfInvalidMinHeight() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(MIN_HEIGHT_PARAM_NAME, "invalidHeight");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(422, response.statusCode());
    }

    @Test
    void shouldReturn422WhenMinHeightHigherThanMaxHeight() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(MIN_HEIGHT_PARAM_NAME, "1000")
                + queryParam(MAX_HEIGHT_PARAM_NAME, "900");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(422, response.statusCode());
    }

    @Test
    void shouldReturn204IfNoResults() throws IOException, InterruptedException, URISyntaxException {
        final String queryString = queryParam(MIN_HEIGHT_PARAM_NAME, "10");
        HttpResponse response = sendRequest(getURI(queryString));
        assertEquals(204, response.statusCode());
    }

    @Test
    void shouldReturn404WhenPathInvalid() throws IOException, InterruptedException, URISyntaxException {
        HttpResponse response = sendRequest(getURIWithPath("", "/invalidPath"));
        assertEquals(404, response.statusCode());
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
}
