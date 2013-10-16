/*
 *      Copyright (c) 2004-2013 YAMJ Members
 *      http://code.google.com/p/moviejukebox/people/list
 *
 *      This file is part of the Yet Another Media Jukebox (YAMJ).
 *
 *      The YAMJ is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      YAMJ is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with the YAMJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 *      Web: http://code.google.com/p/moviejukebox/
 *
 */
package com.omertron.omdbapi;

import com.omertron.omdbapi.emumerations.PlotType;
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

    public OmdbApiTest() {
    }

    @BeforeClass
    public static void setUpClass() {
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
     * Test of isTomatoes method, of class OmdbApi.
     */
    @Test
    public void testIsTomatoes() {
        LOG.info("isTomatoes");
        boolean expResult = false;
        boolean result = OmdbApi.isTomatoes();
        assertEquals("Default Rotten Tomatoes has changed", expResult, result);
    }

    /**
     * Test of setTomatoes method, of class OmdbApi.
     */
    @Test
    public void testSetTomatoes() {
        LOG.info("setTomatoes");
        boolean defaultTomatoes = false;
        OmdbApi.setTomatoes(defaultTomatoes);
        assertEquals("Failed to set rotten tomatoes default (" + defaultTomatoes + ")", defaultTomatoes, OmdbApi.isTomatoes());
        defaultTomatoes = true;
        OmdbApi.setTomatoes(defaultTomatoes);
        assertEquals("Failed to set rotten tomatoes default (" + defaultTomatoes + ")", defaultTomatoes, OmdbApi.isTomatoes());
    }

    /**
     * Test of getPlotLength method, of class OmdbApi.
     */
    @Test
    public void testGetPlotLength() {
        LOG.info("getPlotLength");
        String expResult = "";
        String result = OmdbApi.getPlotLength();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLongPlot method, of class OmdbApi.
     */
    @Test
    public void testSetLongPlot() {
        LOG.info("setLongPlot");
        OmdbApi.setLongPlot();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setShortPlot method, of class OmdbApi.
     */
    @Test
    public void testSetShortPlot() {
        LOG.info("setShortPlot");
        OmdbApi.setShortPlot();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCallback method, of class OmdbApi.
     */
    @Test
    public void testGetCallback() {
        LOG.info("getCallback");
        String expResult = "";
        String result = OmdbApi.getCallback();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCallback method, of class OmdbApi.
     */
    @Test
    public void testSetCallback() {
        LOG.info("setCallback");
        String callback = "";
        OmdbApi.setCallback(callback);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of search method, of class OmdbApi.
     */
    @Test
    public void testSearch_String() throws Exception {
        LOG.info("search");
        String title = "";
        OmdbApi instance = new OmdbApi();
        String expResult = "";
        String result = instance.search(title);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of search method, of class OmdbApi.
     */
    @Test
    public void testSearch_String_int() throws Exception {
        LOG.info("search");
        String title = "";
        int year = 0;
        OmdbApi instance = new OmdbApi();
        String expResult = "";
        String result = instance.search(title, year);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of movieInfo method, of class OmdbApi.
     */
    @Test
    public void testMovieInfo_String() throws Exception {
        LOG.info("movieInfo");
        String query = "";
        OmdbApi instance = new OmdbApi();
        String expResult = "";
        String result = instance.movieInfo(query);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of movieInfo method, of class OmdbApi.
     */
    @Test
    public void testMovieInfo_4args() throws Exception {
        LOG.info("movieInfo");
        String query = "";
        int year = 0;
        PlotType plotType = null;
        boolean tomatoes = false;
        OmdbApi instance = new OmdbApi();
        String expResult = "";
        String result = instance.movieInfo(query, year, plotType, tomatoes);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}