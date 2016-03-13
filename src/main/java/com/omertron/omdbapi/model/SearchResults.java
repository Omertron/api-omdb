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
package com.omertron.omdbapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SearchResults extends AbstractJsonMapping {

    @JsonProperty("Search")
    private List<OmdbVideoBasic> results;
    @JsonProperty("totalResults")
    private int totalResults;

    public List<OmdbVideoBasic> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setResults(List<OmdbVideoBasic> results) {
        if (results == null || results.isEmpty()) {
            // looks like an error or no results
            setResponse(false);
            setError("No results returned");
        } else {
            setResponse(true);
            setError(results.size() + " results returned");
            this.results = results;
        }
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
