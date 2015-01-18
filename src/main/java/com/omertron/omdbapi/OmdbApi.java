/*
 *      Copyright (c) 2013-2015 Stuart Boston
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
import com.omertron.omdbapi.emumerations.PlotType;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.tools.OmdbUrlBuilder;
import com.omertron.omdbapi.wrapper.WrapperSearch;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.exception.ApiExceptionType;
import org.yamj.api.common.http.CommonHttpClient;
import org.yamj.api.common.http.DefaultPoolingHttpClient;
import org.yamj.api.common.http.DigestedResponse;

/**
 * The main class for the OMDB API
 *
 * TODO: Add proxy/timeout support for the local http client
 *
 * @author stuart.boston
 */
public class OmdbApi {

    private static final Logger LOG = LoggerFactory.getLogger(OmdbApi.class);
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final int DEFAULT_YEAR = 0;
    private final CommonHttpClient httpClient;
    private boolean tomatoes = Boolean.FALSE;
    private PlotType plotLength = PlotType.getDefault();
    private String callback = "";
    // Jackson JSON configuration
    private static ObjectMapper mapper = new ObjectMapper();
    // HTTP Status codes
    private static final int HTTP_STATUS_300 = 300;
    private static final int HTTP_STATUS_500 = 500;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Create an instance of the API with the default HTTP Client
     */
    public OmdbApi() {
        this.httpClient = new DefaultPoolingHttpClient();
    }

    /**
     * Create an instance of the API with the provided HTTP Client
     *
     * @param httpClient
     */
    public OmdbApi(CommonHttpClient httpClient) {
        this.httpClient = httpClient;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Configuration">
    /**
     * Is Rotten Tomatoes data added automatically?
     *
     * @return TRUE will add the data, FALSE will not.
     */
    public boolean isTomatoes() {
        return this.tomatoes;
    }

    /**
     * Add rotten tomatoes data automatically to each request.
     *
     * TRUE will add the data, FALSE will not.
     *
     * @param defaultTomatoes
     */
    public void setTomatoes(boolean defaultTomatoes) {
        this.tomatoes = defaultTomatoes;
    }

    /**
     * Is a long plot returned by default?
     *
     * TRUE for the long plot, FALSE for short.
     *
     * @return
     */
    public PlotType getPlotLength() {
        return this.plotLength;
    }

    /**
     * Return the long plot automatically
     */
    public void setLongPlot() {
        this.plotLength = PlotType.LONG;
    }

    /**
     * Return the short plot automatically
     */
    public void setShortPlot() {
        this.plotLength = PlotType.SHORT;
    }

    /**
     * Return the current callback parameter added to each request
     *
     * @return
     */
    public String getCallback() {
        return this.callback;
    }

    /**
     * Set the callback parameter to be added to each request
     *
     * @param callback
     */
    public void setCallback(String callback) {
        this.callback = callback;
    }
    //</editor-fold>

    private String requestWebPage(URL url) throws OMDBException {
        LOG.trace("Requesting: {}", url.toString());
        try {
            HttpGet httpGet = new HttpGet(url.toURI());
            httpGet.addHeader("accept", "application/json");
            DigestedResponse response = httpClient.requestContent(httpGet, Charset.forName(DEFAULT_CHARSET));

            if (response.getStatusCode() >= HTTP_STATUS_500) {
                throw new OMDBException(ApiExceptionType.HTTP_503_ERROR, "Service Unavailable");
            } else if (response.getStatusCode() >= HTTP_STATUS_300) {
                throw new OMDBException(ApiExceptionType.HTTP_404_ERROR, "Page Unavailable");
            }

            return response.getContent();
        } catch (URISyntaxException ex) {
            throw new OMDBException(ApiExceptionType.CONNECTION_ERROR, "", url, ex);
        } catch (IOException ex) {
            throw new OMDBException(ApiExceptionType.CONNECTION_ERROR, "", url, ex);
        }
    }

    /**
     * Get a list of movies using the movie title.
     *
     * @param title
     * @return
     * @throws OMDBException
     */
    public WrapperSearch search(String title) throws OMDBException {
        return search(title, DEFAULT_YEAR);
    }

    /**
     * Get a list of movies using the movie title and year.
     *
     * @param title
     * @param year
     * @return
     * @throws OMDBException
     */
    public WrapperSearch search(String title, int year) throws OMDBException {
        WrapperSearch resultList;
        URL url = new OmdbUrlBuilder().setSearch(title).setYear(year).createUrl();

        // Get the JSON
        String jsonData = requestWebPage(url);

        // Process the JSON into an object
        try {
            resultList = mapper.readValue(jsonData, WrapperSearch.class);
        } catch (IOException ex) {
            throw new OMDBException(ApiExceptionType.MAPPING_FAILED, jsonData, 0, url, ex);
        }

        return resultList;
    }

    /**
     * Get movie information using only the title or IMDB ID
     *
     * @param query
     * @return
     * @throws OMDBException
     */
    public OmdbVideoFull movieInfo(String query) throws OMDBException {
        return movieInfo(query, DEFAULT_YEAR, plotLength, tomatoes);
    }

    /**
     * Get movie information using only the title or IMDB ID
     *
     * @param query
     * @param year
     * @return
     * @throws OMDBException
     */
    public OmdbVideoFull movieInfo(String query, int year) throws OMDBException {
        return movieInfo(query, year, plotLength, tomatoes);
    }

    /**
     * Get movie information using the title or IMDB ID and with specific plot length & RT data
     *
     * @param query
     * @param year
     * @param plotType
     * @param tomatoes
     * @return
     * @throws OMDBException
     */
    public OmdbVideoFull movieInfo(String query, int year, PlotType plotType, boolean tomatoes) throws OMDBException {
        OmdbVideoFull result;
        URL url;
        OmdbUrlBuilder base = new OmdbUrlBuilder();

        if (StringUtils.startsWith(query, "tt")) {
            // IMDB Query
            base = base.setImdb(query);
        } else {
            // Title query
            base = base.setTitle(query);
        }
        // Set the rest of the parameters
        base = base.setYear(year).setPlot(plotType).setTomatoes(tomatoes);

        // Get the URL
        url = base.createUrl();

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
