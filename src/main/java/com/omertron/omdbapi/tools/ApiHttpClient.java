/*
 *      Copyright (c) 2004-2013 YAMJ Members
 *      https://github.com/organizations/YAMJ/teams
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
 *      Web: https://github.com/YAMJ/yamj-v3
 *
 */
package com.omertron.omdbapi.tools;

import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.yamj.api.common.http.DefaultPoolingHttpClient;

public class ApiHttpClient extends DefaultPoolingHttpClient {

    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    public ApiHttpClient() {
        this(null, null);
    }

    public ApiHttpClient(ClientConnectionManager conman) {
        this(conman, null);
    }

    public ApiHttpClient(HttpParams params) {
        this(null, params);
    }

    public ApiHttpClient(ClientConnectionManager connectionManager, HttpParams httpParams) {
        super(connectionManager, httpParams);
    }

    @Override
    public String requestContent(HttpGet httpGet, Charset charset) throws IOException {
        HttpResponse response = execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new RuntimeException("Unexpected status " + statusCode + " for uri " + httpGet.getURI());
        }

        if (charset == null) {
            // use UTF8 char set if none charset given
            return readContent(response, UTF8_CHARSET);
        }
        // use given charset
        return readContent(response, charset);
    }
}