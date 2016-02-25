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

import java.util.EnumSet;
import org.apache.commons.lang3.StringUtils;

/**
 * Parameters for use in the URL
 *
 * @author Stuart
 */
public enum Param {

    /**
     * Title to search for.
     */
    SEARCH("s="),
    /**
     * A valid IMDb ID.
     */
    IMDB("i="),
    /**
     * Title to search for.
     */
    TITLE("t="),
    /**
     * Year of release.
     */
    YEAR("y="),
    /**
     * The data type to return.
     */
    DATA("r="),
    /**
     * Type of result to return.
     */
    RESULT("type="),
    /**
     * Return short or full plot.
     */
    PLOT("plot="),
    /**
     * JSONP callback name.
     */
    CALLBACK("callback="),
    /**
     * Include Rotten Tomatoes ratings.
     */
    TOMATOES("tomatoes="),
    /**
     * API version.
     */
    VERSION("v=");

    private final String value;

    private Param(String value) {
        this.value = value;
    }

    /**
     * Get the URL parameter to use
     *
     * @return
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Convert a string into an Enum type
     *
     * @param value
     * @return
     */
    public static Param fromString(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (final Param param : EnumSet.allOf(Param.class)) {
                if (value.equalsIgnoreCase(param.value)) {
                    return param;
                }
            }
        }

        // We've not found the type!
        throw new IllegalArgumentException("Value '" + value + "' not recognised");
    }

}
