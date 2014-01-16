/*
 *      Copyright (c) 2013-2014 Stuart Boston
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
package com.omertron.omdbapi.tools;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.emumerations.PlotType;
import com.omertron.omdbapi.emumerations.ResponseType;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OmdbUrlBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(OmdbUrlBuilder.class);
    private static final String BASE_URL = "http://www.omdbapi.com/";
    private static final String DELIMITER_FIRST = "?";
    private static final String DELIMITER_SUBSEQUENT = "&";
    // Parameter keys
    private static final String PARAM_SEARCH = "s=";
    private static final String PARAM_IMDB = "i=";
    private static final String PARAM_TITLE = "t=";
    private static final String PARAM_YEAR = "y=";
    private static final String PARAM_RESPONSE = "r=";
    private static final String PARAM_PLOT = "plot=";
    private static final String PARAM_CALLBACK = "callback=";
    private static final String PARAM_TOMATOES = "tomatoes=";
    // Default values (if required)
    private static final boolean DEFAULT_TOMATOES = Boolean.FALSE;
    private static final int DEFAULT_YEAR = 1900;
    // Parameter values
    private String search = "";
    private String imdb = "";
    private String title = "";
    private int year = 0;
    private PlotType plot = PlotType.getDefault();
    private String callback = "";
    private boolean tomatoes = false;
    private ResponseType response = ResponseType.getDefault();

    public OmdbUrlBuilder() {
    }

    public OmdbUrlBuilder setSearch(String search) {
        this.search = search;
        return this;
    }

    public OmdbUrlBuilder setImdb(String imdb) {
        this.imdb = imdb;
        return this;
    }

    public OmdbUrlBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public OmdbUrlBuilder setYear(int year) {
        this.year = year;
        return this;
    }

    public OmdbUrlBuilder setPlot(PlotType plot) {
        this.plot = plot;
        return this;
    }

    public OmdbUrlBuilder setPlotShort() {
        this.plot = PlotType.SHORT;
        return this;
    }

    public OmdbUrlBuilder setPlotLong() {
        this.plot = PlotType.LONG;
        return this;
    }

    public OmdbUrlBuilder setCallback(String callback) {
        this.callback = callback;
        return this;
    }

    public OmdbUrlBuilder setTomatoes(boolean tomatoes) {
        this.tomatoes = tomatoes;
        return this;
    }

    public OmdbUrlBuilder setResponse(ResponseType response) {
        this.response = response;
        return this;
    }

    public OmdbUrlBuilder setResponseJson() {
        this.response = ResponseType.JSON;
        return this;
    }

    public OmdbUrlBuilder setResponseXml() {
        this.response = ResponseType.XML;
        return this;
    }

    /**
     * Create the String representation of the URL
     *
     * @return
     * @throws OMDBException
     */
    public String create() throws OMDBException {
        StringBuilder sb = new StringBuilder(BASE_URL);

        sb.append(DELIMITER_FIRST);
        if (StringUtils.isNotBlank(search)) {
            sb.append(PARAM_SEARCH).append(search.replace(" ", "+"));
        } else if (StringUtils.isNotBlank(imdb)) {
            sb.append(PARAM_IMDB).append(imdb);
        } else if (StringUtils.isNotBlank(title)) {
            sb.append(PARAM_TITLE).append(title.replace(" ", "+"));
        } else {
            throw new OMDBException(OMDBException.OMDBExceptionType.INVALID_URL, "Must include a search or ID");
        }

        if (year > DEFAULT_YEAR) {
            sb.append(DELIMITER_SUBSEQUENT).append(PARAM_YEAR).append(year);
        }

        // Append the plot requirement
        if (plot != PlotType.getDefault()) {
            sb.append(DELIMITER_SUBSEQUENT).append(PARAM_PLOT).append(plot);
        }

        // Append the tomatoes requirement
        if (tomatoes != DEFAULT_TOMATOES) {
            sb.append(DELIMITER_SUBSEQUENT).append(PARAM_TOMATOES).append(tomatoes);
        }

        // Append the JSON request
        if (response != ResponseType.getDefault()) {
            sb.append(DELIMITER_SUBSEQUENT).append(PARAM_RESPONSE);
        }

        if (StringUtils.isNotBlank(callback)) {
            sb.append(DELIMITER_SUBSEQUENT).append(PARAM_CALLBACK).append(callback);
        }

        LOG.debug("Created URL: {}", sb.toString());
        return sb.toString();
    }

    /**
     * Create a URL object
     *
     * @return
     * @throws OMDBException
     */
    public URL createUrl() throws OMDBException {
        return createUrl(create());
    }

    /**
     * Create a URL object from a String URL
     *
     * @param url
     * @return
     * @throws OMDBException
     */
    public URL createUrl(String url) throws OMDBException {
        URL omdbUrl = null;
        try {
            omdbUrl = new URL(url);
        } catch (MalformedURLException ex) {
            throw new OMDBException(OMDBException.OMDBExceptionType.INVALID_URL, "Failed to create URL from '" + url + "'", ex);
        }
        return omdbUrl;
    }
}
