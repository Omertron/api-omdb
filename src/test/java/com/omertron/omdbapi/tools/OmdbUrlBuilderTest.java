/*
 *      Copyright (c) 2013-2015 Stuart Boston
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

import com.omertron.omdbapi.TestLogger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.CommonHttpClient;
import org.yamj.api.common.http.DefaultPoolingHttpClient;
import org.yamj.api.common.http.DigestedResponse;

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
    public void testCreateUrl1() throws Exception {
        LOG.info("createUrl1");

        String expResult = "http://www.omdbapi.com/?s=Star+Wars&y=1977";
        String result = new OmdbUrlBuilder().setSearch("Star Wars").setYear(1977).create();

        assertEquals("Failed text search", expResult, result);

        CommonHttpClient x = new DefaultPoolingHttpClient();
        DigestedResponse webpage = x.requestContent(result);
        LOG.info(webpage.getContent());
    }

    @Test
    public void testCreateUrl2() throws Exception {
        LOG.info("createUrl2");

        String expResult = "http://www.omdbapi.com/?t=True+Grit&y=1969";
        String result = new OmdbUrlBuilder().setTitle("True Grit").setYear(1969).create();

        assertEquals("Failed title search", expResult, result);
    }

    @Test
    public void testCreateUrl3() throws Exception {
        LOG.info("createUrl3");

        String expResult = "http://www.omdbapi.com/?i=tt1285016";
        String result = new OmdbUrlBuilder().setImdb("tt1285016").create();

        assertEquals("Failed Search test", expResult, result);
    }
}