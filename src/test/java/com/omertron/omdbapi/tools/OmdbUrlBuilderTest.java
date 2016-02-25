/*
 *      Copyright (c) 2013-2016 Stuart Boston
 *
 *      This file is part of the OMDB API.
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
 *      along with the OMDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.omdbapi.tools;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.TestLogger;
import com.omertron.omdbapi.emumerations.ResultType;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.exception.ApiExceptionType;

/**
 *
 * @author stuart.boston
 */
public class OmdbUrlBuilderTest {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(OmdbUrlBuilderTest.class);

    public OmdbUrlBuilderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        TestLogger.Configure();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateSearch() throws OMDBException {
        LOG.info("Create Search");

        String expResult = "http://www.omdbapi.com/?s=Star+Wars&y=1977";
        String result = OmdbUrlBuilder.create(new OmdbBuilder().setSearchTerm("Star Wars").setYear(1977).build());
        assertEquals("Failed text search", expResult, result);
    }

    @Test
    public void testCreateTitle() throws OMDBException {
        LOG.info("Create Title");

        String expResult = "http://www.omdbapi.com/?t=True+Grit&y=1969";
        String result = OmdbUrlBuilder.create(new OmdbBuilder().setTitle("True Grit").setYear(1969).build());

        assertEquals("Failed title search", expResult, result);
    }

    @Test
    public void testCreateImdb() throws OMDBException {
        LOG.info("Create IMDB");

        String expResult = "http://www.omdbapi.com/?i=tt1285016";
        String result = OmdbUrlBuilder.create(new OmdbBuilder().setImdbId("tt1285016").build());

        assertEquals("Failed Search test", expResult, result);
    }

    @Test
    public void testResultType() throws OMDBException {
        LOG.info("Test ResultType");

        final String term = "Star Wars";
        final String base = "http://www.omdbapi.com/?s=Star+Wars";
        String expResult, result;

        for (ResultType rt : ResultType.values()) {
            LOG.info("Testing '{}' is {}the default", rt, ResultType.isDefault(rt) ? "" : "not ");
            if (ResultType.isDefault(rt)) {
                expResult = base;
            } else {
                expResult = base + "&type=" + rt.toString();
            }

            switch (rt) {
                case EPISODE:
                    result = OmdbUrlBuilder.create(new OmdbBuilder().setSearchTerm(term).setTypeEpisode().build());
                    break;
                case MOVIE:
                    result = OmdbUrlBuilder.create(new OmdbBuilder().setSearchTerm(term).setTypeMovie().build());
                    break;
                case SERIES:
                    result = OmdbUrlBuilder.create(new OmdbBuilder().setSearchTerm(term).setTypeSeries().build());
                    break;
                case ALL:
                    result = OmdbUrlBuilder.create(new OmdbBuilder().setSearchTerm(term).setResultType(rt).build());
                    break;
                default:
                    throw new OMDBException(ApiExceptionType.UNKNOWN_CAUSE, "Unknown Result Type: " + rt.toString());
            }

            assertEquals("Failed " + rt + " search test", expResult, result);
        }
    }
}
