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
package com.omertron.omdbapi;

import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.model.SearchResults;
import com.omertron.omdbapi.tools.OmdbBuilder;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Stuart
 */
@Ignore //These tests are ignored until you add an APIKey
public class OmdbApiTest {

    private static final Logger LOG = LoggerFactory.getLogger(OmdbApiTest.class);
    private static final String apiKey = null; //SET ME
    private static final OmdbApi OMDB = new OmdbApi(apiKey);
    private static final List<TestID> IDS = new ArrayList<>();
    private static final String TESTING = "Testing: '{}'";

    @BeforeClass
    public static void setUpClass() {
        TestLogger.configure();

        IDS.add(new TestID("Blade Runner", 1982, "tt0083658"));
        IDS.add(new TestID("Mr. Robot", 2015, "tt4158110"));
        
        //GET AN APIKEY
        LOG.debug("You must set and APIKEY. Get it from http://www.omdbapi.com/apikey.aspx You could even make a donation etc!!");
        
    }
    
    

    @Test
    public void testSearch() throws OMDBException {
        SearchResults response = OMDB.search(new OmdbBuilder().setSearchTerm("Star Wars").setYear(1977).build());
        LOG.info(response.toString());
    }

    /**
     * Test of build method, of class OmdbApi.
     *
     * @throws OMDBException
     */
    @Test
    public void testSearchString() throws OMDBException {
        LOG.info("Search - Title");

        for (TestID test : IDS) {
            LOG.info(TESTING, test.getTitle());

            SearchResults result = OMDB.search(test.getTitle());
            validateSearch(result, test.getImdb());
        }
    }

    /**
     * Test of build method, of class OmdbApi.
     *
     * @throws OMDBException
     */
    @Test
    public void testSearchStringInt() throws OMDBException {
        LOG.info("Search - Title & Year");
        for (TestID test : IDS) {
            LOG.info(TESTING, test.getTitle());
            SearchResults result = OMDB.search(test.getTitle(), test.getYear());
            validateSearch(result, test.getImdb());
        }
    }

    private void validateSearch(SearchResults result, String imdb) {
        assertNotNull("Null search returned", result);
        assertTrue("Error response", result.isResponse());
        assertTrue("No records found", result.getResults().size() > 0);

        boolean found = false;
        for (OmdbVideoBasic movie : result.getResults()) {
            if (StringUtils.equals(imdb, movie.getImdbID())) {
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
    @Test
    public void testMovieInfoByName() throws OMDBException {
        LOG.info("movieInfo_ByName");

        for (TestID test : IDS) {
            LOG.info(TESTING, test.getTitle());
            OmdbVideoFull result = OMDB.getInfo(new OmdbBuilder().setTitle(test.getTitle()).build());
            assertEquals("Wrong movie returned", test.getImdb(), result.getImdbID());
        }
    }

    /**
     * Test of getInfo method, of class OmdbApi.
     *
     * @throws OMDBException
     */
    @Test
    public void testMovieInfoByTT() throws OMDBException {
        LOG.info("movieInfo_ByTT");

        for (TestID test : IDS) {
            LOG.info(TESTING, test.getTitle());
            OmdbVideoFull result = OMDB.getInfo(new OmdbBuilder().setImdbId(test.getImdb()).build());
            assertEquals("Wrong movie returned", test.getTitle(), result.getTitle());
        }
    }

    /**
     * Test of getInfo method, of class OmdbApi.
     *
     * @throws OMDBException
     */
    @Test
    public void testMovieInfo4args() throws OMDBException {
        LOG.info("movieInfo - All args");
        OmdbVideoFull result = null;

        for (TestID test : IDS) {
            LOG.info(TESTING, test.getTitle());

            result = OMDB.getInfo(new OmdbBuilder().setTitle(test.getTitle()).setYear(test.getYear()).setPlotLong().setTomatoes(true).build());

            assertNotNull("Null object returned", result);
            assertTrue("Error with call", result.isResponse());
            assertEquals("Wrong video returned", test.getImdb(), result.getImdbID());
            assertTrue("No RT data", StringUtils.isNotBlank(result.getTomatoConsensus()));
        }

        try {
            result = OMDB.getInfo(new OmdbBuilder().setTitle("Some movie that can't be found at all").build());
            assertFalse("What do you mean this was found?", result.isResponse());
        } catch (OMDBException ex) {
            LOG.info("Exception encountered: {}", ex.getMessage(), ex);
            assertNotNull("Null object returned", result);
        }
    }
}