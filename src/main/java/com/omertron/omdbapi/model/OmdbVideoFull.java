/*
 *      Copyright (c) 2013-2015 Stuart Boston
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

public class OmdbVideoFull extends OmdbVideoBasic {

    @JsonProperty("Rated")
    private String rated = "";
    @JsonProperty("Released")
    private String released = "";
    @JsonProperty("Runtime")
    private String runtime = "";
    @JsonProperty("Genre")
    private String genre = "";
    @JsonProperty("Director")
    private String director = "";
    @JsonProperty("Writer")
    private String writer = "";
    @JsonProperty("Actors")
    private String actors = "";
    @JsonProperty("Plot")
    private String plot = "";
    @JsonProperty("Poster")
    private String poster = "";
    @JsonProperty("imdbRating")
    private String imdbRating = "";
    @JsonProperty("imdbVotes")
    private String imdbVotes = "";
    // Rotten Tomatoes fields
    @JsonProperty("tomatoMeter")
    private String tomatoMeter = "";
    @JsonProperty("tomatoImage")
    private String tomatoImage = "";
    @JsonProperty("tomatoRating")
    private String tomatoRating = "";
    @JsonProperty("tomatoReviews")
    private String tomatoReviews = "";
    @JsonProperty("tomatoFresh")
    private String tomatoFresh = "";
    @JsonProperty("tomatoRotten")
    private String tomatoRotten = "";
    @JsonProperty("tomatoConsensus")
    private String tomatoConsensus = "";
    @JsonProperty("tomatoUserMeter")
    private String tomatoUserMeter = "";
    @JsonProperty("tomatoUserRating")
    private String tomatoUserRating = "";
    @JsonProperty("tomatoUserReviews")
    private String tomatoUserReviews = "";
    @JsonProperty("DVD")
    private String tomatoDvd = "";
    @JsonProperty("BoxOffice")
    private String tomatoBoxOffice = "";
    @JsonProperty("Production")
    private String tomatoProduction = "";
    @JsonProperty("Website")
    private String tomatoWebsite = "";

    //<editor-fold defaultstate="collapsed" desc="Getter Methods">
    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getPoster() {
        return poster;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public String getTomatoMeter() {
        return tomatoMeter;
    }

    public String getTomatoImage() {
        return tomatoImage;
    }

    public String getTomatoRating() {
        return tomatoRating;
    }

    public String getTomatoReviews() {
        return tomatoReviews;
    }

    public String getTomatoFresh() {
        return tomatoFresh;
    }

    public String getTomatoRotten() {
        return tomatoRotten;
    }

    public String getTomatoConsensus() {
        return tomatoConsensus;
    }

    public String getTomatoUserMeter() {
        return tomatoUserMeter;
    }

    public String getTomatoUserRating() {
        return tomatoUserRating;
    }

    public String getTomatoUserReviews() {
        return tomatoUserReviews;
    }

    public String getTomatoDvd() {
        return tomatoDvd;
    }

    public String getTomatoBoxOffice() {
        return tomatoBoxOffice;
    }

    public String getTomatoProduction() {
        return tomatoProduction;
    }

    public String getTomatoWebsite() {
        return tomatoWebsite;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter Methods">
    public void setRated(String rated) {
        this.rated = rated;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public void setTomatoMeter(String tomatoMeter) {
        this.tomatoMeter = tomatoMeter;
    }

    public void setTomatoImage(String tomatoImage) {
        this.tomatoImage = tomatoImage;
    }

    public void setTomatoRating(String tomatoRating) {
        this.tomatoRating = tomatoRating;
    }

    public void setTomatoReviews(String tomatoReviews) {
        this.tomatoReviews = tomatoReviews;
    }

    public void setTomatoFresh(String tomatoFresh) {
        this.tomatoFresh = tomatoFresh;
    }

    public void setTomatoRotten(String tomatoRotten) {
        this.tomatoRotten = tomatoRotten;
    }

    public void setTomatoConsensus(String tomatoConsensus) {
        this.tomatoConsensus = tomatoConsensus;
    }

    public void setTomatoUserMeter(String tomatoUserMeter) {
        this.tomatoUserMeter = tomatoUserMeter;
    }

    public void setTomatoUserRating(String tomatoUserRating) {
        this.tomatoUserRating = tomatoUserRating;
    }

    public void setTomatoUserReviews(String tomatoUserReviews) {
        this.tomatoUserReviews = tomatoUserReviews;
    }

    public void setTomatoDvd(String tomatoDvd) {
        this.tomatoDvd = tomatoDvd;
    }

    public void setTomatoBoxOffice(String tomatoBoxOffice) {
        this.tomatoBoxOffice = tomatoBoxOffice;
    }

    public void setTomatoProduction(String tomatoProduction) {
        this.tomatoProduction = tomatoProduction;
    }

    public void setTomatoWebsite(String tomatoWebsite) {
        this.tomatoWebsite = tomatoWebsite;
    }
    //</editor-fold>
}
