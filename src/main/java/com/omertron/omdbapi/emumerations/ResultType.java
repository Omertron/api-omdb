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
package com.omertron.omdbapi.emumerations;

public enum ResultType {

    /**
     * Movie results
     */
    MOVIE,
    /**
     * Series results
     */
    SERIES,
    /**
     * Episode results
     */
    EPISODE,
    /**
     * Default to all result types
     */
    ALL;
    private static final ResultType DEFAULT = ResultType.ALL;

    /**
     * Is the supplied ResultType the default
     *
     * @param resultType
     * @return
     */
    public static boolean isDefault(ResultType resultType) {
        return resultType.equals(DEFAULT);
    }

    /**
     * Get the default ResultType
     *
     * @return
     */
    public static ResultType getDefault() {
        return DEFAULT;
    }
}
