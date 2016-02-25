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

public enum DataType {

    /**
     * Get the data as JSON format
     */
    JSON,
    /**
     * Get the data as XML format
     */
    XML;
    private static final DataType DEFAULT = DataType.JSON;

    /**
     * Is the supplied DataType the default
     *
     * @param dataType
     * @return
     */
    public static boolean isDefault(DataType dataType) {
        return dataType.equals(DEFAULT);
    }

    /**
     * Get the default DataType
     *
     * @return
     */
    public static DataType getDefault() {
        return DEFAULT;
    }
}
