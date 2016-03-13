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
import java.io.Serializable;

public class OmdbVideoBasic extends AbstractJsonMapping implements Serializable {

    @JsonProperty("Title")
    private String title = "";
    @JsonProperty("Year")
    private String year = "";
    @JsonProperty("imdbID")
    private String imdbID = "";
    @JsonProperty("Type")
    private String type = "";
    @JsonProperty("Poster")
    private String poster = "";

    //<editor-fold defaultstate="collapsed" desc="Getter Methods">
    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return type;
    }

    public String getPoster() {
        return poster;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter Methods">
    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
    //</editor-fold>
}
