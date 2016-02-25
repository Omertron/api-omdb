/*
 *      Copyright (c) 2013-2016 Stuart Boston
 *
 *      This file is part of the OMDB API.
 *
 *      The OMDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      The OMDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with the OMDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.omdbapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.model.SearchResults;
import com.omertron.omdbapi.tools.OmdbBuilder;
import com.omertron.omdbapi.tools.OmdbParameters;
import com.omertron.omdbapi.tools.OmdbUrlBuilder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.exception.ApiExceptionType;
import org.yamj.api.common.http.DigestedResponse;
import org.yamj.api.common.http.DigestedResponseReader;
import org.yamj.api.common.http.SimpleHttpClientBuilder;
import org.yamj.api.common.http.UserAgentSelector;

/**
 * The main class for the OMDB API
 *
 * @author stuart.boston
 */
public class OmdbApi {

    private static final Logger LOG = LoggerFactory.getLogger(OmdbApi.class);
    private final HttpClient httpClient;
    // Jackson JSON configuration
    private static ObjectMapper mapper = new ObjectMapper();
    // HTTP Status codes
    private static final int HTTP_STATUS_300 = 300;
    private static final int HTTP_STATUS_500 = 500;
    private static final String DEFAULT_CHARSET = "UTF-8";
    private final Charset charset;

    /**
     * Create an instance of the API with a default HTTP Client
     */
    public OmdbApi() {
        this(new SimpleHttpClientBuilder().build());
    }

    /**
     * Create an instance of the API with the provided HTTP Client
     *
     * @param httpClient
     */
    public OmdbApi(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.charset = Charset.forName(DEFAULT_CHARSET);
    }

    private String requestWebPage(URL url) throws OMDBException {
        LOG.trace("Requesting: {}", url.toString());
        try {
            final HttpGet httpGet = new HttpGet(url.toURI());
            httpGet.addHeader("accept", "application/json");
            httpGet.addHeader(HTTP.USER_AGENT, UserAgentSelector.randomUserAgent());

            final DigestedResponse response = DigestedResponseReader.requestContent(httpClient, httpGet, charset);

            if (response.getStatusCode() >= HTTP_STATUS_500) {
                throw new OMDBException(ApiExceptionType.HTTP_503_ERROR, response.getContent(), response.getStatusCode(), url);
            } else if (response.getStatusCode() >= HTTP_STATUS_300) {
                throw new OMDBException(ApiExceptionType.HTTP_404_ERROR, response.getContent(), response.getStatusCode(), url);
            }

            return response.getContent();
        } catch (URISyntaxException ex) {
            throw new OMDBException(ApiExceptionType.INVALID_URL, "Invalid URL", url, ex);
        } catch (IOException ex) {
            throw new OMDBException(ApiExceptionType.CONNECTION_ERROR, "Error retrieving URL", url, ex);
        }
    }

    /**
     * Execute a search using the passed parameters.
     *
     * To create the parameters use the OmdbBuilder builder:
     * <p>
     * E.G.: build(new OmdbBuilder(term).setYear(1900).build());
     *
     * @param searchParams Use the OmdbBuilder to build the parameters
     * @return
     * @throws com.omertron.omdbapi.OMDBException
     */
    public SearchResults search(OmdbParameters searchParams) throws OMDBException {
        SearchResults resultList;

        String url = OmdbUrlBuilder.create(searchParams);
        LOG.info("URL: {}", url);

        // Get the JSON
        String jsonData = requestWebPage(OmdbUrlBuilder.generateUrl(url));

        // Process the JSON into an object
        try {
            resultList = mapper.readValue(jsonData, SearchResults.class);
        } catch (IOException ex) {
            throw new OMDBException(ApiExceptionType.MAPPING_FAILED, jsonData, 0, url, ex);
        }

        return resultList;
    }

    /**
     * Get a list of movies using the movie title.
     *
     * @param title
     * @return
     * @throws OMDBException
     */
    public SearchResults search(String title) throws OMDBException {
        return search(new OmdbBuilder()
                .setSearchTerm(title)
                .build());
    }

    /**
     * Get a list of movies using the movie title and year.
     *
     * @param title
     * @param year
     * @return
     * @throws OMDBException
     */
    public SearchResults search(String title, int year) throws OMDBException {
        return search(new OmdbBuilder()
                .setSearchTerm(title)
                .setYear(year)
                .build());
    }

    /**
     * Get movie information using the supplied parameters
     *
     * @param parameters parameters to use to retrieve the information
     * @return
     * @throws OMDBException
     */
    public OmdbVideoFull getInfo(OmdbParameters parameters) throws OMDBException {
        OmdbVideoFull result;
        URL url = OmdbUrlBuilder.createUrl(parameters);

        // Get the JSON
        String jsonData = requestWebPage(url);

        // Process the JSON into an object
        try {
            result = mapper.readValue(jsonData, OmdbVideoFull.class);

            if (result == null || !result.isResponse()) {
                throw new OMDBException(ApiExceptionType.ID_NOT_FOUND, result == null ? "No data returned" : result.getError());
            }
        } catch (IOException ex) {
            throw new OMDBException(ApiExceptionType.MAPPING_FAILED, jsonData, 0, url, ex);
        }

        return result;
    }
}
