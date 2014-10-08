/*
 *      Copyright (c) 2013-2014 Stuart Boston
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
    private static final int STATUS_OK = 200;

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
        if (statusCode != STATUS_OK) {
            throw new IOException("Unexpected status " + statusCode + " for uri " + httpGet.getURI());
        }

        if (charset == null) {
            // use UTF8 char set if none charset given
            return readContent(response, UTF8_CHARSET);
        }
        // use given charset
        return readContent(response, charset);
    }
}