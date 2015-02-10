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
package com.omertron.omdbapi;

import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.model.SearchResults;
import com.omertron.omdbapi.tools.OmdbBuilder;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stuart
 */
public class OmdbApiTest {

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

    @Test
    public void testSearch() throws OMDBException {
        SearchResults response = omdb.search(new OmdbBuilder().setSearchTerm("Star Wars").setYear(1977).build());
        LOG.info(response.toString());
    }

    /**
     * Test of build method, of class OmdbApi.
     *
     * @throws OMDBException
     */
    @Ignore
    public void testSearch_String() throws OMDBException {
        LOG.info("Search - Title");
        String title = "Star Wars";
        SearchResults result = omdb.search(title);
        assertNotNull("Null search returned", result);
        assertTrue("Error response", result.isResponse());
        assertTrue("No records found", result.getResults().size() > 0);

        boolean found = false;
        for (OmdbVideoBasic movie : result.getResults()) {
            if (StringUtils.equals("tt0076759", movie.getImdbID())) {
                found = true;
                break;
            }
        }
        assertTrue("Movie not found in search results", found);
    }

    /**
     * Test of build method, of class OmdbApi.
     *
     * @throws OMDBException
     */
    @Ignore
    public void testSearch_String_int() throws OMDBException {
        LOG.info("Search - Title & Year");
        String title = "Star Wars";
        int year = 1977;
        SearchResults result = omdb.search(title, year);
        assertNotNull("Null search returned", result);
        assertTrue("Error response", result.isResponse());
        assertTrue("No records found", result.getResults().size() > 0);

        boolean found = false;
        for (OmdbVideoBasic movie : result.getResults()) {
            if (StringUtils.equals("tt0076759", movie.getImdbID())) {
                found = true;
                break;
            }
        }
        assertTrue("Movie not found in search results", found);
    }

    /**
     * Test of getInfo method, of class OmdbApi.
     *
     * @throws OMDBException
     */
    @Ignore
    public void testMovieInfo_ByName() throws OMDBException {
        LOG.info("movieInfo_ByName");
        String query = "Blade Runner";
        OmdbVideoFull result = omdb.getInfo(new OmdbBuilder().setTitle(query).build());
        assertEquals("Wrong movie returned", "tt0083658", result.getImdbID());
    }

    /**
     * Test of getInfo method, of class OmdbApi.
     *
     * @throws OMDBException
     */
    @Ignore
    public void testMovieInfo_ByTT() throws OMDBException {
        LOG.info("movieInfo_ByTT");
        String query = "tt0083658";
        OmdbVideoFull result = omdb.getInfo(new OmdbBuilder().setImdbId(query).build());
        assertEquals("Wrong movie returned", "Blade Runner", result.getTitle());
    }

    /**
     * Test of getInfo method, of class OmdbApi.
     *
     * @throws OMDBException
     */
    @Ignore
    public void testMovieInfo_4args() throws OMDBException {
        LOG.info("movieInfo - All args");

        OmdbVideoFull result = omdb.getInfo(new OmdbBuilder().setTitle("Blade Runner").setYear(1982).setPlotLong().setTomatoes(true).build());

        assertNotNull("Null object returned", result);
        assertTrue("Error with call", result.isResponse());
        assertEquals("Wrong video returned", "tt0083658", result.getImdbID());
        assertTrue("No RT data", StringUtils.isNotBlank(result.getTomatoConsensus()));

        try {
            result = omdb.getInfo(new OmdbBuilder().setTitle("Some movie that can't be found at all").build());
            assertFalse("What do you mean this was found?", result.isResponse());
        } catch (OMDBException ex) {
            assertNotNull("Null object returned", result);
        }
    }
}
