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

public class OMDBException extends Exception {

    private static final long serialVersionUID = 1L;

    public enum OMDBExceptionType {
        /*
         * Unknown error occured
         */
        UNKNOWN_CAUSE,
        /*
         * URL is invalid
         */
        INVALID_URL,
        /*
         * Page not found
         */
        HTTP_404_ERROR,
        /*
         * The movie id was not found
         */
        MOVIE_NOT_FOUND,
        /*
         * Mapping failed from target to internal onbjects
         */
        MAPPING_FAILED,
        /*
         * Error connecting to the service
         */
        CONNECTION_ERROR,
        /*
         * Image was invalid
         */
        INVALID_IMAGE,
        /*
         * Autorisation rejected
         */
        AUTHORISATION_FAILURE,
        /*
         * Service Unavailable, usually temporary
         */
        HTTP_503_ERROR;
    }

    private final OMDBExceptionType exceptionType;
    private final String response;

    public OMDBException(final OMDBExceptionType exceptionType, final String response) {
        super();
        this.exceptionType = exceptionType;
        this.response = response;
    }

    public OMDBException(final OMDBExceptionType exceptionType, final String response, final Throwable cause) {
        super(cause);
        this.exceptionType = exceptionType;
        this.response = response;
    }

    public OMDBExceptionType getExceptionType() {
        return exceptionType;
    }

    public String getResponse() {
        return response;
    }
}
