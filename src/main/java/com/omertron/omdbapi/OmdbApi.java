/*
 *      Copyright (c) 2013 Stuart Boston
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
import com.omertron.omdbapi.tools.ApiHttpClient;
import com.omertron.omdbapi.tools.OmdbUrlBuilder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

/**
 * The main class for the OMDB API
 *
 * TODO: Add proxy/timeout support for the local http client
 *
 * @author stuart.boston
 */
public class OmdbApi {

    private static final Logger LOG = LoggerFactory.getLogger(OmdbApi.class);
    private static final int DEFAULT_YEAR = 0;
    private CommonHttpClient httpClient;
    private boolean tomatoes = Boolean.FALSE;
    private PlotType plotLength = PlotType.getDefault();
    private String callback = "";
    // Jackson JSON configuration
    private static ObjectMapper mapper = new ObjectMapper();

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Create an instance of the API with the default HTTP Client
     */
    public OmdbApi() {
        this.httpClient = new ApiHttpClient();
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
     *
     * @param defaultLongPlot
     */
    public void setLongPlot() {
        this.plotLength = PlotType.LONG;
    }

    /**
     * Return the short plot automatically
     *
     * @param defaultLongPlot
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
     * @param callbackParameter
     */
    public void setCallback(String callback) {
        this.callback = callback;
    }
    //</editor-fold>

    private String requestWebPage(URL url) throws OMDBException {
        String webpage;

        LOG.trace("Requesting: {}", url.toString());
        try {
            HttpGet httpGet = new HttpGet(url.toURI());
            httpGet.addHeader("accept", "application/json");
            webpage = httpClient.requestContent(httpGet);
        } catch (URISyntaxException ex) {
            throw new OMDBException(OMDBException.OMDBExceptionType.CONNECTION_ERROR, null, ex);
        } catch (IOException ex) {
            throw new OMDBException(OMDBException.OMDBExceptionType.CONNECTION_ERROR, null, ex);
        } catch (RuntimeException ex) {
            throw new OMDBException(OMDBException.OMDBExceptionType.HTTP_503_ERROR, "Service Unavailable", ex);
        }

        return webpage;
    }

    /**
     * Get a list of movies using the movie title.
     *
     * @param title
     */
    public String search(String title) throws OMDBException {
        return search(title, DEFAULT_YEAR);
    }

    /**
     * Get a list of movies using the movie title and year.
     *
     * @param title
     */
    public String search(String title, int year) throws OMDBException {
        URL url = new OmdbUrlBuilder().setSearch(title).setYear(year).createUrl();

        return url.toString();
    }

    /**
     * Get movie information using only the title or IMDB ID
     *
     * @param query
     * @return
     */
    public OmdbVideoFull movieInfo(String query) throws OMDBException {
        return movieInfo(query, DEFAULT_YEAR, plotLength, tomatoes);
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
        OmdbVideoFull video;
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

        String jsonData;

        // Get the JSON
        jsonData = requestWebPage(url);

        LOG.info("JSON: {}", jsonData); // XXX DEBUG

        // Process the JSON into an object
        try {
            video = mapper.readValue(jsonData, OmdbVideoFull.class);
            LOG.info("Video: {}", video.toString());

        } catch (IOException ex) {
            throw new OMDBException(OMDBException.OMDBExceptionType.MAPPING_FAILED, jsonData, ex);
        }

        return video;
    }
}
