/*
 *      Copyright (c) 2013 Stuart Boston
 *
 *      This file is part of OMDB API.
 *
 *      OMDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      OMDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheMovieDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.omdbapi;

import com.omertron.omdbapi.emumerations.PlotType;
import com.omertron.omdbapi.tools.OmdbUrlBuilder;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;

public class OmdbApi {

    private static final Logger LOG = LoggerFactory.getLogger(OmdbApi.class);
    private CommonHttpClient httpClient;
    private static boolean tomatoes = Boolean.FALSE;
    private static PlotType plotLength = PlotType.getDefault();
    private static String callback = "";
    private static final int DEFAULT_YEAR = 0;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Create an instance of the API with the default HTTP Client
     */
    public OmdbApi() {
        this.httpClient = null;
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
    public static boolean isTomatoes() {
        return tomatoes;
    }

    /**
     * Add rotten tomatoes data automatically to each request.
     *
     * TRUE will add the data, FALSE will not.
     *
     * @param defaultTomatoes
     */
    public static void setTomatoes(boolean defaultTomatoes) {
        OmdbApi.tomatoes = defaultTomatoes;
    }

    /**
     * Is a long plot returned by default?
     *
     * TRUE for the long plot, FALSE for short.
     *
     * @return
     */
    public static String getPlotLength() {
        return plotLength.toString();
    }

    /**
     * Return the long plot automatically
     *
     * @param defaultLongPlot
     */
    public static void setLongPlot() {
        OmdbApi.plotLength = PlotType.LONG;
    }

    /**
     * Return the short plot automatically
     *
     * @param defaultLongPlot
     */
    public static void setShortPlot() {
        OmdbApi.plotLength = PlotType.SHORT;
    }

    /**
     * Return the current callback parameter added to each request
     *
     * @return
     */
    public static String getCallback() {
        return callback;
    }

    /**
     * Set the callback parameter to be added to each request
     *
     * @param callbackParameter
     */
    public static void setCallback(String callback) {
        OmdbApi.callback = callback;
    }
    //</editor-fold>

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
    public String movieInfo(String query) throws OMDBException {
        return movieInfo(query, DEFAULT_YEAR, plotLength, tomatoes);
    }

    public String movieInfo(String query, int year, PlotType plotType, boolean tomatoes) throws OMDBException {
        URL url;
        OmdbUrlBuilder base = new OmdbUrlBuilder();

        if (StringUtils.startsWith(query, "tt")) {
            // IMDB Query
            base = base.setImdb(query);
        } else {
            // Title query
            base = base.setTitle(query);
        }

        url = base.createUrl();

        return url.toString();
    }
}
