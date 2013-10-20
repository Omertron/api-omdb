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

import com.omertron.omdbapi.emumerations.PlotType;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.wrapper.WrapperSearch;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stuart
 */
public class OmdbApiTest {
    // Logger

    private static final Logger LOG = LoggerFactory.getLogger(OmdbApiTest.class);
    private static final OmdbApi omdb = new OmdbApi();

    public OmdbApiTest() {
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

    /**
     * Test of Rotten Tomatoes methods, of class OmdbApi.
     */
    @Test
    public void testTomatoes() {
        LOG.info("testTomatoes");
        boolean defaultTomatoes = false;
        omdb.setTomatoes(defaultTomatoes);
        assertEquals("Failed to set rotten tomatoes default (" + defaultTomatoes + ")", defaultTomatoes, omdb.isTomatoes());

        defaultTomatoes = true;
        omdb.setTomatoes(defaultTomatoes);
        assertEquals("Failed to set rotten tomatoes default (" + defaultTomatoes + ")", defaultTomatoes, omdb.isTomatoes());
    }

    /**
     * Test of PlotType methods, of class OmdbApi.
     */
    @Test
    public void testPlotType() {
        LOG.info("testPlotType");
        omdb.setLongPlot();
        assertEquals("Failed to set long plot", PlotType.LONG, omdb.getPlotLength());

        omdb.setShortPlot();
        assertEquals("Failed to set short plot", PlotType.SHORT, omdb.getPlotLength());
    }

    /**
     * Test of Callback methods, of class OmdbApi.
     */
    @Test
    public void testCallback() {
        LOG.info("getCallback");

        String callback1 = "This is my callback?!?";
        String callback2 = "I need a better callback!!";

        omdb.setCallback(callback1);
        String result = omdb.getCallback();
        assertEquals("Callback was not set/read correctly", callback1, result);

        omdb.setCallback(callback2);
        result = omdb.getCallback();
        assertEquals("Callback was not set/read correctly", callback2, result);
    }

    /**
     * Test of search method, of class OmdbApi.
     */
    @Test
    public void testSearch_String() throws Exception {
        LOG.info("search");
        String title = "Star Wars";
        WrapperSearch result = omdb.search(title);
        assertNotNull("Null search returned", result);
        assertTrue("Error response", result.isResponse());
        assertTrue("No records found", result.getResults().size() > 0);
    }

    /**
     * Test of search method, of class OmdbApi.
     */
    @Test
    public void testSearch_String_int() throws Exception {
        LOG.info("search");
        String title = "Star Wars";
        int year = 1977;
        WrapperSearch result = omdb.search(title, year);
        assertNotNull("Null search returned", result);
        assertTrue("Error response", result.isResponse());
        assertTrue("No records found", result.getResults().size() > 0);

    }

    /**
     * Test of movieInfo method, of class OmdbApi.
     */
    @Test
    public void testMovieInfo_ByName() throws Exception {
        LOG.info("movieInfo_ByName");
        String query = "Blade Runner";
        OmdbVideoFull result = omdb.movieInfo(query);
        assertEquals("Wrong movie returned", "tt0083658", result.getImdbID());
    }

    /**
     * Test of movieInfo method, of class OmdbApi.
     */
    @Test
    public void testMovieInfo_ByTT() throws Exception {
        LOG.info("movieInfo_ByTT");
        String query = "tt0083658";
        OmdbVideoFull result = omdb.movieInfo(query);
        assertEquals("Wrong movie returned", "Blade Runner", result.getTitle());
    }

    /**
     * Test of movieInfo method, of class OmdbApi.
     */
    @Test
    public void testMovieInfo_4args() throws Exception {
        LOG.info("movieInfo");

        OmdbVideoFull result = omdb.movieInfo("Blade Runner", 1982, PlotType.LONG, true);
        assertNotNull("Null object returned", result);
        assertTrue("Error with call", result.isResponse());
        assertEquals("Wrong video returned", "tt0083658", result.getImdbID());
        assertTrue("No RT data", StringUtils.isNotBlank(result.getTomatoConsensus()));

        try {
            result = omdb.movieInfo("Some movie that can't be found at all", 0, PlotType.SHORT, false);
            assertFalse("What do you mean this was found?", result.isResponse());
        } catch (OMDBException ex) {
            assertNotNull("Null object returned", result);
        }
    }
}