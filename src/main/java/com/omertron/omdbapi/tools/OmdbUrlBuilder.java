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
package com.omertron.omdbapi.tools;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.emumerations.PlotType;
import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.exception.ApiExceptionType;

public class OmdbUrlBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(OmdbUrlBuilder.class);
    private static final String BASE_URL = "http://www.omdbapi.com/";
    private static final String DELIMITER_FIRST = "?";
    private static final String DELIMITER_SUBSEQUENT = "&";

    private OmdbUrlBuilder() {
        throw new UnsupportedOperationException("Class cannot be initialised");
    }

    /**
     * Create the String representation of the URL for passed parameters
     *
     * @param params The parameters to use to create the URL
     * @return String URL
     * @throws OMDBException
     */
    public static String create(final OmdbParameters params) throws OMDBException {
        StringBuilder sb = new StringBuilder(BASE_URL);

        sb.append(DELIMITER_FIRST);
        if (params.has(Param.SEARCH)) {
            String search = (String) params.get(Param.SEARCH);
            sb.append(Param.SEARCH.getValue()).append(search.replace(" ", "+"));
        } else if (params.has(Param.IMDB)) {
            sb.append(Param.IMDB.getValue()).append(params.get(Param.IMDB));
        } else if (params.has(Param.TITLE)) {
            String title = (String) params.get(Param.TITLE);
            sb.append(Param.TITLE.getValue()).append(title.replace(" ", "+"));
        } else {
            throw new OMDBException(ApiExceptionType.INVALID_URL, "Must include a search or ID");
        }

        // Append the year
        appendParam(params, Param.YEAR, sb);

        // Append the plot requirement
        if (params.has(Param.PLOT)
                && (PlotType) params.get(Param.PLOT) != PlotType.getDefault()) {
            appendParam(params, Param.PLOT, sb);
        }

        // Append the tomatoes requirement
        appendParam(params, Param.TOMATOES, sb);

        // Append the Type
        appendParam(params, Param.RESULT, sb);

        // Append the JSON request - This is not used by this API
        appendParam(params, Param.DATA, sb);

        // Append the version
        appendParam(params, Param.VERSION, sb);

        // Append the callback function - This is not used by this API
        appendParam(params, Param.CALLBACK, sb);

        LOG.trace("Created URL: {}", sb.toString());
        return sb.toString();
    }

    /**
     * Create URL for the passed parameters
     *
     * @param params The parameters to use to create the URL
     * @return String URL
     * @throws OMDBException
     */
    public static URL createUrl(final OmdbParameters params) throws OMDBException {
        return generateUrl(create(params));
    }

    /**
     * Append a parameter and value to the URL line
     *
     * @param params The parameter list
     * @param key The parameter to add
     * @param sb The StringBuilder instance to use
     */
    private static void appendParam(final OmdbParameters params, final Param key, StringBuilder sb) {
        if (params.has(key)) {
            sb.append(DELIMITER_SUBSEQUENT).append(key.getValue()).append(params.get(key));
        }
    }

    /**
     * Generate a URL object from a String URL
     *
     * @param url
     * @return
     * @throws OMDBException
     */
    public static URL generateUrl(final String url) throws OMDBException {
        try {
            return new URL(url);
        } catch (MalformedURLException ex) {
            throw new OMDBException(ApiExceptionType.INVALID_URL, "Failed to create URL", url, ex);
        }
    }
}
